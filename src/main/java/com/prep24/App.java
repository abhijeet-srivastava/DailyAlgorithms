package com.prep24;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) {
        App app = new App();
        //app.testCountStringWithPairs();
        //app.testFormula();
        app.testPrintCombinations();
    }

    private void testPrintCombinations() {
        int[] coins = {1,5,10, 15, 25};
        //printValidCombinations(10, coins);
        printValidCombinationsDP(10, coins);

    }

    private void printValidCombinationsDP(int value, int[] coins) {
        int len = coins.length;
        Map<Integer, List<int[]>> DP = new HashMap<>();
        DP.put(0, Arrays.asList(new int[len]));
        for(int val = 1; val <= value; val++) {
            List<int[]> currCombinations = new ArrayList<>();
            for(int cIdx = 0; cIdx < len; cIdx++) {
                int coin = coins[cIdx];
                if(coin > val) {
                    continue;
                }
                List<int[]> prevCombinations = DP.get(val-coin);
                for(int[] prevComb: prevCombinations) {
                    int[] newComb = prevComb.clone();
                    newComb[cIdx] += 1;
                    currCombinations.add(newComb);
                }
            }
            DP.put(val, currCombinations);
        }
        List<int[]> validCombinations = DP.get(value);
        for (int[] combination: validCombinations) {
            System.out.printf("~~~~~~~~~~~~~~~Valid Combination~~~~~~~~~~~`\n");
            for(int idx = 0; idx < len; idx++) {
                if(combination[idx] > 0) {
                    System.out.printf("%d coin of denomination %d\n", combination[idx], coins[idx]);
                }
            }
        }
    }

    public  void findCombinations(int[] coins, int amount, List<Integer> combination, List<List<Integer>> result, int start) {
        if (amount == 0) {
            result.add(new ArrayList<>(combination));
            return;
        }

        for (int i = start; i < coins.length; i++) {
            if (coins[i] <= amount) {
                combination.add(coins[i]);
                findCombinations(coins, amount - coins[i], combination, result, i); // Allow unlimited coins of the same denomination
                combination.remove(combination.size() - 1);
            }
        }
    }

    //[0,
    private void printValidCombinations(int val, int[] coins) {
        int len = coins.length;
        int[] combination = new int[len];
        backtrack(0, val, coins, combination);
    }

    private void backtrack(int startIdx, int val, int[] coins, int[] combination) {
        if(val == 0) {
            System.out.printf("~~~~~~~~~~~~Combination~~~~~~~~~~~~~~~~~~\n");
            for(int i = 0; i < coins.length; i++) {
                if(combination[i] > 0) {
                    System.out.printf("%d coin of denomination %d\n", combination[i], coins[i]);
                }
            }
            return;
        }
        for(int idx = startIdx; idx < coins.length; idx++) {
            if(coins[idx] <= val) {
                combination[idx] += 1;
                backtrack(idx, val - coins[idx], coins, combination);
                combination[idx] -= 1;
            }
        }
    }

    private void testFormula() {
        //String formula = "Mg(OH)2";
        String formula = "K4(ON(SO3)2)2";
        String val = countOfAtoms(formula);
        System.out.printf("Val: %s\n", val);
    }

    public String countOfAtoms(String formula) {
        Deque<AtomCounts> stack = new ArrayDeque<>();
        int idx = 0, len = formula.length();
        while(idx < len) {
            char ch = formula.charAt(idx);
            if(ch == '(') {
                AtomCounts ac = new AtomCounts("(");
                stack.push(ac);
                idx += 1;
                continue;
            } else if(ch == ')') {
                int i = idx + 1;
                while(i < len && isDigit(formula.charAt(i))) {
                    i += 1;
                }
                int count = idx == i-1 ? 1: Integer.parseInt(formula.substring(idx+1, i));
                List<AtomCounts> tmp = new ArrayList<>();
                while(!stack.isEmpty() && !"(".equals(stack.peek().name)) {
                    AtomCounts ac = stack.pop();
                    ac.count *= count;
                    tmp.add(ac);
                }
                stack.pop();
                for(AtomCounts ac: tmp) {
                    stack.push(ac);
                }
                idx = i;
            } else {
                int i = idx+1;
                while(i < len && isCharacter(formula.charAt(i))) {
                    i += 1;
                }
                String name = formula.substring(idx, i);
                idx = i;
                while(i < len && isDigit(formula.charAt(i))) {
                    i += 1;
                }
                int count = idx == i ? 1 : Integer.parseInt(formula.substring(idx, i));
                stack.push(new AtomCounts(name, count));
                idx = i;
            }
        }
        Map<String, Integer> counter = new HashMap<>();
        while (!stack.isEmpty()) {
            AtomCounts ac = stack.pop();
            counter.merge(ac.name, ac.count, Integer::sum);
        }
        return counter.entrySet().stream()
                .sorted((a, b) -> a.getKey().compareTo(b.getKey()))
                .map(ac -> format(ac))
                .collect(Collectors.joining(""));
    }

    private String format(Map.Entry<String, Integer> ac) {
        return ac.getKey() + (ac.getValue() == 1 ? "": ac.getValue());
    }

    private boolean isDigit(char ch) {
        return ch >= '0' && ch <= '9';
    }
    private boolean isCharacter(char ch) {
        return (ch >= 'a' && ch <= 'z');// || (ch >= 'A' && ch <= 'Z');
    }
    private class AtomCounts {
        String name;
        Integer count;
        public AtomCounts(String name) {
            this(name, 1);
        }
        public AtomCounts(String name, int count) {
            this.name = name;
            this.count = count;
        }
        @Override
        public String toString() {
            return name + (count == 1 ? "" : count.toString());
        }
    }

    private void testCountStringWithPairs() {
        int count = countSubStrWithKPairs("010101", 2);
        System.out.printf("Count: %d\n", count);
    }

    private int countSubStrWithKPairs(String s, int k) {
        //int countPairs = countNumberOfPairs(s);
        int count = 0;
        for(int windowSize = k*2; windowSize <= s.length(); windowSize++) {
            for(int l = 0; l <= s.length() - windowSize; l++) {
                int r = l+windowSize-1;
                Map<Character, Integer> counter = new HashMap<>();
                for(int i = l; i <= r; i++) {
                    counter.merge(s.charAt(i), 1, Integer::sum);
                }
                if(atLeastKPairs(counter, k)) {
                    count += 1;
                }
            }
        }
        return count;
    }

    private boolean atLeastKPairs(Map<Character, Integer> counter, int k) {
        int cp = 0;
        int prev = 0;
        for(int freq: counter.values()) {
            if(prev > 0 && prev != freq ) {
                return false;
            }
            prev = freq;
            cp += 1;
        }
        return cp >= k;
    }

    private  int countNumberOfPairs(String s) {
        Map<Character,Integer> freq = new HashMap<>();
        for(char ch: s.toCharArray()) {
            freq.merge(ch, 1, Integer::sum);
        }
        int countPairs = 0;
        for(var t: freq.entrySet()) {
            if(t.getValue() > 1) {
                countPairs += 1;
            }
        }
        return countPairs;
    }
}
