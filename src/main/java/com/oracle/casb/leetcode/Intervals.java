package com.oracle.casb.leetcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class Intervals {

    public static void main(String[] args) {
        Intervals test = new Intervals();
        test.testInsert();
    }

    private void testInsert() {
        int[][] intervals = {{1,3}, {5,7}, {8,12}};
        List<int[]> res = insertInerval(intervals, new int[]{4,6});
        System.out.printf("%s\n", res.stream().map(a -> a[0] + "<->" + a[1]).collect(Collectors.joining(", ", "[", "]")));
    }

    private List<int[]> insertInerval(int[][] intervals, int[] interval) {
        List<int[]> res = new ArrayList<>();
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);

        int i = 0;
        //Skip intervals ended before interval to merge
        while (i < intervals.length && intervals[i][1] < interval[0]) {
            res.add(intervals[i]);
            i+=1;
        }
        //int[] merged = new int[2];
        while (i < intervals.length && intervals[i][0] <= interval[1]) {
            interval[0] = Math.min(intervals[i][0], interval[0]);
            interval[1] = Math.max(intervals[i][1], interval[1]);
            i+=1;
        }
        res.add(interval);
        while (i < intervals.length) {
            res.add(intervals[i]);
            i+=1;
        }
        return res;
    }

    public static List<Interval> merge(List<Interval> intervals) {
        Collections.sort(intervals, (i1, i2) -> i1.start - i2.start);
        Stack<Interval> stack = new Stack<>();
        for (int i = 0; i < intervals.size(); i++) {
            if (!stack.isEmpty() && intersects(stack.peek(), intervals.get(i))) {
                Interval merged = merge(stack.pop(), intervals.get(i));
                stack.push(merged);
            } else {
                stack.push(intervals.get(i));
            }
        }
        return stack.stream().collect(Collectors.toList());
    }

    private static boolean intersects(Interval interval1, Interval interval2) {
        int mergeStart = Math.max(interval1.start, interval2.start);
        int mergeEnd = Math.min(interval1.end, interval2.end);
        return mergeStart <= mergeEnd;
    }

    private static Interval merge(Interval interval1, Interval interval2) {
        int mergeStart = Math.max(interval1.start, interval2.start);
        int mergeEnd = Math.min(interval1.end, interval2.end);
        return new Interval(mergeStart, mergeEnd);
    }

    static class Interval {
        int start;
        int end;

        public Interval(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }
}
