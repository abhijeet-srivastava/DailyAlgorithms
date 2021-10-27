package com.oracle.casb.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SkyLine {


    public List<int[]> getSkyline1(int[][] buildings) {
        return Arrays.stream(buildings)
                .sorted((a, b) -> (a[0] == b[0]) ? a[1] - b[1] : a[0] - b[0])
                .map(a -> mapToListArr(a))
                .reduce((a, b) -> reduceBuilding(a, b))
                .get();
    }

    private List<int[]> reduceBuilding(List<int[]> a, List<int[]> b) {
        return Collections.emptyList();
    }

    private List<int[]> mapToListArr(int[] a) {
        List<int[]> list = new ArrayList<>();
        list.add(new int[]{a[0], a[1], 0});
        list.add(new int[]{a[0], a[2], 1});
        return list;
    }

    public List<int[]> getSkyline(int[][] buildings) {
        int n = buildings.length;
        List<int[]> output = new ArrayList<int[]>();

        // The base cases
        if (n == 0) return output;
        if (n == 1) {
            output.add(new int[]{buildings[0][0], buildings[0][2]});
            output.add(new int[]{buildings[0][1], buildings[0][2]});
           /* int xStart = buildings[0][0];
            int xEnd = buildings[0][1];
            int y = buildings[0][2];

            output.add(new int[]{xStart, y});
            output.add(new int[]{xEnd, 0});*/
            return output;
        }

        // If there is more than one building,
        // recursively divide the input into two subproblems.
        List<int[]> leftSkyline, rightSkyline;
        leftSkyline = getSkyline(Arrays.copyOfRange(buildings, 0, n / 2));
        rightSkyline = getSkyline(Arrays.copyOfRange(buildings, n / 2, n));

        // Merge the results of subproblem together.
        return mergeSkylines(leftSkyline, rightSkyline);
    }

    public List<int[]> mergeSkylines(int[] first, int[] second) {
        List<int[]> res = new ArrayList<>();
        int[][] tempRes = new int[3][3];
        int[] s1 = (first[0] < second[0]) ? first  : second;
        int[] s2 = (first[0] < second[0]) ? second  : first;

        int[] e1 = (first[1] < second[1]) ? first  : second;
        int[] e2 = (first[1] < second[1]) ? second  : first;

        int[] strip1 = new int[]{s1[0], s2[0], s1[2]};
        if (strip1[0] != strip1[1])
            res.add(strip1);

        int height2 = (s1[2] > s2[2]) ? s1[2] : s2[2];
        int[] strip2 = new int[]{s2[0], e1[1], height2};
        if (strip1[2] == height2) {
            strip1[1] = e1[1];
            strip2 = strip1;
        } else {
            res.add(strip2);
        }
        int[] strip3 = new int[]{e1[1], e2[1], e2[2]};

        if (height2 == e2[2]) {
            strip2[1] =  e2[1];
            strip3 = strip2;
        } else {
            if (strip3[0] != strip3[1])
                res.add(strip3);
        }
        return res;
    }
    /**
     *  Merge two skylines together.
     */
    public List<int[]> mergeSkylines(List<int[]> left, List<int[]> right) {
        int nL = left.size(), nR = right.size();
        int pL = 0, pR = 0;
        int currY = 0, leftY = 0, rightY = 0;
        int x, maxY;
        List<int[]> output = new ArrayList();

        // while we're in the region where both skylines are present
        while ((pL < nL) && (pR < nR)) {
            int[] pointL = left.get(pL);
            int[] pointR = right.get(pR);
            // pick up the smallest x
            if (pointL[0] < pointR[0]) {
                x = pointL[0];
                leftY = pointL[1];
                pL++;
            }
            else {
                x = pointR[0];
                rightY = pointR[1];
                pR++;
            }
            // max height (i.e. y) between both skylines
            maxY = Math.max(leftY, rightY);
            // update output if there is a skyline change
            if (currY != maxY) {
                updateOutput(output, x, maxY);
                currY = maxY;
            }
        }

        // there is only left skyline
        currY = appendSkyline(output, left, pL, nL, currY);

        // there is only right skyline
        currY = appendSkyline(output, right, pR, nR, currY);

        return output;
    }

    /**
     * Update the final output with the new element.
     */
    public void updateOutput(List<int[]> output, int x, int y) {
        // if skyline change is not vertical -
        // add the new point
        if (output.isEmpty() || output.get(output.size() - 1)[0] != x)
            output.add(new int[]{x, y});
            // if skyline change is vertical -
            // update the last point
        else {
            output.get(output.size() - 1)[1] = y;
        }
    }

    /**
     *  Append the rest of the skyline elements with indice (p, n)
     *  to the final output.
     */
    public int appendSkyline(List<int[]> output, List<int[]> skyline,
                             int p, int n, int currY) {
        while (p < n) {
            int[] point = skyline.get(p);
            int x = point[0];
            int y = point[1];
            p++;

            // update output
            // if there is a skyline change
            if (currY != y) {
                updateOutput(output, x, y);
                currY = y;
            }
        }
        return currY;
    }
}
