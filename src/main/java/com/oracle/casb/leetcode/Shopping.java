package com.oracle.casb.leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Shopping {

    public static void main(String[] args) {

    }

    public static long minArea(List<Integer> x, List<Integer> y, int k) {
        // Write your code here
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < x.size(); i++) {
            points.add(new Point(x.get(i), y.get(i)));
        }
        points.stream().sorted((a, b) -> (a.x == b.x) ? (a.y - b.y) : (a.x - b.x));
        long minArea = Long.MAX_VALUE;
        for (int i = 0; i < points.size() - k - 1; i++) {
            long currentArea = 0l;
            int lo_x = points.get(i).x - 1;
            int hi_x = points.get(i+k).x + 1;

            int lo_y = points.get(i).y - 1;
            int hi_y = points.get(i+k).y + 1;

            int sqrSide = Integer.max((hi_x - lo_x), (hi_y - lo_y));

            minArea = Long.min(minArea, sqrSide * sqrSide);
        }
        points.stream().sorted((a, b) -> (a.y == b.y) ? (a.x - b.x) : (a.y - b.y));
        for (int i = 0; i < points.size() - k - 1; i++) {
            long currentArea = 0l;
            int lo_x = points.get(i).x - 1;
            int hi_x = points.get(i+k).x + 1;

            int lo_y = points.get(i).y - 1;
            int hi_y = points.get(i+k).y + 1;

            int sqrSide = Integer.max((hi_x - lo_x), (hi_y - lo_y));

            minArea = Long.min(minArea, sqrSide * sqrSide);
        }
        return minArea;
    }

    private static class Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static int findLowestPrice(List<List<String>> products, List<List<String>> discounts) {
        // Write your code here
        Map<String, Discount> discs = getDiscountsAvailable(discounts);
        int totalCost = 0;
        for (List<String> product : products) {
            System.out.println(product.stream().collect(Collectors.joining(", ")));
            int price = Integer.valueOf(product.get(0));
            int sellPrice = Integer.MAX_VALUE;
            for (int i = 1; i < product.size(); i++) {
                Discount discount = discs.get(product.get(i));
                if (discount == null) {
                    continue;
                }
                if (discount.type == 0) {
                    sellPrice = Integer.min(sellPrice, discount.value);
                } else if (discount.type == 1) {
                    sellPrice = Integer.min(sellPrice, price *(100 - discount.value)/100);
                } else {
                    sellPrice = Integer.min(sellPrice, (price - discount.value));
                }
            }
            totalCost += sellPrice;
        }
        return totalCost;
    }

    private static Map<String, Discount> getDiscountsAvailable(List<List<String>> discounts) {
        Map<String, Discount> discs = new HashMap<>();
        for (List<String> curr : discounts) {
            discs.put(curr.get(0), new Discount(curr.get(1), curr.get(2)));
        }
        return discs;
    }

    public static class Discount {
        private Integer type;
        private Integer value;

        public Discount(String type, String value) {
            this.type = Integer.valueOf(type);
            this.value = Integer.valueOf(value);
        }
    }


}
