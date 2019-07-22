package Experiments.Local.v1;

import com.sun.istack.internal.NotNull;

import java.util.stream.*;

public class DecaByte {
    public static final int SIZE=10;

    private final boolean[] deca;

    public DecaByte(@NotNull final boolean[] deca) {
        if (deca.length!=SIZE) throw new RuntimeException("bad length");
        this.deca = deca.clone();
    }

    public DecaByte() {
        this(new boolean[SIZE]);
    }
    public DecaByte(@NotNull final Integer num) {
        deca=new boolean[SIZE];
        String s = toBin(num, SIZE);
        for (int j = 0; j < SIZE; j++) {
            deca[j] = (s.charAt(j) & 1) != 0;
        }
    }

    public boolean getBit(@NotNull final int index){
        if (index<0||index>=SIZE) throw new IndexOutOfBoundsException("bad Index got index="+index);
        return deca[index];
    }
    public void setBit(@NotNull final int index,@NotNull final boolean bit){
        if (index<0||index>=SIZE) throw new IndexOutOfBoundsException("bad Index got index="+index);
        this.deca[index]=bit;
    }

    @Override
    public String toString() {
        return Integer.parseInt(arrAsBin(deca),2)+"";
    }
    public String toStringAsBin(){
        return arrAsBin(deca);
    }

    public static String arrAsBin(@NotNull final boolean[] I) {
		return IntStream.range(0, I.length).mapToObj(idx -> I[idx])
				.map(b -> b ? "1" : "0").collect(Collectors.joining(""));
	}

    public static String toBin(@NotNull final int i,@NotNull final int len) {
        String s = Integer.toBinaryString(i);
        return Stream.generate(() -> "0").limit(len-s.length()).collect(Collectors.joining("")) + s;
    }
}
