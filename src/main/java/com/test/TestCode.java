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
import java.util.Stack;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class TestCode {

    public static void main(String[] args) {
        TestCode tc = new TestCode();
        //tc.solveContnated();
        //tc.test3Sum();
        //tc.testFindAllConcat();
        //tc.testCheckInclusion();
        //tc.testCountStable();
        //tc.testCountAndSay();
        //tc.testLongestHappy();
        //tc.testLargestSquare();
        tc.testJump();
    }

    private void testJump() {
        String str = "74256312214562469823";
        StringBuilder sb = new StringBuilder();
        int n = str.length();
        int count = 0;
        for(int i = 0; i < n; i++) {
            if(count > 0 && count%3 == 0) {
                sb.append(',');
            }
            count += 1;
            sb.append(str.charAt(i));
        }
        /*for(int i = n -1; i >= 0; i--) {
            sb.append(str.charAt(i));
            int consumed = n-i;
            int rest = n - consumed;
            if(i > 0 && rest%3 == 0) {
                sb.append(',');
            }
        }*/
        System.out.printf("final: %s\n", sb.reverse().toString());
       /* int[] arr = {2,3,1,1,4};
        int count = jump(arr);
        System.out.printf("count: %d\n", count);*/
    }

    public int jump(int[] nums) {
        int n = nums.length;
        int[] DP = new int[n];
        Arrays.fill(DP, Integer.MAX_VALUE);
        DP[0] = 0;
        for(int i = 0; i < n; i++) {
            int jump = nums[i];
            for(int j = i+1; j <= Math.min(n-1, i+jump); j++) {
                DP[j] = Math.min(DP[j], 1+DP[i]);
            }
        }
        return DP[n-1];
    }

    private void testLargestSquare() {
        int[][] grid = {{7,1,4,5,6},{2,5,1,6,4},{1,5,4,3,2},{1,2,7,3,4}};
        int len = largestMagicSquare(grid);
        System.out.printf("MagicSquare: %d\n",len);
    }

    public int largestMagicSquare(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int size = Math.min(m, n);
        int[][] psRow = new int[m][n+1];
        for(int i = 0; i < m; i++) {
            for(int j = 0; j < n; j++) {
                psRow[i][j+1] = psRow[i][j] + grid[i][j];
            }
        }
        int[][] psCol = new int[n][m+1];
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++) {
                psCol[i][j+1] = psCol[i][j] + grid[j][i];
            }
        }
        for(int k = size; k > 1; k--) {
            for(int i = 0; i+k-1 < m; i++) {
                for(int j = 0; j+k-1 < n; j++) {
                    if(isMagicSquare(i, j, k, psRow, psCol, grid)) {
                        return k;
                    }
                }
            }
        }
        return 1;
    }
    //0,1,5,11,13
    private boolean isMagicSquare(int i, int j, int k, int[][] psRow, int[][] psCol, int[][] grid) {
        int reqSum = psRow[i][j+k] - psRow[i][j];
        for(int r = 0; r < k; r++) {
            //Verify all row have sum = reqSum
            int currRowSum = psRow[r+i][j+k] -  psRow[r+i][j];
            if(currRowSum != reqSum) {
                return false;
            }
        }
        for(int c = 0; c < k; c++) {
            int currColSum = psCol[c+j][i+k] - psCol[c+j][i];
            if(currColSum != reqSum) {
                return false;
            }
        }
        int d1 = 0, d2 = 0;
        int d1i = i, d1j = j;
        int d2i = i, d2j = j+k-1;
        for(int len = 0; len < k; len++) {
            d1 += grid[d1i++][d1j++];
            d2 += grid[d2i++][d2j--];
        }
        if(d1 != reqSum || d2 != reqSum) {
            return false;
        }
        return true;
    }

    private void testLongestHappy() {
        String res = longestDiverseString(2,2,1);
        System.out.printf("res: %s\n", res);
    }
    public String longestDiverseString(int a, int b, int c) {
        return generate(a, b, c, 'a', 'b', 'c');
    }
    private String generate(int lg, int mid, int sm, char slg, char smid, char ssm) {
        if(mid > lg) {
            return generate(mid, lg, sm, smid, slg, ssm);
        }
        if(sm > mid) {
            return generate(lg, sm, mid, slg, ssm, smid);
        }
        StringBuilder sb = new StringBuilder();
        int count = Math.min(2, lg);
        sb.append(repeat(slg, count));
        lg -= count;
        if(mid == 0) {
            return sb.toString();
        }
        count = lg > mid ? Math.min(1, mid): Math.min(2, mid);
        sb.append(repeat(smid, count));
        mid -= count;
        sb.append(generate(lg, mid, sm, slg, smid, ssm));
        return sb.toString();
    }
    private String repeat(char ch, int count) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < count;i++) {
            sb.append(ch);
        }
        return sb.toString();
    }

    private void testCountAndSay() {
        String val = countAndSay(4);
        System.out.printf("val: %s\n", val);
    }

    public String countAndSay(int n) {
        String curr = "1";
        while(n-- > 1) {
            List<String[]>  counter = countInteger(curr);
            curr = mapToStr(counter);
        }
        return curr;
    }
    private String mapToStr(List<String[]> counter) {
        StringBuilder sb = new StringBuilder();
        for(String[] arr: counter) {
            sb.append(arr[1]);
            sb.append(arr[0]);
        }
        return sb.toString();
    }
    private List<String[]> countInteger(String s) {
        List<String[]> res = new ArrayList<>();
        char prev = s.charAt(0);
        Integer count = 1;
        for(int i = 1; i < s.length(); i++) {
            char curr = s.charAt(i);
            if(prev == curr) {
                count += 1;
            } else {
                res.add(new String[]{String.valueOf(prev), String.valueOf(count+"")});
                prev = curr;
                count = 1;
            }
        }
        res.add(new String[]{String.valueOf(prev), count.toString()});
        return res;
    }

    private void testCountStable() {
        List<String> res = new ArrayList<>();
        int zero = 3, one = 3, limit = 2;
        int count = numberOfStableArrays(zero, one, limit, res);
        System.out.printf("Stable count: %d\n", count);
        res.stream().forEach(System.out::println);
    }

    int MOD = 1_000_000_007;
    public int numberOfStableArrays(int zero, int one, int limit, List<String> res) {
        StringBuilder sbz = new StringBuilder(), sbo = new StringBuilder();
        sbz.append(0);
        sbo.append(1);
        return(numberOfStableArrays(zero-1, one, 0, 1, limit, sbz, res) + numberOfStableArrays(zero, one-1, 1, 1, limit, sbo, res))%MOD;
    }
    private int numberOfStableArrays(int remZero, int remOne, int lastSymbol, int len, int limit, StringBuilder sb, List<String> res) {
        if(remZero == 0 && remOne == 0) {
            res.add(sb.toString());
            return 1;
        } else if(remZero == 0) {
            if(lastSymbol == 0) {
                if(remOne <= limit) {
                    sb.append(createRemaining('1', remOne));
                    res.add(sb.toString());
                    return 1;
                }
                return  0;
            } else {
                if(len + remOne <= limit) {
                    sb.append(createRemaining('1', remOne-len));
                    res.add(sb.toString());
                    return 1;
                }
                return 0;
            }
        } else if(remOne == 0) {
            if(lastSymbol == 1) {
                if(remZero <= limit) {
                    sb.append(createRemaining('0', remZero));
                    res.add(sb.toString());
                    return 1;
                }
                return  0;
            } else {
                if(len + remZero <= limit) {
                    sb.append(createRemaining('0', remZero-len));
                    res.add(sb.toString());
                    return 1;
                }
                return  0;
            }
        }
        if(len == limit) {
            if(lastSymbol == 0) {
                sb.append(1);
                return numberOfStableArrays(remZero, remOne-1, 1, 1, limit, sb, res);
            } else {
                sb.append(0);
                return numberOfStableArrays(remZero-1, remOne, 0, 1, limit, sb, res);
            }
        }
        int countUsingOne = 0, countUsingZero = 0;
        if(lastSymbol == 0) {
            StringBuilder clone = new StringBuilder(sb.toString());
            sb.append(1);
            countUsingOne = numberOfStableArrays(remZero, remOne-1, 1, 1, limit, sb, res);
            clone.append(0);
            countUsingZero = numberOfStableArrays(remZero-1, remOne, 0, len+1, limit, clone, res);
        } else {
            StringBuilder clone = new StringBuilder(sb.toString());
            sb.append(1);
            countUsingOne = numberOfStableArrays(remZero, remOne-1, 1, len+1, limit, sb,  res);
            clone.append(0);
            countUsingZero = numberOfStableArrays(remZero-1, remOne, 0, 1, limit, clone,  res);
        }
        return (countUsingOne + countUsingZero)%MOD;
    }

    private char[] createRemaining(char sym, int len) {
        char[] arr = new char[len];
        Arrays.fill(arr, sym);
        return arr;
    }

    private void testCheckInclusion() {
        String s1= "ab", s2 = "eidbaooo";
        boolean included = checkInclusion(s1, s2);
        System.out.printf("Included: %b\n", included);
    }

    public boolean checkInclusion(String s1, String s2) {
        int m = s1.length(), n = s2.length();
        if(m > n) {
            return false;
        }
        int[] counter1 = new int[26], counter2 = new int[26];
        for(int i = 0; i < m; i++) {
            counter1[s1.charAt(i) - 'a'] += 1;
            counter2[s2.charAt(i) - 'a'] += 1;
        }
        int matchingCount = 0;
        for(int i = 0; i < 26; i++) {
            if(counter1[i] == counter2[i]) {
                matchingCount += 1;
            }
        }
        if(matchingCount == 26) {
            return true;
        }
        //Fixed size window of length m
        for(int i = m; i < n; i++) {
            if(matchingCount == 26) {
                return true;
            }
            int r = s2.charAt(i) - 'a', l = s2.charAt(i-m) -'a';
            counter2[r] += 1;
            if(counter1[r] == counter2[r]) {
                matchingCount += 1;
            } else if(counter2[r] == counter1[r] + 1){
                matchingCount -= 1;
            }
            counter2[l] -= 1;
            if(counter1[l] == counter2[l]) {
                matchingCount += 1;
            } else if(counter2[l] == counter1[l] -1) {
                matchingCount -= 1;
            }
        }
        return matchingCount == 26;
    }

    public boolean isValid(String s) {
        Map<Character, Character> opposite = Map.of(
                ')', '(',
                '}', '{',
                ']', '['
        );
        Map<Character, Integer> countOpen = new HashMap<>();
        for(int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch == '(') {
                countOpen.merge('(', 1, Integer::sum);
            } else if (ch == ')') {
                if (countOpen.getOrDefault('(', 0) > 0) {
                    countOpen.merge('(', -1, Integer::sum);
                } else {
                    return false;
                }
            } else if (ch == '{') {
                countOpen.merge('{', 1, Integer::sum);
            } else if (ch == '}') {
                if (countOpen.getOrDefault('{', 0) > 0) {
                    countOpen.merge('{', -1, Integer::sum);
                } else {
                    return false;
                }
            } else if (ch == '[') {
                countOpen.merge('[', 1, Integer::sum);
            } else if (ch == ']') {
                if (countOpen.getOrDefault('[', 0) > 0) {
                    countOpen.merge('[', -1, Integer::sum);
                } else {
                    return false;
                }
            }
        }
        return countOpen.values().stream().reduce(Integer::sum).get() == 0;
    }
    public int evalRPN(String[] tokens) {
        Map<Character, BiFunction<Integer, Integer, Integer>> opMap = Map.of(
                '+', Integer::sum,
                '-', (a, b) -> a-b,
                '*', (a, b) -> a*b,
                '/', (a,b) -> a/b
        );
        Deque<Integer> stack = new ArrayDeque<>();
        for(String token: tokens) {
            char ch = token.charAt(0);
            if(opMap.containsKey(ch)) {
                int op2 = stack.pop(), op1 = stack.pop();
                int res = opMap.get(ch).apply(op1, op2);
                stack.push(res);
            } else {
                stack.push(Integer.parseInt(token));
            }
        }
        return stack.pop();
    }

    private void testFindAllConcat() {
        String[] words = {"cat","cats","catsdogcats","dog","dogcatsdog","hippopotamuses","rat","ratcatdogcat"};
        List<String> res = findAllConcatenatedWordsInADictH(words);
        res.stream().forEach(System.out::println);
    }

    public List<String> findAllConcatenatedWordsInADictH(String[] words) {
        //Arrays.sort(words, (w1, w2) -> w1.length() - w2.length());
        Set<String> dict = Arrays.stream(words).collect(Collectors.toSet());
        List<String> res = new ArrayList<>();
        for(String word: words) {
            if(isConcatenation(word, dict)) {
                res.add(word);
            }
        }
        return res;
    }
    private boolean isConcatenation(String word, Set<String> dict) {
        int len = word.length();
        boolean[] DP = new boolean[len+1];
        DP[0] = true;
        for(int i = 1; i <= len; i++) {
            int j = (i == len) ? 1 : 0;//Avoid using whole word to check if its existence in disctionary
            for(;j < i; j++) {
                DP[i] = dict.contains(word.substring(j, i)) && DP[j];
                if(DP[i]) {
                    break;
                }
            }
        }
        return DP[len];
    }
    private void test3Sum() {
        int[] arr = {-1,0,1,2,-1,-4};
        List<List<Integer>> res = threeSum(arr);
        for(List<Integer> list: res) {
            System.out.printf("[%d, %d, %d]\n", list.get(0), list.get(1), list.get(2));
        }
    }

    public List<List<Integer>> threeSum(int[] nums) {
        int n = nums.length;
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();
        for(int i = 0; i < n-2; i++) {
            if(i > 0 && nums[i] == nums[i-1]) {
                continue;
            }
            int l = i + 1, r = n-1;
            while(l < r) {
                int sum = nums[i] + nums[l] + nums[r];
                if(sum == 0) {
                    res.add(Arrays.asList(nums[i], nums[l], nums[r]));
                    l += 1;
                    r -= 1;
                } else if(sum < 0) {
                    l += 1;
                } else {
                    r -= 1;
                }
            }
        }
        return res;
    }
    private void solveContnated() {
        String[] words = {"cat","cats","catsdogcats","dog","dogcatsdog","hippopotamuses","rat","ratcatdogcat"};
        List<String> cw =findAllConcatenatedWordsInADict(words);
    }

    public List<String> findAllConcatenatedWordsInADict(String[] words) {
        Arrays.sort(words, (w1, w2) -> w1.length() - w2.length());
        List<String> res = new ArrayList<>();
        for(int wi = words.length-1; wi > 1; wi--) {
            if(isConcatenation(words[wi], wi, words)) {
                res.add(words[wi]);
            }
        }
        return res;
    }

    private boolean isConcatenation(String word, int wi, String[] words) {
        int l = word.length();
        boolean[] DP = new boolean[l+1];
        DP[0] = true;
        for(int i = 1;i <= l; i++) {
            for(int j = 0; j < wi; j++) {
                String str = words[j];
                if(i < str.length()) {
                    continue;
                }
                String suffix = word.substring(i-str.length(), i);
                if(suffix.equals(str) && DP[i-str.length()]) {
                    DP[i] = true;
                }
            }
        }
        return DP[l];
    }

    public String encode(List<String> strs) {
        StringBuilder sb = new StringBuilder();
        for(String str: strs) {
            sb.append(str.replaceAll("$", "$$")).append("$:");
        }
        return sb.toString();
    }

}
