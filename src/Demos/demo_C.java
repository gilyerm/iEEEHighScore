package Demos;

import Experiments.Local.DecaByte;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class demo_C {

	public static void main(String[] args) {
		System.out.println("values of C[] for which c95=T :");
		System.out.println("---------------");
		ArrayList<DecaByte> list = new ArrayList<>();
		for (int i = 0; i < 1024; i++) {
			DecaByte C = new DecaByte(i);
			boolean c95 = run_C(C);
			if (c95 == true) {
				list.add(C);
				System.out.printf("%s = %d\t(bc=%d)\n", C.toStringAsBin(), C.asInt(), C.bitCount());
			}
		}
		System.out.println();
		System.out.printf("");

		System.out.println();
		System.out.println("count of values above, for which bitCount!=5 :");
		System.out.println(list.stream().filter(d -> d.bitCount() != 5).count());

	}

	public static boolean run_C(DecaByte C) {

		boolean c0=C.getBit(0),c5=C.getBit(5),
				c1=C.getBit(1),c6=C.getBit(6),
				c2=C.getBit(2),c7=C.getBit(7),
				c3=C.getBit(3),c8=C.getBit(8),
				c4=C.getBit(4),c9=C.getBit(9);

		boolean c10, c11, c12, c20, c21, c22, c23;
		boolean c30, c31, c32, c33, c34, c40, c41, c42, c43, c44, c45;
		boolean c51, c52, c53, c54, c55, c61, c62, c63, c64, c65, c73, c74, c75;
		boolean c84, c85, c95;

		c10=!(c0 | c1);
		c11=c0^c1;c12=c0&c1;
		c20=(c10&(!c2));c21=(c10&c2)|(c11&(!c2));c22=(c11&c2)|(c12&(!c2));c23=(c12&c2);
		c30=(c20&(!c3));c31=(c20&c3)|(c21&(!c3));c32=(c21&c3)|(c22&(!c3));
		c33=(c22&c3)|(c23&(!c3));c34=(c23&c3);c40=(c30&(!c4));c41=(c30&c4)|(c31&(!c4));
		c42=(c31&c4)|(c32&(!c4));c43=(c32&c4)|(c33&(!c4));c44=(c33&c4)|(c34&(!c4));
		c45=(c34&c4);c51=(c40&c5)|(c41&(!c5));c52=(c41&c5)|(c42&(!c5));
		c53=(c42&c5)|(c43&(!c5));c54=(c43&c5)|(c44&(!c5));c55=(c44&c5)|(c45&(!c5));
		c62=(c51&c6)|(c52&(!c6));c63=(c52&c6)|(c53&(!c6));c64=(c53&c6)|(c54&(!c6));
		c65=(c54&c6)|(c55&(!c6));c73=(c62&c7)|(c63&(!c7));c74=(c63&c7)|(c64&(!c7));
		c75=(c64&c7)|(c65&(!c7));c84=(c73&c8)|(c74&(!c8));c85=(c74&c8)|(c75&(!c8));
		c95=(c84&c9)|(c85&(!c9));

		return c95;
	}

}
