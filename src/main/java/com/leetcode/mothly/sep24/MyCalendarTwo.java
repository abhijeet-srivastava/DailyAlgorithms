package com.leetcode.mothly.sep24;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

public class MyCalendarTwo {
    TreeMap<Integer, Integer> timeLine;
    int maxAllowedOverlap;
    public MyCalendarTwo() {
        this.timeLine = new TreeMap<>();
        this.maxAllowedOverlap = 2;
    }

    public boolean book(int start, int end) {
        this.timeLine.merge(start, 1, Integer::sum);
        this.timeLine.merge(end, -1, Integer::sum);
        int ps = 0;
        for(var timeEntry : timeLine.entrySet()) {
            ps += timeEntry.getValue();
            if(ps > this.maxAllowedOverlap) {
                this.timeLine.merge(start, -1, Integer::sum);
                this.timeLine.merge(end, 1, Integer::sum);
                if(this.timeLine.get(start) == 0) {
                    this.timeLine.remove(start);
                }
                return false;
            }
        }
        return true;
    }
    public static void main(String[] args) {
        MyCalendarTwo mc2 = new MyCalendarTwo();
        mc2.testCase1();
    }

    private void testCase1() {
        MyCalendarTwo mc2 = new MyCalendarTwo();
        System.out.printf("Res of interval (%d,%d)= %b\n", 10, 20, mc2.book(10, 20));
        System.out.printf("Res of interval (%d,%d)= %b\n", 50, 60, mc2.book(50, 60));
        System.out.printf("Res of interval (%d,%d)= %b\n", 10, 40, mc2.book(10, 40));
        System.out.printf("Res of interval (%d,%d)= %b\n", 5, 15, mc2.book(5, 15));
        System.out.printf("Res of interval (%d,%d)= %b\n", 5, 10, mc2.book(5, 10));
        System.out.printf("Res of interval (%d,%d)= %b\n", 25, 55, mc2.book(25, 55));
    }

}
