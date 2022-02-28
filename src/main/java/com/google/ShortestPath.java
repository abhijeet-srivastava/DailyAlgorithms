package com.google;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class ShortestPath {

    private class StepState implements Comparable<StepState>{
        int row;
        int col;
        int steps;
        int remainingQuota;
        int estimation;

        public StepState(int row, int col, int steps, int remainingQuota, int[] target) {
            this.row = row;
            this.col = col;
            this.steps = steps;
            this.remainingQuota = remainingQuota;
            int distance = target[0] - row + target[1] -col;
            this.estimation = distance + steps;
        }

        @Override
        public int compareTo(StepState o) {

            StepState ss = (StepState) o;
            return this.estimation - ss.estimation;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            StepState stepState = (StepState) o;
            return row == stepState.row && col == stepState.col && remainingQuota == stepState.remainingQuota;
        }

        @Override
        public int hashCode() {
            return row*col*remainingQuota;
        }
    }
    public int shortestPath(int[][] grid, int k) {
        int m = grid.length;
        int n = grid[0].length;
        int[] target = {m-1, n-1};
        PriorityQueue<StepState> queue = new PriorityQueue<>();
        Set<StepState> visited = new HashSet<>();
        StepState start = new StepState(0, 0, 0, k, target);
        queue.offer(start);
        visited.add(start);
        int[][] DIRS = {{0,1}, {1,  0}, {0,-1}, {-1, 0}};
        while (!queue.isEmpty()) {
            StepState curr = queue.poll();
            int remainDist = curr.estimation - curr.steps;
            if(remainDist <= curr.remainingQuota) {
                return curr.estimation;
            }
            for(int[] dir: DIRS) {
                int x = curr.row + dir[0];
                int y = curr.col + dir[1];
                if(x < 0 || x >= m || y < 0 || y >= n) {
                    continue;
                }
                int nextRemainingQuota = curr.remainingQuota - grid[x][y];
                if(nextRemainingQuota < 0) {
                    continue;
                }
                StepState next = new StepState(x, y, curr.steps+1, nextRemainingQuota, target);
                if(visited.contains(next) ) {
                    continue;
                }
                queue.offer(next);
                visited.add(next);
            }
        }
        return -1;
    }
}
