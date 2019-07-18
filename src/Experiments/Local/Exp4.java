package Experiments.Local;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static Experiments.Local.BaseProgram.*;
import static Experiments.Local.PrevResults.working2len.first2Diff;

public class Exp4 {

	// notes:

	// conclusion:


	public static void main(String[] args) {

		for (int[] f2 : first2Diff) {
			for (int i=0; i<1024; i++) {
				List<String> collect = Arrays.asList(f2[0],f2[1],i).stream()
						.map(k -> toBin(k, 10)).collect(Collectors.toList());
				Helper h = solMain(collect);
				if (h.res > 2000)
					System.out.printf("%s,%d \t=>\t %s\n", Arrays.toString(f2), i, h.indexs);
			}
		}

	}

}

