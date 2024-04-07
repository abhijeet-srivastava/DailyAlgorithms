package com.google.mission24;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GeneralProbs {
    public static void main(String[] args) {
        GeneralProbs gp = new GeneralProbs();
        //gp.testEqualPartitions();
        //gp.testMinGoal();
        //gp.testMinDiff();
        //gp.testPatternMatching();
        //gp.testResultArr();
        gp.testBinarySearch();
    }

    private void testBinarySearch() {
        List<Integer> list = new ArrayList<>();
        list.add(2);
        list.add(3);
        list.add(5);
        list.add(5);
        list.add(5);
        list.add(5);
        //int index =Collections.binarySearch(list, 5);
        int[] arr = {2,3,5,5,5,7,7,9};
        int index = bisectRight(arr, 10);
        System.out.printf("Index: %d\n", index);
    }

    public static int bisectLeft(int[] nums, int target) {
        int i = 0;
        int j = nums.length - 1;
        while (i <= j) {
            int m = i + (j-i) / 2;
            if (nums[m] >= target) {
                j = m - 1;
            } else {
                i = m + 1;
            }
        }
        return i;
    }
    public static int bisectRight(int[] nums, int target) {
        int i = 0;
        int j = nums.length - 1;
        while (i <= j) {
            int m = i + (j-i) / 2;
            if (nums[m] <= target) {
                i = m + 1;
            } else {
                j = m - 1;
            }
        }
        return j+1;
    }

    public static int bisectRight(List<Integer> nums, int target) {
        int i = 0;
        int j = nums.size() - 1;
        while (i <= j) {
            int m = i + (j-i) / 2;
            if (nums.get(m) <= target) {
                i = m + 1;
            } else {
                j = m - 1;
            }
        }
        return j+1;
    }

    private void testResultArr() {
        //int[] nums = {94,358,56,435,834,738,738};//94,56,834,738,738,358,435]
        //int[] nums = {2,1,3,3};
        //int[] nums = {5,14,3,1,2};
        int[] nums = {3,3,3,3};
        int[] res = resultArray(nums);
        String str = IntStream.of(res).mapToObj(String::valueOf).collect(Collectors.joining(", "));
        System.out.printf("Resut: [%s]\n", str);
    }

    public int[] resultArray(int[] nums) {
        List<int[]> arr1 = new ArrayList<>();
        List<int[]> arr2 = new ArrayList<>();
        arr1.add(new int[]{nums[0], 0});
        arr2.add(new int[]{nums[1], 0});
        int p = 1, q = 1;
        Comparator<int[]> comp = Comparator.<int[]>comparingInt(a -> a[0]);
        for(int idx = 2; idx < nums.length; idx++) {
            int num = nums[idx];
            int[] t1 = {num, p}, t2 = {num, q};
            int i = Collections.binarySearch(arr1, t1, comp);
            int j = Collections.binarySearch(arr2, t2, comp);
            if(i < 0) {
                i = -(i+1);
            } else {
                while(i < arr1.size() && arr1.get(i)[0] == num) {
                    i += 1;
                }
            }
            if(j < 0) {
                j = -(j+1);
            } else {
                while(j < arr2.size() && arr2.get(j)[0] == num) {
                    j += 1;
                }
            }
            int count1 = arr1.size()-i;
            int count2 = arr2.size()-j;
            if(count1 > count2) {
                arr1.add(i, t1);
                p += 1;
            } else if(count1 < count2) {
                arr2.add(j, t2);
                q += 1;
            } else if (arr2.size() < arr1.size()) {
                arr2.add(j, t2);
                q += 1;
            } else {
                arr1.add(i, t1);
                p += 1;
            }
        }
        for(int[] num: arr1) {
            nums[num[1]] = num[0];
        }
        for(int[] num: arr2) {
            nums[p + num[1]] = num[0];
        }
        return nums;
    }
    public int[] resultArray1(int[] nums) {
        Comparator<int[]> comp = Comparator.<int[]>comparingInt(a -> a[0]);
        TreeSet<int[]> arr1 = new TreeSet<>(comp), arr2 = new TreeSet<int[]>(comp);
        arr1.add(new int[]{nums[0], 0});
        arr2.add(new int[]{nums[1], 0});
        int i = 1, j = 1;
        for(int idx = 2; idx < nums.length; idx++) {
            int num = nums[idx];
            int[] t1 = {num, i}, t2 = {num, j};
            Set<int[]> ts1 = arr1.tailSet(t1, false);
            Set<int[]> ts2 = arr2.tailSet(t2, false);
            if(ts1.size() > ts2.size()) {
                arr1.add(t1);
                i += 1;
            } else if(ts1.size() < ts2.size()) {
                arr2.add(t2);
                j += 1;
            } else if(arr2.size() < arr1.size()) {
                arr2.add(t2);
                j += 1;
            } else {
                arr1.add(t1);
                i += 1;
            }
        }
        for(int[] e: arr1) {
            nums[e[1]] = e[0];
        }
        for(int[] e: arr2) {
            nums[i+e[1]] = e[0];
        }
        return nums;
    }

    private void testPatternMatching() {
        String pattern = "a?b*d**c";
        String str = "afbbdfgwtywedwwrrrrwqfc";
        System.out.printf("Match: %b\n", isMatch(str, pattern));
    }

    private void testMinDiff() {
        int[] arr = {-7370,36615,-35171,66464,-36580,-25918,-16513,-2874,4353,24235,-76833,82464,60854,-132621,69625,-9720};
        //int[] arr = {2,-1,0,4,-2,-9};
        int diff = minimumDifference(arr);
        System.out.printf("Diff: %d\n", diff);
    }

    public boolean isMatch(String s, String p) {
        int i = 0, j = 0;
        int starInd = -1, match = 0;
        while(i < s.length()) {
            if(j < p.length()
                    && (s.charAt(i) == p.charAt(j) || p.charAt(j) == '?')) {
                i += 1;
                j += 1;
            } else if(j < p.length() && p.charAt(j) == '*') {
                starInd  = j;
                match = i;
                j += 1;
            } else if(starInd >= 0) {
                j = starInd + 1;
                match += 1;
                i = match;
            } else {
                return false;
            }
        }
        while(j < p.length() && p.charAt(j) == '*') {
            j += 1;
        }
        return j == p.length();
    }

    private void testMinGoal() {
        int[] nums = {5,-7,3,5};
        int minDiff = minAbsDifference(nums, 6);
    }

    private void testEqualPartitions() {
        int[] arr = {478, 757, 314, 471, 729, 100, 459, 618};
        boolean isEqualPossible = findPartition(arr);
        System.out.printf("Possible: %b\n", isEqualPossible);
    }


    public int minAbsDifference(int[] nums, int goal) {
        int len = nums.length;
        int n = len >> 1;
        TreeSet<Integer> subSetSumLeft = generateAllSubSeqSum(0, n-1, nums);
        TreeSet<Integer> subSetSumRight = generateAllSubSeqSum(n, len-1, nums);
        int res = Integer.MAX_VALUE;
        int smallest = subSetSumRight.first(), largest = subSetSumRight.last();
        for(int fh: subSetSumLeft) {
            int diff = goal-fh;
            if(diff < smallest) {
                res = Math.min(res, Math.abs(fh + smallest - goal));
                continue;
            } else if(diff > largest) {
                res = Math.min(res, Math.abs(fh + largest - goal));
                continue;
            }
            Integer lower = subSetSumRight.floor(diff);
            if(lower != null) {
                res = Math.min(res, Math.abs(fh + lower - goal));
            }
            if(res == 0) {
                break;
            }
            Integer higher = subSetSumRight.ceiling(diff);
            if(higher != null) {
                res = Math.min(res, Math.abs(fh + higher - goal));
            }
            if(res == 0) {
                break;
            }
        }
        return res;
    }

    private TreeSet<Integer> generateAllSubSeqSum(int l, int r, int[] nums) {
        int len = r-l+1;
        int count = 1 << len;
        TreeSet<Integer> set = new TreeSet<>();
        for(int i = 0; i < count; i++) {
            int sum = 0;
            for(int j = 0; j < len; j++) {
                if ((i & (1 << j)) != 0) {
                    sum += nums[l + j];
                }
            }
            set.add(sum);
        }
        return set;
    }
    private int[] generateAllSubSeqSumArr(int l, int r, int[] nums) {
        int len = r-l+1;
        int count = 1 << len;
        int[] res = new int[count];
        for(int i = 0; i < count; i++) {
            for(int j = 0; j < len; j++) {
                if ((i & (1 << j)) != 0) {
                    res[i] += nums[l + j];
                }
            }
        }
        return res;
    }
    private boolean findPartition(int[] arr) {
        int n = arr.length;
        int sum = IntStream.of(arr).sum();
        if(sum%2 == 1) {
            return false;
        }
        int half = sum >> 1;
        boolean[][] DP = new boolean[n+1][half+1];
        Arrays.sort(arr);
        DP[0][0] = true;
        for(int i = 1; i <= n; i++) {
            DP[i][0] = true;
            for(int j = 1; j <= half; j++) {
                DP[i][j] = DP[i-1][j];
                if(j >= arr[i-1]) {
                    DP[i][j] = DP[i][j] || DP[i-1][j-arr[i-1]];
                }

            }
        }
        return DP[n][half];
    }

    public int minimumDifference(int[] nums) {
        int len = nums.length;
        int reqLen = len >> 1;
        int start = (1 << reqLen) - 1;
        int end = ((1 << len) - 1) ^ start;
        //1100   0011
        int n = 1 << len;
        //4 = 8 = 1111 0011
        int minDiff  = Integer.MAX_VALUE;
        List<int[]> list = new ArrayList<>();
        for(int i = start; i <= end; i++) {
            int sum = 0, count = 0;
            for(int j = 0; j < len; j++) {
                if((i & (1 << j)) != 0) {
                    sum += nums[j];
                    count += 1;
                }
            }
            if(count == reqLen) {
                list.add(new int[]{sum, i});
            }
        }
        for(int i = 0; i < list.size()-1; i++) {
            for(int j = i+1; j < list.size(); j++) {
                if(((list.get(i)[1] & list.get(j)[1])) != 0) {
                    //Overlapping
                    continue;
                }
                minDiff = Math.min(minDiff, Math.abs(list.get(i)[0] - list.get(j)[0]));
            }
        }
        return minDiff;
    }

    private boolean nonOverlapping(int mask, Set<Integer> masks) {
        for(int existing: masks) {
            if((existing & mask) != 0) {
                return false;
            }
        }
        return true;
    }
}
