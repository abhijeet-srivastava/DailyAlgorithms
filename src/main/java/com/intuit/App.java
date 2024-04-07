package com.intuit;

import com.list.ListNode;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class App {
/**
 * You are given an integer array coins representing coins of different denominations and an integer amount representing a total amount of money.
 *
 * Return the fewest number of coins that you need to make up that amount. If that amount of money cannot be made up by any combination of the coins, return -1.
 *
 * You may assume that you have an infinite number of each kind of coin.
 *
 * You are given an integer array coins representing coins of different denominations and an integer amount representing a total amount of money.
 *
 * Return the fewest number of coins that you need to make up that amount. If that amount of money cannot be made up by any combination of the coins, return -1.
 *
 * You may assume that you have an infinite number of each kind of coin.
 *
 * Input: coins = [1,2,5], amount = 11
 * Output: 3
 * Explanation: 11 = 5 + 5 + 1
 * Individual coins length 1...12, values = 2^31
 * Amount : 10^4
 */

public static void main(String[] args) {
    App app = new App();
    //testCompletableFeature();
    //app.testMinCoins();
    //app.testDeleteConsSum();
    //app.testMinDel();
    app.testMinArrows();
}

    private void testMinArrows() {
        //int[][] points = {{-2147483646,-2147483645},{2147483646,2147483647}};
        //int[][] points = {{3,9},{7,12},{3,8},{6,8},{9,10},{2,9},{0,9},{3,9},{0,6},{2,8}};
        int[][] points = {{9,12},{1,10},{4,11},{8,12},{3,9},{6,9},{6,7}};
        int count = findMinArrowShots(points);
        System.out.printf("Min Arrow count: %d\n", count);
    }

    private void testMinDel() {
        int count = minimumDeletions("aaabaaa", 2);
        System.out.printf("count: %d\n", count);
    }
    public int findMinArrowShots(int[][] points) {
        if(points.length == 0) {
            return 0;
        }
        Arrays.sort(points, Comparator.<int[]>comparingInt(a -> a[0]).thenComparing(a -> a[1]));
        int count = 1;
        int prev = points[0][1];
        for(int i = 1; i < points.length; i++) {
            int[] ballon = points[i];
            prev = Math.min(prev, ballon[1]);
            if(ballon[0] > prev) {
                count += 1;
                prev = ballon[1];
            }
        }
        return count;
    }

    private int minimumDeletions(String word, int k) {
        int[] freq = new int[26];
        for(char ch: word.toCharArray()) {
            freq[ch-'a'] += 1;
        }
        Arrays.sort(freq);
        int i = 0, j = 25;
        while(i < 26 && freq[i] == 0) {
            i += 1;
        }
        return minimumDeletions(i, j, freq, k);
    }

    private int minimumDeletions(int i, int j, int[] freq, int k) {
        if(freq[j]-freq[i] <= k) {
            return 0;
        }
        int delFirst = freq[i] + minimumDeletions(i+1, j, freq, k);
        int delLast = Math.max(0, freq[j]-freq[i]-k) + minimumDeletions(i, j-1, freq, k);
        return Math.min(delFirst, delLast);
    }

    private void testDeleteConsSum() {
        ListNode list = createList(new int[]{2,2,-2,1,-1,-1});
        ListNode res = removeZeroSumSublists1(list);
        printList(res);
    }

    private void printList(ListNode head) {
        ListNode curr = head;
        while (curr != null) {
            System.out.printf("%d, ", curr.val);
            curr = curr.next;
        }
        System.out.printf("\n");
    }
    public ListNode removeZeroSumSublists(ListNode head) {
        Deque<StackElement> stack = new ArrayDeque<>();
        stack.push(new StackElement(null, 0));
        Map<Integer, ListNode> map  = new HashMap<>();
        ListNode curr = head;
        int currSum = 0;
        while(curr != null) {
            currSum += curr.val;
            curr = curr.next;
            if(map.containsKey(currSum)) {
                ListNode prev = map.get(currSum);
                while(!stack.isEmpty() && stack.peek().node != prev) {
                    StackElement tos = stack.pop();
                    map.remove(tos.sum);
                }
                continue;
            }
            map.put(currSum, curr);
            stack.push(new StackElement(curr, currSum));
        }
        head = null;
        curr = null;
        while(!stack.isEmpty()) {
            StackElement left = stack.removeLast();
            if(head == null) {
                head = left.node;
                curr = left.node;
            } else {
                curr.next = left.node;
                curr = curr.next;
            }
        }
        if(curr != null) {
            curr.next = null;
        }
        return head;
    }
    public class StackElement {
        ListNode node;
        int sum;

        private StackElement(ListNode node, int sum) {
            this.node = node;
            this.sum = sum;
        }
    }

    public ListNode removeZeroSumSublists1(ListNode head) {
        Map<Integer, ListNode> sum = new HashMap<>();
        sum.put(0, null);
        int currSum = 0;
        ListNode curr = head, prev = null;
        while(curr != null) {
            currSum += curr.val;
            if(sum.containsKey(currSum)) {
                ListNode prevNode = sum.get(currSum);
                if(prevNode == null) {
                    head = curr.next;
                    sum.clear();
                    sum.put(0, null);
                } else {
                    ListNode tmp = prevNode.next;
                    int comSum = currSum;
                    while(tmp != curr) {
                        comSum += tmp.val;
                        sum.remove(comSum);
                        tmp = tmp.next;
                    }
                    prevNode.next = curr.next;
                }
            } else {
                sum.put(currSum, curr);
            }
            curr = curr.next;
        }
        return head;
    }

    private ListNode createList(int[] arr) {
        ListNode head = null, curr = null;
        for(int num: arr) {
            ListNode node = new ListNode(num);
            if(head == null) {
                head = node;
                curr = node;
            } else {
                curr.next = node;
                curr = curr.next;
            }
        }
        return head;
    }

    private static void testCompletableFeature() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            // Simulate a time-consuming operation
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Hello";
        });

        future.thenApplyAsync(result -> result + " World")
                .thenAccept(System.out::println)
                .join();
    }

    private void testMinCoins() {
        int[] coins = {7, 2, 5};
        int amount = 3;
        int minCount = minimumCoins(coins, amount);
        System.out.printf("Min count required: %d\n", minCount);
    }

    private int minimumCoins(int[] coins, int amount) {
        // 1 - 1, 2 - 1+1, 2, 5 - 10:1, 5:2, 2: 5
        //If amt = 0 = 0,  amt[i] = amt[i-c]
        int[] DP = new int[amount+1];
        Arrays.fill(DP, amount+1);
        DP[0] = 0;
        for(int coin: coins) {
            for(int i = coin; i <= amount; i++) {
                DP[i] = Math.min(DP[i], 1 + DP[i-coin]);
            }
        }
        if(DP[amount] > amount) {
            return -1;
        }
        return DP[amount];
    }
    //n = number of coins
    //O(n*amount)

}
