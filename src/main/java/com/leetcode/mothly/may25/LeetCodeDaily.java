package com.leetcode.mothly.may25;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class LeetCodeDaily {

    public static void main(String[] args) {
        LeetCodeDaily lcd = new LeetCodeDaily();
        //lcd.test16May();
        //lcd.test28May();
        lcd.test29May();
    }

    private void test29May() {
        int[][] edges1 = {{0,1},{0,2},{2,3},{2,4}};
        int[][] edges2 = {{0,1},{0,2},{0,3},{2,7},{1,4},{4,5},{4,6}};
        int[] res = maxTargetNodes(edges1, edges2);
        for(int i = 0; i < res.length; i++) {
            System.out.printf("res[%d] = %d\n", i, res[i]);
        }
    }

    private void test28May() {
        int[][] edges1 = {{2,1},{7,3},{0,4},{7,5},{2,6},{0,2},{0,7}};
        int[][] edges2 = {{3,0},{1,2},{5,1},{6,3},{9,4},{5,6},{7,5},{9,7},{8,9}};
        int k = 7;
        int[] res = maxTargetNodes(edges1, edges2, k);
        for(int i = 0; i < res.length; i++) {
            System.out.printf("res[%d] = %d\n", i, res[i]);
        }

    }

    public int[] maxTargetNodes(int[][] edges1, int[][] edges2) {
        int n = edges1.length, m = edges2.length;
        Set<Integer>[] graph1 = createGraph(edges1);
        Set<Integer>[] graph2 = createGraph(edges2);
        int count2 = 0;
        for(int i = 0; i <=m; i++) {
            count2 = Math.max(count2, countNodeWithParityDistance(i, graph2, 1));
        }
        System.out.printf("Count2: %d\n", count2);
        int[] res = new int[n+1];
        for(int i = 0; i <= n; i++) {
            res[i] = count2 + countNodeWithParityDistance(i, graph1, 0);
        }
        return res;
    }
    private int countNodeWithParityDistance(int i, Set<Integer>[] graph, int level) {
        int n = graph.length;
        boolean[] visited = new boolean[n];
        Deque<Integer> queue = new ArrayDeque<>();
        queue.offer(i);
        visited[i] = true;
        int count = 0;
        while(!queue.isEmpty()) {
            int size = queue.size();
            while(size-- > 0) {
                int curr = queue.poll();
                if(level%2 == 0) {
                    count += 1;
                }
                for(int next: graph[curr]) {
                    if(!visited[next]) {
                        queue.offer(next);
                        visited[next] = true;
                    }
                }
            }
            level += 1;
        }
        return count;
    }
    private Set<Integer>[] createGraph29(int[][] edges) {
        int n = edges.length;
        Set<Integer>[] graph = new HashSet[n+1];
        for(int i = 0; i <= n; i++) {
            graph[i] = new HashSet<Integer>();
        }
        for(int[] edge:edges) {
            graph[edge[0]].add(edge[1]);
            graph[edge[1]].add(edge[0]);
        }
        return graph;
    }

    public int[] maxTargetNodes(int[][] edges1, int[][] edges2, int k) {
        int n = edges1.length, m = edges2.length;
        int[] res = new int[n+1];
        Set<Integer>[] graph1 = createGraph(edges1);
        Set<Integer>[] graph2= createGraph(edges2);
        int maxCount = 0;
        for(int i = 0; i <= m; i++) {
            maxCount = Math.max(maxCount, countNodesAtMoskKDistance(i, graph2, k-1));
        }
        for(int i = 0; i <= n; i++) {
            res[i] = maxCount +  countNodesAtMoskKDistance(i, graph1, k);

        }

        return res;
    }
    private int countNodesAtMoskKDistance(int node, Set<Integer>[] graph, int k) {
        int n = graph.length;
        int count = 0;
        Deque<Integer> queue = new ArrayDeque<>();
        queue.offer(node);
        boolean[] visited = new boolean[n];
        visited[node] = true;
        while(!queue.isEmpty() && k-- >= 0) {
            int size = queue.size();
            while (size-- > 0) {
                int curr = queue.poll();
                count += 1;
                for(int next: graph[curr]) {
                    if(!visited[next]) {
                        queue.offer(next);
                        visited[next] = true;
                    }
                }
            }
        }
        return count;
    }

    private Set<Integer>[] createGraph(int[][] edges) {
        int n = edges.length;
        Set<Integer>[] graph = new HashSet[n+1];
        for(int i = 0; i <= n; i++) {
            graph[i] = new HashSet<Integer>();
        }
        for(int[] edge:edges) {
            graph[edge[0]].add(edge[1]);
            graph[edge[1]].add(edge[0]);
        }
        return graph;
    }

    private void test16May() {
        String[] words = {"bab","dab","cab"};
        int[] groups = {1,2,2};
        List<String> res = getWordsInLongestSubsequence(words, groups);
        res.stream().forEach(System.out::println);
    }


    public List<String> getWordsInLongestSubsequence(String[] words, int[] groups) {
        int len = words.length;
        WordGroup[] DP = new WordGroup[len];
        for(int i = 0; i < len; i++) {
            DP[i] = new WordGroup(words[i], groups[i], 1, -1);
        }
        int maxIdx = 0;
        for(int i = 1; i < len; i++) {
            int maxLenIdx = 1;
            for(int j = i-1; j >= 0; j--) {
                if(DP[i].group != DP[j].group
                        && hammingDistance(DP[i].word, DP[j].word) == 1
                        && DP[i].len < (1+DP[j].len)) {
                    DP[i] = new WordGroup(words[i], groups[i], 1+DP[j].len, j);
                }
            }
            if(DP[i].len > DP[maxIdx].len) {
                maxIdx = i;
            }
        }
        List<String> res = new LinkedList<>();
        while(maxIdx >= 0) {
            res.add(0, DP[maxIdx].word);
            maxIdx =  DP[maxIdx].prev;
        }
        return res;
    }

    private int hammingDistance(String w1, String w2) {
        if(w1.length() != w2.length()) {
            return 2;
        }
        int dist = 0;
        for(int idx = 0; idx < w1.length();idx++) {
            if(w1.charAt(idx) != w2.charAt(idx)) {
                dist += 1;
            }
        }
        return dist;
    }
    private class WordGroup {
        String word;
        int group;
        int len;
        int prev;
        public WordGroup(String word, int group, int len, int prev) {
            this.word = word;
            this.group = group;
            this.len = len;
            this.prev = prev;
        }
    }

    public int minTimeToReach(int[][] moveTime) {
        int m = moveTime.length, n = moveTime[0].length;
        int[][] mttr = new int[m][n];
        for(int[] row: mttr) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }
        mttr[0][0] = 0;
        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> Integer.compare(a[0], b[0]));
        int[][] dirs = {{0,1}, {1, 0}, {0,-1}, {-1,0}};
        pq.offer(new int[]{0, 0, 0, 1});
        while(!pq.isEmpty()) {
            int[] curr = pq.poll();
            int ct = curr[0], cx = curr[1], cy = curr[2], cd = curr[3];
            if(cx == m-1 && cy == n-1) {
                return ct;
            }
            int nd = (cd == 1) ? 2:1;
            for(int[] dir: dirs) {
                int nx = cx + dir[0], ny = cy  + dir[1];
                if(nx < 0 || nx >= m || ny < 0 || ny >= n) {
                    continue;
                }
                int ttr = Math.max(ct, moveTime[nx][ny]) + cd;
                if(ttr < mttr[nx][ny]) {
                    mttr[nx][ny] = ttr;
                    pq.offer(new int[]{ttr, nx, ny, nd});
                }
            }
        }
        return -1;
    }
}
