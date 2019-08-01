package Demos.exps_giladNgil;

import Demos.known_results.list239_server;
import Experiments.Local.DecaByte;
import Experiments.Local.v3.DecaState;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class check_server_list {

    public static void main(String[] args) {
        String[] list = list239_server.listState;
        System.out.println(list[0]);

        List<DecaState> decaStates = Arrays.stream(list).map(DecaState::new).collect(Collectors.toList());
        List<DecaByte> decaBytes = decaStates.stream().map(DecaState::asDecaByte).collect(Collectors.toList());

        List<DecaByte> missing = IntStream.range(0, 1024)
                .filter(value -> Integer.bitCount(value) == 5)
                .boxed()
                .map(integer -> DecaByte.toBin(integer, 10))
                .map(DecaByte::new)
                .filter(s -> !decaBytes.contains(s))
                .collect(Collectors.toList());

//        System.out.println(decaStates.size());
//        System.out.println(missing.size());
//        System.out.println(missing);

        HashMap<DecaByte, ArrayList<DecaByte>> map1diff = graphMissingVals(missing);

//        String from="803";
//        String[] path= {"810", "872", "968"};
//        List<List<DecaState>> sequences = getLists("803", "810", "872", "968");
        List<List<DecaState>> sequences = getLists("868", "964", "961", "968" ,"872", "810", "803");

//        new int {
//
//        };
//
        canBeAddedTo(list, sequences);

//        System.out.println("\n");
//        DecaState test = froms.get(1).get(0);
//        System.out.println(test+"\n------------");
//        test.prevIterations().forEach(System.out::println);
//        System.out.println();
//
//        List<List<DecaState>> next810 = getNextOf(froms, "810");
//        List<List<DecaState>> next872 = getNextOf(next810, "872");
//        List<List<DecaState>> next968 = getNextOf(next872, "968");

//        System.out.println(next968.size());
    }

    public static List<List<DecaState>> getLists(String from, String... path) {
        List<List<DecaState>> froms = DecaState.allPerms().stream()
                .map(ttl -> new DecaState(Integer.parseInt(from), ttl)).map(Arrays::asList).collect(Collectors.toList());
        for (String s : path) {
            froms=getNextOf(froms,s);
            System.out.println(froms.size());
        }
        return froms;
    }

    public static HashMap<DecaByte, ArrayList<DecaByte>> graphMissingVals(List<DecaByte> missing) {
        HashMap<DecaByte, ArrayList<DecaByte>> map1diff = new HashMap<>();
        for (DecaByte miss : missing) {
            map1diff.putIfAbsent(miss,new ArrayList<>());
            for (DecaByte other : missing) {
                if (miss==other) continue;
                DecaByte xor = miss.XOR(other);
                DecaByte deadBit = xor.AND(miss);
                DecaByte newBit = xor.AND(other);
                if (deadBit.bitCount()==1 && newBit.bitCount()==1) {
                    map1diff.get(miss).add(other);
                }
            }
        }
//        System.out.println(map1diff.entrySet().stream()
//           .sorted(Comparator.comparingInt(o -> o.getValue().size())).map(Map.Entry::getKey).map(DecaByte::toStringAsBin).collect(Collectors.toList()));
        return map1diff;
    }

    public static void canBeAddedTo(String[] list, List<List<DecaState>> sequences) {
        for (List<DecaState> states : sequences) {
            DecaState head = states.get(0);
            DecaState tail = states.get(states.size()-1);
            ArrayList<DecaState> prevsHead = head.prevIterations();
            ArrayList<DecaState> nextsTail = tail.nextIterations();

            for (DecaState prev : prevsHead) {
                String prevStr = prev.toString();
                for (DecaState next : nextsTail) {
                    String nextStr = next.toString();

                    // at start
                    if (list[0].equals(nextStr)) {
                        System.out.println("start!");
                    }
                    if (list[list.length-1].equals(prevStr)) {
                        System.out.println("end!");
                    }

                    // find 'split'
                    for (int i = 0; i < list.length-1; i++) {
                        if (list[i].equals(prevStr) && list[i+1].equals(nextStr)) {
                            System.out.println(i+"  aksjdhaksjdhakdh");
                        }
                    }
                }
            }

//            System.out.println(states);
        }
    }

    public static List<List<DecaState>> getNextOf(List<List<DecaState>> froms, String to) {
        ArrayList<List<DecaState>> nexts = new ArrayList<>();
        for (List<DecaState> fromList : froms) {
            DecaState last = fromList.get(fromList.size() - 1);
            for (DecaState next : last.nextIterations()) {
                if (next.asDecaByte().toString().equals(to)) {
                    ArrayList<DecaState> newList = new ArrayList<>(fromList);
                    newList.add(next);
                    nexts.add(newList);
                    break;
                }
            }
        }
        return nexts;
    }

//    public List<DecaState> getNextOf(List<DecaState> froms,String to){
//        List<DecaState> next = froms.stream()
//                .filter(decaState ->
//                        decaState.nextIterations().stream()
//                        .anyMatch(decaState1 -> decaState1.asDecaByte().toString().equals(to)))
//
//                .collect(Collectors.toList());
//        return next;
//    }

}
