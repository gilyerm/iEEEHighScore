package Experiments.Local.v3;

import Experiments.Local.DecaByte;
import Experiments.Local.v2.GenerateGraph;
import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class DecaState {

    private int num;
    private String bin;
    private String ttl;
    private String state;

    public DecaState(int num, String ttl) {
        if (ttl.length() != 5) throw new RuntimeException("wront ttl: "+ttl);
        if (num<0||num>=1024) throw new RuntimeException("bad num: "+num);
        int bc = Integer.bitCount(num);
        if (bc!=5) throw new RuntimeException("bad numcount: "+bc+"\t (num: "+num+")");
        this.num = num;
        this.bin = toBin(num,10);
        this.ttl = ttl;
        this.state = asState(bin,ttl);
    }
    public DecaState(String bin, String ttl) {
        if (ttl.length() != 5) throw new RuntimeException("wront ttl: "+ttl);
        if (bin.length()!=10) throw new RuntimeException("bad length: "+bin.length());
        this.num = Integer.parseInt(bin,2);
        this.bin = bin;
        this.ttl = ttl;
        this.state = asState(bin,ttl);
    }

    public DecaState(String state) {

        this.ttl = state.replaceAll("0","");
        this.state = state;

        this.bin = state.replaceAll("2", "1").replaceAll("3", "1")
                        .replaceAll("4", "1").replaceAll("5", "1");
        this.num = Integer.parseInt(bin,2);

    }


    public static String arrAsBin(@NotNull final boolean[] I) {
        return IntStream.range(0, I.length).mapToObj(idx -> I[idx])
                .map(b -> b ? "1" : "0").collect(Collectors.joining(""));
    }
    public static String toBin(@NotNull final int i,@NotNull final int len) {
        String s = Integer.toBinaryString(i);
        return Stream.generate(() -> "0").limit(len-s.length()).collect(Collectors.joining("")) + s;
    }

    private String asState(String bin, String ttl) {
        String s = new String(bin).replaceAll("1","#");
        for (char c : ttl.toCharArray())
            s = s.replaceFirst("#",c+"");
        return s;
    }
    public static void main(String[] args) {
        DecaState decaState = new DecaState("0000011111", "15342");
        System.out.println(decaState);
        ArrayList<DecaState> decaStates = decaState.nextIterations();
        System.out.println();
    }

    public ArrayList<DecaState> nextIterations() {
        ArrayList<DecaState> list = new ArrayList<>();
        int[] indexes = new int[5]; int i=0;
        char[] chars = this.state.toCharArray();
        for (int j = 0; j < chars.length; j++) {
            if (chars[j] == '0') {
                indexes[i++] = j;
            }
            else {
                chars[j]--;
            }
        }
        for (int j = 0; j < indexes.length; j++) {
            int zIdx = indexes[j];
            chars[zIdx] = '5';
            String t = new String(chars).replaceAll("0","");
            chars[zIdx] = '0';
            int deadIdx = this.state.indexOf("1");
            char[] binChars = this.bin.toCharArray();
            binChars[deadIdx] = '0';
            binChars[zIdx] = '1';
            String b = new String(binChars);
            list.add(new DecaState(b,t));

        }
//        System.out.println(list);
        return list;
    }

    public DecaState nextIteration(int index) {
        if (index<0 || index> DecaByte.SIZE) throw new RuntimeException("bad index request: "+index);
        if (this.state.charAt(index) != '0')
            throw new RuntimeException("index already on:"+index+"\t"+this.state);

        int[] indexes = new int[5];
        char[] chars = this.state.toCharArray();
        for (int j = 0; j < chars.length; j++) {
            if (chars[j] != '0')
                chars[j]--;
        }

        int zIdx = index;
        chars[zIdx] = '5';
        String t = new String(chars).replaceAll("0","");
        chars[zIdx] = '0';
        int deadIdx = this.state.indexOf("1");
        char[] binChars = this.bin.toCharArray();
        binChars[deadIdx] = '0';
        binChars[zIdx] = '1';
        String b = new String(binChars);

        return new DecaState(b,t);
    }

    public ArrayList<DecaState> prevIterations() {
        ArrayList<DecaState> list = new ArrayList<>();
        int[] indexes = new int[5]; int i=0, newIdx=-1;
        char[] chars = this.state.toCharArray();
        for (int j = 0; j < chars.length; j++) {
            if (chars[j] == '0') {
                indexes[i++] = j;
            }
            else if (chars[j] == '5') {
                newIdx = j;
                chars[j] = '0';
            }
            else {
                chars[j]++;
            }
        }
        for (int j = 0; j < indexes.length; j++) {
            int zIdx = indexes[j];
            chars[zIdx] = '1';
            String t = new String(chars).replaceAll("0","");
            chars[zIdx] = '0';
            char[] binChars = this.bin.toCharArray();
            binChars[newIdx] = '0';
            binChars[zIdx] = '1';
            String b = new String(binChars);
            list.add(new DecaState(b,t));
        }
//        System.out.println(list);
        return list;
    }


    public DecaByte asDecaByte() {
        return new DecaByte(this.num);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DecaState)) return false;

        DecaState decaState = (DecaState) o;

        return bin.equals(decaState.bin);

    }

    @Override
    public int hashCode() {
        return bin.hashCode();
    }

    @Override
    public String toString() {
        return state;
    }


    public boolean deepEquals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DecaState)) return false;

        DecaState decaState = (DecaState) o;

        if (num != decaState.num) return false;
        if (bin != null ? !bin.equals(decaState.bin) : decaState.bin != null) return false;
        if (ttl != null ? !ttl.equals(decaState.ttl) : decaState.ttl != null) return false;
        return state != null ? state.equals(decaState.state) : decaState.state == null;

    }

    private static ArrayList<String> allPerms =
            Permutation.makeAllPermutationsOf("12345");
    public static List<String> allPerms(){
        return allPerms;
    }

    static private class Permutation
    {
        public static ArrayList<String> makeAllPermutationsOf(String str)
        {
//            String str = "12345";
//            Permutation permutation = new Permutation();
//            ArrayList<String> permute = permute(str, 0, str.length() - 1);
//            System.out.println(permute);
            return permute(str, 0, str.length() - 1);
        }

        /**
         * permutation function
         * @param str string to calculate permutation for
         * @param l starting index
         * @param r end index
         */
        private static ArrayList<String> permute(String str, int l, int r)
        {
            ArrayList<String> strings = new ArrayList<>();

            if (l == r) {
                strings.add(str);
//                System.out.println(str);
            }
            else
            {
                for (int i = l; i <= r; i++)
                {
                    str = swap(str,l,i);
                    ArrayList<String> permute = permute(str, l + 1, r);
                    strings.addAll(permute);
                    str = swap(str,l,i);
                }
            }
            return strings;
        }

        /**
         * Swap Characters at position
         * @param a string value
         * @param i position 1
         * @param j position 2
         * @return swapped string
         */
        public static String swap(String a, int i, int j)
        {
            char temp;
            char[] charArray = a.toCharArray();
            temp = charArray[i] ;
            charArray[i] = charArray[j];
            charArray[j] = temp;
            return String.valueOf(charArray);
        }

    }

}
