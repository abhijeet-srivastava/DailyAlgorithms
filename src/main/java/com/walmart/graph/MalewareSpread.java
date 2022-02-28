package com.walmart.graph;

import com.walmart.DSU;

import java.util.Arrays;

public class MalewareSpread {

    public static void main(String[] args) {
        MalewareSpread ms = new MalewareSpread();
        //ms.testDfs();
        ms.testMaxFruits();
    }
   //https://leetcode.com/problems/maximum-fruits-harvested-after-at-most-k-steps/
    private void testMaxFruits() {
        //int[][] fruits = {{0,9},{4,1},{5,7},{6,2},{7,4},{10,9}};
        int[][] fruits = {{0,10000}};
        int startPos = 200000;
        int k = 200000;
        int count = maxTotalFruits(fruits, startPos, k);
        System.out.printf(" Max fruits: %d\n", count);
    }

    //https://leetcode.com/problems/minimize-malware-spread/
    private void testDfs() {
        int[][] graph = {{1,0,0}, {0,1,1}, {0,1,1}};
        int[] initial = {1,2};
        int minCount = minMalwareSpread(graph, initial);
        System.out.printf("Min Count %d\n", minCount);
    }


    public int minMalwareSpread(int[][] graph, int[] initial) {
        // 1. Color each component.
        // colors[node] = the color of this node.
        int N = graph.length;
        int[] colors = new int[N];
        Arrays.fill(colors, -1);
        int C = 0;

        for (int node = 0; node < N; ++node)
            if (colors[node] == -1)
                dfs(graph, colors, node, C++);

        // 2. Size of each color.
        int[] size = new int[C];
        for (int color: colors)
            size[color]++;

        // 3. Find unique colors.
        int[] colorCount = new int[C];
        for (int node: initial)
            colorCount[colors[node]]++;

        // 4. Answer
        int ans = Integer.MAX_VALUE;
        for (int node: initial) {
            int c = colors[node];
            if (colorCount[c] == 1) {
                if (ans == Integer.MAX_VALUE)
                    ans = node;
                else if (size[c] > size[colors[ans]])
                    ans = node;
                else if (size[c] == size[colors[ans]] && node < ans)
                    ans = node;
            }
        }
        if (ans == Integer.MAX_VALUE)
            for (int node: initial)
                ans = Math.min(ans, node);

        return ans;
    }

    public void dfs(int[][] graph, int[] colors, int node, int color) {
        colors[node] = color;
        for (int nei = 0; nei < graph.length; ++nei)
            if (graph[node][nei] == 1 && colors[nei] == -1)
                dfs(graph, colors, nei, color);
    }


    public int minMalwareSpreadDSU(int[][] graph, int[] initial) {
        int N = graph.length;
        DSU dsu = new DSU(N);
        for (int i = 0; i < N; ++i)
            for (int j = i+1; j < N; ++j)
                if (graph[i][j] == 1)
                    dsu.union(i, j);

        int[] count = new int[N];
        for (int node: initial)
            count[dsu.find(node)]++;

        int ans = -1, ansSize = -1;
        for (int node: initial) {
            int root = dsu.find(node);
            if (count[root] == 1) {  // unique color
                int rootSize = dsu.size(root);
                if (rootSize > ansSize) {
                    ansSize = rootSize;
                    ans = node;
                } else if (rootSize == ansSize && node < ans) {
                    ansSize = rootSize;
                    ans = node;
                }
            }
        }

        if (ans == -1) {
            ans = Integer.MAX_VALUE;
            for (int node: initial)
                ans = Math.min(ans, node);
        }
        return ans;
    }
    public int minMalwareSpread2Art(int[][] graph, int[] initial) {
        int n = graph.length;
        int ans = initial[0];
        int max = 0;
        boolean[] infected = new boolean[n];
        for(int i : initial) {
            infected[i] = true;
        }
        int[] depth = new int[n];
        int[] low = new int[n];
        int[] count = new int[n];
        for(int u : initial) {
            if(depth[u] == 0) {
                dfs(u, -1, 1, graph, depth, low, count, infected);
            }
            if(count[u] > max || (count[u] == max && u < ans)) {
                max = count[u];
                ans = u;
            }
        }
        return ans;
    }
    //Tarzan's Algorithm
        private int dfs(int u, int parent, int time, int[][] graph, int[] depth, int[] low, int[] count, boolean[] infected) {
            low[u] = depth[u] = time;
            boolean flag = infected[u];
            int size = 1;
            for(int v = 0; v < graph[u].length; v++) {
                if(graph[u][v] != 1) {
                    continue;
                }
                //Unvisited
                if(depth[v] == 0) {//Forward edge
                    int s = dfs(v, u, time+1, graph, depth, low, count, infected);
                    if(s == 0) {
                        //Infected node in subtree
                        flag = true;
                    } else {
                        size += s;
                    }
                    if(low[v] >= depth[u]) {//u is articulation point
                        count[u] += s;
                    }
                    low[u] = Math.min(low[u], low[v]);
                } else {//Back edge
                    if(v != parent) {
                        low[u] = Math.min(low[u], depth[v]);
                    }
                }
            }
            return flag ? 0 : size;
        }

    public int minMalwareSpread2DFS(int[][] graph, int[] initial) {
        int n = graph.length;
        int max = 0;
        int ans = initial[0];
        boolean[] infected = new boolean[n];
        for(int i : initial) {
            infected[i] = true;
        }
        for(int u : initial) {
            int count = 0;
            boolean[] visited = new boolean[n];
            visited[u] = true;
            for(int i = 0; i < n; i++) {
                if(!visited[u] && graph[u][i] == 1) {
                    count += dfs(i, graph, visited, infected);
                }
            }
            if(count > max || ((count == max) && (ans > u))) {
                ans = u;
                max = count;
            }
        }
        return ans;
    }

    private int dfs(int u, int[][] graph, boolean[] visited, boolean[] infected) {
        if(infected[u]) {
            return 0;
        }
        visited[u] = true;
        int count = 1;
        for(int v = 0; v < graph[u].length; v++) {
            if(!visited[v] && graph[u][v] == 1) {
                int c = dfs(v, graph, visited, infected);
                if(c == 0) {
                    infected[u] = true;
                    return 0;
                }
                count += c;
            }
        }
        return count;
    }

    public int maxTotalFruits(int[][] fruits, int startPos, int k) {
        int count = 0;
        int len = fruits.length;
        int n = fruits[len-1][0];
        int[] prefixSum = new int[n+1];
        int index = 0;
        for(int i = 0; i <= n; i++) {
            if(i > 0) {
                prefixSum[i] += prefixSum[i-1];
            }
            if(index <  len && i == fruits[index][0]) {
                prefixSum[i] += fruits[index][1];
                index += 1;
            }
        }
        for(int dist = k; dist >= 0; dist--) {
            int r = Math.min(n, startPos+dist);
            int l =   Math.max(1, startPos - (k - (r-startPos))/2);
            if(r < 0 || l > n+1) {
                break;
            }
            int current = ( r == l-1) ?  prefixSum[r] : prefixSum[r] -prefixSum[l-1];
            count = Math.max(count, current);
        }
        for(int dist = k; dist >= 0; dist--) {
            int l = Math.max(1, startPos-dist);
            int r = Math.min(n, startPos + (k - (startPos-l))/2);
            if(r < 0 || l > n+1) {
                break;
            }
            int current = ( r == l-1) ?  prefixSum[r] : prefixSum[r] - prefixSum[l-1];
            count = Math.max(count, current);
        }
        return count;
    }
}
