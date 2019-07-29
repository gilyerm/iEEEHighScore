package Experiments.Local.v2;

import Experiments.Local.BufferQueue;
import Experiments.Local.DecaByte;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EditProgramV2 {

	public static class Helper {
		public ArrayList<Integer> indexs = new ArrayList<>();
		BufferQueue<DecaByte> B = new BufferQueue<>(5,new DecaByte());
		char[] M = new char[256];
		int last = -1;
		public int c = 0;
		public int res = -111;
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

	public static int f(DecaByte I, Helper h) {
		DecaByte A=new DecaByte();
		BufferQueue<DecaByte> B = h.B;
		DecaByte C=new DecaByte();

		DecaByte X=new DecaByte();
		DecaByte Y=new DecaByte();

		B.push(I);

		X.setBit(0,I.getBit(0));
		for (int i = 1; i < DecaByte.SIZE; i++) {
			X.setBit(i,X.getBit(i-1)|I.getBit(i));
		}

		Y.setBit(0,!X.getBit(9));
		for (int i = 1; i < DecaByte.SIZE; i++) {
			Y.setBit(i,
					Y.getBit(i-1)|(
							X.getBit(i-1)&I.getBit(i)
							));
		}

		/// C = init "0"
		for (int i = 0; i < B.getMaxSize(); i++) {
			C = DecaByte.OR(C,B.cell(i));
		}
		ArrayList<DecaByte> CList = new ArrayList<>();
		final int offset=1;
		{
			CList.add(0, null);
			for (int i = 1; i <= 9; i++) {
				CList.add(i, new DecaByte());
			}

			DecaByte C10 = CList.get(1);
			C10.setBit(0+offset,   !(C.getBit(0) | C.getBit(1)));
			C10.setBit(1+offset, C.getBit(0) ^ C.getBit(1));
			C10.setBit(2+offset, C.getBit(0) & C.getBit(1));

			for (int i = 2; i < CList.size(); i++) {  // for C20-C90
				DecaByte prevC = CList.get(i - 1);
				DecaByte curC = CList.get(i);
				boolean curCDigit = C.getBit(i);

				// code makes 2 changes: use of decabyte and
				// assuming "non existent" indexs to be FLASE!
				// also, made all shift over by 1 (20-23 => 21-24), so no need for IFs!
				for (int j = offset; j < DecaByte.SIZE; j++) {
					curC.setBit(j, (prevC.getBit(j - 1) & curCDigit) | (prevC.getBit(j) & (!curCDigit)));
				}
			}
		}

		boolean e=(!CList.get(9).getBit(5+offset))|Y.getBit(9);

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

		{
			A.setBit(0,e|((((!c0)&(!c1)&(!c2)&(!c3)&(!c4))|(c0&c1&c2&c3&c4))^c0^c1^c2^c3^c4^(c3&
					(((c0^c8)&c1&c2&c4)^((((c0^c1)&c2&c5)^(c1&c4&c7))&c8)))));
			A.setBit(1,e|((((!c0)&(!c1)&(c2)&(!c5)&(c6))|(c0&c1&((!c2)&(!c6))&c5))^c0^c1^c2^c5^c6^(c4&
					((c0&c1&((c2&c3)^(c5&c6)))^(((c1&c7)^(c6&c9))&c3&c8)))));
			A.setBit(2,e|((((!c0)&(!c1)&(c3)&(!c5)&(!c7))|(c0&c1&(!c3)&c5&c7))^c0^c1^c3^c5^c7^(c0&c1&c2&
					(c3^c4)&c5)^((c3^c4)&c5&c7&c8&c9)));
			A.setBit(3,e|((c3&c5)^(c3&c6)^(c3&c8)^(c3&c9)^(c5&c6)^(c5&c8)^(c5&c9)^(c6&c8)^(c6&c9)^
					(c8|c9)^c3^c5^c6^c8^c9^(c0&c1&c3&c6&c9)));
			A.setBit(4,e|((c2&c5)^(c2&c7)^(c2&c8)^(c2&c9)^(c5&c7)^(c5&c8)^(c5&c9)^(c7&c8)^(c7|c9)^
					(c8&c9)^c2^c5^c7^(((c0&c5&c6)^(c1&c3&c4))&c7&c8)));
			A.setBit(5,e|((c0&c1)^c0^c2^c4^c6^c7^(c0&c1&c2&c3&c4)^
					(((c0&((c3&c5)^(c2&c4)))^(c1&c4&c6))&c7&c8)^(c3&c4&c6&((c2&c9)^(c5&c7)))));
			A.setBit(6,e|(c0^c1^c3^c4^c7^(c0&c1&c2&c4&c9)^(c0&((c1&c4)^(c3&c8))&c5&c7)^
					((((((c0^c1)&c5)^(c0&c4))&c2)^(c1&(c2^c7)&c4))&c6&c8)));
			A.setBit(7,e|(c2^c3^c4^(c0&((c2&c3)^((c2^c3)&c7))&c4&c8)^
					((((c0^c1)&c3&c5)^(((c0^c1)& (c4^c5))&c6))&c7&c8)));
		}

		int val = 0;
		for (int i = DecaByte.SIZE-1; i >= 0; i--) { /// original i=7 change to i=9 not change anything
			val = val << 1;
			if (A.getBit(i)) val |= 1;
		}

		h.M[val]=1;
		h.indexs.add(val);
		h.e.add(e);

		return val;
	}

	public static List<DecaByte> k_v1(DecaByte I){
		DecaByte X=new DecaByte();
		DecaByte Y=new DecaByte();
		X.setBit(0,I.getBit(0));
		for (int i = 1; i < DecaByte.SIZE; i++) {
			X.setBit(i,X.getBit(i-1)|I.getBit(i));
		}

		Y.setBit(0,!X.getBit(9));
		for (int i = 1; i < DecaByte.SIZE; i++) {
			Y.setBit(i,
					Y.getBit(i-1)|(
							X.getBit(i-1)&I.getBit(i)
					));
		}

//		System.out.println("I:"+I.toStringAsBin());
//		System.out.println("X:"+X.toStringAsBin());
//		System.out.println("Y:"+Y.toStringAsBin());
//		System.out.println("###################");
		return Arrays.asList(I,X,Y);
	}

	public static List<DecaByte> k_vBase(DecaByte dI){
		boolean x1, x2, x3, x4, x5, x6, x7, x8, x9,
				y1, y2, y3, y4, y5, y6, y7, y8, y9;

		boolean[] I = dI.getDeca();

		x1= I[0]|I[1];x2= x1|I[2];x3= x2|I[3];x4= x3|I[4];x5= x4|I[5];x6= x5|I[6];
		x7= x6|I[7];x8= x7|I[8];x9= x8|I[9];
		y1=(!x9)|(I[0]&I[1]);y2=y1|(x1&I[2]);y3=y2|(x2&I[3]);y4=y3|(x3&I[4]);y5=y4|(x4&I[5]);
		y6=y5|(x5&I[6]);y7=y6|(x6&I[7]);y8=y7|(x7&I[8]);y9=y8|(x8&I[9]);

		DecaByte X = new DecaByte(new boolean[]{
				I[0],x1,x2,x3,x4,x5,x6,x7,x8,x9});
		DecaByte Y = new DecaByte(new boolean[]{
				!x9,y1,y2,y3,y4,y5,y6,y7,y8,y9});

//		System.out.println("I:"+dI.toStringAsBin());
//		System.out.println("X:"+X.toStringAsBin());
//		System.out.println("Y:"+Y.toStringAsBin());
//		System.out.println("###################");
		return Arrays.asList(dI,X,Y);

	}

	public static void main(String[] args) {
//		BufferQueue<DecaByte> queue=new BufferQueue<>(5,new DecaByte());
//		for (int i = 0; i < 10; i++) {
//			queue.push(new DecaByte(i));
//			System.out.println(queue.toString());
//		}
//		System.out.println(queue.cell(0)+"\t"+queue.cell(4));

		for (int i = 1; i < 1024; i++) {
//			List<DecaByte> db1 = k_vBase(new DecaByte(i));
			List<DecaByte> db2 = k_v1(new DecaByte(i));
			DecaByte I=db2.get(0);
			DecaByte X=db2.get(1);
			DecaByte Y=db2.get(2);
			int index = X.toStringAsBin().indexOf("1");
			if (
//					I.toStringAsBin().indexOf("1")!=X.toStringAsBin().indexOf("1")&&
//					index!=-1&&index!=9&&Y.toStringAsBin().charAt(index+1)=='1'
					Y.getBit(9)==false
			){
				System.out.println("num="+i);
				System.out.println("I:"+I.toStringAsBin()+"\t"+I.toString());
				System.out.println("X:"+X.toStringAsBin()+"\t"+X.toString());
				System.out.println("Y:"+Y.toStringAsBin()+"\t"+Y.toString());
				System.out.println("###################");
			}
		}


	}
}