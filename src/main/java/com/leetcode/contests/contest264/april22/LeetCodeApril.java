package com.leetcode.contests.contest264.april22;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LeetCodeApril {

    public static void main(String[] args) {
        LeetCodeApril lca = new LeetCodeApril();
        //lca.testKClosest();
        //lca.testEncoded();
        //lca.testCircularSum();
        //lca.testReplacement();
        //lca.testMaxEquation();
        //lca.testSlidingWinMax();
        lca.testTaskAssign();
        //lca.testKthLargest();
    }

    private void testKthLargest() {
        int[] arr = {4, 5, 8, 2,3,5,10,9,4};
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for(int num : arr) {
            pq.offer(num);
            if(pq.size() > 3) {
                pq.remove();
            }
            System.out.printf("3rd largest: %d\n", pq.peek());
        }
    }

    private void testTaskAssign() {
        /*int[] tasks = {5,9,8,5,9};
        int[] workers = {1,6,4,2,6};
        int pill = 1,  strength = 5;*/
        int[] tasks = {3,2,1};
        int[] workers = {0,3,3};
        int pill = 1,  strength = 1;
        int task = maxTaskAssign(tasks, workers, 1,5);
        System.out.printf("count: %d\n", task);
    }

    public int maxTaskAssign1(int[] tasks, int[] workers, int pills, int strength) {
        int left = 0, right = Math.min(tasks.length, workers.length);
        Arrays.sort(tasks);
        Arrays.sort(workers);
        int ans = left;
        while(left <= right) {
            int mid = left + (right - left)/2;
            if(canAssign1(mid, tasks, workers, pills, strength)) {
                ans = mid;
                left = mid+1;
            } else {
                right = mid-1;
            }
        }
        return ans;
    }

    public int maxTaskAssign(int[] tasks, int[] workers, int pills, int strength) {
        int left = 0, right = Math.min(tasks.length, workers.length);
        Arrays.sort(tasks);
        Arrays.sort(workers);
        /*TreeMap<Integer, Long> workerCount = Arrays.stream(workers)
                .boxed()
                .collect(Collectors.groupingBy(Function.identity(), TreeMap::new, Collectors.counting()));*/

        int ans = left;
        while(left <= right) {
            int mid = left + (right - left)/2;
            if(canAssign1(mid, tasks, workers, pills, strength)) {
                ans = mid;
                left = mid+1;
            } else {
                right = mid-1;
            }
        }
        return ans;
    }
    private boolean canAssign(int reqCount, int[] tasks, int[] workers, int pills, int strength) {
        int wi = workers.length-1;
        Deque<Integer> dq = new ArrayDeque<>();
        for(int ti = reqCount-1; ti >= 0; ti--) {
            while (wi >= workers.length - reqCount
                    && workers[wi] + strength >= tasks[ti]) {
                dq.offerLast(workers[wi]);
                wi -= 1;
            }
            if(dq.isEmpty()) {
                return false;
            } else if(dq.peekFirst() >= tasks[ti]) {
                dq.pollFirst();
            } else if(pills > 0) {
                dq.pollLast();
                pills -= 1;
            } else  {
                return false;
            }
        }
        return true;
    }

    public boolean canAssign1(int count, int[] tasks, int[] workers, int pills, int strength){
        Deque<Integer> dq = new ArrayDeque<>();
        int ind = workers.length - 1;
        for (int i = count - 1; i >= 0; i--) {
            while(ind >= workers.length-count
                    && workers[ind]+strength>=tasks[i]) {
                dq.offerLast(workers[ind]);
                ind--;
            }
            if(dq.isEmpty())
                return false;
            if(dq.peekFirst()>=tasks[i]) {
                dq.pollFirst();
            } else {
                dq.pollLast();
                pills--;
                if(pills<0)return false;
            }
        }
        return true;
    }

    private void testSlidingWinMax() {
        //int[] arr = {1,3,-1,-3,5,3,6,7};
        //PriorityQueue<Integer> pq = new PriorityQueue<>();
        /*PriorityQueue<Integer> possibleRewards =
                IntStream.of(tasks).boxed()
                        .collect(Collectors.toCollection(PriorityQueue::new)); */
        /*for(int task : tasks) {
            pq.offer(task);
        }*/

        int[] arr = {1,-1};
        int[] res = maxSlidingWindow(arr, 1);
        System.out.printf("[%s]\n", IntStream.of(res).mapToObj(String::valueOf).collect(Collectors.joining(", ")));
    }

    private void testMaxEquation() {
        int[][] arr = {
                {-19,-12},{-13,-18},{-12,18},{-11,-8},{-8,2},{-7,12},{-5,16},
                {-3,9},{1,-7},{5,-4},{6,-20},{10,4},{16,4},{19,-9},{20,19}
        };
        int k = 6;
        int findMax = findMaxValueOfEquation(arr, k);
        System.out.printf("Max: %d\n", findMax);
    }

    private void testReplacement() {
        int max = characterReplacement("KRSCDCSONAJNHLBMDQGIFCPEKPOHQIHLTDIQGEKLRLCQNBOHNDQGHJPNDQPERNFSSSRDEQLFPCCCARFMDLHADJADAGNNSBNCJQOF", 4);
        System.out.printf("Max: %d\n",max);
    }

    public int[] maxSlidingWindow(int[] nums, int k) {
        int len = nums.length;
        int[] res = new int[len-k+1];
        Deque<Integer> dq = new ArrayDeque<>();
        for(int i = 0; i < len; i++) {
            while(!dq.isEmpty() && (i - dq.peekLast()) >= k) {
                dq.pollLast();
            }
            while (!dq.isEmpty() && nums[dq.peekFirst()] <= nums[i]) {
                dq.pollFirst();
            }
            dq.offerFirst(i);
            if(i-k+1 >= 0) {
                res[i-k+1] = nums[dq.peekLast()];
            }
        }
        return res;
    }

    public int findMaxValueOfEquation(int[][] points, int k) {
        Deque<Integer> dq = new ArrayDeque<>();
        int max = Integer.MIN_VALUE;
        for(int i = 0; i < points.length; i++) {
            while(!dq.isEmpty()
                    && (points[i][0] - points[dq.peekLast()][0]) > k)  {
                dq.pollLast();
            }
            if(!dq.isEmpty()) {
                int least = dq.peekLast();
                max = Math.max(max,
                        points[i][0] + points[i][1]
                                - (points[least][0] - points[least][1]));
            }
            int currVal = points[i][0] - points[i][1];
            int front = dq.isEmpty() ? -1 : dq.peekFirst();
            System.out.printf("Current: %d\n", (points[i][0] - points[i][1]));
            while(!dq.isEmpty() && currVal <= (points[front][0] - points[front][1])) {
                System.out.printf("Front: %d\n", (points[front][0] - points[front][1]));
                dq.pollFirst();
                front = dq.isEmpty() ? -1 : dq.peekFirst();
            }
            dq.offerFirst(i);
        }
        return max;
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
        int max = 0, total = 0;
        for(int value: counter.values()) {
            max = Math.max(max, value);
            total += value;
        }
        return (total - max) <= k;
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
    public int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> counter = new HashMap<>();
        for(int num : nums) {
            counter.merge(num, 1, Integer::sum);
        }
        PriorityQueue<Map.Entry<Integer, Integer>> pq
                = new PriorityQueue<>(Comparator.comparingInt(Map.Entry::getValue));
        for(Map.Entry<Integer, Integer> entry: counter.entrySet()) {
            pq.offer(entry);
            if(pq.size() > k) {
                pq.remove();
            }
        }
        int[] res = new int[k];
        for(int i = 0; i < k; i++) {
            res[i] = pq.remove().getKey();
        }
        return res;
    }
}
