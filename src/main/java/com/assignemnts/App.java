package com.assignemnts;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class App {

    public static void main(String[] args) {
        App app = new App();
        //app.testAssignment();
        app.testCommonOcean();
    }

    private void testCommonOcean() {
        int[][] island = {{3,3,3,3,3,3}, {3,0,3,3,0,3}, {3,3,3,3,3,3}};
        List<List<Integer>> common = pacificAtlantic(island);
        String res = common.stream().map(e -> String.format("{%d, %d}", e.get(0), e.get(1))).collect(Collectors.joining(","));
        System.out.printf("Res: [%s]\n", res);
    }


    public List<List<Integer>> pacificAtlantic(int[][] heights) {
        int m = heights.length, n = heights[0].length;
        Set<Integer> poCells = fetchCells(0, 0, heights);
        Set<Integer> aoCells = fetchCells(m-1, n-1, heights);
        poCells.retainAll(aoCells);
        List<List<Integer>> res = new ArrayList<>();
        for(int commonCell: poCells) {
            res.add(Arrays.asList(commonCell/n, commonCell%n));
        }
        return res;
    }
    int[] DIRS = {-1, 0, 1, 0, -1};
    private Set<Integer> fetchCells(int initRow, int initCol, int[][] heights) {
        int m = heights.length, n = heights[0].length;
        Deque<int[]> queue = new ArrayDeque<>();
        boolean[][] visited = new boolean[m][n];
        for(int i = 0; i < m; i++) {
            queue.offer(new int[]{i, initCol});
            visited[i][initCol] = true;
        }
        for(int i = 0; i < n; i++) {
            queue.offer(new int[]{initRow, i});
            visited[initRow][i] = true;
        }
        Set<Integer> result = new HashSet<>();

        while(!queue.isEmpty()) {
            int[] curr = queue.poll();
            result.add(curr[0]*n + curr[1]);
            for(int i = 0; i < 4; i++) {
                int x = curr[0] + DIRS[i], y = curr[1] + DIRS[i+1];
                if(x < 0 || x  >= m
                        || y < 0 || y >= n
                        || visited[x][y]
                        || heights[curr[0]][curr[1]] > heights[x][y]) {
                    continue;
                }
                visited[x][y] = true;
                queue.offer(new int[]{x, y});
            }
        }
        return result;
    }

    private void testAssignment() {
        String s = "0110011";
        //String s = "0110";
        int n1 = 1, n2 = 2;
        long count = fixedRatio(s, n1, n2);
        System.out.printf("Count: %d\n", count);
    }

    public long fixedRatio(String s, int n1, int n2) {
        Map<Long, Integer> freq =new HashMap<>();
        freq.put(0l, 1);
        long res = 0l, prefix = 0l;
        for(int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if(ch == '0') {
                prefix += n2;
            } else {
                prefix -= n1;
            }
            res += freq.getOrDefault(prefix, 0);
            freq.merge(prefix, 1, Integer::sum);
        }
        return res;
    }
}
