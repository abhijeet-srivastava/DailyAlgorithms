package com.leetcode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.Queue;

public class SolutionMay {
    int[][] dirs = {{0, -1}, {-1, 0}, {0, 1}, {1, 0}};

    public static void main(String[] args) {
        SolutionMay sm = new SolutionMay();
        //sm.testMaxSafenessPath();
        //sm.testMaxSubArrLen();
        sm.testBuildNumWaysToBuildWall();
    }

    private void testBuildNumWaysToBuildWall() {
        int[] bricks = {1,2};
        int h = 2, w = 3;
        int count = buildWall(h, w, bricks);
        System.out.printf("Ways: %d\n", count);
    }

    public int buildWall(int height, int width, int[] bricks) {
        int mod = 1000000007;

        ArrayList<Integer> buildLayer = waysOfBuildLayerRecursive(width, bricks);
        if (height == 1) {
            return buildLayer.size();
        }

        ArrayList<ArrayList<Integer>> nexts = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < buildLayer.size(); i++) {
            int split = buildLayer.get(i);
            ArrayList<Integer> next = new ArrayList<Integer>();
            for (int j = 0; j < buildLayer.size(); j++) {
                int nextSplit = buildLayer.get(j);
                if ((split & nextSplit) == 0) {
                    next.add(j);
                }
            }
            nexts.add(next);
        }

        int[] thisLayer = new int[buildLayer.size()];
        Arrays.fill(thisLayer, 1);

        for (int i = 1; i < height; i++) {
            int[] nextLayer = new int[buildLayer.size()];
            for (int j = 0; j < thisLayer.length; j++) {
                ArrayList<Integer> next = nexts.get(j);
                for (int nextSplit : next) {
                    nextLayer[nextSplit] = (nextLayer[nextSplit] + thisLayer[j]) % mod;
                }
            }
            thisLayer = nextLayer;
        }
        int result = 0;
        for (int num : thisLayer) {
            result = (result + num) % mod;
        }
        return result;
    }

    private ArrayList<Integer> waysOfBuildLayerRecursive(int width, int[] bricks) {
        ArrayList<Integer> result = new ArrayList<>();
        waysOfBuildLayerRecursive(new ArrayList<>(), width, bricks, result);
        return result;
    }

    private void waysOfBuildLayerRecursive(ArrayList<Integer> aList, int width, int[] bricks, ArrayList<Integer> result) {
        if (width == 0) {
            result.add(stack2int(aList));
            return;
        }
        for (int brick : bricks) {
            if (brick <= width) {
                aList.add(brick);
                waysOfBuildLayerRecursive(aList, width - brick, bricks, result);
                aList.remove(aList.size() - 1);
            }
        }
    }

    private int stack2int(ArrayList<Integer> aList) {
        // System.out.println("----------------------------");
        // System.out.println(aList);
        int result = -1;
        for (int brick : aList) {
            // System.out.print("brick "+brick+" | ");
            result++;
            result = result << (brick);
        }
        // System.out.println();
        // System.out.println("----------------------------" + result);
        return result;
    }

    private void testMaxSubArrLen() {
        int[] nums = {7,6,5,4,3,2,1,6,10,11};
        //int[] nums = {57,55,50,60,61,58,63,59,64,60,63};
        //int[] nums = {1,2,3,4};
        //int[] nums = {92, 50};
        int res = maxSubarrayLength1(nums);
        System.out.printf("res: %d\n", res);
    }

    public int maxSubarrayLength1(int[] nums) {
        int res = 0;

        int[] rightMin = new int[nums.length];
        rightMin[nums.length-1] = nums[nums.length-1];
        for(int i = nums.length - 2; i >= 0; i--){
            rightMin[i] = Math.min(nums[i], rightMin[i+1]);
        }

        for(int i = 0, j = 0; i < nums.length && j < nums.length; i++){
            while(j < nums.length && nums[i] > rightMin[j]){
                j++;
            }

            res = Math.max(res, j - i);
        }

        return res;
    }
    public int maxSubarrayLength(int[] nums) {
        int n = nums.length;
        int[][] numInd = new int[n][2];
        for(int i = 0; i < n; i++) {
            numInd[i] = new int[]{nums[i], i};
        }
        Arrays.sort(numInd, (a, b) -> a[0] ==  b[0] ? Integer.compare(b[1], a[1]) : Integer.compare(b[0], a[0]));
        int max = 0, minInd = n;
        for(int i = 0; i < n; i++) {
            int[] curr = numInd[i];
            max = Math.max(max, curr[1] - minInd +1);
            minInd = Math.min(minInd, curr[1]);
        }
        return max;
    }

    private void testMaxSafenessPath() {
        List<List<Integer>> grid = Arrays.asList(
                Arrays.asList(0,0,1),
                Arrays.asList(0,0,0),
                Arrays.asList(0,0,0)
        );
        int maxSafeness = maximumSafenessFactor(grid);
        System.out.printf("max: %d\n", maxSafeness);
    }

    public int maximumSafenessFactor(List<List<Integer>> grid) {
        int n = grid.size();
        if(grid.get(0).get(0) == 1 || grid.get(n-1).get(n-1) == 1) {
            return 0;
        }
        int[][] dist = populateDistance(grid);
        int l = 0, r = 2*n, res = 0;
        while(l <= r) {
            int mid = l + (r-l)/2;
            if(existsPath(mid, dist)) {
                res = mid;
                l = mid+1;
            } else {
                r = mid-1;
            }
        }
        return res;
    }
    private boolean existsPath(int max, int[][] dist) {
        int n = dist.length;
        if(dist[0][0] < max || dist[n-1][n-1] < max) {
            return false;
        }
        Deque<int[]> queue = new ArrayDeque<>();
        boolean[][] visited = new boolean[n][n];
        queue.offer(new int[]{0, 0});
        visited[0][0] = true;
        while(!queue.isEmpty()) {
            int[] curr = queue.poll();
            int x = curr[0], y = curr[1];
            if(x == n-1 && y == n-1) {
                return true;
            }
            for(int[] dir: dirs) {
                int r = x + dir[0], c = y + dir[1];
                if(!isValid(r, c, n, visited) || dist[r][c] < max) {
                    continue;
                }
                queue.offer(new int[]{r, c});
                visited[r][c] = true;
            }
        }
        return false;
    }
    private boolean isValid(int r, int c, int n, boolean[][] visited) {
        return r >= 0 && r < n && c >= 0 && c < n && !visited[r][c];
    }
    private int[][] populateDistance(List<List<Integer>> grid) {
        int n = grid.size();
        int[][] dist = new int[n][n];
        for(int[] arr: dist) {
            Arrays.fill(arr, 2*n);
        }
        Queue<int[]> queue = new ArrayDeque<>();
        boolean[][] visited = new boolean[n][n];
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                if(grid.get(i).get(j) == 1) {
                    queue.offer(new int[]{i, j});
                    visited[i][j] = true;
                    dist[i][j] = 0;
                }
            }
        }
        while(!queue.isEmpty()) {
            int size = queue.size();
            while(size-- > 0) {
                int[] cell = queue.poll();
                int x = cell[0], y = cell[1];
                for(int[] dir: dirs) {
                    int r = x + dir[0], c = y + dir[1];
                    if(isValid(r, c, n, visited)) {
                        int md = Math.abs(r - x) + Math.abs(c - y) + dist[x][y];
                        if (dist[r][c] < md) continue;
                        visited[r][c] = true;
                        dist[r][c] = md;
                        queue.offer(new int[]{r, c});
                    }
                }
            }
        }
        return dist;

    }
}
