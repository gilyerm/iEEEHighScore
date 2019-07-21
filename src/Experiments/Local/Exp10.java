package Experiments.Local;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static Experiments.Local.BaseProgram.*;
import static Experiments.Local.PrevResults.working2len.first2Diff;

public class Exp10 {

	// notes:

	// conclusion:
	
	public static void main(String[] args) {
		HashMap<Integer,List<Integer>> map = new HashMap<>();
		for (int[] f2 : first2Diff) {
			map.putIfAbsent(f2[0], new ArrayList<>());
			map.get(f2[0]).add(f2[1]);
		}
		map.keySet().stream().sorted().forEach(first -> {
					System.out.println(first+"=["+toBin(first,10)+"]"
							+", 5LF=["+toBin(first,10).substring(0,5)+"]"
							+" => "+map.get(first));
		}
		);
	}

}

