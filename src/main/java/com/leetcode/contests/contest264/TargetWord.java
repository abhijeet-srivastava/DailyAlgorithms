package com.leetcode.contests.contest264;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class TargetWord {

    public static void main(String[] args) {
        TargetWord tw = new TargetWord();
        //tw.testTargetWord();
        tw.testTaskOrder();
    }

    private void testTaskOrder() {
        //int[][] tasks = {{5,2},{7,2},{9,4},{6,3},{5,10},{1,1}};
        //int[][] tasks = {{7,10},{7,12},{7,5},{7,4}, {7,2}};
        int[][] tasks = {{5,6},{9,4},{3,9},{3,7},{1,1},{6,9},{9,1}};
        int[] order = getOrder(tasks);
        System.out.printf("Actual:   %s\n", IntStream.of(order).mapToObj(Integer::new).map(String::valueOf).collect(Collectors.joining(",", "[", "]")));
        System.out.printf("Expected: %s\n","[6,1,2,9,4,10,0,11,5,13,3,8,12,7]");
    }

    public TargetWord() {
    }

    private void testTargetWord() {
        String[] startWords = {"ant","act","tack"};
        String[] targetWords = {"tack","act","acti"};
        int[] jobs = {};
        Integer[] arr = IntStream.of(jobs).mapToObj(Integer::valueOf).sorted(Comparator.reverseOrder()).toArray(Integer[]::new);
        int matchedCount = wordCount(startWords, targetWords);
        System.out.printf("Matched: %d\n", matchedCount);
    }
    public int wordCount(String[] startWords, String[] targetWords) {
        Set<Integer> srcSet = new HashSet<>();
        for(String srcWord: startWords) {
            int bwx = 0;
            for(char ch : srcWord.toCharArray()) {
                bwx ^= (1 << (ch - 'a'));
            }
            srcSet.add(bwx);
        }
        int count = 1;
        Set<Integer> targetSet = new HashSet<>();
        for(String targetWord: targetWords) {
            int tbwx = 0;
            for(char ch : targetWord.toCharArray()) {
                tbwx ^= (1 << (ch - 'a'));
            }
            for(char ch : targetWord.toCharArray()) {
                //XOR each character in
                int newWord = tbwx ^ (1 << (ch - 'a'));
                if(srcSet.contains(newWord)) {
                    count += 1;
                    break;
                }
            }
        }
        return count;
    }


    public int[] getOrder(int[][] tasks) {
        int n = tasks.length;
        int[][] taskList = new int[n][3];
        for(int i = 0; i < n; i++) {
            taskList[i][0] = tasks[i][0];
            taskList[i][1] = tasks[i][1];
            taskList[i][2] = i;
        }
        //Sort by Enqueue time
        Arrays.sort(taskList,(t1, t2) -> (t1[0] == t2[0])
                ? (t1[2] - t2[2]) : (t1[0] - t2[0]));
        int index= 0;
        //Priortise by completion time
        PriorityQueue<int[]> tasq= new PriorityQueue<>((t1, t2) -> (t1[1] == t2[1])
                ? (t1[2] - t2[2]) : (t1[1] - t2[1]));
        int[] result = new int[n];
        int i = 0;
        //Next possible available time is lowest enqueue time
        int currentTime = taskList[index][0];
        while(index < n || !tasq.isEmpty())  {
            //Add all tasks which are less than  or equal to next possible enqueue time
            while(index < n && taskList[index][0] <= currentTime) {
                tasq.offer(taskList[index]);
                index += 1;
            }
            if(tasq.isEmpty()  && index  == n) {
                break;
            }
            //If there is no tasks in tasq, but we have available tasks, Update next enqueue time as next
            if(tasq.isEmpty() && index < n) {
                currentTime = taskList[index][0];
                continue;
            }
            int[]  nextTask = tasq.poll();
            result[i++] = nextTask[2];
            currentTime = currentTime + nextTask[1];
        }
        return result;
    }
    public int eraseOverlapIntervals(int[][] intervals) {
        Arrays.sort(intervals, (i1, i2) -> (i1[0] == i2[0])
                ?(i1[1] -  i2[1]) :  (i1[0]-i2[0]));
        String s = Stream.of(intervals).map(i -> String.format("{%d, %d}", i[0], i[1])).collect(Collectors.joining(", "));
        int count = 0;
        int i = 0;
        int[] prev = intervals[0];
        while(i < intervals.length){
            if(overlaps(prev, intervals[i])) {
                count += 1;
            } else {
                prev = intervals[i];
            }
            i += 1;
        }
        return count;
    }

    private boolean overlaps(int[] interval1, int[] interval2) {
        return Math.max(interval1[0], interval2[0]) < Math.min(interval1[1], interval2[1]);
    }

        public int minimizedMaximum(int n, int[] quantities) {
            int left  = 0;
            int right = IntStream.of(quantities).max().getAsInt();
            while(left < right) {
                int mid = left + ((right-left)/2);
                int stores = getStoreCount(mid, quantities);
                //System.out.printf("For size: %d, stores requird: %d\n", mid, stores);
                if(stores <= n) {
                    right = mid;
                } else {
                    left = mid+1;
                }
            }
            return right;
        }

        private int getStoreCount(int size, int[] quantities) {
            int count = 0;
            int i = 0;
            while(i < quantities.length) {
                count += Math.ceil((double)quantities[i]/size);
                i += 1;
            }

            return count;
        }

    public int splitArray(int[] nums, int m) {
    int left = Integer.MAX_VALUE;

    int right = 0;
    for(int num : nums) {
        right = Math.max(right, num);
        left = Math.min(left, num);
    }
        while(left < right) {
        int mid = left + ((right-left)>>1);
        int counts = getCounts(mid, nums);
        System.out.printf("Left: %d, Right: %d, mid: %d, count: %d\n", left, right, mid, counts);
        if(counts <= m) {
            right = mid;
        } else {
            left = mid+1;
        }
    }
        return right;
}


    private int getCounts(int k, int[] nums) {
        int currSum = 0;
        int counts = 0;
        for(int i =0; i< nums.length; i++) {
            if((currSum + nums[i]) > k) {
                counts += 1;
                currSum = nums[i];
            } else {
                currSum += nums[i];
            }
            System.out.printf("i: %d, nums[i]: %d, currSum: %d, counts: %d\n", i, nums[i], currSum, counts);
        }
        return counts;
    }
}
