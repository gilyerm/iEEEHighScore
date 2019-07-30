package Demos;

import Experiments.Local.BaseProgram;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static Experiments.Local.BaseProgram.toBin;

public class demo_AnySeq {
    public static void main(String[] args) {
        List<String> collect = Stream.of(2, 1, 4, 32, 16, 8, 128, 256, 512)
                .map(j -> toBin(j, 10)).collect(Collectors.toList());

        BaseProgram.Helper hr = BaseProgram.solMain(collect);
        System.err.println("solMainRES_REV:::::");
        System.err.println(hr.res);
    }
}
