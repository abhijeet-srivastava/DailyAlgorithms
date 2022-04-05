package com.tree;

import java.util.*;

public class Kruskal {

    public static void main(String[] args) {
        Kruskal kruskal = new Kruskal();
        kruskal.testMst();
    }

    private void testMst() {
        List<Edge> edges = new ArrayList<>();
        edges.add(new Edge(0, 1, 7));
        edges.add(new Edge(1, 2, 8));
        edges.add(new Edge(0, 3, 5));
        edges.add(new Edge(1, 3, 9));
        edges.add(new Edge(1, 4, 7));
        edges.add(new Edge(2, 4, 5));
        edges.add(new Edge(3, 4, 15));
        edges.add(new Edge(3, 5, 6));
        edges.add(new Edge(4, 5, 8));
        edges.add(new Edge(4, 6, 9));
        edges.add(new Edge(5, 6, 11));
        Set<Edge> mst = constructMinimumSpanningTree(edges);
        for (Edge edge: mst) {
            System.out.printf("%d - %d [%d]\n", edge.source, edge.dest, edge.weight);
        }
    }

    class Edge {
        int source, dest, weight;

        public Edge(int source, int dest, int weight) {
            this.source = source;
            this.dest = dest;
            this.weight = weight;
        }

        // Overridden toString(), equals() and hashCode() method
    }

    class DSU {
        int[] rank;
        int[] parent;

        public DSU(int n) {
            this.rank = new int[n];
            this.parent = new int[n];
            for (int i = 0; i < n; i++) {
                this.rank[i] = 1;
                this.parent[i] = i;
            }
        }

        private int find(int x) {
            while (x != parent[x]) {
                parent[x] = parent[parent[x]];
                x = parent[x];
            }
            return parent[x];
        }

        private void union(int x, int y) {
            int px = find(x);
            int py = find(y);
            if (px == py) {
                return;
            } else if (rank[px] < py) {
                parent[px] = py;
            } else {
                parent[py] = px;
                if (rank[px] == rank[py]) {
                    rank[px] += 1;
                }
            }
        }
    }

    public Set<Edge> constructMinimumSpanningTree(List<Edge> edges) {
        Collections.sort(edges, Comparator.comparingInt(((Edge e) -> e.weight)));
        int n = edges.size();
        DSU dsu = new DSU(n);
        Set<Edge> mst = new HashSet<>();
        int index = 0;
        while (index < n ) {
            Edge edge = edges.get(index++);

            int ps = dsu.find(edge.source);
            int pd = dsu.find(edge.dest);
            if (ps == pd) {
                continue;
            }
            dsu.union(ps, pd);
            mst.add(edge);
        }
        return mst;
    }
}
