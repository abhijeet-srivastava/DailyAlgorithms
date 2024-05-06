package com.test;

import java.util.*;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LC350 {
    public static void main(String[] args) {
        LC350 lc = new LC350();
        //lc.testIntersection();
        lc.testLongestObstacles();
    }

    private void testLongestObstacles() {
        int[] obstacles = {3,1,5,6,4,2};
        int[] res = longestObstacleCourseAtEachPosition(obstacles);
        IntStream.of(res).forEach(printConsumer());
    }

    private void testIntersection() {
        int[] num1 = {1,2,2,1};
        int[] num2 = {2};
        int[] inx = intersect(num1, num2);
        IntStream.of(inx).forEach(printConsumer());
    }

    private IntConsumer printConsumer() {
        return (x) -> System.out.printf("%d", x);
    }

    public int[] longestObstacleCourseAtEachPosition(int[] obstacles) {
        int n = obstacles.length, length = 0;
        int[] res = new int[n];
        int[] mono = new int[n];
        for(int i = 0; i < n; i++) {
            int l = 0;
            int r = length;
            while(l < r) {
                int m = l + (r-l)/2;
                if(mono[m] <= obstacles[i]){
                    l = m+1;
                } else {
                    r = m;
                }
            }
            res[i] = l+1;
            if(length == l) {
                length += 1;
            }
            mono[l] = mono[i];
        }
        return res;
    }

    public int[] intersect(int[] nums1, int[] nums2) {
        Map<Integer, Long> m1 = getCounts(nums1);
        Map<Integer, Long> m2 = getCounts(nums2);
        List<Integer> res = new ArrayList<>();
        for(Map.Entry<Integer, Long> mapEntry : m1.entrySet()) {
            if (!m2.containsKey(mapEntry.getKey())) {
                continue;
            }
            int count = Math.min(mapEntry.getValue().intValue(), m2.get(mapEntry.getKey()).intValue());
            res.addAll(Collections.nCopies(count, mapEntry.getKey()));
        }
        return res.stream().mapToInt(x -> x).toArray();
    }

    private Map<Integer, Long> getCounts(int[] nums) {
        return IntStream.of(nums).boxed().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    public void gameOfLife(int[][] board) {
        int rows = board.length;
        int columns = board[0].length;
        int[][] directions = {{-1,-1}, {-1, 0}, {-1, 1}, {0,-1}, {0,1}, {1, -1}, {1, 0}, {1, 1}};
        for(int row = 0; row < rows; row++) {
            for(int col = 0; col < columns; col++) {
                int liveNeighbours = countLiveNeighbours(board, row, col,directions);
                if(board[row][col] == 1 && (liveNeighbours == 2 || liveNeighbours == 3)) {
                    board[row][col] = 3;
                } else if(board[row][col] == 0 && liveNeighbours == 3) {
                    board[row][col] = 2;
                }
            }
        }
        for(int row = 0; row < rows; row++) {
            for(int col = 0; col <columns; col++) {
                board[row][col] >>= 1;
            }
        }
    }

    private int countLiveNeighbours(int[][] board, int row, int col, int[][] directions) {
        int count = 0;
        for(int[] direction : directions) {
            int currRow = row + direction[0];
            int currCol = col + direction[1];
            if(currRow < 0 || currRow >= board.length
                    || currCol < 0 || currCol >= board[currRow].length
                    || board[currRow][currCol] == 0) {
                continue;
            }
            count += 1;
        }
        return count;
    }

    //Exact one Txn
    public int maxProfit(int[] prices) {
        int maxProfit = 0;
        int minPrice = prices[0];
        for(int price : prices) {
            minPrice = Math.min(minPrice, price);
            int currProfit = price - minPrice;
            maxProfit = Math.max(maxProfit, currProfit);
        }
        return maxProfit;
    }
    //Any number of Txn
    public int maxProfitII(int[] prices) {
        int profit = 0;
        for(int i = 0; i < prices.length-1; i++) {
            if(prices[i+1] > prices[i]) {
                profit += (prices[i+1] - prices[i]);
            }
        }
        return profit;
    }
    //At most 2 Txn
    public int maxProfitIII(int[] prices) {
        int state1 = -prices[0];
        int state2 = Integer.MIN_VALUE;
        int state3 = Integer.MIN_VALUE;
        int state4 = Integer.MIN_VALUE;
        //for(int i = 0; i < prices.length; i++) {
        for(int price : prices) {
            //int price = prices[i];
            state1 = Math.max(state1, -price);
            state2 = Math.max(state2, state1+price);
            state3 = Math.max(state3, state2-price);
            state4 = Math.max(state4, state3+price);
        }
        return Math.max(0, state4);
    }
    public int maxProfitWithKTxn(int[] prices, int k) {
        if(prices == null || prices.length < 2 || k == 0) {
            return 0;
        }
        if(k >= prices.length/2) {
            return maxProfitByInfiniteTxn(prices);
        }
        int[] buy = new int[k+1];
        int[] sell = new int[k+1];
        Arrays.fill(buy, Integer.MIN_VALUE);
        for(int price : prices) {
            for(int txnNum = 1; txnNum <= k; txnNum++) {
                buy[txnNum] = Math.max(buy[txnNum], sell[txnNum-1]-price);
                sell[txnNum] = Math.max(sell[txnNum], buy[txnNum] + price);
            }
        }
        return sell[k];
    }

    private int maxProfitByInfiniteTxn(int[] prices) {
        int profit = 0;
        for(int i = 0; i < prices.length-1; i++) {
            if(prices[i] < prices[i+1]) {
                profit += (prices[i+1] - prices[i]);
            }
        }
        return profit;
    }

    public int maxProfitWithFee(int[] prices, int fee) {
        int sell = 0;
        int buy = -prices[0];
        for(int i = 1; i < prices.length; i++) {
            int tmp = sell;
            sell = Math.max(sell, buy+prices[i]-fee);
            buy = Math.max(buy, tmp-prices[i]);
        }
        return sell;
    }
    public int maxProfitWithCoolDownI(int[] prices) {
        int b0 = -prices[0];
        int b1 = b0;
        int s0 = 0, s1=0, s2=0;
        for(int price:prices) {
            b0 = Math.max(b1, s2 - price);
            s0 = Math.max(s1, b1+price);
            b1 = b0; s2 = s1; s1 = s0;
        }
        return s0;
    }
    public int maxProfit(int[] prices, int k) {
        if(prices == null || prices.length < 2) {
            return 0;
        }
        if(k >= prices.length/2) {
            return maxProfitII(prices);
        }
        int[][] DP = new int[k+1][prices.length];
        for(int txnNum = 1; txnNum <= k; txnNum++) {
            int localMax =  -prices[0];
            for(int dayNum = 1; dayNum < prices.length; dayNum++) {
                DP[txnNum][dayNum] = Math.max(DP[txnNum][dayNum-1], prices[dayNum]+localMax);
                localMax = Math.max(localMax, DP[txnNum-1][dayNum-1]-prices[dayNum]);
            }
        }
        return DP[k][prices.length-1];
    }
}
