package com.oracle.casb.leetcode.may23;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class May23 {

    Random random = new Random();
    public static void main(String[] args) {
        May23 may = new May23();
        may.test22May();
    }

    private void test22May() {
        int[] arr = {1,1,1,2,2,3};
        int[] res = topKFrequent(arr, 2);
        System.out.printf("[%s]\n", IntStream.of(res).mapToObj(String::valueOf).collect(Collectors.joining(", ")));
    }

    private int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> counter = new HashMap<>();
        for(int num: nums) {
            counter.merge(num, 1, Integer::sum);
        }
        int n = counter.size();
        int[][] uniqueFreq = new int[n][2];
        int index = 0;
        for(Map.Entry<Integer, Integer> entry: counter.entrySet()) {
            uniqueFreq[index][0] = entry.getKey();
            uniqueFreq[index][1] = entry.getValue();
            index += 1;
        }
        quickSelect(0, n-1, uniqueFreq, n-k);
        int[] res = new int[k];
        for(int i = 0; i < k; i++) {
            res[i] = uniqueFreq[n-k+i][0];
        }
        return res;
    }

    private void quickSelect(int l, int r, int[][] uniqueFreq, int k) {
        if(l == r) {
            return;
        }
        int pi = partition(uniqueFreq, l, r);
        if(pi == k) {
            return;
        } else if(pi < k) {
            quickSelect(pi+1, r, uniqueFreq, k);
        } else {
            quickSelect(l, pi-1, uniqueFreq, k);
        }
    }

    private int partition(int[][] uniqueFreq, int l, int r) {
        int pi = l + random.nextInt(r - l);
        int pivotFreq = uniqueFreq[pi][1];
        swap(uniqueFreq, pi, r);
        int si = l;
        for(int i = l; i <= r; i++) {
            if(uniqueFreq[i][1] < pivotFreq) {
                swap(uniqueFreq, i, si);
                si += 1;
            }
        }
        swap(uniqueFreq, si, r);
        return si;
    }

    private void swap(int[][] uniqueFreq, int i, int j) {
        int num = uniqueFreq[i][0], freq = uniqueFreq[i][1];
        uniqueFreq[i][0] = uniqueFreq[j][0];
        uniqueFreq[i][1] = uniqueFreq[j][1];

        uniqueFreq[j][0] = num;
        uniqueFreq[j][1] = freq;
    }
}
