package com.walmart.graph;

import org.jetbrains.annotations.NotNull;

public class MST {
    class Graph {
        int V;
        int E;
        Edge[] edges;
        public Graph(int v, int e) {
            V = v;
            E = e;
            this.edges = new Edge[e];
            for(int i = 0; i < e; i++) {
                this.edges[i] = new Edge();
            }
        }
    }
    class Edge implements Comparable<Edge>{
        int src,  dest, weight;

        @Override
        public int compareTo(@NotNull MST.Edge o) {
            return this.weight - o.weight;
        }
    }
    class subset {
        int parent, rank;
    }

    int find(int x, subset[] parentMapping) {
        while(x != parentMapping[x].parent) {
            parentMapping[x].parent = parentMapping[]
        }
    }
}
