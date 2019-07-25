package Experiments.Local.Exprs;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static Experiments.Local.BaseProgram.*;
import static Experiments.Local.PrevResults.working2LenUNIQUE.unique2Diff;

public class Exp9 {

	// notes:

	// conclusion:


	public static void main(String[] args) {

		int[][] pairList = unique2Diff;
		HashMap<int[], ArrayList<int[]>> map = new HashMap<>();
		for (int[] f1 : pairList) {
			for (int[] f2 : pairList) {
				List<String> collect = Arrays.asList(f1[0], f1[1], 0,0, f2[0], f2[1]).stream()
						.map(k -> toBin(k, 10)).collect(Collectors.toList());
				Helper h = solMain(collect);
				if (h.indexs.size() > 2) {
//					count_2_0++;
//					System.out.printf("%s,%s \t=>\t %s\n",
//							Arrays.toString(f1), Arrays.toString(f2), indexs);
					map.putIfAbsent(f1, new ArrayList<>());
					map.get(f1).add(f2);
				}
			}
		}
//		System.out.println(count_2_0);
		System.out.println(pairList.length);
		System.out.println(map.keySet().size());

		ArrayList<int[][]> flat = new ArrayList();
		for (int[] src : map.keySet()) {
			for (int[] tgt : map.get(src)) {
				int[][] st = new int[2][];
				st[0] = src;
				st[1] = tgt;
				flat.add(st);
			}
		}

		List<int[]> path = Path.Main.run(flat);

		ArrayList<Integer> delim2_0s = new ArrayList<>();
		int[] cur = path.get(0);
		delim2_0s.addAll(Arrays.asList(cur[0],cur[1]));
		for (int i=1; i<path.size(); i++) {
			cur = path.get(i);
			delim2_0s.addAll(Arrays.asList(0,0));
			delim2_0s.addAll(Arrays.asList(cur[0],cur[1]));
		}

		List<String> collect = delim2_0s.stream()
				.map(k -> toBin(k, 10)).collect(Collectors.toList());

//		collect.forEach(x -> System.out.println("\""+x+"\","));

		System.out.println(solMain(collect));


	}


	static class Path {

		// data structure to store graph edges
		static class Edge {
			int[] source, dest;
			public Edge(int[] source, int[] dest) {
				this.source = source;
				this.dest = dest;
			}
		}

		// class to represent a graph object
		static class Graph {
			// A List of Lists to represent an adjacency list
			Map<int[], List<int[]>> adjList = null;

			// Constructor
			Graph(List<Edge> edges) {
				adjList = new ConcurrentHashMap<>();
				// add edges to the undirected graph
				for (int i = 0; i < edges.size(); i++) {
					int[] src = edges.get(i).source;
					int[] dest = edges.get(i).dest;
					adjList.putIfAbsent(src, new ArrayList<>());
					adjList.putIfAbsent(dest, new ArrayList<>());

					adjList.get(src).add(dest);
					adjList.get(dest).add(src);
				}
			}
		}

		static class Main {
			public static List<int[]> printAllHamiltonianPaths(Graph g,
															   int[] v,
															   Map<int[], Boolean> visited,
															   List<int[]> path,
															   int N) {
				// if all the vertices are visited, then
				// hamiltonian Path exists
				if (path.size() == N) {
					// print hamiltonian Path
					for (int[] i : path)
						System.out.print(Arrays.toString(i) + " ");
					System.out.println();

					return path;
				}

				// Check if every edge starting from vertex v leads
				// to a solution or not
				for (int[] w : g.adjList.get(v)) {
					// process only unvisited vertices as hamiltonian
					// Path visits each vertex exactly once
					if (!visited.getOrDefault(w, false)) {
						visited.put(w, true);
						path.add(w);

						// check if adding vertex w to the Path leads
						// to solution or not
						List<int[]> integers = printAllHamiltonianPaths(g, w, visited, path, N);
						if (integers != null) return integers;
						// Backtrack
						visited.put(w, false);
						path.remove(path.size() - 1);
					}
				}
				return null;
			}

//			public static void main(String[] args) {
//				int[] a0 = {0}, a1 = {1}, a2 = {2}, a3 = {3};
//				// List of graph edges as per above diagram
//				List<Edge> edges = Arrays.asList(
//						new Edge(a0, a1), new Edge(a0, a2), new Edge(a0, a3),
//						new Edge(a1, a2), new Edge(a1, a3), new Edge(a2, a3)
//				);
//
//
//				// create a graph from edges
//				Graph g = new Graph(edges);
//
//				// Set number of vertices in the graph
//				final int N = g.adjList.keySet().size();
//				// starting node
//				int[] start = g.adjList.keySet().stream().findFirst().get();
//
//				// add starting node to the Path
//				List<int[]> Path = new ArrayList<>();
//				Path.add(start);
//
//				// mark start node as visited
//				Map<int[], Boolean> visited = new ConcurrentHashMap<>();
////        boolean[] visited = new boolean[N];
//				visited.put(start, true);
//
//				List<int[]> integers = printAllHamiltonianPaths(g, start, visited, Path, N);
//				System.out.println(integers);
//				System.out.println(integers);
//			}

			public static List<int[]> run(List<int[][]> list) {
				// List of graph edges as per above diagram
				List<Edge> edges = list.parallelStream().map(integers -> new Edge(integers[0], integers[1])).collect(Collectors.toList());
//        List<Edge> edges = Arrays.asList(
//                new Edge(0, 1), new Edge(0, 2), new Edge(0, 3),
//                new Edge(1, 2), new Edge(1, 3), new Edge(2, 3)
//        );


				// create a graph from edges
				Graph g = new Graph(edges);

				// Set number of vertices in the graph
				final int N = g.adjList.keySet().size();
				// starting node
				int[] start = g.adjList.keySet().stream().findFirst().get();

				// add starting node to the Path
				List<int[]> path = new ArrayList<>();
				path.add(start);

				// mark start node as visited
				Map<int[], Boolean> visited = new ConcurrentHashMap<>();
//        boolean[] visited = new boolean[N];
				visited.put(start, true);

				List<int[]> integers = printAllHamiltonianPaths(g, start, visited, path, N);
				System.out.println(integers);
				return integers;
			}

		}
	}

}
