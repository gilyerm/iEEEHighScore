package Experiments.Local;

import com.sun.istack.internal.NotNull;

import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class DecaByte {

    public static final DecaByte DECA_BYTE_1=new DecaByte(1);
    public static final DecaByte DECA_BYTE_2=new DecaByte(2);
    public static final DecaByte DECA_BYTE_4=new DecaByte(4);
    public static final DecaByte DECA_BYTE_8=new DecaByte(8);
    public static final DecaByte DECA_BYTE_16=new DecaByte(16);
    public static final DecaByte DECA_BYTE_32=new DecaByte(32);
    public static final DecaByte DECA_BYTE_64=new DecaByte(64);
    public static final DecaByte DECA_BYTE_128=new DecaByte(128);
    public static final DecaByte DECA_BYTE_256=new DecaByte(256);
    public static final DecaByte DECA_BYTE_512=new DecaByte(512);

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
        final String s = toBin(num, SIZE);
        for (int j = 0; j < SIZE; j++) {
            deca[j] = (s.charAt(j) & 1) != 0;
        }
    }

    public DecaByte(@NotNull final String bin) {
        this(Integer.parseInt(bin,2));
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
        final String s = Integer.toBinaryString(i);
        return Stream.generate(() -> "0").limit(len-s.length()).collect(Collectors.joining("")) + s;
    }

    public int bitCount(){
        return DecaByte.BitCount(this);
    }

    private static int asInt(final boolean[] deca) {
        return Integer.parseInt(arrAsBin(deca), 2);
    }
    public int asInt() {
        return Integer.parseInt(arrAsBin(deca), 2);
    }


    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DecaByte)) return false;
        final DecaByte decaByte = (DecaByte) o;
        return Arrays.equals(deca, decaByte.deca);
    }
    @Override public int hashCode() {
        return Arrays.hashCode(deca);
    }

    public static DecaByte OR(@NotNull final DecaByte o1,@NotNull final DecaByte o2){
        return BitwiseOperation(o1,o2,(b1,b2) -> b1|b2);
    }
    public static DecaByte AND(@NotNull final DecaByte o1,@NotNull final DecaByte o2){
        return BitwiseOperation(o1,o2,(b1,b2) -> b1&b2);
    }
    public static DecaByte XOR(@NotNull final DecaByte o1,@NotNull final DecaByte o2){
        return BitwiseOperation(o1,o2,(b1,b2) -> b1^b2);
    }
    public static DecaByte BitwiseOperation(
            @NotNull final DecaByte o1, @NotNull final DecaByte o2,
            BiFunction<Boolean, Boolean, Boolean> opr) {
        DecaByte decaByte=new DecaByte();
        for (int j = 0; j < DecaByte.SIZE; j++)
            decaByte.setBit(j, opr.apply(o1.getBit(j), o2.getBit(j)));
        return decaByte;
    }

    public DecaByte OR(@NotNull final DecaByte o){
        return OR(this,o);
    }
    public DecaByte AND(@NotNull final DecaByte o){
        return AND(this,o);
    }
    public DecaByte XOR(@NotNull final DecaByte o){
        return XOR(this,o);
    }


    public static int BitCount(@NotNull final DecaByte decaByte){
        return Integer.bitCount(asInt(decaByte.deca));
    }

    public void shiftL(@NotNull final int num){
        for (int i = 0; i < num; i++) {
            for (int j = 0; j < SIZE-1; j++) {
                deca[j]=deca[j+1];
            }
            deca[SIZE-1]=false;
        }
    }
    public void shiftR(@NotNull final int num){
        for (int i = 0; i < num; i++) {
            for (int j = SIZE-1; j > 0; j--) {
                deca[j]=deca[j-1];
            }
            deca[0]=false;
        }
    }

    public void shiftLCirc(@NotNull final int num){
        for (int i = 0; i < num; i++) {
            final boolean tmp = deca[0];
            for (int j = 0; j < SIZE-1; j++) {
                deca[j]=deca[j+1];
            }
            deca[SIZE-1]=tmp;
        }
    }
    public void shiftRCirc(@NotNull final int num){
        for (int i = 0; i < num; i++) {
            final boolean tmp = deca[SIZE-1];
            for (int j = SIZE-1; j > 0; j--)
                deca[j]=deca[j-1];
            deca[0]=tmp;
        }
    }

    public void swapBits(@NotNull final int i,@NotNull final int j){
//        System.out.print(this.toStringAsBin());
        final boolean tmp = getBit(i);
        setBit(i,getBit(j));
        setBit(j,tmp);
//        System.out.println("\t"+i+","+j+"\t"+this.toStringAsBin());
    }
}
