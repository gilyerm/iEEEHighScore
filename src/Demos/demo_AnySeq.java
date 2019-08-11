package Demos;

import Experiments.Local.BaseProgram;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static Experiments.Local.v3.DecaState.toBin;
import static Experiments.Local.v3.EditProgramV3.solMain;

import Experiments.Local.DecaByte;
import Experiments.Local.v3.EditProgramV3;

public class demo_AnySeq {

    static int[] Seq = {4,256,128,1,2,8,4,16,32,1,2,8,4,64,16,1,2,8,128,32,4,1,2,64,128,8,4,1,16,256,2,8,4,1,512,16,2,8,4,128,256,1,16,2,32,64,4,8,1,256,32,2,4,8,512,64,1,2,4,256,512,8,1,2,32,64,128,4,1,16,32,64,8,2,128,16,32,1,8,256,64,2,4,16,128,1,512,2,4,32,16,128,8,64,1,32,512,2,16,8,64,256,1,128,2,32,512,4,64,1,16,256,2,512,4,64,16,8,32,256,2,64,1,512,16,8,32,4,256,64,1,128,16,32,256,2,4,64,128,512,1,8,16,256,128,2,64,8,512,32,4,1,16,256,512,32,2,8,128,256,512,1,4,32,128,256,2,64,512,16,32,4,128,64,8,16,256,4,128,32,8,512,256,64,16,128,32,2,512,256,1,128,32,64,512,16,256,4,128,64,8,512,32,16,256,128,8,512,4,16,256,32,64,2,512,128,1,8,32,256,512,4,2,128,16,256,512,1,64,128,16,2,512,8,256,64,128,32,4,512,256,64,1,32,16,512,128,8,64};


    public static void main(String[] args) {
        List<String> collect = Arrays.stream(Seq).boxed()
                .map(j -> toBin(j, 10)).collect(Collectors.toList());

        EditProgramV3.Helper hr = solMain(collect);
        System.err.println("solMainRES_REV:::::");
        System.err.println(hr.Chistory);

        System.out.println("missing:\n"+ IntStream.range(0, 1024)
                .filter(value -> Integer.bitCount(value) == 5)
                .boxed().map(integer -> DecaByte.toBin(integer,10))
                .map(DecaByte::new).filter(s -> !hr.Chistory.contains(s))
                .map(DecaByte::toStringAsBin).map(s -> Arrays.toString(s.toCharArray()) +"\n").collect(Collectors.toList()));

        List<String> strings = hr.Chistory.stream().map(DecaByte::toStringAsBin).collect(Collectors.toList());
        for (String string : strings) {
            System.out.println(Arrays.toString(string.toCharArray()));
        }
    }
}
