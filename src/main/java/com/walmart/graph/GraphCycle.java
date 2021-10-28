package com.walmart.graph;

public class GraphCycle {

    public static void main(String[] args) {
        int V = 3, E = 3;
        GraphCycle graph =new GraphCycle(V, E);
        graph.edges[0].src = 0;
        graph.edges[0].dest = 1;

        // add edge 1-2
        graph.edges[1].src = 1;
        graph.edges[1].dest = 2;

        // add edge 0-2
        graph.edges[2].src = 0;
        graph.edges[2].dest = 2;

        if (graph.isCycle(graph)==1)
            System.out.println( "graph contains cycle" );
        else
            System.out.println( "graph doesn't contain cycle" );
    }
    class Edge {
        int src;
        int dest;
    }
    int V;
    int E;
    Edge edges[];

    public GraphCycle(int v, int e) {
        V = v;
        E = e;
        edges = new Edge[e];
        for(int i = 0; i < e; i++) {
            edges[i] = new Edge();
        }
    }

    int find(int i, int[] parent){
        while(i != parent[i]) {
            parent[i] = parent[parent[i]];
            i = parent[i];
        }
        return i;
    }
    private void union(int x, int y,int[] parent) {
        int rootx = find(x, parent);
        int rooty = find(y, parent);
        if(rootx != rooty) {
            parent[x] = y;
        }
    }

    int isCycle(GraphCycle gc) {
        int[] parent = new int[gc.V];
        for(int i = 0; i < gc.V; i++) {
            parent[i] = i;
        }
        for(Edge e : gc.edges) {
            int x = gc.find(e.src, parent);
            int y = gc.find(e.dest, parent);

            if(x == y) {
                return 1;
            }
            gc.union(x, y, parent);
        }
        return 0;
    }
}
