package com.oracle.casb;

import com.oracle.casb.common.ListNode;

import java.util.Random;

/**
 * Created By : abhijsri
 * Date  : 18/05/18
 **/
public class ListUtils {

    public static ListNode createRandomList() {
        Random ren = new Random();
        int len = ren.nextInt(10);
        ListNode head = new ListNode(ren.nextInt(10));
        ListNode current = head;
        for (int i = 0; i < len; i++) {
            ListNode next = new ListNode(ren.nextInt(10));
            current.setNext(next);
            current = next;
        }
        return head;
    }

    public static ListNode createGivenList(int[] arr) {
        //int[] arr = new int[]{2, 6, 2, 0, 5, 3, 4, 4, 4, 9};
        if (arr == null || arr.length == 0) {
            return null;
        }
        ListNode head = new ListNode(arr[0]);
        ListNode current = head;
        for (int i = 1; i < arr.length; i++) {
            ListNode next = new ListNode(arr[i]);
            current.setNext(next);
            current = next;
        }
        return head;
    }
    public static int getLength(ListNode head) {
        int length = 0;
        while (head != null) {
            length += 1;
            head = head.getNext();
        }
        return length;
    }
}
