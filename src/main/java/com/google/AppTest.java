package com.google;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AppTest {

    public static void main(String[] args) {
        AppTest at = new AppTest();
        at.testSquare();
    }

    private void testSquare() {
        int[][] points = {{35264,38657},{15626,7304},{35264,19260},{28284,19260},{32875,19260},{35264,38594},{28284,38594},{32875,38657},{28284,38657},{15626,38657},{35264,7304},{15626,19260},{28284,7304}};
        int size = minAreaRect(points);
        System.out.printf("Min Size: %d\n", size);
    }
    public int minAreaRect(int[][] points) {
        //1:(1,3), 3:(1,3), 4:(1,3)
        Map<Integer, List<Integer>> x = new HashMap<>();
        for(int[] point: points) {
            x.computeIfAbsent(point[0], a -> new ArrayList<Integer>()).add(point[1]);
        }
        var xList = x.entrySet()
                .stream().filter(a -> a.getValue().size() > 1)
                .sorted((a, b) -> Integer.compare(a.getKey(), b.getKey()))
                .peek(a -> Collections.sort(a.getValue()))
                .collect(Collectors.toList());
        int minArea = Integer.MAX_VALUE;
        for(int i = 0; i < xList.size()-1; i++) {
            for(int j = i+1; j < xList.size(); j++) {
                int deltaX = xList.get(j).getKey() - xList.get(i).getKey();
                int minDeltaY = commonPoints(xList.get(i).getValue(), xList.get(j).getValue());
                if(minDeltaY == 0) {
                    continue;
                }
                minArea = Math.min(minArea, deltaX*minDeltaY);
            }
        }
        return minArea < Integer.MAX_VALUE ? minArea : 0;
    }
    private int commonPoints(List<Integer> l1, List<Integer> l2) {
        List<Integer> matches = new ArrayList<>();
        int i  = 0, j = 0;
        while(i < l1.size() && j < l2.size()) {
            if(l1.get(i) < l2.get(j)) {
                i += 1;
            } else if(l1.get(i) > l2.get(j)) {
                j += 1;
            } else {
                matches.add(l1.get(i));
                i += 1;
                j += 1;
            }
        }
        if(matches.size() < 2) {
            return 0;
        }
        int diff = matches.get(1) - matches.get(0);
        for(i = 2; i < matches.size(); i++) {
            diff = Math.min(diff, matches.get(i) - matches.get(i-1));
        }
        return diff;
    }
}
