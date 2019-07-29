package Demos;

import Experiments.Local.DecaByte;

public class demo_XY {

	public static void main(String[] args) {
		System.out.println("values of I[] for which y9=F :");
		System.out.println("---------------");
		for (int i = 0; i < 1024; i++) {
			DecaByte I = new DecaByte(i);
			boolean y9 = run_XY(I.getDeca());
			if (y9 == false) {
				System.out.printf("%s = %d\n", I.toStringAsBin(), I.asInt());
			}
		}

	}

	public static boolean run_XY(boolean[] I) {
		boolean x1, x2, x3, x4, x5, x6, x7, x8, x9, y1, y2, y3, y4, y5, y6, y7, y8, y9;
		x1= I[0]|I[1];x2= x1|I[2];x3= x2|I[3];x4= x3|I[4];x5= x4|I[5];x6= x5|I[6];
		x7= x6|I[7];x8= x7|I[8];x9= x8|I[9];
		y1=(!x9)|(I[0]&I[1]);y2=y1|(x1&I[2]);y3=y2|(x2&I[3]);y4=y3|(x3&I[4]);y5=y4|(x4&I[5]);
		y6=y5|(x5&I[6]);y7=y6|(x6&I[7]);y8=y7|(x7&I[8]);y9=y8|(x8&I[9]);
		return y9;
	}
}
