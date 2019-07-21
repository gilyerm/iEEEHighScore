package Experiments.Local.v1;

import com.sun.istack.internal.NotNull;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class DecaByte {
    private boolean[] deca;

    public DecaByte(@NotNull boolean[] deca) {
        if (deca.length!=10) throw new RuntimeException("bad length");
        this.deca = deca.clone();
    }

    public DecaByte() {
        this(new boolean[10]);
    }
    public DecaByte(Integer num) {
        deca=new boolean[10];
        String s = toBin(num, 10);
        for (int j = 0; j < 10; j++) {
            deca[j] = (s.charAt(j) & 1) != 0;
        }
    }

    public boolean getBit(int index){
        if (index<0||index>=10) throw new RuntimeException("bad Index");
        return deca[index];
    }
    public void setBit(int index,boolean b){
        if (index<0||index>=10) throw new RuntimeException("bad Index");
        this.deca[index]=b;
    }

    @Override
    public String toString() {
        return  Integer.parseInt(arrAsBin(deca),2)+"";
    }

    private static String arrAsBin(boolean[] I) {
		return IntStream.range(0, I.length).mapToObj(idx -> I[idx])
				.map(b -> b ? "1" : "0").collect(Collectors.joining(""));
	}

    private static String toBin(int i, int len) {
        String s = Integer.toBinaryString(i);
        return Stream.generate(() -> "0").limit(len-s.length()).collect(Collectors.joining("")) + s;
    }
}
