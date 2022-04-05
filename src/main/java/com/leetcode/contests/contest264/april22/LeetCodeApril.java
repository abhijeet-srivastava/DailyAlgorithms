package com.leetcode.contests.contest264.april22;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LeetCodeApril {

    public static void main(String[] args) {
        LeetCodeApril lca = new LeetCodeApril();
        //lca.testKClosest();
        //lca.testEncoded();
        //lca.testCircularSum();
        lca.testReplacement();
    }

    private void testReplacement() {
        int max = characterReplacement("KRSCDCSONAJNHLBMDQGIFCPEKPOHQIHLTDIQGEKLRLCQNBOHNDQGHJPNDQPERNFSSSRDEQLFPCCCARFMDLHADJADAGNNSBNCJQOF", 4);
        System.out.printf("Max: %d\n",max);
    }

    private void testCircularSum() {
        int[] arr = {-3,-2,-3};
        int sum = maxSubarraySumCircular(arr);
        System.out.printf("Sum: %d\n", sum);
    }

    public int characterReplacement(String s, int k) {
        int l = 0, r= 0;
        int max = 0;
        Map<Character, Integer> counter = new HashMap<>();
        while(r < s.length()) {
            char ch = s.charAt(r);
            int count = counter.getOrDefault(ch, 0) + 1;
            counter.put(ch, count);
            while(!isValid(counter, k)) {
                count = counter.get(s.charAt(l));
                count -= 1;
                if(count == 0) {
                    counter.remove(s.charAt(l));
                } else {
                    counter.put(s.charAt(l), count);
                }
                l += 1;
            }
            max = Math.max(max, r-l+1);
            r += 1;
        }
        return max;
    }

    private boolean isValid(Map<Character, Integer> counter, int k) {
        if(counter.size() > 2) {
            return false;
        } else if(counter.size() < 2) {
            return true;
        }
        int min = Integer.MAX_VALUE;
        for(int value: counter.values()) {
            min = Math.min(min, value);
        }
        return min <= k;
    }
    private void testEncoded() {
        String s = "aabcaabcd";
        //boolean repeated = isRepeatedSegmentOf(s, 4);
        String encoded = encode(s);
        System.out.printf("Encoded : %s\n", encoded);

    }

    public String encode(String s) {
        int len = s.length();
        if(len <= 4) {
            return s;
        }
        String[][] DP = new String[len][len];
        StringBuilder sb = new StringBuilder();
        for(int l = 0; l < len; l++) {
            for(int i = 0; i < len-l; i++) {
                int j = i+l;
                String substr = s.substring(i, j+1);
                DP[i][j] = substr;
                if(l <= 4) {
                    continue;
                }
                for(int k = i ; k < j; k++) {
                    if(DP[i][k].length() + DP[k+1][j].length() < DP[i][j].length()) {
                        sb.append(DP[i][k]).append(DP[k+1][j]);
                        DP[i][j] = sb.toString();
                        sb.setLength(0);
                    }
                }
                for(int k=0;k <= l;k++) {
                    if((l+1)%(k+1) == 0
                            && isRepeatedSegmentOf(substr, k+1)) {
                        String ss = (l+1)/(k+1) + "[" + DP[i][i+k] + "]";
                        if(ss.length() < DP[i][j].length()) {
                            DP[i][j] = ss;
                        }
                    }
                }
            }
        }
        return DP[0][len-1];
    }
    private boolean isRepeatedSegmentOf(String s, int k) {
        String subStr = s.substring(0, k);
        boolean isSeg = true;
        s = s.substring(k);
        while(s.length() >= k) {
            if(!s.startsWith(subStr)) {
                isSeg = false;
                break;
            }
            s = s.substring(k);
        }
        return isSeg;
    }
    private void testKClosest() {
        /**
         * [0,1,2,2,2,3,6,8,8,9]
         * 5
         * 9
         */
        int[] arr = {0,1,2,2,2,3,6,8,8,9};
        int k = 5, x = 9;
        List<Integer> closest = findClosestElements(arr, k, x);
        System.out.printf("List: [%s]\n", closest.stream().map(String::valueOf).collect(Collectors.joining(", ")));
    }

    public List<Integer> findClosestElements(int[] arr, int k, int x) {
        List<Integer> res = new ArrayList<>();
        int len = arr.length;
        if(x <= arr[0]) {
            for(int i = 0; i < Math.min(k, len); i++) {
                res.add(arr[i]);
            }
            return res;
        }
        if(x >= arr[len-1]) {
            for(int i = len-k; i < len; i++) {
                res.add(arr[i]);
            }
            return res;
        }
        int left = getFloor(x, arr);
        int right = left +1;
        while(k-- > 0) {
            int leftDiff = left >= 0 ? x - arr[left] : Integer.MAX_VALUE;
            int rightDiff = right < len ? arr[right] - x : Integer.MAX_VALUE;
            if(leftDiff <= rightDiff) {
                res.add(0, arr[left]);
                left -= 1;
            } else {
                res.add(arr[right]);
                right += 1;
            }
        }
        return res;
    }

    private int getFloor(int x, int[] nums) {
        int lo = 0, hi = nums.length-1;
        int floor = -1;
        while(lo <= hi) {
            int mid = lo +(hi-lo)/2;
            if(nums[mid] == x) {
                floor = mid;
                break;
            } else if(nums[mid] < x) {
                floor = mid;
                lo = mid+1;
            } else {
                hi = mid-1;
            }
        }
        return floor;
    }
    public List<Integer> findClosestElements1(int[] arr, int k, int x) {
        List<Integer> res = new ArrayList<>();
        int len = arr.length;
        if(x <= arr[0] || x >= arr[len-1]) {
            for(int i = 0; i < Math.min(arr.length, k); i++) {
                int next = (x <= arr[0]) ? arr[i] : arr[len-i-1];
                res.add(next);
            }
            return res;
        }
        return  IntStream.of(arr)
                .boxed()
                .sorted(
                        Comparator.comparingInt((Integer a) -> Math.abs(a-x))
                                .thenComparing(Comparator.naturalOrder())
                )
                .limit(k)
                .collect(Collectors.toList());
    }

    public int balancedString(String s) {
        Map<Character, Integer> charIndex = new HashMap<>();
        charIndex.put('E', 0);
        charIndex.put('Q', 1);
        charIndex.put('R', 2);
        charIndex.put('W', 3);
        int[] count = new int[4];
        for(char ch : s.toCharArray()) {
            count[charIndex.get(ch)] += 1;
        }
        int len = s.length();
        int l = 0, r = 0, res = len, k = len/4;
        while(r < len) {
            count[charIndex.get(s.charAt(r))] -= 1;
            while(l < len
                    && count[charIndex.get('E')] <= k
                    && count[charIndex.get('R')] <= k
                    && count[charIndex.get('Q')] <= k
                    && count[charIndex.get('W')] <= k
            ) {
                count[charIndex.get(s.charAt(l))] += 1;
                res = Math.min(res, r-l+1);
                l += 1;
            }
            r += 1;
        }
        return res;
    }
    public int maxSubarraySumCircular(int[] nums) {
        int len = nums.length;
        int[] PS = new int[2*len+1];
        for(int i = 0; i < 2*len; i++) {
            PS[i+1] = PS[i] + nums[i%len];
        }
        int res = Integer.MIN_VALUE;
        Deque<Integer> dq = new ArrayDeque<>();
        dq.offer(0);
        for(int i = 1; i <= 2*len; i++) {
            while(!dq.isEmpty() && i-dq.peekFirst() > len) {
                dq.pollFirst();
            }
            if(!dq.isEmpty()) {
                res = Math.max(res, PS[i] - PS[dq.peekFirst()]);
            }
            while(!dq.isEmpty() && PS[dq.peekLast()] >= PS[i]) {
                dq.pollLast();
            }
            dq.offerLast(i);
        }
        return res;
    }
    public int constrainedSubsetSum(int[] nums, int k) {
        //Monotonic decreasing queue, Left most element is highest
        Deque<Integer> deque = new ArrayDeque<>();

        // sum[i] means local max sum till index ii
        int[] sum =  new int[nums.length];
        int res = nums[0];

        for(int i = 0; i < nums.length; i++) {
            sum[i] = nums[i];
            if(!deque.isEmpty()) {
                //Add highest(i.e. Leftmost element to sum)
                sum[i] += sum[deque.peekFirst()];
            }
            res = Math.max(res, sum[i]);
            // Widow Size = i -dq.peekFisrt() + 1, should be lesst then equal to k
            if(!deque.isEmpty() && (i - deque.peekFirst() +1) > k){
                deque.pollFirst();
            }
            //Maintain Monotonic Queue, remove elements from last which have sum greater then sum[i], before inserting sum[i]
            while(!deque.isEmpty() && sum[deque.peekLast()] <= sum[i]) {
                deque.pollLast();
            }
            // Add sum[i] if posetive
            if(sum[i] > 0) {
                deque.offer(i);
            }
        }
        return res;
    }
}
