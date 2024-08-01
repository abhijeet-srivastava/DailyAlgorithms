package com.test;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class HackerRank {

    public static void main(String[] args) {
        HackerRank hr = new HackerRank();
        //hr.testSquare();
        //hr.testIsValid();
        //hr.testInfixToPostFix();
        hr.testConcatenation();
    }

    private void testConcatenation() {
        String s = "barfoothefoobarman", word[] = {"foo", "bar"};
        List<Integer> res = findSubstring(s, word);
        System.out.printf("[%s]\n", res.stream().map(String::valueOf).collect(Collectors.joining(", ")));
    }

    private void testInfixToPostFix() {
        //String infix = "A+B*C/(E-F)";// abc*+
        String infix = "a+b*(c^d-e)^(f+g*h)-i";// abc*+
        System.out.printf("infix: %s, posfix: %s\n", infix, infixToPostfix(infix));
    }

    public List<Integer> findSubstring(String s, String[] words) {
        int len = words.length, wordLen = words[0].length();
        List<Integer> result = new ArrayList<>();
        Set<String> set = Arrays.stream(words).collect(Collectors.toSet());
        for(int i = 0; i < wordLen; i++) {
            result.addAll(backtrack(i, s, len, wordLen, set));
        }
        return result;
    }
    private List<Integer> backtrack(int idx, String s, int len, int wordLen, Set<String> target)  {
        Map<String, Integer> srcMap = new HashMap<>();
        int wordFound = 0;
        boolean extraWord = false;
        List<Integer> res = new ArrayList<>();
        for(int l = idx, r = idx; r + wordLen <= s.length(); r += wordLen) {
            String rmw = s.substring(r, r+wordLen);
            if(!target.contains(rmw)) {
                srcMap.clear();
                wordFound = 0;
                extraWord = false;
                l = r + wordLen;
                continue;
            }
            while(r-l == len*wordLen || extraWord) {
                String lmw = s.substring(l, l+wordLen);
                srcMap.merge(lmw, -1, Integer::sum);
                l += wordLen;
                if(srcMap.get(lmw) == 0) {
                    wordFound -= 1;
                } else {
                    extraWord = false;
                }
            }
            srcMap.merge(rmw, 1, Integer::sum);
            if(srcMap.get(rmw) == 1) {
                wordFound += 1;
            } else {
                extraWord = true;
            }
            if(wordFound == len && !extraWord) {
                res.add(l);
            }
        }
        return res;
    }

    public List<Integer> findSubstring1(String s, String[] words) {
        int len = words.length, wordLen = words[0].length();
        Map<String, Integer> targetMap = new HashMap<>();
        for(String word: words) {
            targetMap.merge(word, 1, Integer::sum);
        }
        List<Integer> result = new ArrayList<>();
        for(int i = 0; i < wordLen; i++) {
            result.addAll(backtrack1(i, s, len, wordLen, targetMap));
        }
        return result;
    }
    private List<Integer> backtrack1(int idx, String s, int len, int wordLen, Map<String, Integer> targetMap) {
        Map<String, Integer> srcMap = new HashMap<>();
        int wordFound = 0;
        boolean extraWord = false;
        List<Integer> res = new ArrayList<>();
        for(int l = idx, r = idx; r+wordLen <= s.length(); r += wordLen) {
            String rightMostWord = s.substring(r, r+wordLen);
            if(!targetMap.containsKey(rightMostWord)) {
                srcMap.clear();
                wordFound = 0;
                extraWord = false;
                l = r + wordLen;
                continue;
            }
            while(r-l == len*wordLen || extraWord) {
                String leftMostWord = s.substring(l, l+wordLen);
                srcMap.merge(leftMostWord, -1, Integer::sum);
                l += wordLen;
                if(srcMap.get(leftMostWord) < targetMap.get(leftMostWord)) {
                    wordFound -= 1;
                } else {
                    extraWord = false;
                }
            }
            srcMap.merge(rightMostWord, 1, Integer::sum);
            if(srcMap.get(rightMostWord) <= targetMap.get(rightMostWord)) {
                wordFound += 1;
            } else {
                extraWord = true;
            }
            if(wordFound == len && !extraWord) {
                res.add(l);
            }
        }
        return res;
    }
    private String infixToPostfix(String str) {
        Map<Character, Integer> opsPrecedence = Map.of(
                '+', 0,
                '-', 0,
                '*', 1,
                '/', 1,
                '%', 1,
                '^', 2
        );
        StringBuilder sb = new StringBuilder();
        Deque<Character> stack = new ArrayDeque<>();
        for(char ch: str.toCharArray()) {
            if(isAlphanumeric(ch)) {
                sb.append(ch);
            } else if(ch == '(') {
                stack.push(ch);
            } else if(ch == ')') {
                char op = stack.pop();
                while(op != '(') {
                    sb.append(op);
                    op = stack.pop();
                }
            } else {
                while(!stack.isEmpty() && (
                        stack.peek() != '('
                        && opsPrecedence.get(stack.peek()) >= opsPrecedence.get(ch)
                        )) {
                    sb.append(stack.pop());
                }
                stack.push(ch);
            }
        }
        while (!stack.isEmpty()) {
            sb.append(stack.pop());
        }
        return sb.toString();
    }

    private boolean isAlphanumeric(char ch) {
        return (ch >= 'a' && ch <= 'z') || ch >= 'A' && ch <= 'Z' || ch >= '0' && ch <= '9';
    }

    private void testIsValid() {
        String word = "Ya$";
        System.out.printf("%s is valid: %b\n", word, isValid(word));
    }

    public boolean isValid(String word) {
        if(word.length() < 3) {
            return false;
        }
        boolean vowel = false, consonant = false, digit = false;
        for(char ch : word.toCharArray()) {
            if(isVowel(ch)) {
                vowel = true;
                System.out.printf("%c is Vowel\n", ch);
            } else if(isConsonant(ch)){
                consonant = true;
                System.out.printf("%c is consonant\n", ch);
            } else if(isDigit(ch)){
                System.out.printf("%c is digit\n", ch);
                continue;
            } else {
                System.out.printf("%c is invalid\n", ch);
                return false;
            }
        }
        return consonant && vowel;
    }
    private boolean isConsonant(char ch) {
        return (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z');
    }
    private boolean isDigit(char ch) {
        return ch >= '0' && ch <= '9';
    }
    private boolean isVowel(char ch) {
        return ch == 'a' || ch == 'A'
                || ch == 'e' || ch == 'E'
                || ch == 'i' || ch == 'I'
                || ch == 'o' || ch == 'O'
                || ch == 'u' || ch == 'U';
    }

    private void testSquare() {
        List<String> grid = Arrays.asList("GGGGGGG", "BGBBBBG", "BGBBBBG", "GGGGGGG", "GGGGGGG", "BGBBBBG" );
        int sq = twoPluses(grid);
        System.out.printf("Square: %d\n",sq);
    }
    public  int twoPluses(List<String> grid) {
        // Write your code here
        int m = grid.size();
        if(m == 0) {
            return 0;
        }
        int n = grid.get(0).length();
        int[][] DP = new int[m][n];
        for(int r = 0; r < m; r++) {
            int count = 0;
            for(int c = 0; c < n; c++) {
                if(grid.get(r).charAt(c) == 'G') {
                    count += 1;
                    DP[r][c] = count;
                } else {
                    count = 0;
                }
            }
            count = 0;
            for(int c = n-1; c >= 0; c--) {
                if(grid.get(r).charAt(c) == 'G') {
                    count += 1;
                    DP[r][c] = Math.min(DP[r][c], count);
                } else {
                    count = 0;
                }
            }
        }
        for(int c = 0; c < n; c++) {
            int count = 0;
            for(int r = 0; r < m; r++) {
                if(grid.get(r).charAt(c) == 'G') {
                    count += 1;
                    DP[r][c] = Math.min(DP[r][c], count);
                } else {
                    count = 0;
                }
            }
            count = 0;
            for(int r = m-1; r >= 0; r--) {
                if(grid.get(r).charAt(c) == 'G') {
                    count += 1;
                    DP[r][c] = Math.min(DP[r][c], count);
                } else {
                    count = 0;
                }
                /*if(DP[r][c] > max) {
                    secMax = max;
                    max = DP[r][c];
                } else if(DP[r][c] > secMax) {
                    secMax = DP[r][c];
                }*/
            }
        }
        int[][] arr = new int[m*n][2];
        for(int i = 0; i < m; i++) {
            for(int j = 0; j < n; j++) {
                arr[i*n+j] = new int[]{DP[i][j], i*n+j};
            }
        }
        Arrays.sort(arr, (a, b) -> b[0]-a[0]);
        int maxProd = 0;
        for(int i = 0; i < arr.length-1; i++) {
            for(int j = i+1; j < arr.length; j++) {
                int[] f = arr[i], s = arr[j];
                int fr = f[1]/n, fc = f[1]%n;
                int sr = s[1]/n, sc = s[1]%n;
                int total = f[0] + f[1];
                if(!areOverLapping(fr, fc, sr, sc)) {
                    maxProd = (4*f[0]-3) * (4*s[0]-3);
                    break;
                }
            }
        }
        //return (4*max-3)*(4*secMax-3);
        return maxProd;

    }

    private boolean areOverLapping(int fr, int fc, int sr, int sc) {
        //TODO: Implement
        return false;
    }
}
