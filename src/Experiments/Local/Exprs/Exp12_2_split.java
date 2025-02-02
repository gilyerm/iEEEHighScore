package Experiments.Local.Exprs;

import Experiments.Local.DecaByte;
import Experiments.Local.v3.DecaState;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Exp12_2_split {
    private static int circ=0;

    public static void main(String[] args) {

//        System.out.println(DecaByte.DECA_BYTE_512.toStringAsBin());

        List<LinkedHashSet<String>> stringsList = Stream
                .generate(() -> new LinkedHashSet<String>())
                .limit(7).collect(Collectors.toList());
//        Collection<String> strings=new HashSet<>();
        int i=0;
        NoSep(stringsList.get(i++));
        Sep_00(stringsList.get(i++));
        sep_0_0(stringsList.get(i++));
        sep_0__0(stringsList.get(i++));
        sep_0_0_0(stringsList.get(i++));
        sep_0_00(stringsList.get(i++));
        sep_0_0_0_0(stringsList.get(i++));

        List<Integer> bc5 = IntStream.range(0, 1024).boxed()
                .filter(n -> Integer.bitCount(n) == 5).collect(Collectors.toList());
        LinkedHashSet<String> total = stringsList.stream().reduce(new LinkedHashSet<>(), (strings, strings2) -> {
            LinkedHashSet<String> list = new LinkedHashSet<>();
            list.addAll(strings);
            list.addAll(strings2);
            return list;
        });
        System.out.println(
                total.stream().map(b->Integer.parseInt(b,2))
                        .collect(Collectors.toList()).containsAll(bc5));

//        System.out.println(strings.size());

//        System.out.println();
//        System.out.println();
//        stringsList.forEach(s-> System.out.println(s.size()+"\t"+s));
//        System.out.println();
//        System.out.println(stringsList.stream().map(HashSet::size).mapToInt(n->n).sum());
//
//        System.out.println();
//        stringsList.forEach(s-> {
//                    List<Integer> l = s.stream().map(b -> Integer.parseInt(b, 2)).collect(Collectors.toList());
//                    System.out.println(l.size()+"\t"+l);
//                }
//        );
//
//
//        HashSet<String> allstrings = new HashSet<>();
//        stringsList.forEach(s->allstrings.addAll(s));
//        System.out.println();
//        System.out.println(allstrings.size()+"\t"+allstrings);
//        System.out.println(circ);

    }


    public static void NoSep(Collection<String> strings) {

        DecaState decaState = new DecaState(31, "54321");
        System.out.println(decaState);
        for (int i=4; i>0; i--) {
            for (int j = 0; j < DecaByte.SIZE; j++) {
                System.out.println(decaState);
                decaState = decaState.nextIteration(i);
                i = Math.floorMod(i-1, 10);
            }
//            i = Math.floorMod(i-1, 10);
        }

        DecaByte decaByte=new DecaByte(31);
        LinkedHashSet<String> trace = new LinkedHashSet<>();
        for (int i = 4; i <= 8; i++) {
            String b = decaByte.toStringAsBin();
            strings.add(b);
            trace.add(b);
            for (int j = 0; j < DecaByte.SIZE; j++) {
                decaByte.shiftLCirc(1); circ++;
                b = decaByte.toStringAsBin();
                strings.add(b);
                trace.add(b);
            }
            decaByte.swapBits(i,i+1);
        }
        trace.forEach(b -> System.out.println(b+"\t"+Integer.parseInt(b,2)));
        Iterator<String> itr = trace.iterator();
        String first, next = itr.next();
        do {
            first = next; next = itr.next();
            DecaByte decaFirst = new DecaByte(first),
                     decaNext = new DecaByte(next);
            if (DecaByte.XOR(decaFirst,decaNext).bitCount() != 2)
                System.out.println("\n"+decaFirst.toStringAsBin()+"\n"+decaNext.toStringAsBin()+"\n");
        } while (itr.hasNext());
//        System.out.println(trace);
    }


    public static void Sep_00(Collection<String> strings) {
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
                //System.out.println("start= "+decaByte.toString());
                int stringRound = findStringRound(decaByte.toStringAsBin(), "0001")+3;
                //System.out.println(stringRound);
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
                //System.out.println("--------------------------");
                for (int j = 0; j < 2; j++) {
                    decaByte.swapBits(r,(r+2)%DecaByte.SIZE);
                    strings.add(decaByte.toStringAsBin());
                    r=Math.floorMod(r-2,DecaByte.SIZE);

                }

                //System.out.println("end= "+decaByte.toString());
            }
            decaByte.shiftLCirc(1); circ++;
        }
        circ--;

    }

    private static void sep_0__0(Collection<String> strings) {//// 0001011011
        DecaByte decaByte=new DecaByte(91);
        for (int k = 0; k < 1; k++) {
            for (int i = 0; i < DecaByte.SIZE; i++) {
                //System.out.println("start= "+decaByte.toString());
                int stringRound = findStringRound(decaByte.toStringAsBin(), "0001")+3;
                //System.out.println(stringRound);
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
                //System.out.println("--------------------------");
                for (int j = 0; j < 1; j++) {
                    decaByte.swapBits(r,(r+2)%DecaByte.SIZE);
                    strings.add(decaByte.toStringAsBin());
                    r=Math.floorMod(r-2,DecaByte.SIZE);

                }

                //System.out.println("end= "+decaByte.toString());
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
