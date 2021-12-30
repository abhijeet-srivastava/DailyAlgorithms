package com.walmart;

import java.util.Map;
import java.util.TreeMap;

public class MaxBeauty {

    public static void main(String[] args) {
        MaxBeauty mb = new MaxBeauty();
        mb.testMaximumBeauty();
    }

    private void testMaximumBeauty() {
        int[][] items = {{193, 732},{781, 962},{864, 954},{749, 627},{136, 746},{478, 548},{640, 908},{210, 799},{
        567, 715},{914, 388},{487, 853},{533, 554},{247, 919},{958, 150},{193, 523},{176, 656},{395, 469},{763, 821},{
        542, 946},{701, 676}};
        int[] queries = {885,1445,1580,1309,205,1788,1214,1404,572,1170,989,265,153,151,1479,1180,875,276,1584};
        int[] mb = maximumBeauty(items, queries);
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
