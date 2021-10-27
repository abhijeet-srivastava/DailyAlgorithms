package com.oracle.casb.CodeJam;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created By : abhijsri
 * Date  : 16/09/18
 **/
public class MaxNeckLaceLength {
    private static Map<String, Integer> cache = new HashMap<>();
    public static void main(String[] args) {
        MaxNeckLaceLength mnll = new MaxNeckLaceLength();
        cache.put("1_1_1_0)", 3);
        cache.put("1_1_1_1)", 4);
        cache.put("0_0_0_0)", 0);
        cache.put("1_0_0_0)", 1);
        cache.put("0_1_0_0)", 1);
        cache.put("0_0_1_0)", 1);
        cache.put("0_0_0_1)", 1);
        int len = mnll.calculatemaxLen();
    }

    private int calculatemaxLen() {
        Scanner sc = new Scanner(System.in);
        int[] stones = new int[4];
        stones[0] = Integer.valueOf(sc.nextLine());
        stones[1] = Integer.valueOf(sc.nextLine());
        stones[2] = Integer.valueOf(sc.nextLine());
        stones[3] = Integer.valueOf(sc.nextLine());
        return getMaxLen(stones);
    }

    private int calculateLen(int[] stones) {
        return 0;
    }

    private int getMaxLen(int[] stones) {
        String key = builtKey(stones);
        if (cache.containsKey(key)) {
            return cache.get(key);
        } else {
            int maxLen = getMaxLen(stones);
            cache.put(key, maxLen);
            return maxLen;
        }
    }

    private String builtKey(int[] stones) {
        return null;
    }
}
