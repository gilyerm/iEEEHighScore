package Experiments.Local;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static Experiments.Local.BaseProgram.*;
import static Experiments.Local.PrevResults.working2len.first2Diff;

public class Exp4_1 {

	// notes:

	// conclusion:

	public static void main(String[] args) {
		for (int[] f2 : first2Diff) {
			for (int i=0; i<1024; i++) {
				List<String> collect = Arrays.asList(f2[0],f2[1],i).stream()
						.map(k -> toBin(k, 10)).collect(Collectors.toList());
				Helper h = solMain(collect);
				if (h.indexs.get(2) !=255 && Integer.bitCount(i)!=1)
					System.out.printf("%s,%d \t=>\t %s\n", Arrays.toString(f2), i, h.indexs);
			}
		}
	}

}

