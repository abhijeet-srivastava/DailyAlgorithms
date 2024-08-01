package com.leetcode.contest392;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeMap;

public class App {

    public static void main(String[] args) {
        App app = new App();
        //app.testFirst();
       // app.testSmallestString();
        //app.testMedian();
        //app.testCheckValidString();
        //app.testSadwitchDistr();
       // app.testLruCache();
        //app.testMaxScore();
        app.testOrderMatch();
    }

    private void testOrderMatch() {
        //int[][] orders = {{10,5,0},{15,2,1},{25,1,1},{30,4,0}};
        //int[][] orders = {{7,1000000000,1},{15,3,0},{5,999999995,0},{5,1,1}};
        int[][] orders = {{944925198,885003661,0},{852263791,981056352,0},{16300530,415829909,0},{940927944,713835606,0},{606389372,407474168,1},{139563740,85382287,1},{700244880,901922025,1},{972900669,15506445,0},{576578542,65339074,0},{45972021,293765308,0},{464403992,97750995,0},{29659852,536508041,0},{799523481,299864737,0},{711908211,480514887,1},{354125407,677598767,1},{279004011,688916331,0},{263524013,64622178,0},{375395974,460070320,0},{971786816,379275200,1},{577774472,214070125,1},{987757349,711231195,0}};
        int count = getNumberOfBacklogOrders1(orders);
        System.out.printf("Count rem: %d\n", count);
    }

    public int getNumberOfBacklogOrders1(int[][] orders) {
        int MOD = 1_000_000_007;
        PriorityQueue<int[]> buyQueue = new PriorityQueue<>((a, b) -> Integer.compare(b[0], a[0]));
        PriorityQueue<int[]> sellQueue = new PriorityQueue<>((a, b) -> Integer.compare(a[0], b[0]));
        for(int[] order:orders) {
            if(order[2] == 0) {
                //Buy Order
                while(order[1] > 0 && !sellQueue.isEmpty() && sellQueue.peek()[0] <= order[0]) {
                    int[] sellOrder = sellQueue.poll();
                    int sellQuantity = Math.min(order[1], sellOrder[1]);
                    order[1] -= sellQuantity;
                    sellOrder[1] -= sellQuantity;
                    if(sellOrder[1] > 0) {
                        sellQueue.offer(sellOrder);
                    }
                }
                if(order[1] > 0) {
                    buyQueue.offer(new int[]{order[0], order[1]});
                }
            } else {
                //Sell Order
                while(order[1] > 0 && !buyQueue.isEmpty() && buyQueue.peek()[0] >= order[0]) {
                    int[] buyOrder = buyQueue.poll();
                    int buyQuantity = Math.min(order[1], buyOrder[1]);
                    order[1] -= buyQuantity;
                    buyOrder[1] -= buyQuantity;
                    if(buyOrder[1] > 0) {
                        buyQueue.offer(buyOrder);
                    }
                }
                if(order[1] > 0) {
                    sellQueue.offer(new int[]{order[0], order[1]});
                }
            }
        }
        int res = 0;
        while(!buyQueue.isEmpty()) {
            res = (res + buyQueue.poll()[1])%MOD;
        }
        while(!sellQueue.isEmpty()) {
            res = (res + sellQueue.poll()[1])%MOD;
        }
        return res;
    }


    public int getNumberOfBacklogOrders(int[][] orders) {
        int MOD = 1_000_000_007;
        TreeMap<Integer, Integer> BUY_ORDERS = new TreeMap<>((a, b) -> Integer.compare(b, a));
        TreeMap<Integer, Integer> SELL_ORDERS = new TreeMap<>((a, b) -> Integer.compare(a, b));
        for(int[] order: orders) {
            if(order[2] == 0) {
                //buy order
                if(SELL_ORDERS.isEmpty() || SELL_ORDERS.firstKey() > order[0]) {
                    //BUY_ORDERS.merge(order[0], order[1], Integer::sum);
                    int totalVolume = (BUY_ORDERS.getOrDefault(order[0], 0) + order[1]) % MOD;
                    BUY_ORDERS.put(order[0], totalVolume);
                } else {
                    Map<Integer, Integer> availableSellOrders = SELL_ORDERS.headMap(order[0], true);
                    Set<Integer> consumedSellOrders = new HashSet<>();
                    for(var t: availableSellOrders.entrySet()) {
                        if(order[1] == 0) {
                            break;
                        }
                        if(order[1] >= t.getValue()) {
                            order[1] -= t.getValue();
                            //availableSellOrders.remove(t.getKey());
                            consumedSellOrders.add(t.getKey());
                        } else {
                            availableSellOrders.merge(t.getKey(), -1*order[1], Integer::sum);
                            order[1] = 0;
                        }
                    }
                    if(order[1] > 0) {
                        BUY_ORDERS.merge(order[0], order[1], Integer::sum);
                    }
                    for(int key: consumedSellOrders) {
                        SELL_ORDERS.remove(key);
                    }
                }
            } else {
                //Sell order
                if(BUY_ORDERS.isEmpty() || BUY_ORDERS.firstKey() < order[0]) {
                    //SELL_ORDERS.merge(order[0], order[1], Integer::sum);
                    int totalVolume = (SELL_ORDERS.getOrDefault(order[0], 0) + order[1]) % MOD;
                    SELL_ORDERS.put(order[0], totalVolume);
                } else {
                    Map<Integer, Integer> availableBuyOrders = BUY_ORDERS.headMap(order[0], true);
                    Set<Integer> consumedBuyOrders = new HashSet<>();
                    for(var t: availableBuyOrders.entrySet()) {
                        if(order[1] == 0) {
                            break;
                        }
                        if(order[1] >= t.getValue()) {
                            order[1] -= t.getValue();
                            consumedBuyOrders.add(t.getKey());
                        } else {
                            availableBuyOrders.merge(t.getKey(), -1*order[1], Integer::sum);
                            order[1] = 0;
                        }
                    }
                    if(order[1] > 0) {
                        SELL_ORDERS.merge(order[0], order[1], Integer::sum);
                    }
                    for(int key: consumedBuyOrders) {
                        BUY_ORDERS.remove(key);
                    }
                }
            }
        }
        int totalVal = 0;
        for(int quantity: BUY_ORDERS.values()) {
            totalVal = (totalVal + quantity)%MOD;
        }
        for(int quantity: SELL_ORDERS.values()) {
            totalVal = (totalVal + quantity)%MOD;
        }
        return totalVal;
    }

    private void testMaxScore() {
        List<List<Integer>> grid = Arrays.asList(Arrays.asList(9,5,7,3),
                Arrays.asList(8,9,6,1),
                Arrays.asList(6,7,14,3),
                Arrays.asList(2,5,3,1));
        int maxScore = maxScore(grid);
        System.out.printf("MaxSocre: %d\n", maxScore);
    }

    public int maxScore(List<List<Integer>> grid) {
        int maxScore = -1000_000;
        int m = grid.size(), n = grid.get(0).size();
        int[][] DP = new int[m][n];
        for(int i = 0; i < m; i++) {
            for(int j = 0; j < n; j++) {
                int pre = Math.min(
                        i == 0 ? 1000_000: DP[i-1][j],
                        j == 0 ? 1000_000: DP[i][j-1]
                );
                maxScore = Math.max(maxScore, grid.get(i).get(j) - pre);
                if(pre < grid.get(i).get(j)) {
                    DP[i][j] = pre;
                } else {
                    DP[i][j] = grid.get(i).get(j);
                }
            }
        }
        return maxScore;
    }
    private int maxScore(int r, int c, List<List<Integer>> grid, Integer[][] DP) {
        int maxScore = Integer.MIN_VALUE + 1000;
        int m = grid.size(), n = grid.get(0).size();
        int currNum = grid.get(r).get(c);
        if (r == m - 1 && c == n - 1) {
            return maxScore;
        } else if(DP[r][c] != null) {
            return DP[r][c];
        }else if (r == m - 1) {
            for (int col = c + 1; col < n; col++) {
                maxScore = Math.max(maxScore, grid.get(r).get(col) - currNum);
            }
            DP[r][c] =  maxScore;
            return maxScore;
        } else if (c == n - 1) {
            for (int row = r + 1; row < m; row++) {
                maxScore = Math.max(maxScore, grid.get(row).get(c) - currNum);
            }
            DP[r][c] =  maxScore;
            return maxScore;
        }
        for (int i = r; i < m; i++) {
            for (int j = c; j < n; j++) {
                if(i == r && j == c) {
                    continue;
                }
                int currScore = grid.get(i).get(j) - currNum;
                int nextScore = maxScore(i, j, grid, DP);
                maxScore = Math.max(maxScore, currScore + nextScore);
            }
        }
        DP[r][c] =  maxScore;
        return maxScore;
    }
    private void testLruCache() {
        LRUCache cache = new LRUCache(2);
        cache.put(1, 1);
        cache.put(2, 2);
        int val = cache.get(1);
        System.out.printf("key: %d, val: %d\n", 1, val);
        cache.put(3,3);
        val = cache.get(2);
        System.out.printf("key: %d, val: %d\n", 2, val);
        cache.put(4,4);
        val = cache.get(1);
        System.out.printf("key: %d, val: %d\n", 1, val);
        val = cache.get(3);
        System.out.printf("key: %d, val: %d\n", 3, val);
        val = cache.get(4);
        System.out.printf("key: %d, val: %d\n", 2, val);


    }

    private void testSadwitchDistr() {
        int[] students = {1,1,1,0,0,1}, sandwitches = {1,0,0,0,1,1};
        int count = countStudents(students, sandwitches);
        System.out.printf("count: %d\n", count);
    }

    public int countStudents(int[] students, int[] sandwiches) {
        Deque<Integer> queue = new ArrayDeque<>();
        for(int student: students) {
            queue.offerLast(student);
        }
        int j = 0;
        while(true) {
            int size = queue.size();
            for(int i = size; i > 0 && j < sandwiches.length;i--) {
                int currChoice = queue.pollFirst();
                if(currChoice == sandwiches[j]) {
                    j += 1;
                } else {
                    queue.offerLast(currChoice);
                }
            }
            if(size == queue.size()) {
                break;
            }
        }
        return queue.size();
    }

    private void testCheckValidString() {
        //String str  = "(((((()*)(*)*))())())(()())())))((**)))))(()())()";
        String str  = "(((((*(()((((*((**(((()()*)()()()*((((**)())*)*)))))))(())(()))())((*()()(((()((()*(())*(()**)()(())";
        boolean isValid = checkValidString(str);
        System.out.printf("valid: %b\n", isValid);
    }

    private void testMedian() {
        int[] nums = {98,52};
        long val = minOperationsToMakeMedianK(nums, 82);
    }
    public boolean checkValidString(String s) {
        int n = s.length();
        boolean[][] DP= new  boolean[n+1][n+1];
        DP[n][0] = true;//if (index == s.size()) return (openingBracket == 0);
        for(int idx = n-1; idx >= 0; idx--) {
            char ch = s.charAt(idx);
            for(int count = 0; count < n; count++) {
                if(ch == '(') {
                    DP[idx][count] = DP[idx+1][count+1];
                } else if(ch == ')' && count > 0) {
                    DP[idx][count] = DP[idx+1][count-1];
                } else if(ch == '*'){
                    DP[idx][count] = DP[idx+1][count] || DP[idx+1][count+1];
                    if(count > 0) {
                        DP[idx][count] =  DP[idx][count] || DP[idx+1][count-1];
                    }
                }
            }
        }
        return DP[0][0];
    }

    public long minOperationsToMakeMedianK(int[] nums, int k) {
        Arrays.sort(nums);
        int n = nums.length;
        long res = 0l;
        for(int i = 0; i <= n/2; i++) {
            res += Math.max(0, nums[i]-k);
        }
        for(int i = n/2; i < n; i++) {
            res += Math.max(0, k- nums[i]);
        }
        return res;
    }

    private void testSmallestString() {
        String str= "xaxcd";
        String smallest = getSmallestString(str, 4);
        System.out.printf("Smallest: %s\n", smallest);
    }

    public String getSmallestString(String s, int k) {
        if(k == 0) {
            return s;
        }
        int n = s.length();
        char[] arr = new char[n];
        for(int i = 0; i < n; i++) {
            int currIdx = s.charAt(i) - 'a';
            int nextADist = 26-currIdx,  prevIdx = currIdx;
            if(nextADist <= k || prevIdx <= k) {
                arr[i] = 'a';
                k -= Math.min(nextADist, prevIdx);
            } else {
                arr[i] = (char)(currIdx-k + 'a');
                k = 0;
            }
        }
        return new String(arr);
    }
    private void testFirst() {
        int[] arr = {3,2,1};
        System.out.printf("max: %d\n", longestMonotonicSubarray(arr));
    }
    public int longestMonotonicSubarray(int[] nums) {
        int lis = 1, lds = 1;
        int prev = nums[0];
        int currMax = 1, currMin = 1;
        for(int i = 1; i < nums.length; i++) {
            if(nums[i] > prev) {
                currMax += 1;
                lis = Math.max(lis, currMax);
            } else {
                currMax =  1;
            }
            if(nums[i] < prev) {
                currMin += 1;
                lds = Math.max(lds, currMin);
            } else {
                currMin =  1;
            }

            prev = nums[i];
        }
        return Math.max(lis, lds);
    }

    private int longestDecreasingSubseq(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        // array to store subproblem solution. `L[i]` stores the length
        // of the longest decreasing subsequence that ends with `nums[i]`
        int[] L = new int[nums.length];
        // longest decreasing subsequence ending at `nums[0]` has length 1
        L[0] = 1;
        // start from the second array element
        for (int i = 1; i < nums.length; i++) {
            // do for each element in subarray `nums[0…i-1]`
            for (int j = 0; j < i; j++) {
                // find longest decreasing subsequence that ends with `nums[j]`
                // where `nums[j]` is more than the current element `nums[i]`

                if (nums[j] > nums[i] && L[j] > L[i]) {
                    L[i] = L[j];
                }
            }
            // include `nums[i]` in LDS
            L[i]++;
        }

        // return longest decreasing subsequence (having maximum length)
        return Arrays.stream(L).max().getAsInt();
    }
    private int longestDecreasingSubseq(int[] nums, int idx, int prev) {
       if(idx == nums.length) {
           return 0;
       }
        int excl = longestDecreasingSubseq(nums, idx + 1, prev);
        int incl = 0;
        if (nums[idx] < prev) {
            incl = 1 + longestDecreasingSubseq(nums, idx + 1, nums[idx]);
        }
        return Math.max(incl, excl);
    }

    private int longestIncreasingSubseq(int[] nums) {
        int n = nums.length;
        int[] DP = new int[n];
        DP[0] = nums[0];
        int l = 1;
        for(int i = 1; i < n; i++) {
            if(nums[i] > DP[l-1]) {
                DP[l] = nums[i];
                l += 1;
            } else {
                int idx = Arrays.binarySearch(DP, 0, l-1, nums[i]);
                if(idx < 0) {
                    idx = -(idx+1);
                }
                DP[idx] = nums[i];
            }
        }
        return l;
    }
}
