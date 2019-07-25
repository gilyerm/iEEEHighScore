package Experiments.Local.v2;

import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
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

    private DecaState() {}

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

}
