package com.advent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) {
        App app = new App();
        //app.testProgram();
        //app.testMaxScore();
        //app.testBeautifulSubSet();
        app.testKthBit();
    }
    public List<String> invalidTransactions(String[] transactions) {
        /*int n = transactions.length;
        Transaction[] txns = new Transaction[n];
        for(int i = 0; i < n; i++) {
            txns[i] = new Transaction(transactions[i]);
        }*/
        //Arrays.sort(txns, (t1, t2) -> Integer.compare(t1.time, t2.time));
        List<String> invalidTxns = new ArrayList<>();
        Map<String, List<Transaction>> userTxnsMap
                = Arrays.stream(transactions).map(s -> new Transaction(s))
                .collect(Collectors.groupingBy(t -> t.name));
        return invalidTxns;
    }
    public class Transaction {
        String name;
        Integer time;
        Integer amount;
        String city;
        String txn;
        public Transaction(String txn) {
            this.txn = txn;
            String[] arr = txn.split(",");
            this.name = arr[0];
            this.time = Integer.parseInt(arr[1]);
            this.amount = Integer.parseInt(arr[2]);
            this.city = arr[3];
            List<Integer> result = new LinkedList<>();

        }
    }

    private void testKthBit() {
        for(int i = 0; i < 8; i++) {
            String str = findKthBit(i, 3);
            System.out.printf("%d str: %s, len: %d\n", i+1 ,  str, str.length());
        }
    }

    public String findKthBit(int n, int k) {
        StringBuilder sb = new StringBuilder();
        sb.append('0');
        while(n-- > 0) {
            StringBuilder next = new StringBuilder();
            next.append(sb).append('1');
            next.append(invert(sb).reverse());
            sb = next;
        }
        return sb.toString();
    }
    private StringBuilder invert(StringBuilder sb) {
        for(int i = 0; i < sb.length(); i++) {
            if(sb.charAt(i) == '0') {
                sb.setCharAt(i, '1');
            } else {
                sb.setCharAt(i, '0');
            }
        }
        return sb;
    }

    private void testBeautifulSubSet() {
        int[] arr = {2,4,6};
        int k = 2;
        int res = beautifulSubsets(arr, k);
        System.out.printf("res: %d\n", res);
    }

    public int beautifulSubsets(int[] nums, int k) {
        Map<Integer, Integer> m = new HashMap<>();
        for (int num : nums) {
            //m.put(num, m.getOrDefault(num, 0) + 1);
            m.merge(num, 1, Integer::sum);
        }
        int res = 1, prev = 0, prevPrev = 0;
        for (Map.Entry<Integer, Integer> e : m.entrySet()) {
            int cur = e.getKey();
            if (m.containsKey(cur - k)) {
                continue;
            }
            prev = 0;
            while (m.containsKey(cur)) {
                prevPrev = prev;
                prev = ((1 << m.get(cur)) - 1) * res;
                res += prevPrev;
                cur += k;
            }
            res += prev;
        }
        return res - 1;
    }

    private void testMaxScore() {
        /*String[] words = {"xxxz","ax","bx","cx"};
        char[] letters = {'z','a','b','c','x','x','x'};
        int[] score = {4,4,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,0,10};*/
        //Map<Character, List<CharScore>> map = buildTreeMap(letters, score);
        //int scoreComb = maxValueOfComb(12, map, words);
        String[] words = {"leetcode"};
        char[] letters = {'l','e','t','c','o','d'};
        int[] score = {0,0,1,1,1,0,0,0,0,0,0,1,0,0,1,0,0,0,0,1,0,0,0,0,0,0};
        int max = maxScoreWords(words, letters, score);
        System.out.printf("Max Score: %d\n", max);

    }

    private void testProgram() {
        Optional<Integer> opt = Optional.of(4);
        Optional.empty();
        List<Integer> list = Arrays.asList(1,2,3,4,5,6,7);
        list.remove(list.size()-1);

    }
    public int maxScoreWords(String[] words, char[] letters, int[] score) {
        int  n = letters.length;
        int[] max = {0};
        int[][] charScoreFreq = new int[26][2];

        for(int i = 0; i < n; i++) {
            int idx = letters[i] -'a';
            charScoreFreq[idx][1] += 1;
        }
        for(int i = 0; i < 26; i++) {
            charScoreFreq[i][0] = score[i];
        }
        backtrack(0, 0, words, charScoreFreq, max);
        return max[0];
    }
    private void backtrack(int idx, int score, String[] words, int[][] charScore, int[] max) {
        if(idx == words.length) {
            max[0] = Math.max(max[0], score);
            return;
        }
        if(covers(charScore, words[idx])) {
            int  wordScore = updateFreq(words[idx], charScore, -1);
            backtrack(idx+1, score+wordScore, words, charScore, max);
            updateFreq(words[idx], charScore, 1);
        }
        backtrack(idx+1, score, words, charScore, max);
    }

    private boolean covers(int[][] charScore, String word) {
        int[] wordCounter = new int[26];
        for(char ch: word.toCharArray()) {
            wordCounter[ch - 'a'] += 1;
        }
        for(int i = 0; i < 26; i++) {
            if(charScore[i][1] < wordCounter[i]) {
                return false;
            }
        }
        return true;
    }

    private int  updateFreq(String word, int[][] charScore, int change) {
        int score = 0;
        for(char ch: word.toCharArray()) {
            int idx = ch -'a';
            score += charScore[idx][0];
            charScore[idx][1] += change;
        }
        return score;
    }
    private int calculateScore(String word, int[][] charScore) {
        int score = 0;
        for(char ch: word.toCharArray()) {
            int idx = ch -'a';
            if(charScore[idx][1] == 0) {
                return -1;
            }
            score += charScore[idx][0];
        }
        return score;
    }
    public int maxScoreWords1(String[] words, char[] letters, int[] score) {
        int m = words.length;
        int size = 1 << m;
        int maxScore = 0;
        for(int comb = 1; comb < size; comb++) {
            Map<Character, List<CharScore>> map = buildTreeMap(letters, score);
            int currScore = maxValueOfComb(comb, map, words);
            maxScore = Math.max(maxScore, currScore);
        }
        return maxScore;
    }
    private int maxValueOfComb(int comb, Map<Character, List<CharScore>> map, String[] words) {
        int currScore = 0;
        int len = words.length;
        for(int wi = 0; wi < len; wi++) {
            if(((1 << wi) & comb) == 0) {
                continue;
            }
            for(char ch: words[wi].toCharArray()) {
                Optional<CharScore> maxCharScoreOpt = findMaxScore(ch, map);
                if(!maxCharScoreOpt.isPresent()) {
                    currScore = 0;
                    break;
                }
                currScore += maxCharScoreOpt.get().score();
            }
        }
        return currScore;
    }
    private Optional<CharScore> findMaxScore(char ch, Map<Character, List<CharScore>> map) {
        if(!map.containsKey(ch)) {
            return Optional.empty();
        }
        List<CharScore> scoreSet = map.get(ch);
        int size = scoreSet.size();
        CharScore score = scoreSet.get(size - 1);
        if(size == 1) {
            map.remove(ch);
        } else {
            scoreSet.remove(size-1);
        }
        return Optional.of(score);
    }
    private Map<Character, List<CharScore>> buildTreeMap(char[] letters, int[] score) {
        Map<Character, List<CharScore>> map = new HashMap<>();
        //Comparator<CharScore> comp = Comparator<CharScore>.comparingInt(a -> a.score)reversed;
        Comparator<CharScore> comp = (a, b) -> Integer.compare(a.score, b.score);
        int n = letters.length;
        for(int i = 0; i < n; i++) {
            map.computeIfAbsent(letters[i], e -> new ArrayList<CharScore>()).add(new CharScore(letters[i], score[i]));
        }
        for(List<CharScore> value: map.values()) {
            Collections.sort(value, comp);
        }
        return map;
    }
    private record CharScore(char ch, int score){};
}
