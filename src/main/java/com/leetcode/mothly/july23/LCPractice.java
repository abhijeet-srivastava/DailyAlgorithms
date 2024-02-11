package com.leetcode.mothly.july23;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Deque;
import java.util.PriorityQueue;

import static java.util.Comparator.comparingInt;

public class LCPractice {

    public static void main(String[] args) {
        LCPractice lcp = new LCPractice();
       // lcp.solve28July();
        lcp.testPaths();
    }

    private void testPaths() {
        String key = "analytics_report/historical_reports";
        String countryCode = "pol".toUpperCase();
        String fileName = String.format("%s_%d.%s", "AuditFile", System.currentTimeMillis(), "csv");
        Path filePath = Paths.get(key, countryCode, fileName);
        System.out.printf("File Path: %s\n", filePath.toString());
    }

    private void solve28July() {
        int[] nums = {1,5,2};
        boolean win = PredictTheWinner(nums);
        System.out.printf("Player 1 is winner: %b\n", win);
    }
    public boolean PredictTheWinner(int[] nums) {
        //return topDown(nums);
        return bottomUp(nums);
    }

    public int shortestPathLengthbfs(int[][] graph) {
        int n = graph.length;
        int endMask = (1 << n) - 1;
        boolean[][] visited = new boolean[n][endMask];
        Deque<int[]> queue = new ArrayDeque<>();
        for(int i = 0; i < n; i++) {
            queue.offer(new int[]{i, 1 << i});
            visited[i][1 << 1] = true;
        }
        int steps = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int[] curr = queue.poll();
                int node = curr[0], mask = curr[1];
                for(int next: graph[node]) {
                    int nextMask = mask | (1 << next);
                    if(nextMask == endMask) {
                        return 1 + steps;
                    }
                    if(visited[next][nextMask]) {
                        continue;
                    }
                    queue.offer(new int[]{next, nextMask});
                    visited[next][nextMask] = true;
                }
            }
            steps += 1;
        }
        return -1;
    }
    public int shortestPathLengthdfs(int[][] graph) {
        int  n = graph.length;
        int endMask = (1 << n) - 1;
        int[][] DP = new int[n+1][endMask+1];
        int minCost = Integer.MAX_VALUE;
        for(int node = 0; node < n; node++) {
            minCost = Math.min(minCost, dfs(node, endMask, DP, graph));
        }
        return minCost;
    }

    private int dfs(int node, int mask, int[][] DP, int[][] graph) {
        if(DP[node][mask] > 0) {
            return DP[node][mask];
        } else if((mask & (mask-1)) == 0) {
            return 0;
        }
        DP[node][mask] = Integer.MAX_VALUE - 1;
        int firstVisitMask = mask ^ (1 << node);
        for(int next: graph[node]) {
            if((mask & (1 << next)) == 0) {
                continue;
            }
            int firstVisit = dfs(next, firstVisitMask , DP, graph);
            int secondVisit = dfs(next, mask , DP, graph);
            DP[node][mask] =  Math.min(DP[node][mask],
                    1 + Math.min(firstVisit, secondVisit)
                    );
        }
        return DP[node][mask];
    }

    private boolean bottomUp(int[] nums) {
        int n = nums.length;
        int[][] DP = new int[n][n];
        for(int i = 0; i < n; i++) {
            DP[i][i] = nums[i];
        }
        for(int diff = 1; diff < n; diff++) {//For all the diffs
            for(int l = 0; l < n-diff; l++) {//For all the left start
                int r = l + diff;
                DP[l][r] = Math.max(
                        nums[l] - DP[l+1][r],
                        nums[r] - DP[l][r-1]
                );
            }
        }
        return DP[0][n-1] >= 0;
    }

    private boolean topDown(int[] nums) {
        int n = nums.length;
        int[][] DP = new int[n][n];
        for(int[] row: DP) {
            Arrays.fill(DP, -1);
        }
        return (topDownScoreSum(nums, 0, n-1, DP) >= 0);
    }

    private int topDownScoreSum(int[] nums, int l, int r, int[][] DP) {
        if(l == r) {
            return nums[l];
        } else if(DP[l][r] >= 0) {
            return DP[l][r];
        }
        DP[l][r] = Math.max(
                nums[l] - topDownScoreSum(nums, l+1, r, DP),//Player choose left
                nums[r] - topDownScoreSum(nums, l, r-1, DP)
        );
        return DP[l][r];
    }
}
