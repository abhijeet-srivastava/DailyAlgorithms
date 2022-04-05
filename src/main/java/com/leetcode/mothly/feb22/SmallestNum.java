package com.leetcode.mothly.feb22;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SmallestNum {
    public static void main(String[] args) {
        SmallestNum sm = new SmallestNum();
        //sm.testRemoveNum();
        //sm.testMaxSwap();
        sm.testRandom();
    }

    private void testRandom() {
        Node list = creatList();
        Node clone = copyRandomList(list);
        System.out.printf("clone head");
        List<int[]> result = new ArrayList<>();
        int[][] arr = result.toArray(new int[result.size()][2]);
    }

    private Node creatList() {
        Node head = new Node(7);
        Node thirteen = new Node(13);
        Node eleven = new Node(10);
        Node one = new Node(1);
        head.next = thirteen;
        head.random = one;
        thirteen.next = eleven;
        thirteen.random = head;
        eleven.next = one;
        eleven.random = one;
        one.random = head;

        return head;

    }

    private void testMaxSwap() {
        int num = maximumSwap(2736);
        System.out.printf("Swapped: %d\n", num);
    }

    public Node copyRandomList(Node head) {
        Node current = head;
        while(current != null) {
            Node next = current.next;
            Node node = new Node(current.val);
            current.next = node;
            node.next = next;
            current = next;
        }
        Node cloneHead = null;
        current = head;
        Node cloneCurrent = null;
        while(current != null) {
            Node cloned = current.next;
            Node next = cloned.next;
            cloned.random = current.random.next;
            if(cloneHead == null) {
                cloneHead = cloned;
                cloneCurrent = cloned;
            } else {
                cloneCurrent.next = cloned;
                cloneCurrent = cloneCurrent.next;
            }
            current.next = next;
            current = next;
        }
        return cloneHead;
    }

    public int maximumSwap(int num) {
        char[] digits = Integer.toString(num).toCharArray();

        int[] buckets = new int[10];
        for (int i = 0; i < digits.length; i++) {
            buckets[digits[i] - '0'] = i;
        }

        for (int i = 0; i < digits.length; i++) {
            for (int k = 9; k > digits[i] - '0'; k--) {
                if (buckets[k] > i) {
                    char tmp = digits[i];
                    digits[i] = digits[buckets[k]];
                    digits[buckets[k]] = tmp;
                    return Integer.valueOf(new String(digits));
                }
            }
        }

        return num;
    }
    private void testRemoveNum() {
        String num = "1432219";
        String res = removeKdigits(num, 3);
        System.out.printf("Res: %s\n", res);
    }

    public String removeKdigits(String num, int k) {
        boolean[] candidates = new boolean[num.length()];
        int prev = Character.getNumericValue(num.charAt(0));
        for(int  i = 1; i < num.length(); i++) {
            int current = Character.getNumericValue(num.charAt(i));
            System.out.printf("Prev: %d Current: %d\n", prev, current);
            if(prev > current) {
                candidates[i-1] = true;
                System.out.printf("Removal candidate: %d\n", i-1);
            }
            prev = current;
        }
        char[] array = new char[num.length() - k];
        int index = 0;
        int removed = 0;
        for(int i = 0; i < num.length(); i++) {
            if(candidates[i] && removed < k) {
                removed += 1;
            } else {
                array[index++] = num.charAt(i);
            }
        }
        return String.valueOf(array);
    }
    class Node {
        int val;
        Node next;
        Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }

    public int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> (a[0] == b[0]) ? a[1] - b[1] : a[0]-b[0]);
        ArrayDeque<int[]> stack = new ArrayDeque<>();
        for(int[] interval : intervals) {
            if(stack.isEmpty() || !overlaps(stack.peek(), interval)){
                stack.push(interval);
            } else {
                int[] top = stack.pop();
                stack.push(merge(top, interval));
            }
        }
        int[][] res = new int[stack.size()][2];
        int i = stack.size();
        while(!stack.isEmpty()) {
            res[i--] = stack.pop();
        }
        return res;
    }
    private boolean overlaps(int[] interval1, int[] interval2) {
        return Math.max(interval1[0], interval2[0]) < Math.min(interval1[1], interval2[1]);
    }

    private int[] merge(int[] inter1, int[] inter2) {
        return  new int[]{Math.min(inter1[0], inter1[0]), Math.max(inter1[1], inter1[1])};
    }
}
