package Experiments.Local;

import Experiments.Local.v1.DecaByte;
import Experiments.Local.v1.EditProgramV1.Helper;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static Experiments.Local.v1.EditProgramV1.solMain;

public class Exp2_2gil {

	// notes:

	// conclusion:


	public static void main(String[] args) {

		List<String> collect = Stream.of(15,16,8,4,2,32,64)
				.map(j -> DecaByte.toBin(j, DecaByte.SIZE)).collect(Collectors.toList());
		Helper h = solMain(collect);
		System.out.printf("%s \t=>\t %s\t%s\n", collect.toString(), h.indexs, h.e.toString() );

	}

}

