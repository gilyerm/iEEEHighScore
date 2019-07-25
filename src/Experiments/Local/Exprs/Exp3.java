package Experiments.Local.Exprs;
import static Experiments.Local.BaseProgram.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static Experiments.Local.BaseProgram.solMain;
import static Experiments.Local.BaseProgram.toBin;

public class Exp3 {

	// notes:

	// conclusion:


	public static void main(String[] args) {


		for (int i=0; i<1024; i++) {
			for (int j=0; j<1024; j++) {
//				for (int r=0; r<1024; r++) {
					List<String> collect = Arrays.asList(i,j).stream()
							.map(k -> toBin(k, 10)).collect(Collectors.toList());
				Helper h = solMain(collect);
				if (h.res > 1000)
						System.out.printf("%d;%d \t=>\t %s\n", i, j, h.indexs);
//				}
			}
		}

	}

}

