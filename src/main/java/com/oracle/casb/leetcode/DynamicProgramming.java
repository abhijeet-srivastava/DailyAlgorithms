package com.oracle.casb.leetcode;

import com.google.common.collect.ImmutableMap;
import com.oracle.casb.common.ListNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created By : abhijsri
 * Date  : 19/09/18
 **/
public class DynamicProgramming {
    private static Set<Integer> primes;
    private static Integer PRIME_COLLECTION_SIZE = 300;

    public DynamicProgramming() {
        primes = createFirstNPrimeNumbers(PRIME_COLLECTION_SIZE);

    }

    private Set<Integer> createFirstNPrimeNumbers(Integer primeCollectionSize) {
        Set<Integer> primeNum = new HashSet<>();
        BitSet b = new BitSet();
        b.set(2, primeCollectionSize+1);
        for (int p = 2; p*p < primeCollectionSize; p++) {
            if (b.get(p)) {
                for (int i = p*2; i <= primeCollectionSize; i += p ) {
                    b.clear(i);
                }
            }
        }
        for (int i = 2; i <= primeCollectionSize; i++ ) {
            if (b.get(i)) {
                primeNum.add(i);
            }
        }
        return primeNum;
    }

    public static void main(String[] args) {
        DynamicProgramming dp = new DynamicProgramming();
        //dp.testMaxProfit();
        dp.testLongestValidParanthesis();
        //dp.testMinWindow();
        //dp.testMinLexicoGraphic();
        //dp.testMaxAreainHistogram();
        //dp.testPrimeNumbers();
    }

    private void testPrimeNumbers() {
        int[] arr = {2,8,5,7,9,5,7};
        getMaxSubArray(arr);
    }

    private void testMinLexicoGraphic() {
        String[] words = {"jibw", "ji", "jp", "bw", "jibw"};
        System.out.println("Lexicographic smallest String -" +smallestString(words));
    }

    private void testMinWindow() {
        String res = minWindowContaingTarget("ADOBECODEBANC", "ABC");
        String res1 = minWindow("ADOBECODEBANC", "ABC");
        System.out.println("Min window is- "+ res);
        System.out.println("Min window  2 is- "+ res1);
    }

    private String minWindow(String source, String target) {
        Map<Character, Integer> hasGot = new HashMap<>();
        Map<Character, Integer> needToGet = new HashMap<>();
        for (char ch : target.toCharArray()) {
            int count = needToGet.containsKey(ch) ? needToGet.get(ch) : 0;
            needToGet.put(ch, count+1);
        }
        int count = 0;
        int minWinStart = 0;
        int minWinEnd = 0;
        int minWinSize = Integer.MAX_VALUE;
        for (int begin = 0,  end = 0; end < source.length(); end++) {
            char ch = source.charAt(end);
            if (!needToGet.containsKey(ch)) {
                continue;
            }
            int gotCount = (hasGot.containsKey(ch) ? hasGot.get(ch) : 0) + 1;
            hasGot.put(ch, gotCount);
            if (gotCount <= needToGet.get(ch)) {
                count += 1;
            }
            if (count >= target.length()) {
                char beginChar = source.charAt(begin);
                while (!needToGet.containsKey(beginChar)
                        || hasGot.get(beginChar) > needToGet.get(beginChar)) {

                    if (needToGet.containsKey(beginChar) &&
                            hasGot.get(beginChar) > needToGet.get(beginChar)) {
                        hasGot.put(beginChar, hasGot.get(beginChar) - 1);
                    }
                    begin += 1;
                    beginChar = source.charAt(begin);
                }
                int currLen = end -begin + 1;
                if (minWinSize > currLen) {
                    minWinSize= currLen;
                    minWinStart = begin;
                    minWinEnd = end;
                }

            }

        }
        return source.substring(minWinStart, minWinEnd+1);
    }

    private void testMaxProfit() {
        double[] prices = {19.35, 19.30, 18.88, 18.93, 18.95, 19.03, 19.00, 18.97, 18.97,18.98};
        //double[] prices = {9.20,8.03,10.02,8.08,8.14,8.10,8.31,8.28,8.35,8.34,8.39,8.45,8.38,8.38,8.32,8.36,8.28,8.28,8.38,8.48,8.49,8.54,8.73,8.72,8.76,8.74,8.87,8.82,8.81,8.82,8.85,8.85,8.86,8.63,8.70,8.68,8.72,8.77,8.69,8.65,8.70,8.98,8.98,8.87,8.71,9.17,9.34,9.28,8.98,9.02,9.16,9.15,9.07,9.14,9.13,9.10,9.16,9.06,9.10,9.15,9.11,8.72,8.86,8.83,8.70,8.69,8.73,8.73,8.67,8.70,8.69,8.81,8.82,8.83,8.91,8.80,8.97,8.86,8.81,8.87,8.82,8.78,8.82,8.77,8.54,8.32,8.33,8.32,8.51,8.53,8.52,8.41,8.55,8.31,8.38,8.34,8.34,8.19,8.17,8.16};
        double[] res = maxStockProfit(prices);
        System.out.printf("Buy at %f, sell at %f\n", res[0],res[1]);
    }

    private void testLongestValidParanthesis() {
        //int len = longestValidParentheses1(")()())");
        String str = "((())()";
        int max1 = maxValidParanLen(str);
        System.out.println("Max len is  - " + max1);
        int len1 = longestValidParenthesesDP(str);
        System.out.println("Max len is  - " + len1);
        int len = longestParanthesesValid(str);
        System.out.println("Max len is  - " + len);
        int max = maxValidLenParntheses(str);
        System.out.println("Max len is  - " + max);
    }


    private int longestParanthesesValid(String str) {
        int[] dp = new int[str.length()];
        int maxLen = 0;
        for (int i = 1; i < str.length(); i++) {
            if (')' == str.charAt(i)
                    && '(' == str.charAt(i -1)) {
                dp[i] = 2;
                if (i > 1) {
                    dp[i] += dp[i-2];
                }
            } else if (')' == str.charAt(i)
                    && ')' == str.charAt(i-1)) {
                int index = (i -1) - dp[i-1];
                if (index >= 0
                        && '(' == str.charAt(index)) {
                    dp[i] = dp[i-1] + 2;
                    if (index > 0) {
                        dp[i] += dp[index-1];
                    }
                }
            }
            maxLen = Math.max(maxLen, dp[i]);
        }
        return maxLen;
    }

    private int longestValidParenthesesDP (String s) {
        int len = 0;
        int[] dp = new int[s.length()];

        for (int i = 1; i< s.length(); i++) {
            if (s.charAt(i) == ')') {
                if (s.charAt(i-1) == '(') {
                    dp[i] = 2;
                    if (i > 2 ) {
                        dp[i] += dp[i-2];
                    }
                } else {
                    int index = i - dp[i-1] -1;
                    if (index >= 0 && s.charAt(index) == '(') {
                        dp[i] = dp[i-1] + 2;
                        if (index > 0) {
                            dp[i] += dp[index - 1];
                        }
                    }
                }
            }
            len = Math.max(len, dp[i]);
        }
        return len;
    }

    private int longestValidParentheses1(String s) {
        int n = s.length();
        int len = 0;
        if (n == 0 || n == 1)
            return 0;

        //use this stack to store the index of '('
        Stack<Integer> stack = new Stack<Integer>();
        for (int i = 0; i < n; i++) {
            if(s.charAt(i) == '(') {
                stack.push(i);
            } else {
                if (!stack.isEmpty() && s.charAt(stack.peek()) == '(') {
                    stack.pop();
                    int temp = stack.isEmpty() ? -1 : stack.peek();
                    len = Math.max(len, i - temp);
                } else {
                    stack.push(i);
                }
            }
        }
        return len;
    }
    private int longestValidParentheses(String s) {
        //use last to store the last matched index
        int len = s.length(), maxLen = 0, last = -1;
        if (len == 0 || len == 1)
            return 0;

        //use this stack to store the index of '('
        Stack<Integer> stack = new Stack<Integer>();
        for (int i = 0; i < len; i++) {
            if (s.charAt(i) == '(')
                stack.push(i);
            else {
                //if stack is empty, it means that we already found a complete valid combo
                //update the last index.
               /* last = stack.isEmpty() ? i : stack.pop();
                maxLen = Math.max(maxLen, i - last + 1);*/
                if (stack.isEmpty()) {
                    last = i;
                } else {
                    stack.pop();
                    //found a complete valid combo and calculate max length
                    if (stack.isEmpty())
                        maxLen = Math.max(maxLen, i - last);
                    else
                        //calculate current max length
                        maxLen = Math.max(maxLen, i - stack.peek());
                }
            }
        }

        return maxLen;
    }
    private double[] maxStockProfit(double[] prices) {
        double maxProfit = Double.MIN_VALUE;
        double buyPrice = prices[0];
        double sellPrice = 0;
        int buyIndex = 0;
        for (int i = 0; i < prices.length; i++) {
            double price = prices[i];
            double currProfit = price - buyPrice;
            if (maxProfit < currProfit
                    && (i - buyIndex) > 1) {
                maxProfit = currProfit;
                sellPrice = price;
            }
            if (buyPrice > price) {
                buyPrice = price;
                buyIndex = i;
            }
        }
        System.out.printf("Max profit %f \n", maxProfit);
        return new double[]{buyPrice, sellPrice};
    }

    private String minWindowContaingTarget(String source, String target) {
        if (source== null || target == null || source.length() < target.length()) {
            return null;
        }
        int[] needToFound= new int[256];
        int[] hasFound = new int[256];
        for (char ch : target.toCharArray()) {
            needToFound[ch] += 1;
        }
        int maxStart = 0;
        int maxEnd = 0;
        int minWindowSize = Integer.MAX_VALUE;
        int count = 0;
        for(int begin = 0, end = 0; end < source.length(); end++) {
            if (needToFound[source.charAt(end)] == 0) {
                continue;
            }
            hasFound[source.charAt(end)] += 1;
            if(hasFound[source.charAt(end)] <= needToFound[source.charAt(end)]) {
                count += 1;
            }

            /**
             * Check if got max window
             */
            if (count >= target.length()) {
                while (needToFound[source.charAt(begin)] == 0
                        || hasFound[source.charAt(begin)] > needToFound[source.charAt(begin)]) {
                    if (hasFound[source.charAt(begin)] > needToFound[source.charAt(begin)]) {
                        hasFound[source.charAt(begin)] -= 1;
                    }
                    begin += 1;
                }
                int currWindowLen = end -begin + 1;
                if (currWindowLen < minWindowSize) {
                    minWindowSize = currWindowLen;
                    maxStart = begin;
                    maxEnd = end;
                }
            }


        }
        return source.substring(maxStart, maxEnd+1);

    }

    private String smallestString(String[] words) {
       // Arrays.sort(words, (s1,s2) -> (s1+s2).compareTo(s2+s1));
        return Arrays.stream(words)
                .sorted((s1, s2) -> (s1+s2).compareTo(s2+s1))
                .collect(Collectors.joining());
    }

    private int maxValidLenParntheses(String str) {
        int maxLen = 0;
        String valid = "";
        Stack<Integer>  stack = new Stack<>();
        stack.push(-1);
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '(') {
                stack.push(i);
            } else if (str.charAt(i) == ')') {
                stack.pop();
                if (stack.isEmpty()) {
                    stack.push(i);
                } else {
                    int currLen = i - stack.peek();
                    if (currLen > maxLen) {
                        maxLen = currLen;
                    }
                }
            }
        }
        return maxLen;
    }

    private boolean isValidPrantheses(String str) {
        Map<Character, Character> map = ImmutableMap.of('(', ')', '{', '}', '[', ']');
        char[] array = str.toCharArray();
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < array.length; i++) {
            char ch = array[i];
            if (ch == '(' || ch == '{' || ch == '[') {
                stack.push(map.get(ch));
            } else {
                if(stack.isEmpty() || stack.pop() != ch) {
                    return false;
                }
            }
        }
        return true;
    }

    private ListNode mergeKList(ListNode [] heads) {
        ListNode head = null;
        ListNode current = head;
        int smallest = -1;
        while ((smallest = getsmallest(heads)) != -1) {
            if(head == null) {
                head = heads[smallest];
                current = head;
            } else {
                current.setNext(heads[smallest]);
                current = current.getNext();
            }
            heads[smallest] = heads[smallest].getNext();
        }
        current.setNext(null);
        return head;
    }

    private int getsmallest(ListNode[] heads) {
        int smallest = -1;
        for (int i = 0; i < heads.length; i++) {
            if (heads[i] == null) {
                continue;
            }
            if (smallest == -1 || heads[smallest].getValue() > heads[i].getValue()) {
                smallest = i;
            }
        }
        return smallest;
    }

    private int maxValidParanLen(String str) {
        int maxLen = 0;
        int left = 0;
        int right = 0;
        for (char ch : str.toCharArray()) {
            if (ch == '(') {
                left += 1;
            } else {
                right += 1;
            }
            if (left == right) {
                int currLen = left + right;
                maxLen = Math.max(maxLen, currLen);
            } else if (right >=  left) {
                left = right = 0;
            }
        }
        left = right = 0;
        for (int i = str.length() - 1; i >= 0; i--) {
            char ch = str.charAt(i);
            if (ch == '(') {
                left += 1;
            } else {
                right += 1;
            }
            if (left == right) {
                int currLen = left + right;
                maxLen = Math.max(maxLen, currLen);
            } else if (left >= right) {
                left = right = 0;
            }
        }
        return maxLen;
    }

    private void testMaxAreainHistogram() {
        int[] histogram = {1,2,3,4,5,3,3,2,2 };
        //int[] histogram = {5,4,3,2,1,3,3,2,2 };
        int area = maxAraOfRectangle(histogram);
        System.out.println("Max Area " + area);
    }
    private int maxAraOfRectangle(int[] histogram) {
        int maxArea = 0;
        if (histogram == null || histogram.length == 0) {
            return maxArea;
        }
        Stack<Integer> stack = new Stack<>();

        int  i = 0;
        while (i < histogram.length) {
            if (stack.isEmpty() || histogram[stack.peek()] <= histogram[i]) {
                stack.push(i);
                i += 1;
                continue;
            }
            int height = histogram[stack.pop()];
            /*while (!stack.isEmpty()
                    && histogram[stack.peek()] >= height) {
                stack.pop();
            }*/
            int currArea = height * (i - 1 - (stack.isEmpty() ? 0 : stack.peek()));
            maxArea = Math.max(maxArea, currArea);
        }

        while (!stack.isEmpty()) {
            //int height = histogram[stack.peek()];
            int height = histogram[stack.pop()];
            /*while (!stack.isEmpty()
                    && histogram[stack.peek()] >= height) {
                stack.pop();
            }*/
            int currArea = height * (i -1  - (stack.isEmpty() ? 0 : stack.peek()));
            maxArea = Math.max(maxArea, currArea);
        }
        /*stack.push(i);
        i += 1;
        for (i = 1; i< histogram.length; i++) {
            if (histogram[i] >= histogram[stack.peek()]) {
                stack.push(i);
                continue;
            }
            while (histogram[stack.peek()] > histogram[i]) {
                int height = histogram[stack.peek()];
                while (height <= histogram[stack.peek()]) {
                    stack.pop();
                }
                int currArea = height * (i - 1 - stack.peek());
                maxArea = Math.max(maxArea, currArea);
            }
            if (histogram[i] >= histogram[stack.peek()]) {
                stack.push(i);
            }
        }
        while (!stack.isEmpty()) {
            int height = histogram[stack.peek()];
            while (!stack.isEmpty() && height <= histogram[stack.peek()]) {
                stack.pop();
            }
            int leftBoundry = stack.isEmpty() ? -1 : stack.peek();
            int currArea = height * (i - 1 - leftBoundry);
            maxArea = Math.max(maxArea, currArea);
        }*/
        return maxArea;
    }
    private boolean isPrime(int number) {
        if (primes.contains(number)) {
            return true;
        } else {
            return isPrimeBruteForce(number);
        }
    }

    private static boolean isPrimeBruteForce(int number) {
        for (int i = 2; i*i < number; i++) {
            if (number % i == 0) {
                return false;
            }
        }
        primes.add(number);
        return true;
    }

    private int getMaxSubArray (int[] array) {
        List<Integer[]> list = new ArrayList<>();
        int i = 0;
        while (i < array.length) {
            int start = i;
            while (!primes.contains(array[start])) {
                start += 1;
            }
            int end = start+1;
            while(end < array.length
                    && primes.contains(array[end])) {
                end += 1;
            }
            i = end;
            list.add(new Integer[]{start, end-1});
        }
        int maxLen = 0;
        for (i = 1; i < list.size(); i++) {
            if ((list.get(i)[0] - list.get(i-1)[1]) == 2) {
                int currLen = list.get(i)[1] - list.get(i-1)[0];
                maxLen = Math.max(maxLen, currLen);
            }
        }
        return maxLen;
    }
}
