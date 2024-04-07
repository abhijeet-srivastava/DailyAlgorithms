package com.intuit;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;

public class IntuitMain {
    public static void main(String[] args) {
        IntuitMain im = new IntuitMain();
        //im.testMinStr();
        //im.testMedian();
        //im.testMinInWindow();
        //im.testFindDuplicate();
        //im.testMaxLen();
        //im.testContinuousBlocks();
        im.testShiftingLetter();
    }

    private void testShiftingLetter() {
        String s = "xuwdbdqik";
        int[][] shifts = {{4,8,0},{4,4,0},{2,4,0},{2,4,0},{6,7,1},{2,2,1},{0,2,1},{8,8,0},{1,3,1}};
        String shifted = shiftingLetters(s, shifts);
        System.out.printf("Shifted: %s\n", shifted);
    }
    public String shiftingLetters(String s, int[][] shifts) {
        int n = shifts.length;
        int strLen = s.length();
        int[] timeLine = new int[strLen+1];
        for(int i = 0; i < n; i++) {
            if(shifts[i][2] == 0) {
                timeLine[shifts[i][0]] -= 1;
                timeLine[shifts[i][1]+1] += 1;
            } else {
                timeLine[shifts[i][0]] += 1;
                timeLine[shifts[i][1]+1] -= 1;
            }
        }
        for(int i = 1; i <= strLen; i++) {
            timeLine[i] += timeLine[i-1];
        }
        char[] arr = s.toCharArray();
        for(int i = 0; i < s.length(); i++) {
            int newIndex = (arr[i] - 'a' + timeLine[i])%26;

            arr[i] = (char)(newIndex + 'a');
        }
        return new String(arr);
    }

    private void testContinuousBlocks() {
        int val = minimumRecolors("WWBBBWBBBBBWWBWWWB", 16);
        System.out.printf("val: %d\n", val);
    }

    private void testMaxLen() {
        int len = maximumLengthSubstring("bcbbbcba");
        System.out.printf("len: %d\n", len);
    }

    public long[] mostFrequentIDs(int[] nums, int[] freq) {
        int n = nums.length;
        long[] res = new long[n];
        PriorityQueue<FreqVal> pq = new PriorityQueue<>((a, b) -> Long.valueOf(b.freq).compareTo(a.freq));
        Map<Integer, Long> numFreq = new HashMap<>();
        for(int i = 0; i < n; i++) {
            numFreq.merge(nums[i], (long) freq[i], Long::sum);
            pq.offer(new FreqVal(numFreq.get(nums[i]), nums[i]));
            FreqVal tos = pq.peek();
            while(numFreq.get(tos.key) != tos.freq) {
                pq.remove();
                if(numFreq.getOrDefault(tos.key, 0l) > 0) {
                    pq.offer(new FreqVal(numFreq.get(tos.key), tos.key));
                }
                tos = pq.peek();
            }
            res[i] = pq.peek().freq;
        }
        return res;
    }
    private record FreqVal(long freq, int key) { }

    public int minimumRecolors(String blocks, int k) {
        int min = k;
        for(int i = 0, w = 0 ; i < blocks.length(); i++) {
            if(blocks.charAt(i) == 'W') {
                w += 1;
            }
            if(i >= k) {
                if(blocks.charAt(i-k) == 'W') {
                    w -= 1;
                }
            }
            if(i >= k-1) {
                min = Math.min(min, w);
            }
        }
        return min;
    }
    public int maximumLengthSubstring(String s) {
        int[] freq = new int[26];
        int maxLen = 0;
        for(int l = 0, r = 0; r < s.length(); r++) {
            char rChar = s.charAt(r);
            freq[rChar - 'a'] += 1;
            while(freq[rChar - 'a'] > 2) {
                char lChar = s.charAt(l);
                freq[lChar-'a'] -= 1;
                l += 1;
            }
            maxLen = Math.max(maxLen, r-l+1);
            System.out.printf("maxLen: %d, l : %d, r: %d\n", maxLen, l, r);
        }
        return maxLen;
    }

    private void testFindDuplicate() {
        int[] arr = {3,1,3,4,2};
        // (0, 2) -> 3 , 1 -> 1, 3 -> 4, 4 -> 2

        int res = findDuplicate(arr);
        System.out.printf("res: %d\n", res);
    }
    public int findDuplicate(int[] nums) {
        //0 -> 2 -> 3 -> 4 -> 2 -> 3
        int s1 = 0, f1 = nums[0];
        while(s1 != f1) {
            s1 = nums[s1];
            f1 = nums[nums[f1]];
        }
        int slow = nums[0], fast = nums[0];
        do {
            slow = nums[slow];
            fast = nums[nums[fast]];
        } while(fast != slow);
        slow = 0;
        while(slow != fast) {
            slow = nums[slow];
            fast = nums[fast];
        }
        return slow;
    }

    private void testMinInWindow() {
        //List<Integer> list = Arrays.asList(176641,818878,590130,846132,359913,699520,974627,806346,343832,619769,760242,693331,832192,775549,353117,23950,496548,183204,971799,393071,727476,351337,811496,24595,417701,664960,745806,538176,230403,942316,21481,605695,598531,651683,558460,583357,530911,721611,308228,724620,429167,909353,330152,116815,986067,713467,906132,428600,927889,567272,647109,992614,747948,192884,879696,262543,782487,829272,470060,427956,751730,597177,870616,754791,421830,11676,425656,841955,693419,462693,245403,192649,750201,180732,17450,44723,527618,174579,515786,444844,210843,563425,809540,752036,608529,748313,667439,255643,387412,320353,704213,755272,267902,657989,651762,325654,582887,382501,715426,897450);
        List<Integer> list = Arrays.asList(33, 11, 44, 11, 55);
        int minInMxWin = solve(list, 5);
        System.out.printf("%d\n", minInMxWin);
        /*for(int q: queries) {
            int mxInMinWindow = solve(list, q);
            System.out.printf("%d, %d\n", mxInMinWindow, q);
        }*/

    }


    //[33,11], [11,44][44,11][11,55]
    //[33,44,44,55] = 33
    private void testMedian() {
        int[] arr1 = {1, 3};
        int[] arr2 = {4, 6};
        double median = median(arr1, arr2);
        System.out.printf("Median : %f\n", median);
    }

    private void testMinStr() {
        String str1 = "AEMGBCODEBANC";
        String str2 = "ABCO";
        String min = minLengthMatching(str1, str2);
        System.out.printf("Min Sub str: %s\n", min);
    }

    public String minLengthMatching(String str1, String str2) {
        if(str1 == null || str2 == null) {
            return "";
        }
        int len1 = str1.length();
        int len2 = str2.length();
        if(len1 < len2) {
            return "";
        }
        //Str1 = "abbbcc" str2="abd"
        //Brute Force approach => For all Substring of str1, greater then lenght len2, if it has all the characters of str2
        //Characters: lowercase and Upper case
        Map<Character, Integer> counter2 = new HashMap<>();
        for(char ch: str2.toCharArray()) {
            counter2.merge(ch, 1, Integer::sum);
        }
        //int[] counter = new int[256];
        int minLen = len1+1;
        String minSubStr = "";
        Map<Character, Integer> counter1 = new HashMap<>();
        for(int l = 0, r = 0; r < len1; r++) {
            counter1.merge(str1.charAt(r), 1, Integer::sum);
            while(areEqual(counter1, counter2)) {
                int currLen = r-l+1;
                if(currLen < minLen) {
                    minLen = currLen;
                    minSubStr = str1.substring(l, r+1);
                }
                counter1.merge(str1.charAt(l), -1, Integer::sum);
                if(counter1.get(str1.charAt(l)) == 0) {
                    counter1.remove(str1.charAt(l));
                }
                l += 1;
            }
        }
        return minLen > len1 ? "" : minSubStr;
    }
    private boolean areEqual(Map<Character, Integer> c1, Map<Character, Integer> c2) {
        if(c1.size() < c2.size()) {
            return false;
        }
        for(Map.Entry<Character, Integer> entry: c2.entrySet()) {
            if(!c1.containsKey(entry.getKey()) || c1.get(entry.getKey()) < entry.getValue()) {
                return false;
            }
        }
        return true;
    }
    public static List<Integer> solve(List<Integer> arr, List<Integer> queries) {
        List<Integer> res = new ArrayList<>();
        int[] digit = {arr.get(0), arr.get(0)};
        for(int n: arr) {
            digit[0] = Math.min(digit[0], n);
            digit[1] = Math.max(digit[1], n);
        }
        for(int q :  queries) {
            if(q == 1){
                res.add(digit[0]);
            } else if (q == arr.size()) {
                res.add(digit[1]);
            } else {
                res.add(solve(arr, q));
            }
        }
        return res;
    }

    private static int[] getMaxInWindow(List<Integer> arr, int q) {
        Deque<Integer> queue = new ArrayDeque<>();
        int[] res = new int[arr.size()-q+1];
        long min = Long.MAX_VALUE;
        for(int i = 0; i < arr.size(); i++) {
            while (!queue.isEmpty() && i-queue.peekLast()+1 > q) {
                queue.removeLast();
            }
            while (!queue.isEmpty() && arr.get(queue.peekFirst()) < arr.get(i)) {
                queue.removeFirst();
            }
            queue.offerFirst(i);
            if(i-q+1 >= 0)
                //min = Math.min(min, arr.get(queue.peekFirst()));
                res[i-q+1] = arr.get(queue.peekLast());
        }
        return res;
    }
    private static int solve(List<Integer> arr, int q) {
        Deque<Integer> queue = new ArrayDeque<>();
        long min = Long.MAX_VALUE;
        for(int i = 0; i < arr.size(); i++) {
            while (!queue.isEmpty() && i-queue.peekLast()+1 > q) {
                queue.removeLast();
            }
            while (!queue.isEmpty() && arr.get(queue.peekFirst()) < arr.get(i)) {
                queue.removeFirst();
            }
            queue.offerFirst(i);
            if(i-q+1 >= 0)
                min = Math.min(min, arr.get(queue.peekLast()));
        }
        return (int)min;
    }

    public double median(int[] arr1, int[] arr2) {
        int l1 = arr1.length, l2 = arr2.length;
        int total = l1+l2;
        int k = total/2;
        if(total%2 == 0) {
            return (median(0, l1-1, arr1, 0, l2-1, arr2, k-1)
                    + median(0, l1-1, arr1, 0, l2-1, arr2, k))/2.0d;
        } else {
            return median(0, l1-1, arr1, 0, l2-1, arr2, k);
        }
    }

    private double median(int l1, int r1, int[] arr1, int l2, int r2, int[] arr2, int k) {
        if(l1 > r1) {
            return 1.0d * arr2[k-l1];
        } else if(l2 > r2) {
            return 1.0d * arr1[k-l2];
        }
        int m1 = l1 + (r1-l1)/2;
        int m2 = l2 + (r2-l2)/2;
        if(m1 + m2 < k) {
            if(arr1[m1] < arr2[m2]) {
                return median(m1+1, r1, arr1, l2, r2, arr2, k);
            } else {
                return median(l1, r1, arr1, m2+1, r2, arr2, k);
            }
        } else {
            if(arr1[m1] < arr2[m2]) {
                return median(l1, r1, arr1, l2, m2-1, arr2, k);
            } else {
                return median(l1, m1-1, arr1, m2+1, r2, arr2, k);
            }
        }
    }
}
