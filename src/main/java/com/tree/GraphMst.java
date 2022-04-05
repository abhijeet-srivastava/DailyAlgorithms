package com.tree;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GraphMst {

    public static void main(String[] args) {
        GraphMst gm = new GraphMst();
        gm.testCriticalEdges();
    }

    private void testCriticalEdges() {
        int[][] edges = {{0,1,1},{1,2,1},{2,3,2},{0,3,2},{0,4,3},{3,4,3},{1,4,6}};
        List<List<Integer>> list = findCriticalAndPseudoCriticalEdges(5, edges);
    }

    class DSU {
        int[] parent;
        int[] rank;

        public DSU(int n) {
            this.parent = new int[n];
            this.rank = new int[n];
            for(int i = 0; i < n; i++) {
                parent[i] = i;
                rank[i] = 1;
            }
        }
        public int find(int x) {
            while(x != parent[x]) {
                parent[x] = parent[parent[x]];
                x = parent[x];
            }
            return parent[x];
        }
        public void union(int x, int y) {
            int px = find(x);
            int py = find(y);
            if(px == py) {
                return;
            } else if(rank[px] < rank[py]) {
                parent[px] = py;
            } else {
                parent[py] = px;
                if(rank[px] == rank[py]) {
                    rank[px] += 1;
                }
            }
        }
    }
    public List<List<Integer>> findCriticalAndPseudoCriticalEdges(int n, int[][] edges) {
        List<Integer> critical = new ArrayList<>();
        List<Integer> pCritical = new ArrayList<>();
        Arrays.sort(edges, Comparator.comparingInt((int[] e) -> e[2]));
        Set<Integer> mstEdge = new HashSet<>();
        int cost = cost(n, edges, edges.length+1, mstEdge);
        for(int i = 0; i < edges.length; i++) {
            Set<Integer> set = new HashSet<>();
            int remCost = cost(n, edges, i, set);
            if(remCost < cost) {
                critical.add(i);
            }
        }
        List<List<Integer>> result = new ArrayList<>();
        result.add(critical);
        result.add(pCritical);
        return result;
    }
    private int cost(int n, int[][] edges, int skipEdge, Set<Integer> mstEdge) {
        int cost = 0;
        DSU dsu = new DSU(n);
        for(int i = 0; i < edges.length; i++) {
            if(i == skipEdge) {
                continue;
            }
            int[] edge = edges[i];
            int ps = dsu.find(edge[0]);
            int pd = dsu.find(edge[1]);
            if(ps == pd) {
                continue;
            }
            dsu.union(ps, pd);
            cost += edge[2];
            mstEdge.add(i);
        }
        //System.out.printf("Skip : %d, Cost: %d\n", skipEdge, cost);
        return cost;
    }

}
