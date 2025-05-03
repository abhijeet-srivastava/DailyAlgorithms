package com.line_sweep;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;

public class SkyLine {

    public List<List<Integer>> getSkyline(int[][] buildings) {
        int len = buildings.length;
        Point[] points = new Point[len<<1];
        for(int i = 0; i < len; i++) {
            points[i*2] = new Point(buildings[i][0], buildings[i][2], "start");
            points[i*2+1] = new Point(buildings[i][1], buildings[i][2], "end");
        }
        Arrays.sort(points, (p1, p2) -> {
            int xCordComparision = Integer.compare(p1.xCord, p2.xCord);
            if(xCordComparision != 0) {
                return xCordComparision;
            }
            int startEndComp = p2.startEnd.compareTo(p1.startEnd);
            if(startEndComp != 0) {
                return startEndComp;
            }
            if(p1.startEnd.equals("start")) {
                return Integer.compare(p2.height, p1.height);
            }
            return Integer.compare(p1.height, p2.height);
        });
        TreeMap<Integer, Integer> maxHeight = new TreeMap<>();
        maxHeight.put(0,0);
        List<List<Integer>> result = new ArrayList<>();
        for(Point point: points) {
            if (point.startEnd.equals("start")) {
                maxHeight.put(point.height, maxHeight.getOrDefault(point.height, 0) + 1);
            } else {
                int count = maxHeight.get(point.height);
                if (count == 1) {
                    maxHeight.remove(point.height);
                } else {
                    maxHeight.put(point.height, count - 1);
                }
            }
            int currMaxHeight = maxHeight.isEmpty()
                    ? point.height : point.startEnd.equals("start") ? Math.max(maxHeight.lastKey(), point.height) : maxHeight.lastKey();
            int size = result.size();
            if(size == 0 || result.get(size-1).get(1) != currMaxHeight) {
                result.add(Arrays.asList(point.xCord, currMaxHeight));
            }
        }
        return result;
    }

    private record Point(int xCord, int height, String startEnd){};
}
