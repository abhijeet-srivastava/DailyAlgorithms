package com.walmart;

public class UnionFind {
    private int count = 0;
    private int[] parent, rank;

    public UnionFind(int n) {
        count = n;
        parent = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
    }

    public int find(int p) {
        while (p != parent[p]) {
            parent[p] = parent[parent[p]];    // path compression by halving
            p = parent[p];
        }
        return p;
    }

    //Union By Rank
    //Always attach smaller depth tree under the root of the deeper tree.
    //Higher the rank greater the depth
    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ) return;
        if (rank[rootQ] > rank[rootP]) {
            parent[rootP] = rootQ;
        } else {
            parent[rootQ] = rootP;
            if (rank[rootP] == rank[rootQ]) {
                rank[rootP]++;
            }
        }
        count--;
    }

    public int count() {
        return count;
    }

}
