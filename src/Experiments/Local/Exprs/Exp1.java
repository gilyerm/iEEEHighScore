package Experiments.Local.Exprs;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static Experiments.Local.BaseProgram.*;

public class Exp1 {

	// notes:
	// by mistake (not resetting STATICs with helper), got [15,16] to score=1998 !

	// conclusion:
	// some pairs can turn cells M[val] other then 255.


	public static void main(String[] args) {
		for (int i=0; i<1024; i++)
			System.out.printf("%d = %s\n",i,toBin(i,10));

		for (int i=0; i<1024; i++) {
			int t = i;
			List<String> collect = Arrays.asList(t).stream()
					.map(j -> toBin(j, 10)).collect(Collectors.toList());
			Helper h = solMain(collect);
			if (h.indexs.size() == 1)
				System.out.println(h.indexs);
		}
	}

}
