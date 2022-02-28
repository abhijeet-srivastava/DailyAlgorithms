package com.walmart;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

public class MaxBeauty {

    public static void main(String[] args) {
        MaxBeauty mb = new MaxBeauty();
        //mb.testMaximumBeauty();
        mb.testUnique();
    }

    private void testUnique() {
        int[] nums = {3,1,1,4,1,5};
        int pairs = findPairs(nums, 2);
        System.out.printf("Unique Pairs: %d\n", pairs);
    }

    private void testMaximumBeauty() {
        int[][] items = {{193, 732},{781, 962},{864, 954},{749, 627},{136, 746},{478, 548},{640, 908},{210, 799},{
        567, 715},{914, 388},{487, 853},{533, 554},{247, 919},{958, 150},{193, 523},{176, 656},{395, 469},{763, 821},{
        542, 946},{701, 676}};
        int[] queries = {885,1445,1580,1309,205,1788,1214,1404,572,1170,989,265,153,151,1479,1180,875,276,1584};
        int[] mb = maximumBeauty(items, queries);
    }
    public int findPairs(int[] nums, int k) {
        Arrays.sort(nums);
        if(k == 0) {
            return uniquePairs(nums);
        }
        int count = 0;
        int l = 0;
        while(l < nums.length-1) {
            int tmp = l;
            while(tmp < nums.length && nums[tmp] == nums[l]) {
                tmp += 1;
            }
            l = tmp-1;
            int r = l+1;
            while(r < nums.length && (nums[r] - nums[l]) < k) {
                r += 1;
            }
            if(r < nums.length && (nums[r] - nums[l]) == k) {
                count += 1;
            }
            l+= 1;
        }
        return count;
    }


    private int uniquePairs(int[] nums) {
        int count = 0;
        int l = 0;
        while(l < nums.length) {
            int tmp = l;
            while(tmp < nums.length && nums[tmp] == nums[l]) {
                tmp += 1;
            }
            l = tmp-1;
            if(l > 0 && nums[l-1] == nums[l]) {
                count += 1;
            }
            l += 1;
        }
        return count;

    }

    public int[] maximumBeauty(int[][] items, int[] queries) {
        TreeMap<Integer, Integer> priceBeautyMap = new TreeMap<>();
        for(int[] item : items) {
            if(priceBeautyMap.containsKey(item[0])
                    && priceBeautyMap.get(item[0]) > item[1]) {
                continue;
            }
            priceBeautyMap.put(item[0], item[1]);
        }
        int[] res = new int[queries.length];
        for(int i = 0; i < queries.length; i++) {
            Map.Entry<Integer, Integer> floorEntry = priceBeautyMap.floorEntry(queries[i]);
            res[i] = floorEntry == null ? 0 : floorEntry.getValue();
        }
        return res;
    }
}
