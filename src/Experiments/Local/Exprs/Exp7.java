package Experiments.Local.Exprs;

import Experiments.Local.BaseProgram;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static Experiments.Local.BaseProgram.*;
import static Experiments.Local.PrevResults.working2len.first2Diff;

public class Exp7 {

	// notes:

	// conclusion:


	public static void main(String[] args) {

		HashMap<Integer, int[]> map = new HashMap<>();
		for (int[] f2 : first2Diff) {
			for (int i = 0; i < 1; i++) {
				List<String> collect = Arrays.asList(f2[0], f2[1]).stream()
						.map(k -> toBin(k, 10)).collect(Collectors.toList());
				BaseProgram.Helper h = solMain(collect);
//				if (true||indexs.size() == 2) {
//					System.out.printf("%s,%s \t=>\t %s\n", Arrays.toString(f2), "-", indexs);
//				}
				map.putIfAbsent(h.indexs.get(1), f2);
			}
		}

//		System.out.println(map);
		for (Integer k : map.keySet()) {
			System.out.printf("%s : %d\n", Arrays.toString(map.get(k)), k);
		}


	}
}

