package Experiments.Local.Exprs;

import Experiments.Local.DecaByte;
import Experiments.Local.v3.EditProgramV3;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static Experiments.Local.BaseProgram.toBin;
import static Experiments.Local.v3.EditProgramV3.solMain;

public class Exp12_1 {
    private static int circ=0;

    public static void main(String[] args) {
        List<HashSet<String>> stringsList = Stream
                .generate(() -> new LinkedHashSet<String>())
                .limit(7).collect(Collectors.toList());
//        Collection<String> strings=new HashSet<>();
        int c=0;
        NoSep(stringsList.get(c++));
        Sep_00(stringsList.get(c++));
        sep_0_0(stringsList.get(c++));
        sep_0__0(stringsList.get(c++));
        sep_0_0_0(stringsList.get(c++));
        sep_0_00(stringsList.get(c++));
        sep_0_0_0_0(stringsList.get(c++));

//        System.out.println(strings.size());
        System.out.println();
        System.out.println();
        stringsList.forEach(s-> System.out.println(s.size()+"\t"+s));
        System.out.println();
        System.out.println(stringsList.stream().map(HashSet::size).mapToInt(n->n).sum());

        System.out.println();
        stringsList.forEach(s-> {
                    List<Integer> l = s.stream().map(b -> Integer.parseInt(b, 2)).collect(Collectors.toList());
                    System.out.println(l.size()+"\t"+l);
                }
        );


        LinkedHashSet<String> allstrings = new LinkedHashSet<>();
        stringsList.forEach(s->allstrings.addAll(s));
        System.out.println();
        System.out.println(allstrings.size()+"\t"+allstrings);

        List<Integer> nums = allstrings.stream().map(b -> Integer.parseInt(b, 2)).collect(Collectors.toList());
        System.out.println(nums);

        ArrayList<Integer> numslist = new ArrayList<>(nums);
        ArrayList<Integer> allInputs = makeAll_Inputs(numslist);

        System.out.println();
        System.out.println(circ);

    }

    public static ArrayList<Integer> makeAll_Inputs(ArrayList<Integer> numslist) {
        int elseCnt = 0;
        ArrayList<Integer> allInputs = new ArrayList<>();
        int prev = 0;
        for (int i = 0; i < numslist.size(); prev = numslist.get(i++)) {
            Integer cur = numslist.get(i);
            if (bitDiff(prev, cur) == 2) { // 1 on 1 off
                allInputs.add(newBit(prev,cur));
            }
            else {
                elseCnt++;
                LinkedList<Integer> inputsToAdd = new LinkedList();
                List<Integer> next5andCur = numslist.subList(i, Math.min(i+6,numslist.size()));

                int badInx = -1;
                for (int j = 0; j < next5andCur.size()-1; j++)
                    if (bitDiff(next5andCur.get(j),next5andCur.get(j+1)) != 2) {
                        badInx = j; break;
                    }
                List<Integer> nextRelevant = next5andCur;
                if (badInx != -1) nextRelevant = next5andCur.subList(0,badInx);

                for (int j = 0; j < nextRelevant.size()-1; j++) {
                    inputsToAdd.add(deadBit(nextRelevant.get(j),nextRelevant.get(j+1)));
                }

                int takenCareOf = inputsToAdd.stream().mapToInt(x -> x).reduce(0, (a, b) -> a | b);
                if (cur != takenCareOf) {
                    List<Integer> others = listOfOtherInputs(cur, takenCareOf);
                    for (Integer otherBit : others) {
                        inputsToAdd.addLast(otherBit);
                    }
                }
                allInputs.addAll(inputsToAdd);

//                System.out.println((nextRelevant.size()-1) + ", "+inputsToAdd.size() + ", "+
//                        (nextRelevant.size()-1+inputsToAdd.size()));
            }
        }

        System.out.println();
        System.out.println(elseCnt);
        System.out.println("inputs:");
        System.out.println(allInputs.size()+"\t"+allInputs);
//        System.out.println(allInputs);


        List<String> collect = allInputs.stream().map(k -> toBin(k, 10)).collect(Collectors.toList());
        EditProgramV3.Helper h = solMain(collect);
        System.out.println(h.res);
        System.out.println(h.Chistory.stream().distinct().collect(Collectors.toList()));
        List<DecaByte> repeatedCs = new ArrayList<>(h.Chistory).stream()
                .filter(d -> {
                    return h.Chistory.stream().filter(x -> x.equals(d)).count() != 1;
                })
                .distinct()
                .collect(Collectors.toList());
        System.out.println(repeatedCs);

//    public static void main(String[] args) {
//
//        int[][] pairList = unique2Diff;
//        HashMap<int[], ArrayList<int[]>> map = new HashMap<>();
//        for (int[] f1 : pairList) {
//            for (int[] f2 : pairList) {
//                List<String> collect = Arrays.asList(f1[0], f1[1], 0,0, f2[0], f2[1]).stream()
//                        .map(k -> toBin(k, 10)).collect(Collectors.toList());
//                Helper h = solMain(collect);
//                if (h.indexs.size() > 2) {
////					count_2_0++;
////					System.out.printf("%s,%s \t=>\t %s\n",
////							Arrays.toString(f1), Arrays.toString(f2), indexs);
//                    map.putIfAbsent(f1, new ArrayList<>());
//                    map.get(f1).add(f2);
//                }
//            }
//        }


            return allInputs;
    }

    private static List<Integer> listOfOtherInputs(int cur, int takenCareOf) {
        ArrayList<Integer> othersList = new ArrayList<>();
        int others = cur^takenCareOf;
        for (int i=0, b=1; i < DecaByte.SIZE; i++, b<<=1) {
            if ((others & b) != 0)
                othersList.add(b);
        }
        return othersList;
    }

    public static int bitDiff(int x, int y) {
        return Integer.bitCount(x^y);
    }
    public static int newBit(int x, int y) {
        return (x^y) & y;
    }
    public static int deadBit(int x, int y) {
        return (x^y) & x;
    }


    public static void NoSep(Collection<String> strings) {
        DecaByte decaByte=new DecaByte(31);
        for (int i = 4; i <= 8; i++) {
            strings.add(decaByte.toStringAsBin());
            for (int j = 0; j < DecaByte.SIZE; j++) {
                decaByte.shiftLCirc(1); //circ++;
                strings.add(decaByte.toStringAsBin());
            }
            decaByte.swapBits(i,i+1);
        }
    }


    public static void Sep_00(Collection<String> strings) {

//        Set<String> strings1=new HashSet<>();
//
//        List<DecaByte> collect = Arrays.asList(79, 103, 115, 121).parallelStream().map(DecaByte::new).collect(Collectors.toList());
//        for (DecaByte decaByte : collect) {
//            for (int i = 0; i < DecaByte.SIZE; i++) {
//                strings1.add(decaByte.toStringAsBin());
//                decaByte.shiftLCirc(1);
//                strings1.add(decaByte.toStringAsBin());
//            }
//            System.out.println("----------");
//        }

        DecaByte decaByte=new DecaByte(79);

        for (int k = 0; k < 2; k++) {
            for (int i = 0; i < DecaByte.SIZE/2; i++) {
//                System.out.println("start= "+decaByte.toString());
                int stringRound = findStringRound(decaByte.toStringAsBin(), "000100")+3;
//                System.out.println(stringRound);
                int l=stringRound,r=l+6;
                l%=DecaByte.SIZE;
                r%=DecaByte.SIZE;
                for (int j = 0; j < 3; j++) {
                    strings.add(decaByte.toStringAsBin());
                    l=Math.floorMod(--l,DecaByte.SIZE);
                    decaByte.swapBits(l,r);
                    r=Math.floorMod(--r,DecaByte.SIZE);
                    strings.add(decaByte.toStringAsBin());
                }
//                System.out.println("--------------------------");
                decaByte.swapBits(r,(r+1)%DecaByte.SIZE);
//                System.out.println("--------------------------");
                strings.add(decaByte.toStringAsBin());
//                System.out.println("end= "+decaByte.toString());
            }
            decaByte.shiftLCirc(1); circ++;
        }
        circ--;

    }

    public static void sep_0_0(Collection<String> strings){/// 1010111
        DecaByte decaByte=new DecaByte(87);
        for (int k = 0; k < 2; k++) {
            for (int i = 0; i < DecaByte.SIZE; i++) {
                System.out.println("start= "+decaByte.toString());
                int stringRound = findStringRound(decaByte.toStringAsBin(), "0001")+3;
                System.out.println(stringRound);
                int l=stringRound,r=l+6;
                l%=DecaByte.SIZE;
                r%=DecaByte.SIZE;
                for (int j = 0; j < 2; j++) {
                    strings.add(decaByte.toStringAsBin());
                    l=Math.floorMod(--l,DecaByte.SIZE);
                    decaByte.swapBits(l,r);
                    r=Math.floorMod(--r,DecaByte.SIZE);
                    strings.add(decaByte.toStringAsBin());
                }
                System.out.println("--------------------------");
                for (int j = 0; j < 2; j++) {
                    decaByte.swapBits(r,(r+2)%DecaByte.SIZE);
                    strings.add(decaByte.toStringAsBin());
                    r=Math.floorMod(r-2,DecaByte.SIZE);

                }

                System.out.println("end= "+decaByte.toString());
            }
            decaByte.shiftLCirc(1); circ++;
        }
        circ--;

    }

    private static void sep_0__0(Collection<String> strings) {//// 0001011011
        DecaByte decaByte=new DecaByte(91);
        for (int k = 0; k < 1; k++) {
            for (int i = 0; i < DecaByte.SIZE; i++) {
                System.out.println("start= "+decaByte.toString());
                int stringRound = findStringRound(decaByte.toStringAsBin(), "0001")+3;
                System.out.println(stringRound);
                int l=stringRound,r=l+6;
                l%=DecaByte.SIZE;
                r%=DecaByte.SIZE;
                for (int j = 0; j < 1; j++) {
                    strings.add(decaByte.toStringAsBin());
                    l=Math.floorMod(--l,DecaByte.SIZE);
                    decaByte.swapBits(l,r);
                    r=Math.floorMod(--r,DecaByte.SIZE);
                    strings.add(decaByte.toStringAsBin());
                }
                System.out.println("--------------------------");
                for (int j = 0; j < 1; j++) {
                    decaByte.swapBits(r,(r+2)%DecaByte.SIZE);
                    strings.add(decaByte.toStringAsBin());
                    r=Math.floorMod(r-2,DecaByte.SIZE);

                }

                System.out.println("end= "+decaByte.toString());
            }
            decaByte.shiftLCirc(1); circ++;
        }
        circ--;

    }

    private static void sep_0_0_0(Collection<String> strings) {
        List<DecaByte> collect = Stream.of(213, 181, 173, 171).map(DecaByte::new).collect(Collectors.toList());
        for (DecaByte decaByte : collect) {
            for (int j = 0; j < DecaByte.SIZE; j++) {
                strings.add(decaByte.toStringAsBin());
                decaByte.shiftLCirc(1); circ++;
                strings.add(decaByte.toStringAsBin());
            }
        }
    }

    private static void sep_0_00(Collection<String> strings) {
        List<DecaByte> collect = Stream.of(233, 211, 217, 185 , 179 , 167).map(DecaByte::new).collect(Collectors.toList());
        for (DecaByte decaByte : collect) {
            for (int j = 0; j < DecaByte.SIZE; j++) {
                strings.add(decaByte.toStringAsBin());
                decaByte.shiftLCirc(1); circ++;
                strings.add(decaByte.toStringAsBin());
            }
        }
    }

    @Deprecated
    private static void sep_00_0(Set<String> strings) {
        List<DecaByte> collect = Stream.of(229, 203, 205, 157 , 155 , 151).map(DecaByte::new).collect(Collectors.toList());
        for (DecaByte decaByte : collect) {
            for (int j = 0; j < DecaByte.SIZE; j++) {
                strings.add(decaByte.toStringAsBin());
                decaByte.shiftLCirc(1); circ++;
                strings.add(decaByte.toStringAsBin());
            }
        }
    }

    private static void sep_0_0_0_0(Collection<String> strings) {
        List<DecaByte> collect = Stream.of(341).map(DecaByte::new).collect(Collectors.toList());
        for (DecaByte decaByte : collect) {
            for (int j = 0; j < 1; j++) {
                strings.add(decaByte.toStringAsBin());
                decaByte.shiftLCirc(1); circ++;
                strings.add(decaByte.toStringAsBin());
            }
        }
    }

    public static int findStringRound(String st,String text){
        int len=st.length();
        return st.concat(st).indexOf(text)%len;
    }
}
