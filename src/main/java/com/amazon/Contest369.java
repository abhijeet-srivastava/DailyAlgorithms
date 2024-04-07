package com.amazon;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.IntStream;

public class Contest369 {
    public static void main(String[] args) {
        Contest369 ct = new Contest369();
        //ct.testLongestBalancedSubStr();
        ct.testMinWeight();
    }

    private void testMinWeight() {
        int[][] edges = {{0,2,7},{0,1,15},{1,2,6},{1,2,1}};
        int[][] query = {{1,2}};
        int n = 3;
        int[] res = minimumCost(n, edges, query);
        for(int i = 0; i < query.length; i++) {
            System.out.printf("query[%d]: {%d, %d} = %d\n", i, query[i][0], query[i][1], res[i]);
        }
    }

    public int[] minimumCost(int n, int[][] edges, int[][] query) {
        DSU dsu = new DSU(n);
        for(int[] edge: edges) {
            dsu.join(edge[0], edge[1], edge[2]);
        }
        int[] res = new int[query.length];
        for(int i = 0; i < query.length; i++) {
            res[i] = dsu.minimum_cost_of_walk(query[i][0], query[i][1]);
        }
        return res;
    }
    private class DSU {
        int count;
        int[] parent;
        int[] rank;
        int[] weight;
        public DSU(int n) {
            this.count = n;
            this.parent = new int[n];
            this.rank = new int[n];
            this.weight = new int[n];
            for(int i = 0; i < n; i++) {
                this.parent[i] = i;
                this.rank[i] = 1;
                this.weight[i] = Integer.MAX_VALUE;
            }
        }
        public int find(int x) {
            while(x != parent[x]) {
                parent[x] = parent[parent[x]];
                x = parent[x];
            }
            return parent[x];
        }
        public void join(int x, int y, int w) {
            int px = find(x), py = find(y);
            if(px != py) {
                if(rank[px] < rank[py]) {
                    parent[px] = py;
                } else {
                    parent[py] = px;
                    if(rank[px] == rank[py]) {
                        rank[px] += 1;
                    }
                }
            }
            int currWeight = weight[px] & weight[py] & w;
            weight[px] = currWeight;
            weight[py] = currWeight;
        }
        public int minimum_cost_of_walk(int x, int y) {
            if (x == y)
                return 0;
            if (find(x) != find(y))
                return -1;
            return weight[find(x)];
        }
    }

    private void testLongestBalancedSubStr() {
        int size = findTheLongestBalancedSubstring("10");
        System.out.printf("Longest Balanced str: %d\n", size);
    }

    public int findTheLongestBalancedSubstring(String s) {
        int c0 = 0, c1 = 0;
        int max = 0;
        for(int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if(ch == '0') {
                if(i > 0 && s.charAt(i-1) == '1') {
                    c1 = 0;
                    c0 = 0;
                }
                c0 += 1;
            } else if(ch == '1') {
                c1 += 1;
                max = Math.max(max, 2*Math.min(c0, c1));
            }
        }
        return max;
    }
    public int miceAndCheese(int[] reward1, int[] reward2, int k) {
        int n = reward1.length;
        int[][] arr = new int[n][2];
        for(int i = 0; i < n; i++) {
            arr[i][0] = reward1[i];
            arr[i][1] = reward2[i];
        }
        Arrays.sort(arr, Comparator.<int[]>comparingInt(rew ->rew[0] - rew[1]));
        int cost = 0;
        for(int  i = 0; i <= k; i--) {
            cost += arr[n-i-1][0];
            cost += arr[i][1];
        }
        return cost;
    }
}
