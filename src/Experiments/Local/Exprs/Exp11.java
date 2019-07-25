package Experiments.Local.Exprs;

import Experiments.Local.DecaByte;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Exp11 {

    public static int calcVal(DecaByte C){
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
//        if (C.bitCount()==5 && I.bitCount()==1){
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
//        }else {
//            A = new DecaByte(255);
//        }

        int val = 0;
        for (int i = 7; i >= 0; i--) {
            val = val << 1;
            if (A.getBit(i)) val |= 1;
        }
        return val;
    }
    public static void main(String[] args) {
        Map<Integer,DecaByte> map=new HashMap<>();
        for (int i = 0; i < 1024; i++) {
            if (Integer.bitCount(i)==5) {
                DecaByte decaByte = new DecaByte(i);
                int val = calcVal(decaByte);
//                System.out.println(decaByte.toString()+"\t"+decaByte.toStringAsBin()+" =>\t"+val);
                map.put(val,decaByte);
            }
        }
        map.keySet().stream().sorted(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return map.get(o1).toStringAsBin().compareTo(map.get(o2).toStringAsBin());
            }
        }).forEach(val -> {
            DecaByte decaByte = map.get(val);
            DecaByte valdec = new DecaByte(val);
            System.out.println(decaByte.toString()+"\t"+decaByte.toStringAsBin()+" =>\t"+val+"\t"+valdec.toStringAsBin());
        });
    }
}
