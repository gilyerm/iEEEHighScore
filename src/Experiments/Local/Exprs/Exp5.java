package Experiments.Local.Exprs;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static Experiments.Local.BaseProgram.*;
import static Experiments.Local.PrevResults.working2len.first2Diff;

public class Exp5  {

	// notes:

	// conclusion:


	public static void main(String[] args) {

		List<String> collect = Arrays.asList(992,32,64,128,256,512).stream()
				.map(j -> toBin(j, 10)).collect(Collectors.toList());
		System.out.println(solMain(collect));

		int f2[] = {15,16};
		for (int i=0; i<1024; i++) {
			Helper h = solMain(Arrays.asList(f2[0], f2[1], i).stream()
					.map(k -> toBin(k, 10)).collect(Collectors.toList()));
			if (h.last != 255)
					System.out.println(i+": "+h.last);
//			if (solMain(collect1) > 2000)
//			System.out.printf("%s,%d \t=>\t %s\n", Arrays.toString(f2), i, indexs);
		}

	}
}

