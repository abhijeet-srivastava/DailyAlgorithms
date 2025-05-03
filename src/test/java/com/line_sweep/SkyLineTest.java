package com.line_sweep;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SkyLineTest {

    @Test
    void getSkyline() {
        SkyLine skyLine = new SkyLine();
        int[][] buildings = {{2,9,10},{3,7,15},{5,12,12},{15,20,10},{19,24,8}};
        List<List<Integer>> res = skyLine.getSkyline(buildings);
        for(List<Integer> point: res) {
            System.out.printf("(%d, %d)\n", point.get(0), point.get(1));
        }
    }
}