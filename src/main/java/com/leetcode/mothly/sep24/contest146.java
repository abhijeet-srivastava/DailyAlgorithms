package com.leetcode.mothly.sep24;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.IntStream;

public class contest146 {

    public static void main(String[] args) {
        contest146 cons = new contest146();
        //const.testKthChar();
        //cons.testMinGroups();
        //cons.testExpression();
        cons.testMinTime();
    }

    private void testMinTime() {
        String s = "aabaaaacaabc";
        int k = 2;
        int minTime  = takeCharacters(s, k);
        System.out.printf("Time: %d\n", minTime);
    }

    public int takeCharacters(String s, int k) {
        int[] counter = new int[3];
        int res = minTime(s, counter, 0, s.length()-1, k, 0);
        return res <= s.length() ? res : 0;
    }
    private int minTime(String s, int[] counter, int li, int ri, int k, int time) {
        if(counter[0] >= k && counter[1] >= k && counter[2] >= k) {
            return time;
        } else if(li > ri) {
            return s.length() + 1;
        }
        int lci = s.charAt(li) - 'a';
        int rci = s.charAt(ri) - 'a';
        counter[lci] += 1;
        int lCount = minTime(s, counter, li+1, ri, k, time+1);
        counter[lci] -= 1;

        counter[rci] += 1;
        int rCount = minTime(s, counter, li, ri-1, k, time+1);
        counter[rci] = 1;
        return Math.min(lCount, rCount);
    }



    private void testExpression() {
        String expr = "&(t,t,t)";
        boolean res = parseBoolExpr(expr);
        System.out.printf("res: %b\n", res);
    }

    public boolean parseBoolExpr(String expression) {
        Map<Character, Function<List<Character>, Character>> operators
                = Map.of(
                '&', l -> l.stream().map(a -> a == 't')
                        .reduce((a,b) -> a && b)
                        .get() ? 't' : 'f',
                '|', l -> l.stream().map(a -> a == 't')
                        .reduce((a,b) -> a || b)
                        .get() ? 't' : 'f',
                '!',  l -> l.stream().map(a -> a == 't' ? 'f' : 't').findFirst().get()
        );
        Set<Character> ops = Set.of('&', '|', '!');
        Deque<Character> stack = new ArrayDeque<>();
        boolean res = true;
        for(char ch: expression.toCharArray()) {
            if(ch == ')') {
                List<Boolean> oprends = new ArrayList<>();
                while(!stack.isEmpty()
                        && !ops.contains(stack.peek())) {
                    oprends.add(stack.pop() == 't');
                }
                if(stack.peek() == '&') {
                    boolean val = andFunction(oprends);
                    stack.pop();
                    stack.push(val ? 't' : 'f');
                } else if(stack.peek() == '|') {
                    boolean val = orFunction(oprends);
                    stack.pop();
                    stack.push(val ? 't' : 'f');
                } else if(stack.peek() == '!') {
                    boolean val = notFunction(oprends.get(0));
                    stack.pop();
                    stack.push(notFunction(oprends.get(0)) ? 't' : 'f');
                }
            } else if(ch == '(') {
                continue;
            } else {
                stack.push(ch);
            }
        }
        return stack.pop() == 't';
    }

    private boolean andFunction(List<Boolean> list) {
        return list.stream().reduce((a, b) -> a && b).orElse(true);
    }
    private boolean orFunction(List<Boolean> list) {
        return list.stream().reduce((a, b) -> a || b).orElse(true);
    }
    private boolean notFunction(Boolean value) {
        return !value;
    }

    private static void testMinGroups() {
        int[][] intervals = {{5,10},{6,8},{1,5},{2,3},{1,10}};

    }

    public int minGroups(int[][] intervals) {
        int len = intervals.length;
        int[][] timestamps = new int[len*2][3];
        for(int idx = 0; idx < len; idx++) {
            timestamps[idx*2] = new int[]{intervals[idx][0], 1, idx};
            timestamps[idx*2 + 1] = new int[]{intervals[idx][1], -1, idx};
        }
        Arrays.sort(timestamps,
                (a, b) -> (a[0] == b[0]) ? Integer.compare(a[1], b[1]) : Integer.compare(a[0], b[0]));
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> Integer.compare(a[0], b[0]));
        for(int[] ts: timestamps) {

        }

        return 0;

    }

    private void testKthChar() {
        char kthChar = kthCharacter(5);
        System.out.printf("Res: %c\n", kthChar);
        int[] nums = {2, 5};
        int max = IntStream.of(nums).boxed().max(Integer::compare).get();
    }

    public char kthCharacter(int k) {
        StringBuilder sb = new StringBuilder("a");
        while(sb.length() < k) {
            int len = sb.length();
            for(int idx = 0; idx < len;idx++) {
                char curr = sb.charAt(idx);
                char next = (char)((curr - 'a' + 1)%26 + 'a');
                System.out.printf("Curr: %c. nxt: %c\n", curr, next);
                sb.append(next);
            }
        }
        return sb.charAt(k-1);
    }
}
