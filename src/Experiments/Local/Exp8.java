package Experiments.Local;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static Experiments.Local.BaseProgram.*;
import static Experiments.Local.PrevResults.working2LenUNIQUE.unique2Diff;
import static Experiments.Local.PrevResults.working2len.first2Diff;

public class Exp8 {

	// notes:

	// conclusion:


	public static void main(String[] args) {

		/// HAAHAHHAHAHAHAHAHHA  LITTLE WORK

		ArrayList<Integer> delim3_0s = new ArrayList<>();
		int[] cur = unique2Diff[0];
		delim3_0s.addAll(Arrays.asList(cur[0],cur[1]));

		for (int i=1; i<unique2Diff.length; i++) {
			cur = unique2Diff[i];
			delim3_0s.addAll(Arrays.asList(0,0,0));
			delim3_0s.addAll(Arrays.asList(cur[0],cur[1]));
		}


		List<String> collect = delim3_0s.stream()
				.map(k -> toBin(k, 10)).collect(Collectors.toList());

		collect.forEach(x -> System.out.println("\""+x+"\","));

		System.out.println(solMain(collect));


	}
}
