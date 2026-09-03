package com.codesignal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Assignment {

    public static void main(String[] args) {
        Assignment a = new Assignment();
        //a.validateArraySum();
        //a.validatePatternMatch();
        a.validateSkyLine();
    }

    private void validateSkyLine() {
        int[][] buildings = {{2,9,10},{3,7,15},{5,12,12},{15,20,10},{19,24,8}};
        List<List<Integer>> res = getSkyline(buildings);
        for(List<Integer> r: res) {
            System.out.printf("(%d,%d)\n", r.get(0), r.get(1));
        }
    }

    public List<List<Integer>> getSkyline(int[][] buildings) {
        int n = buildings.length;
        int[][] points = new int[n<<1][3];
        for(int i = 0; i < n; i++) {
            points[i*2] = new int[]{buildings[i][0], buildings[i][2], 1};
            //System.out.printf("point[%d]: (%d,%d, %d)\n", i*2, buildings[i][0], buildings[i][2], 1);
            points[(i*2) + 1] = new int[]{buildings[i][1], buildings[i][2], -1};
            //System.out.printf("point[%d]: (%d,%d, %d)\n", i*2 + 1, buildings[i][1], buildings[i][2], -1);
        }
        Arrays.sort(points, (a,b) -> {
            if(a[0] != b[0]) {
                return Integer.compare(a[0], b[0]);
            }
            if(a[2] != b[2]) {
                // Priortise start first
                return Integer.compare(b[2], a[2]);
            }
            if(a[2] > 0) {
                // For both start: Greater hight first
                return Integer.compare(b[1], a[1]);
            } else {
                // Both End: Smaller hight first
                return Integer.compare(a[1], b[1]);
            }
        });
        List<List<Integer>> res = new ArrayList<>();
        TreeMap<Integer, Integer> hm = new TreeMap<>();
        hm.put(0, 0);
        for(int[] point: points) {
            System.out.printf("point:(%d,h : %d, SE: %c)\n", point[0], point[1], point[2] == 1? 'S': 'E');
            if(point[2] > 0) {
                hm.merge(point[1], 1, Integer::sum);
            } else {
                hm.merge(point[1], -1, Integer::sum);
                if(hm.get(point[1]) == 0) {
                    hm.remove(point[1]);
                }
            }
            int ht = hm.lastKey();
            res.add(Arrays.asList(point[0], ht));
        }
        return res;
    }

    private void validatePatternMatch() {
        String src = "amazing", pattern = "010";
        System.out.printf("Count: %d\n",countSubStrMatchPattern("amazing", "010"));
        System.out.printf("Count2: %d\n",countSubStrMatchPattern("codesignal", "01101"));
    }

    private void validateArraySum() {
        int[] a = new int[]{4, 0, 1, -2, 3};
        int[] b = sumArr(a);
        System.out.printf("[%s]\n", Arrays.stream(a).mapToObj(String::valueOf).collect(Collectors.joining(", ")));

    }

    private int[] sumArr(int[] a) {
        int prev = 0;
        for(int i = 0; i < a.length; i++) {
            int next  = i == a.length-1 ? 0 : a[i+1];
            int curr = a[i];
            a[i] += (prev + next);
            prev = curr;
        }
        return a;
    }

    private int countSubStrMatchPattern(String src, String pattern) {
        Set<Character> vowels = Set.of('a','e','i','o','u');
        int m = src.length(), n = pattern.length();
        if(m < n) {
            return 0;
        }
        char[] srcArr = src.toCharArray();
        for(int i = 0; i < m; i++) {
            srcArr[i] = vowels.contains(src.charAt(i)) ? '0' : '1';
        }
        int res = 0;
        for(int i = 0; i+n <= m; i++) {
            boolean match = true;
            for(int j = 0; j < n; j++) {
                char srcChar = srcArr[i+j];
                char patChar = pattern.charAt(j);
                if(srcChar != patChar) {
                    match = false;
                    break;
                }
            }
            if(match) {
                res += 1;
            }
        }
        return res;
    }
}
