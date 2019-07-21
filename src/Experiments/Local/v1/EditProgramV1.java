package Experiments.Local.v1;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EditProgramV1 {

	public static String toBin(int i, int len) {
		String s = Integer.toBinaryString(i);
		return Stream.generate(() -> "0").limit(len-s.length()).collect(Collectors.joining("")) + s;
	}
//	public static String arrAsBin(boolean[] I) {
//		return IntStream.range(0, I.length).mapToObj(idx -> I[idx])
//				.map(b -> b ? "1" : "0").collect(Collectors.joining(""));
//	}

	public static class Helper {
		public ArrayList<Integer> indexs = new ArrayList<>();
		BufferQueue<DecaByte> B = new BufferQueue<>(5,new DecaByte());
		char[] M = new char[256];
		int last = -1;
		public int c = 0;
		int res = -111;
		public ArrayList<Boolean> e=new ArrayList<>();
	}

	public static Helper solMain(List<String> inp) {
		Helper h = new Helper();

		int n = inp.size(); // sc.nextInt();
		boolean[] I = new boolean[10];

		for (int i = 0; i < n; i++) {
			String s = inp.get(i); // sc.next();
			for (int j = 0; j < 10; j++) {
				I[j] = (s.charAt(j) & 1) != 0;
			}
			h.last = f(new DecaByte(I), h);
		}
		h.res = 1000*sum(h.M)-n;
		return h;
	}

	public static int sum(char[] M) {
		int retVal = 0;
		for (int i = 0; i < M.length; i++)
			retVal += M[i];
		return retVal;
	}
	public static int f(DecaByte I,Helper h) {
		BufferQueue<DecaByte> B = h.B;

		char[] M = h.M;
		ArrayList<Integer> indexs = h.indexs;
		Queue<DecaByte> decaBytes=new LinkedList<>();


		DecaByte X=new DecaByte();
		DecaByte Y=new DecaByte();

		boolean[] C = new boolean[10];

		boolean	c10, c11, c12, c20, c21, c22, c23;
		boolean c30, c31, c32, c33, c34, c40, c41, c42, c43, c44, c45;
		boolean c51, c52, c53, c54, c55, c62, c63, c64, c65, c73, c74, c75;
		boolean c84, c85, c95;

		B.push(I);
//		System.out.println("B="+B.toString());
//		B[49]=B[39];B[48]=B[38];B[47]=B[37];B[46]=B[36];B[45]=B[35];B[44]=B[34];
//		...
//		B[ 1]=I[ 1];B[ 0]=I[ 0];

		X.setBit(0,I.getBit(0));
		for (int i = 1; i < 10; i++) {
			X.setBit(i,
					X.getBit(i-1)|I.getBit(i));
		}
		
//		boolean x0=I[0];
//		x1= x0|I[1];
//		x2= x1|I[2];
//		x3= x2|I[3];
//		x4= x3|I[4];
//		x5= x4|I[5];
//		x6= x5|I[6];
//		x7= x6|I[7];
//		x8= x7|I[8];
//		x9= x8|I[9];

		Y.setBit(0,!X.getBit(9));
		for (int i = 1; i < 10; i++) {
			Y.setBit(i,
					Y.getBit(i-1)|(
							X.getBit(i-1)&I.getBit(i)
							));
		}


//		boolean y0=(!x9);
//		y1=y0|(x0&I[1]);// x0 = i0
//		y2=y1|(x1&I[2]);
//		y3=y2|(x2&I[3]);
//		y4=y3|(x3&I[4]);
//		y5=y4|(x4&I[5]);
//		y6=y5|(x5&I[6]);
//		y7=y6|(x6&I[7]);
//		y8=y7|(x7&I[8]);
//		y9=y8|(x8&I[9]);

		for (int i = 0; i < B.getMaxSize(); i++) {
			for (int j = 0; j < 10; j++) {
				C[j] |= B.cell(i).getBit(j);
			}
		}

//		c0=B[0]|B[10]|B[20]|B[30]|B[40];c1=B[1]|B[11]|B[21]|B[31]|B[41];
//		c2=B[2]|B[12]|B[22]|B[32]|B[42];c3=B[3]|B[13]|B[23]|B[33]|B[43];
//		c4=B[4]|B[14]|B[24]|B[34]|B[44];c5=B[5]|B[15]|B[25]|B[35]|B[45];
//		c6=B[6]|B[16]|B[26]|B[36]|B[46];c7=B[7]|B[17]|B[27]|B[37]|B[47];
//		c8=B[8]|B[18]|B[28]|B[38]|B[48];c9=B[9]|B[19]|B[29]|B[39]|B[49];

		boolean c0=C[0], c1=C[1], c2=C[2], c3=C[3], c4=C[4], c5=C[5], c6=C[6], c7=C[7], c8=C[8], c9=C[9];

		ArrayList<boolean[]> CList = new ArrayList<>();
		CList.add(0,null);
		for (int i = 3, j=1; i <= 6; i++, j++) {
			CList.add(j, new boolean[i]);
		}

//		init CList[0] // C10
//		c10=!(c0 | c1);
//		c11=c0^c1;c12=c0&c1;
		boolean[] C10 = CList.get(1);
		C10[0] = !(C[0] | C[1]);
		C10[1] =   C[0] ^ C[1];
		C10[2] =   C[0] & C[1];

		for (int i = 2; i < CList.size(); i++) {  // for C20-C40
			boolean[] prevC = CList.get(i-1);
			boolean[] curC = CList.get(i);
			boolean curCDigit = C[i];

			for (int j = 0; j < curC .length; j++) {
				boolean a=false,b=false;
				if (j-1>=0){
					a=prevC [j-1];
				}
				if (j<prevC.length){
					b=prevC [j];
				}
				curC [j]=(a&curCDigit)|(b&(!curCDigit));
			}

		}

//		CList.stream().map(c->Arrays.toString(c)).forEach(System.out::println);

//		c20= false  |(c10&(!c2));
//		c21=(c10&c2)|(c11&(!c2));
//		c22=(c11&c2)|(c12&(!c2));
//		c23=(c12&c2)| false;
//
//		c30= false  |(c20&(!c3));
//		c31=(c20&c3)|(c21&(!c3));
//		c32=(c21&c3)|(c22&(!c3));
//		c33=(c22&c3)|(c23&(!c3));
//		c34=(c23&c3)| false;
//		c40= false  |(c30&(!c4));
//		c41=(c30&c4)|(c31&(!c4));
//		c42=(c31&c4)|(c32&(!c4));
//		c43=(c32&c4)|(c33&(!c4));
//		c44=(c33&c4)|(c34&(!c4));
//		c45=(c34&c4)| false;

//		boolean[] C40 = CList.get(4);
//		c40 = C40[0];
//		c41 = C40[1];
//		c42 = C40[2];
//		c43 = C40[3];
//		c44 = C40[4];
//		c45 = C40[5];

		final int k = 6;
		for (int j=5; j <= 9; j++) {
			CList.add(j, new boolean[k]);
		}

		for (int i = 5, p = 1; i < CList.size(); i++, p++) {  // for C50-C90
			boolean[] prevC = CList.get(i-1);
			boolean[] curC = CList.get(i);
			boolean curCDigit = C[i];

			for (int j = p; j < curC.length; j++) {
				boolean a=false,b=false;
//				if (j-1>=0){
					a=prevC [j-1];
//				}
//				if (j<prevC.length){
					b=prevC [j];
//				}
				curC[j]=(a&curCDigit)|(b&(!curCDigit));
			}
		}



//		c51=(c40&c5)|(c41&(!c5));
//		c52=(c41&c5)|(c42&(!c5));
//		c53=(c42&c5)|(c43&(!c5));
//		c54=(c43&c5)|(c44&(!c5));
//		c55=(c44&c5)|(c45&(!c5));
//
//		c62=(c51&c6)|(c52&(!c6));
//		c63=(c52&c6)|(c53&(!c6));
//		c64=(c53&c6)|(c54&(!c6));
//		c65=(c54&c6)|(c55&(!c6));
//
//		c73=(c62&c7)|(c63&(!c7));
//		c74=(c63&c7)|(c64&(!c7));
//		c75=(c64&c7)|(c65&(!c7));
//
//		c84=(c73&c8)|(c74&(!c8));
//		c85=(c74&c8)|(c75&(!c8));
//
//		c95=(c84&c9)|(c85&(!c9));

		c95 = CList.get(9)[5];
		boolean e=(!c95)|Y.getBit(9);

		boolean[] a= new boolean[10];

		a[0]=e|((((!c0)&(!c1)&(!c2)&(!c3)&(!c4))|(c0&c1&c2&c3&c4))^c0^c1^c2^c3^c4^(c3&
				(((c0^c8)&c1&c2&c4)^((((c0^c1)&c2&c5)^(c1&c4&c7))&c8))));
		a[1]=e|((((!c0)&(!c1)&(c2)&(!c5)&(c6))|(c0&c1&((!c2)&(!c6))&c5))^c0^c1^c2^c5^c6^(c4&
				((c0&c1&((c2&c3)^(c5&c6)))^(((c1&c7)^(c6&c9))&c3&c8))));
		a[2]=e|((((!c0)&(!c1)&(c3)&(!c5)&(!c7))|(c0&c1&(!c3)&c5&c7))^c0^c1^c3^c5^c7^(c0&c1&c2&
				(c3^c4)&c5)^((c3^c4)&c5&c7&c8&c9));
		a[3]=e|((c3&c5)^(c3&c6)^(c3&c8)^(c3&c9)^(c5&c6)^(c5&c8)^(c5&c9)^(c6&c8)^(c6&c9)^
				(c8|c9)^c3^c5^c6^c8^c9^(c0&c1&c3&c6&c9));
		a[4]=e|((c2&c5)^(c2&c7)^(c2&c8)^(c2&c9)^(c5&c7)^(c5&c8)^(c5&c9)^(c7&c8)^(c7|c9)^
				(c8&c9)^c2^c5^c7^(((c0&c5&c6)^(c1&c3&c4))&c7&c8));
		a[5]=e|((c0&c1)^c0^c2^c4^c6^c7^(c0&c1&c2&c3&c4)^(((c0&((c3&c5)^(c2&c4)))^
				(c1&c4&c6))&c7&c8)^(c3&c4&c6&((c2&c9)^(c5&c7))));
		a[6]=e|(c0^c1^c3^c4^c7^(c0&c1&c2&c4&c9)^(c0&((c1&c4)^(c3&c8))&c5&c7)^
				((((((c0^c1)&c5)^(c0&c4))&c2)^(c1&(c2^c7)&c4))&c6&c8));
		a[7]=e|(c2^c3^c4^(c0&((c2&c3)^((c2^c3)&c7))&c4&c8)^((((c0^c1)&c3&c5)^(((c0^c1)&
				(c4^c5))&c6))&c7&c8));
		int val = 0;
		for (int i = 7; i >= 0; i--) {
			val = val << 1;
			if (a[i]) val = val | 1;
		}
		M[val]=1;
		indexs.add(val);
		h.e.add(e);
		return val;
		// String bin = arrAsBin(I);
		// int i = Integer.parseInt(bin, 2);
		// System.out.printf("#%d: %d << %s = %d\n",++c,val,bin,i);
	}

	public static void main(String[] args) {
		BufferQueue<DecaByte> queue=new BufferQueue<>(5,new DecaByte());
		for (int i = 0; i < 10; i++) {
			queue.push(new DecaByte(i));
			System.out.println(queue.toString());
		}
		System.out.println(queue.cell(0)+"\t"+queue.cell(4));
	}
}