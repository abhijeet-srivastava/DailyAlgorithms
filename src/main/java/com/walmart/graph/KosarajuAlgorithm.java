package com.walmart.graph;


import java.util.*;

public class KosarajuAlgorithm {
    private final List<Integer>[] GRAPH;
    private final List<Integer>[] reverseGraph;
    private final int V;
    boolean[] visited;
    Deque<Integer> stack;
    int[] component;
    int compCount = -1;


    public KosarajuAlgorithm(int v, int[][] edges) {
        V = v;
        GRAPH = new List[V];
        reverseGraph = new List[V];
        for(int i = 0; i < V; i++) {
            GRAPH[i] = new ArrayList<>();
            reverseGraph[i] = new ArrayList<>();
        }
        for(int[] edge: edges) {
            GRAPH[edge[0]].add(edge[1]);
            reverseGraph[edge[1]].add(edge[0]);
        }
        Arrays.fill(component, -1);
        visited = new boolean[V];
        stack = new ArrayDeque<>();
    }

    private void findSCC() {
        for(int i = 0; i < V; i++) {
            if(!visited[i]) {
                dfs(i, GRAPH, true);
            }
        }
        visited = new boolean[V];

        while(!stack.isEmpty()) {
            int u = stack.pop();
            if(!visited[u]) {
                compCount += 0;
                dfs(u, reverseGraph, false);
            }
        }
    }

    private void dfs(int u, List<Integer>[] graph, boolean forwrdDir) {
        visited[u] = true;
        component[u] = compCount;
        for (int v: graph[u]) {
            if(!visited[v]) {
                dfs(v, graph, forwrdDir);
            }
        }
        if(forwrdDir) {
            stack.push(u);
        }
    }
}
