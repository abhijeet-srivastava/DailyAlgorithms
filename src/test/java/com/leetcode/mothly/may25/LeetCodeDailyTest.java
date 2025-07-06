package com.leetcode.mothly.may25;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class LeetCodeDailyTest {

    @Test
    public void testDungeon2() {
        LeetCodeDaily lcd = new LeetCodeDaily();
        int[][] moveTime = {{0,4}, {4,4}};
        int time = lcd.minTimeToReach(moveTime);
        assertEquals(7, time);
    }

    @Test
    public void givenTwoDateTimesInJava8_whenDifferentiating_thenWeGetSix() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime sixHourAfter = now.plusHours(6);
        LocalDateTime tenMinsAfter = sixHourAfter.plusMinutes(30);

        Duration duration = Duration.between(now, tenMinsAfter);
        long diffHours = duration.toHours();
        long diffMins = duration.toMinutes() - diffHours*60;

        assertEquals(6, diffHours);
        assertEquals(30, diffMins);

        BigDecimal totalDiff = new BigDecimal(diffHours).add(new BigDecimal(diffMins/60d));

        System.out.printf("Total Diff: %s\n", totalDiff);
    }

    @Test
    public final void givenList_whenParitioningIntoNSublistsUsingGroupingBy_thenCorrect() {
        List<Integer> intList = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7, 8);

        Map<Integer, List<Integer>> groups =
                intList.stream().collect(Collectors.groupingBy(s -> (s - 1) / 3));
        List<List<Integer>> subSets = new ArrayList<List<Integer>>(groups.values());

        List<Integer> lastPartition = subSets.get(2);
        List<Integer> expectedLastPartition = Lists.<Integer> newArrayList(7, 8);
        assertThat(subSets.size(), equalTo(3));
        assertThat(lastPartition, equalTo(expectedLastPartition));
    }

    private final Integer MOD = 1_000_000_007;
    private final BiFunction<Long, Integer, Long> multipication = (a, b) -> (a*b)%MOD;
    public int possibleStringCount(String word, int k) {

        List<Segment> segments = new ArrayList<>();
        int count = 1;
        for(int idx = 1; idx < word.length(); idx++) {
            if(word.charAt(idx) == word.charAt(idx-1)) {
                count += 1;
            } else {
                segments.add(new Segment(word.charAt(idx-1), count));
                count = 1;
            }
        }
        long res = 1l;
        for(Segment s: segments) {
            res = multipication.apply(res, s.count);
        }

        return segments.size();
    }
    public record Segment(char ch, int count){};




}