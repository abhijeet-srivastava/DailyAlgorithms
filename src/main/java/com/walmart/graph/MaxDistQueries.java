package com.walmart.graph;

import java.util.HashMap;
import java.util.Map;

public class MaxDistQueries {
    public static void main(String[] args) {
        MaxDistQueries md = new MaxDistQueries();
        md.testQueries();
    }

    private void testQueries() {
        int n = 3;
        int[][] edges = {{0,1,2},{1,2,4},{2,0,8},{1,0,16}};
        int[][] queries = {{0,1,2},{0,2,5}};
        boolean[] res = distanceLimitedPathsExist(n, edges, queries);
        System.out.printf("res:\n");
    }

    public boolean[] distanceLimitedPathsExist(int n, int[][] edgeList, int[][] queries) {
        boolean[]  result = new boolean[queries.length];
        Map<Integer, Integer>[] GRAPH = new Map[n];
        for(int i = 0; i < n; i++) {
            GRAPH[i] = new HashMap<Integer, Integer>();
        }
        for(int[] edge: edgeList) {
            if(!GRAPH[edge[0]].containsKey(edge[1])
                    || GRAPH[edge[0]].get(edge[1]) > edge[2]) {
                GRAPH[edge[0]].put(edge[1], edge[2]);
                GRAPH[edge[1]].put(edge[0], edge[2]);
            }
        }
        for(int i = 0; i < queries.length; i++) {
            boolean[] visited = new boolean[n];
            result[i] = dfs(queries[i][0], queries[i], visited, GRAPH);
        }

        return result;
    }

    private boolean dfs(int current, int[] query, boolean[] visited, Map<Integer, Integer>[] GRAPH) {
        if(current == query[1]) {
            return true;
        }
        if(visited[current]) {
            return false;
        }
        visited[current] = true;
        Map<Integer, Integer> adjacents = GRAPH[current];
        for(Map.Entry<Integer, Integer> next : adjacents.entrySet()) {
            if(visited[next.getKey()] || next.getValue() >= query[2]) {
                continue;
            }
            boolean isLessThen = dfs(next.getKey(), query, visited, GRAPH);
            if(isLessThen) {
                return true;
            }
        }
        return false;
    }
}
