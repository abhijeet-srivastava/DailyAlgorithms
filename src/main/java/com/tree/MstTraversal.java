package com.tree;

import java.util.*;

public class MstTraversal {

    public static void main(String[] args) {
        MstTraversal traversal = new MstTraversal();
        traversal.solveWellsAndPipes();
        traversal.testDuplicate();
    }

    private void testDuplicate() {
        int[] arr = {1,3,4,2,2};
        int dup = findDuplicate(arr);
        System.out.printf("dup: %d\n", dup);
    }

    public int findDuplicate(int[] nums) {
        while(nums[0] != nums[nums[0]]) {
            int tmp = nums[nums[0]];
            nums[nums[0]] = nums[0];
            nums[0] = tmp;
        }
        return nums[0];
    }

    private void solveWellsAndPipes() {
        /**
         * 5
         * [46012,72474,64965,751,33304]
         * [[2,1,6719],[3,2,75312],[5,3,44918]]
         */
        int[] wells = {46012,72474,64965,751,33304};
        int[][] pipes = {{2,1,6719}, {3,2,75312}, {5,3,44918}};
        int minCost = minCostToSupplyWater(wells.length, wells, pipes);
        System.out.printf("Min Cost: %d\n", minCost);
    }

    public int minCostToSupplyWater(int n, int[] wells, int[][] pipes) {
        int cost = 0;
        List<int[]>[] GRAPH = new List[n+1];
        for(int i = 0; i <= n; i++) {
            GRAPH[i] = new ArrayList<>();
        }
        for(int[] pipe: pipes) {
            GRAPH[pipe[0]].add(new int[]{pipe[1], pipe[2]});
            GRAPH[pipe[1]].add(new int[]{pipe[0], pipe[2]});
        }
        for(int i = 1; i <= n; i++) {
            GRAPH[0].add(new int[]{i, wells[i-1]});
            GRAPH[i].add(new int[]{0, wells[i-1]});
        }
        Set<Integer> visited = new HashSet<>();
        PriorityQueue<int[]> pq
                = new PriorityQueue<>(Comparator.comparingInt((int[] arr) -> arr[1]));
        visited.add(0);
        for(int[] next: GRAPH[0]) {
            pq.offer(next);
        }
        while(visited.size() <= n) {
            int[] current = pq.remove();
            if(visited.contains(current[0])) {
                //Next lowest cost node already in current tree
                continue;
            }
            System.out.printf("Considering node: %d of cost: %d\n", current[0], current[1]);
            visited.add(current[0]);
            cost += current[1];
            for(int[] next: GRAPH[current[0]]) {
                if(visited.contains(next[0])) {
                    continue;
                }
                pq.offer(next);
            }
        }
        return cost;
    }
}
