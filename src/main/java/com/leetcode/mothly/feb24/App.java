package com.leetcode.mothly.feb24;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

public class App {

    public static void main(String[] args) {
        App app = new App();
        //app.testSortArray();
        app.testCount();
        //app.testAsteroids();
    }
    private void testCount() {
        String buildings = "001101";
        long cnt = numberOfWays(buildings);
        System.out.printf("cnt :%d\n", cnt);
    }
    public long numberOfWays(String s) {
        int n = s.length();
        int[] total = {0, 0};
        int[][] count = new int[n+1][2];
        for(int i = 1; i <= n; i++) {
            int idx = s.charAt(i-1) - '0';
            count[i][0] = count[i-1][0];
            count[i][1] = count[i-1][1];
            count[i][idx] += 1;
            total[idx] += 1;
        }
        long res = 0l;
        for(int i = 1; i < n-1; i++) {
            int idx = s.charAt(i) - '0';
            idx ^= 1;
            int lc = count[i][idx];
            int rc = total[idx] - lc;
            res += (lc*rc);
        }
        return res;
    }

    public  int poisonousPlants(List<Integer> p) {
        // Write your code here
        int days = 0;
        Deque<int[]> stack  = new ArrayDeque<>();
        for(int i = 0; i < p.size(); i++) {
            if(stack.isEmpty()) {
                stack.push(new int[]{p.get(i), 0});
            } else if(stack.peek()[0] < p.get(i)) {
                stack.push(new int[]{p.get(i), 1});
            } else {
                int pr = 0;
                while(!stack.isEmpty() && stack.peek()[0] >= p.get(i)) {
                    int[] tos = stack.pop();
                    pr = Math.max(pr, tos[1]);
                }
            }

        }
        return days;
    }

    private void testAsteroids() {
        int[] asteroids = {-5, -4, 10, 1, -2};
        List<Integer> res = calculateCollisionRes(asteroids);
        System.out.printf("res: [%s]\n", res.stream().map(String::valueOf).collect(Collectors.joining(", ")));
    }

    private void testSortArray() {
        int[] arr = {1,20,3,4,0,7,18,9,6,10,11,-1,12,13,25};
        System.out.printf("len: %d\n", arr.length);
        int min = minLengthToSort(arr);
        System.out.printf("Min: %d\n", min);
    }

    private int minLengthToSort(int[] arr) {
        int l = arr.length-1, r = 0;
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        for(int i = 0; i < arr.length; i++) {
            if(arr[i] < max) {
                r = i;
            }
            max = Math.max(max, arr[i]);
        }
        for(int j = arr.length-1; j >= 0; j--) {
            if(arr[j] > min) {
                l = j;
            }
            min = Math.min(min, arr[j]);
        }
        if(l < r) {
            return r-l+1;
        }
        return 0;
    }

    private List<Integer> calculateCollisionRes(int[] asteroids) {
        Deque<Integer> stack = new ArrayDeque<>();
        for(int i = 0; i < asteroids.length; i++) {
            int curr = asteroids[i];
            while (!stack.isEmpty() && stack.peekFirst() > 0 && curr < 0) {
                int tos = stack.pop();
                int currAbs = -1*curr;
                if(tos == currAbs) {
                    curr = 0;
                } else if(tos > currAbs) {
                    curr = tos;
                }
            }
            if(curr != 0) {
                stack.push(curr);
            }
        }
        List<Integer> res = new ArrayList<>();
        while(!stack.isEmpty()) {
            res.add(stack.removeLast());
        }
        return res;
    }

}
