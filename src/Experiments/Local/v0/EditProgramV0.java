package Experiments.Local.v0;

import Experiments.Local.BufferQueue;
import Experiments.Local.DecaByte;

import java.util.Scanner;

public class EditProgramV0 {

	public static boolean[] B = new boolean[50];
	public static char[] M = new char[256];

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		boolean[] I = new boolean[10];
		for (int i = 0; i < n; i++) {
			String s = sc.next();
			for (int j = 0; j < 10; j++) {
				I[j] = (s.charAt(j) & 1) != 0;
			}
			f(new DecaByte(I));
		}
		System.out.println(1000*sum(M)-n);

	}
	public static int sum(char[] M) {
		int retVal = 0;
		for (char c : M) retVal += c;
		return retVal;
	}
	public static void f(DecaByte I) {
		BufferQueue<DecaByte> B =
				new BufferQueue<>(5,new DecaByte());
		B.push(I);
		DecaByte C=new DecaByte();
		for (int i = 0; i < B.getMaxSize(); i++) {
			C = DecaByte.OR(C,B.cell(i));
		}
		boolean c0=C.getBit(0),c5=C.getBit(5),
				c1=C.getBit(1),c6=C.getBit(6),
				c2=C.getBit(2),c7=C.getBit(7),
				c3=C.getBit(3),c8=C.getBit(8),
				c4=C.getBit(4),c9=C.getBit(9);
		DecaByte A;
		if (C.bitCount()==5 && I.bitCount()==1){ /// byE byE
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
		}
		else {
			A = new DecaByte(255<<2);
		}
		int val = 0;
		for (int i = 7; i >= 0; i--) {
			val = val << 1;
			if (A.getBit(i)) val |= 1;
		}
		M[val]=1;
	}

}