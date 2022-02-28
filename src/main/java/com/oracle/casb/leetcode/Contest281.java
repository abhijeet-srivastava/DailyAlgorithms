package com.oracle.casb.leetcode;

import java.util.*;

public class Contest281 {
    public static void main(String[] args) {
        Contest281 contest = new Contest281();
        //contest.testRepeatStr();
        //contest.testCoveredIntervals();
        contest.evaluateExpression("3+5-8*6/5");
    }

    private void testCoveredIntervals() {
        int[][] intervals = {{1,4}, {3,6}, {2,8}};
        int count = removeCoveredIntervals(intervals);
        System.out.printf("Count: %d\n", count);
    }

    private void testRepeatStr() {
        String str = "aababab";
        String ret = repeatLimitedString(str, 2);
        System.out.printf("Ret: %s\n", ret);
    }

    public int removeCoveredIntervals(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> a[0] == b[0] ? (a[1] - b[1] ): (a[0] - b[0]));
        Deque<int[]> stack = new ArrayDeque<>();
        //stack.push(intervaals[0]);
        for(int i = 0; i < intervals.length; i++) {
            if(!stack.isEmpty() && isOverlap(stack.peek(), intervals[i])) {
                int[] interval1 = stack.pop();
                int[] merged = mergeInterval(interval1, intervals[i]);
                stack.push(merged);
            } else {
                stack.push(intervals[i]);
            }
        }
        return stack.size();
    }
    private boolean isOverlap(int[] interval1, int[]  interval2) {
        return (interval1[0] <= interval2[0] && interval1[1] >= interval2[1])
                || (interval2[0] <= interval1[0] && interval2[1] >= interval1[1]);
    }

    private int[] mergeInterval(int[] interval1, int[] interval2) {
        int min = Math.min(interval1[0], interval2[0]);
        int max = Math.max(interval1[1], interval2[1]);
        return new int[] {min, max};
    }
    public String repeatLimitedString(String s, int repeatLimit) {
        TreeMap<Character, Integer> map = new TreeMap<>(Comparator.reverseOrder());
        for(char ch : s.toCharArray()) {
            map.put(ch, map.getOrDefault(ch, 0) + 1);
        }
        StringBuilder sb = new StringBuilder();
        while(sb.length() < s.length()) {
            char largest = map.firstKey();
            int count = map.get(largest);
            if(count > repeatLimit) {
                map.put(largest, count - repeatLimit);
                count = repeatLimit;
            } else {
                map.remove(largest);
            }
            for(int i = 0; i < count; i++) {
                sb.append(largest);
            }
            if(map.containsKey(largest)) {
                Character secondLargest = map.higherKey(largest);
                if(secondLargest == null) {
                    break;
                }
                int secondCount = map.get(secondLargest);
                sb.append(secondLargest);
                secondCount -= 1;
                if(secondCount == 0) {
                    map.remove(secondLargest);
                } else {
                    map.put(secondLargest, secondCount);
                }
            }
        }
        return sb.toString();
    }

    public int[] findBuildings(int[] heights) {
        Deque<Integer> stack = new ArrayDeque<>();
        for(int i = 0; i < heights.length; i++) {
            while(!stack.isEmpty() && heights[stack.peek()] < heights[i]) {
                stack.pop();
            }
            stack.push(i);
        }
        int[] res = new int[stack.size()];
        for(int i = 0; i < stack.size(); i++) {
            res[i] = stack.pop();
        }
        return res;
    }
    private int evaluateExpression(String s) {
        String[] array = s.split("[+-/*//]");
        return array.length;
    }
}
