package com.leetcode.contests.contest264;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Weekly285 {

    public static void main(String[] args) {
        Weekly285 contest = new Weekly285();
        //contest.testPalindrome();
        //contest.testminReplace();
        //contest.testScore();
        contest.testMatrixSearch();
    }

    private void testMatrixSearch() {
        int[][] matrix = {{1,3,5,7},{10,11,16,20},{23,30,34,60}};
        boolean f = searchMatrix(matrix, 13);
    }

    public boolean searchMatrix(int[][] matrix, int target) {
        int m = matrix.length;
        int n = matrix[0].length;
        int row = 0, col = n-1;
        while(row < m && col >= 0) {
            if(matrix[row][col] == target) {
                return true;
            } else if(matrix[row][col] < target) {
                row += 1;
            } else {
                //col -= 1;
                int lo = 0, hi = col;
                while(hi-lo > 1) {
                    int mid = lo + ((hi-lo)/2);
                    System.out.printf("Row: %d, lo: %d, hi; %d, mid; %d\n", row, lo, hi, mid);
                    if(matrix[row][mid] == target) {
                        return true;
                    } else if(matrix[row][mid] < target) {
                        lo = mid+1;
                    } else {
                        hi = mid-1;
                    }
                }
                return false;
            }
        }
        return false;
    }

    private void testScore() {
        int[][] mat = {{1,0}, {0,0}, {1,0}};
        int[] scores = kWeakestRows(mat, 2);
        System.out.printf("[%s]\n", IntStream.of(scores).mapToObj(String::valueOf).collect(Collectors.joining()));
    }

    public int maxValueOfCoins(List<List<Integer>> piles, int k) {
        int len = piles.size();
        //DP[i][j] = Choose k elements for list  index  i....piles.size()
        Integer[][] DP = new Integer[len+1][k+1];
        return maxValueOfCoins(0, k, piles, DP);
    }

    private int maxValueOfCoins(int index, int k, List<List<Integer>> piles, Integer[][] DP) {
        if(index == piles.size() || k == 0) {
            return 0;
        } else if(DP[index][k] != null) {
            return DP[index][k];
        }
        List<Integer> currentPile = piles.get(index);
        //Do no choose any of item from current pile
        int res =  maxValueOfCoins(index+1, k, piles, DP);
        //Choose 1,2....Math.min(k, currPile.size()) items from current  index and rest from subsequent indices
        int sum = 0;
        for(int i = 0; i < Math.min(k, currentPile.size()); i++) {
            sum += currentPile.get(i);
            int rest = maxValueOfCoins(index+1, k-i-1, piles, DP);
            res = Math.max(res, sum+rest);
        }
        DP[index][k] = res;
        return res;
    }

    public int[] kWeakestRows(int[][] mat, int k) {
        PriorityQueue<int[]> pq = new PriorityQueue<>(
            Comparator.comparingInt(
                (int[] score) -> score[1]
            ).reversed().thenComparing(
                (int[] score) -> score[0]
            ).reversed()
        );
        int m = mat.length, n = mat[0].length;
        for(int r = 0; r < m; r++) {
            int count = -1;
            int lo = 0, hi = n-1;
            while(lo <= hi) {
                int mid = lo + ((hi-lo) >> 1);
                if(mat[r][mid] == 1) {
                    count = mid;
                    lo = mid+1;
                } else {
                    hi = mid-1;
                }
            }
            count += 1;
            int[] score = {r, count};
            pq.offer(score);
            if(pq.size() > k) {
                pq.poll();
            }
        }
        int[] res = new int[k];
        for(int i = 0; i < k; i++) {
            res[k-i-1] = pq.poll()[0];
        }
        return res;
    }
    private void testminReplace() {
        int[] arr = {1,1,2,2,3,3};
        int res = minDeletion(arr);
        System.out.printf("Min: %d\n", res);
    }


    public int minDeletion(int[] nums) {
        int count = 0;
        if(nums.length <= 1) {
            return nums.length;
        }
        int i = 0;
        int ind1 = 0;
        int ind2 = 1;
        while(ind2 < nums.length) {
            if(nums[ind1] == nums[ind2]) {
                count+=1;
                ind2 += 1;
            } else {
                ind1 = ind2 + 1;
                ind2 = ind1 + 1;
            }
        }
        if((nums.length - count)%2 != 0) {
            count += 1;
        }
        return count;
    }
    private void testPalindrome() {
        int[] q = {6,910748851,2,2,471967776,568041505,706056676,28558130,8,6,9,9,3,8};
        long[] res = kthPalindrome(q, 1);
        System.out.printf("res: %d\n", res.length);
    }
    public long[] kthPalindrome(int[] queries, int intLength) {
        int base =  Double.valueOf(Math.pow(10, (intLength+1)/2  - 1)).intValue();
        int max = 9*base;
        long[] res = new long[queries.length];
        Arrays.fill(res, -1l);
        boolean isOdd = intLength%2 != 0;
        for(int i=0; i < queries.length; i++) {
            int query = queries[i];
            if(query>max) {
                continue;
            }
            int val = base+query-1;
            long num = val;
            if(isOdd) {
                val /= 10;
            }
            while (val > 0) {
                int rem  = val%10;
                val /= 10;
                num = num *10 + rem;
            }
            res[i] = num;
        }
        return res;
    }
    private char[] getNextPalindrome(char[] current) {
        int len = current.length;
        int mid = len%2 == 0 ? len/2 - 1 : len/2;
        while(mid >= 0 && current[mid] == '9') {
            mid -= 1;
        }
        if(mid < 0) {
            return null;
        }
        int m = len%2 == 0 ? len/2 - 1 : len/2;
        while(m > mid) {
            current[m] = '0';
            current[len-1-m] = '0';
            m -= 1;
        }
        char next = (char)(current[mid] + 1);
        current[mid] = next;
        current[len-1-mid] = next;
        return current;
    }
}
