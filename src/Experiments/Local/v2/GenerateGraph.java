package Experiments.Local.v2;

import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class GenerateGraph {

    public static void main(String[] args) {
        ArrayList<Integer> bc5 = getBitCountOf(5);
        ArrayList<String> ttlPerms = Permutation.makeAllPermutationsOf("12345");

        ArrayList<DecaState[]> graph = new ArrayList<>();
        for (Integer inp : bc5) {
            for (String ttl : ttlPerms) {
                DecaState state = new DecaState(inp, ttl);
                ArrayList<DecaState> nexts = state.nextIterations();
                for (DecaState next : nexts) {
                    graph.add(new DecaState[]{state,next});
                }
            }
        }
//        System.setOut(new PrintStream(
//                new OutputStream() {
//                    public void write(int b) { }
//                }));
        List<DecaState> path = Path.Main.run(graph);
        System.out.println(path);
    }

    public static ArrayList<Integer> getBitCountOf(final int b) {
        ArrayList<Integer> bitCount = new ArrayList<>();
        for (int i = 0; i < 1024; i++) {
            if (Integer.bitCount(i) != b) continue;
            bitCount.add(i);
        }
        return bitCount;
    }

    static public class Permutation
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

