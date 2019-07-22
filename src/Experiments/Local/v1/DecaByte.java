package Experiments.Local.v1;

import com.sun.istack.internal.NotNull;

import java.util.Arrays;
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

    public boolean[] getDeca() {
        return deca.clone();
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
        return asInt(deca) +"";
    }
    public String toStringAsBin(){
        return arrAsBin(deca);
    }
    public String toStringAsBin(final int start,final int end) {
        if (start<0||end>=SIZE) throw new IndexOutOfBoundsException("bad Index got start="+start+" and end="+end);
        return toStringAsBin().substring(start, end);
    }


    public static String arrAsBin(@NotNull final boolean[] I) {
		return IntStream.range(0, I.length).mapToObj(idx -> I[idx])
				.map(b -> b ? "1" : "0").collect(Collectors.joining(""));
	}

    public static String toBin(@NotNull final int i,@NotNull final int len) {
        String s = Integer.toBinaryString(i);
        return Stream.generate(() -> "0").limit(len-s.length()).collect(Collectors.joining("")) + s;
    }

    public int bitCount(){
        return Integer.bitCount(asInt(deca));
    }

    private static int asInt(boolean[] deca) {
        return Integer.parseInt(arrAsBin(deca), 2);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DecaByte)) return false;

        DecaByte decaByte = (DecaByte) o;

        return Arrays.equals(deca, decaByte.deca);

    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(deca);
    }
}
