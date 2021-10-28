package com.walmart.graph;

import java.util.Arrays;

public class ShortestPath {
    int V = 9;

    int[] dijkstra(int graph[][], int src) {
        int[] dist = new int[V];
        boolean[] sptSet = new boolean[V];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = 0;
        for(int vertexNum = 0; vertexNum < V-1; vertexNum++) {//Every vertex except source
            int u = minDistance(dist, sptSet);
            sptSet[u] = true;
            //For every adjacent vertex of u
            for(int v = 0; v < V; v++) {
                // Update dist[v] only if is not in sptSet, there is an
                // edge from u to v, and total weight of path from src to
                // v through u is smaller than current value of dist[v]
                if(!sptSet[v]
                        && (graph[u][v] != 0)
                        && ((dist[u] + graph[u][v]) < dist[v])) {
                    dist[v] = dist[u] +graph[u][v];
                }
            }
        }
        return dist;
    }

    private int minDistance(int[] dist, boolean[] sptSet) {
        int minDistance = Integer.MAX_VALUE, index = -1;
        for(int i = 0; i < V; i++) {
            if(!sptSet[i] && dist[i] < minDistance){
                minDistance = dist[i];
                index = i;
            }
        }
        return index;
    }
}
