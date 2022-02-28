package com.walmart.graph;

import java.util.ArrayList;
import java.util.List;

public class ArticulationPointsAndBridges {

    private final List<Integer>[] GRAPH;
    private int V;
    private int[] num, low, parent;
    private int root, rootChildren, counter;
    private boolean[] artPoints;
    private List<int[]> bridges;

    public ArticulationPointsAndBridges(int nodes, int[][] edges) {
        this.V = nodes;
        num = new int[V];
        low = new int[V];
        parent = new int[V];
        artPoints = new boolean[V];
        this.GRAPH = new List[nodes];
        for(int i = 0; i < nodes; i++) {
            GRAPH[i] = new ArrayList<>();
        }
        for(int[] edge: edges) {
            GRAPH[edge[0]].add(edge[1]);
        }
        bridges = new ArrayList<>();
        counter = 0;
    }

    public boolean[] getArtPoints() {
        return artPoints;
    }

    public List<int[]> getBridges() {
        return bridges;
    }

    public void findArtPointsAndBridges() {
        for(int i = 0; i < V; i++) {
            if(num[i] == 0) {
                root = i;
                rootChildren = 0;
                dfs(root);
                if(rootChildren <= 1) {
                    artPoints[root] = false;
                }
            }
        }
    }

    private void dfs(int current) {
        low[current] = num[current] = counter;
        counter += 1;
        for(int child : GRAPH[current]) {
            if(num[child] == 0) {
                //Tree Edge
                parent[child] = current;
                if(current == root) {
                    rootChildren += 1;
                }
                dfs(child);
                if(low[child] >= num[current]) {
                    artPoints[current] = true;
                }
                if(low[child] > num[current]) {
                    bridges.add(new int[]{current, child});
                }
                low[current] = Math.min(low[current], low[child]);
            } else {
                //Back Edge
                if(child != parent[current]) {
                    low[current] = Math.min(low[current], num[child]);
                }
            }
        }
    }
}
