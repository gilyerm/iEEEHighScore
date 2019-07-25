package Experiments.Local;


import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static Experiments.Local.v3.EditProgramV3.*;

public class Exp2_2gil {

	// notes:

	// conclusion:



	public static void main(String[] args) {

		List<String> collect = Stream.of(2,4,8,16,32,64,128,256,256)
				.map(j -> DecaByte.toBin(j, DecaByte.SIZE)).collect(Collectors.toList());
		Helper h = solMain(collect);
		System.out.printf("%s \t=>\t %s\t%s\n", collect.toString(), h.indexs, h.e.toString() );

	}

}

