package Experiments.Local.Exprs;

import Experiments.Local.DecaByte;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Exp12 {
    public static void main(String[] args) {
        Collection<String> strings=new HashSet<>();
        NoSep(strings);
        Sep_00(strings);
        sep_0_0(strings);
        sep_0__0(strings);
        sep_0_0_0(strings);
        sep_0_00(strings);
        sep_0_0_0_0(strings);
        System.out.println(strings.size());
    }




    public static void NoSep(Collection<String> strings) {
        DecaByte decaByte=new DecaByte(31);
        for (int i = 4; i <= 8; i++) {
            strings.add(decaByte.toStringAsBin());
            for (int j = 0; j < DecaByte.SIZE; j++) {
                decaByte.shiftLCirc(1);
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
            decaByte.shiftLCirc(1);
        }
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
            decaByte.shiftLCirc(1);
        }

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
            decaByte.shiftLCirc(1);
        }

    }

    private static void sep_0_0_0(Collection<String> strings) {
        List<DecaByte> collect = Stream.of(213, 181, 173, 171).map(DecaByte::new).collect(Collectors.toList());
        for (DecaByte decaByte : collect) {
            for (int j = 0; j < DecaByte.SIZE; j++) {
                strings.add(decaByte.toStringAsBin());
                decaByte.shiftLCirc(1);
                strings.add(decaByte.toStringAsBin());
            }
        }
    }

    private static void sep_0_00(Collection<String> strings) {
        List<DecaByte> collect = Stream.of(233, 211, 217, 185 , 179 , 167).map(DecaByte::new).collect(Collectors.toList());
        for (DecaByte decaByte : collect) {
            for (int j = 0; j < DecaByte.SIZE; j++) {
                strings.add(decaByte.toStringAsBin());
                decaByte.shiftLCirc(1);
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
                decaByte.shiftLCirc(1);
                strings.add(decaByte.toStringAsBin());
            }
        }
    }

    private static void sep_0_0_0_0(Collection<String> strings) {
        List<DecaByte> collect = Stream.of(341).map(DecaByte::new).collect(Collectors.toList());
        for (DecaByte decaByte : collect) {
            for (int j = 0; j < 1; j++) {
                strings.add(decaByte.toStringAsBin());
                decaByte.shiftLCirc(1);
                strings.add(decaByte.toStringAsBin());
            }
        }
    }

    public static int findStringRound(String st,String text){
        int len=st.length();
        return st.concat(st).indexOf(text)%len;
    }
}
