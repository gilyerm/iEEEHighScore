package Experiments.Local;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static Experiments.Local.BaseProgram.*;

public class Exp2 {

	// notes:

	// conclusion:


	public static void main(String[] args) {

		List<String> collect = Arrays.asList(15,16).stream()
				.map(j -> toBin(j, 10)).collect(Collectors.toList());
		solMain(collect);

	}

}

