package Experiments.Local.Exprs;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static Experiments.Local.PrevResults.working2len.first2Diff;
import static Experiments.Local.v1.EditProgramV1.*;

import Experiments.Local.DecaByte;
import Experiments.Local.v1.EditProgramV1.Helper;

public class Exp4_2 {

	// notes:

	// conclusion:
	
	public static void main(String[] args) {
		for (int[] f2 : first2Diff) {
//			for (int[] f3 : first2Diff) {
				List<String> collect = Stream.of(f2[0],f2[1])
						.map(k -> DecaByte.toBin(k, DecaByte.SIZE)).collect(Collectors.toList());
				Helper h = solMain(collect);
//				if (h.indexs.get(2) !=255 && h.indexs.get(4) !=255)
					System.out.printf("%s \t=>\t %s\t%s\n", Arrays.toString(f2), h.indexs, h.e.toString() );
//			}
		}
	}

}

