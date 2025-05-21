package com.leetcode.mothly.may25;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LeetCodeDailyTest {

    @Test
    public void testDungeon2() {
        LeetCodeDaily lcd = new LeetCodeDaily();
        int[][] moveTime = {{0,4}, {4,4}};
        int time = lcd.minTimeToReach(moveTime);
        assertEquals(7, time);
    }

}