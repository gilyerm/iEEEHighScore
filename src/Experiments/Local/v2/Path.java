package Experiments.Local.v2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class Path {
    // data structure to store graph edges
    public static class Edge<T> {
        T source, dest;

        public Edge(T source, T dest) {
            this.source = source;
            this.dest = dest;
        }
    }

    ;

    // class to represent a graph object
    public static class Graph<T> {
        // A List of Lists to represent an adjacency list
        Map<T, List<T>> adjList = null;

        // Constructor
        Graph(List<Edge<T>> edges) {
            adjList = new ConcurrentHashMap<>();
            // add edges to the undirected graph
            for (int i = 0; i < edges.size(); i++) {
                T src = edges.get(i).source;
                T dest = edges.get(i).dest;
                adjList.putIfAbsent(src, new ArrayList<>());
                adjList.putIfAbsent(dest, new ArrayList<>());

                adjList.get(src).add(dest);
                adjList.get(dest).add(src);
            }
        }
    }

    public static class Main {
        public static <T extends DecaState> List<T> printAllHamiltonianPaths(Graph<T> g,
                                                           T v,
                                                           Map<T, Boolean> visited,
                                                           List<T> path,
                                                           int N) {
            // if all the vertices are visited, then
            // hamiltonian Path exists
            if (path.size() == N) {
                // print hamiltonian Path
                for (T i : path)
                    System.out.print(i + " ");
                System.out.println();

                return path;
            }

            // Check if every edge starting from vertex v leads
            // to a solution or not
            for (T w : g.adjList.get(v)) {
                ArrayList<DecaState> decaStates = v.nextIterations();
                boolean flag=false;
                for (DecaState decaState : decaStates) {
                    if (decaState.deepEquals(w)) {
                        flag=true;
                    }
                }
                if (!flag) continue;
                // process only unvisited vertices as hamiltonian
                // Path visits each vertex exactly once
                if (!visited.getOrDefault(w, false)) {
                    visited.put(w, true);
                    path.add(w);

                    // check if adding vertex w to the Path leads
                    // to solution or not
                    List<T> integers = printAllHamiltonianPaths(g, w, visited, path, N);
                    if (integers != null) return integers;
                    // Backtrack
                    visited.put(w, false);
                    path.remove(path.size() - 1);
                }
            }
            return null;
        }



        public static <T extends DecaState> List<T> run(List<T[]> list) {
            // List of graph edges as per above diagram
            List<Edge<T>> edges = list.parallelStream().map(ts -> new Edge<T>(ts[0], ts[1])).collect(Collectors.toList());


            // create a graph from edges
            Graph<T> g = new Graph<T>(edges);

            // Set number of vertices in the graph
            final int N = g.adjList.keySet().size();
            // starting node
            T start = g.adjList.keySet().stream().findFirst().get();

            // add starting node to the Path
            List<T> path = new ArrayList<>();
            path.add(start);

            // mark start node as visited
            Map<T, Boolean> visited = new ConcurrentHashMap<>();
//        boolean[] visited = new boolean[N];
            visited.put(start, true);

            List<T> integers = printAllHamiltonianPaths(g, start, visited, path, N);
            System.out.println(integers);
            return integers;
        }
    }
}