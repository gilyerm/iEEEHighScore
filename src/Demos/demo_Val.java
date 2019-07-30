package Demos;

import Experiments.Local.DecaByte;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class demo_Val {

    public static void main(String[] args) {
//        System.out.println("values of C[] for which c95=T :");
        System.out.println("---------------");
        Map<DecaByte,Integer> map=new LinkedHashMap<>();
        for (int i = 0; i < 1024; i++) {
            DecaByte C = new DecaByte(i);
            if (C.bitCount()!=5) continue;
            int val = run_A(C);
            map.put(C,val);
        }
        System.out.println("int\t=>\tvalue");
        map.entrySet().stream().filter(entry -> entry.getValue()!=255)
                .sorted(Comparator.comparingInt(Map.Entry::getValue))
                .forEach(entry -> System.out.println(entry.getKey()+"\t=>\t"+entry.getValue()));

        System.out.println("unreachable values for e=F :");
        IntStream.range(0,256).filter(value -> !map.values().contains(value))
                .sorted().forEach(x -> System.out.println("\t"+x));
    }



    public static int run_A(DecaByte C){
        boolean c0=C.getBit(0),
                c1=C.getBit(1),
                c2=C.getBit(2),
                c3=C.getBit(3),
                c4=C.getBit(4),
                c5=C.getBit(5),
                c6=C.getBit(6),
                c7=C.getBit(7),
                c8=C.getBit(8),
                c9=C.getBit(9);

        DecaByte A;
        if (C.bitCount()==5){
            A=new DecaByte();
            A.setBit(0,((((!c0)&(!c1)&(!c2)&(!c3)&(!c4))|(c0&c1&c2&c3&c4))^c0^c1^c2^c3^c4^(c3&
                    (((c0^c8)&c1&c2&c4)^((((c0^c1)&c2&c5)^(c1&c4&c7))&c8)))));
            A.setBit(1,((((!c0)&(!c1)&(c2)&(!c5)&(c6))|(c0&c1&((!c2)&(!c6))&c5))^c0^c1^c2^c5^c6^(c4&
                    ((c0&c1&((c2&c3)^(c5&c6)))^(((c1&c7)^(c6&c9))&c3&c8)))));
            A.setBit(2,((((!c0)&(!c1)&(c3)&(!c5)&(!c7))|(c0&c1&(!c3)&c5&c7))^c0^c1^c3^c5^c7^(c0&c1&c2&
                    (c3^c4)&c5)^((c3^c4)&c5&c7&c8&c9)));
            A.setBit(3,((c3&c5)^(c3&c6)^(c3&c8)^(c3&c9)^(c5&c6)^(c5&c8)^(c5&c9)^(c6&c8)^(c6&c9)^
                    (c8|c9)^c3^c5^c6^c8^c9^(c0&c1&c3&c6&c9)));
            A.setBit(4,((c2&c5)^(c2&c7)^(c2&c8)^(c2&c9)^(c5&c7)^(c5&c8)^(c5&c9)^(c7&c8)^(c7|c9)^
                    (c8&c9)^c2^c5^c7^(((c0&c5&c6)^(c1&c3&c4))&c7&c8)));
            A.setBit(5,((c0&c1)^c0^c2^c4^c6^c7^(c0&c1&c2&c3&c4)^
                    (((c0&((c3&c5)^(c2&c4)))^(c1&c4&c6))&c7&c8)^(c3&c4&c6&((c2&c9)^(c5&c7)))));
            A.setBit(6,(c0^c1^c3^c4^c7^(c0&c1&c2&c4&c9)^(c0&((c1&c4)^(c3&c8))&c5&c7)^
                    ((((((c0^c1)&c5)^(c0&c4))&c2)^(c1&(c2^c7)&c4))&c6&c8)));
            A.setBit(7,(c2^c3^c4^(c0&((c2&c3)^((c2^c3)&c7))&c4&c8)^
                    ((((c0^c1)&c3&c5)^(((c0^c1)& (c4^c5))&c6))&c7&c8)));
        }else {
            A = new DecaByte(255<<2);
        }

        int val = 0;
        for (int i = 7; i >= 0; i--) {
            val = val << 1;
            if (A.getBit(i)) val |= 1;
        }
        return val;
    }
}
