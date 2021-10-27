package com.oracle.casb.leetcode;

import com.oracle.casb.common.ListNode;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LinkedList {

    public static void main(String[] args) {
        LinkedList ll = new LinkedList();
        //ll.testKReverse();
        ll.testDutchSort();
    }

    private Node flatten(Node root) {
        Node main = root;
        if (main == null) {
            return root;
        }
        while(main != null) {
            Node scndry = main.down;
            if (scndry != null) {
                merge(main, scndry);
                main.down = null;
            }
            main = main.right;
        }
        return root;
    }

    private void merge(Node main, Node scndry) {
        Node current  = main;
        main = main.right;
        Node prev = main;
        while (scndry != null) {
            int mainData = (main != null) ? main.data : Integer.MAX_VALUE;
            int scndryData = (scndry != null) ? scndry.data : Integer.MAX_VALUE;
            if (mainData < scndryData) {
                prev = main;
                main = main.right;
            } else {
                scndry.right = main;
                prev.right = scndry;
                scndry = scndry.down;
            }
        }
    }

    public int[][] kClosest(int[][] points, int K) {
        List<int[]> arr =  Arrays.stream(points)
                .sorted((a, b) -> (a[0]*a[0] + a[1]*a[1] - (b[0]*b[0] + b[1]*b[1])))
                .limit(K)
                .collect(Collectors.toList());
        int[][] ret = new int[K][];
        final int[] i = {0};
        arr.stream().forEach(
                a -> ret[i[0]++] = a
        );
        return ret;
    }
    private void testDutchSort() {
        int[] arr = {0,2,1,0,0,2,1,3,3,3,2,2,1,1,3,2,1,0,0,1,0,2,0,2,3,3,2,1,2,3,1};
        //dutchSort(arr);
        dutch4sort(arr);
        System.out.println(Arrays.stream(arr).boxed().map(String::valueOf).collect(Collectors.joining(",")));
    }

    private void testKReverse() {
        ListNode head = createList();
        ListNode reverse  = reverseKGroup(head, 3);
    }

    public void dutch4sort(int[] arr) {
        int lo = 0, mid = 0, hi = arr.length-1;
        while (mid <= hi) {
            if (arr[mid] == 0) {
                swap(arr, mid, lo);
                mid += 1;
                lo += 1;
            } else if (arr[mid] == 1) {
                mid += 1;
            } else {
                swap(arr, mid, hi);
                hi -= 1;
            }
        }
        lo = mid+1;
        hi = arr.length-1;
        while (lo <= hi) {
            if (arr[lo] == 2) {
                lo += 1;
            } else {
                swap(arr, lo, hi);
                hi -= 1;
            }
        }
    }

    public void dutchSort(int[] arr) {
        if(arr == null || arr.length ==0) {
            return;
        }
        int lo = 0, mid = 0, hi = arr.length - 1;
        while(mid <= hi) {
            if (arr[mid] == 0) {
                swap(arr, mid, lo);
                mid += 1;
                lo += 1;
            } else if (arr[mid] == 1) {
                mid += 1;
            } else if (arr[mid] == 2) {
                swap(arr, mid, hi);
                hi -= 1;
            }
        }
    }

    private void swap(int[] arr, int p1, int p2) {
        int tmp = arr[p1];
        arr[p1] = arr[p2];
        arr[p2] = tmp;
    }

    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode curr = head;
        int count = 0;
        while (curr != null && count != k) { // find the k+1 node
            curr = curr.getNext();
            count++;
        }
        if (count == k) { // if k+1 node is found
            curr = reverseKGroup(curr, k); // reverse list with k+1 node as head
            // head - head-pointer to direct part,
            // curr - head-pointer to reversed part;
            while (count-- > 0) { // reverse current k-group:
                ListNode tmp = head.getNext(); // tmp - next head in direct part
                head.setNext(curr);  // preappending "direct" head to the reversed list
                curr = head; // move head of reversed part to a new node
                head = tmp; // move "direct" head to the next node in direct part
            }
            head = curr;
        }
        return head;
    }

    private ListNode reverseKGroup1(ListNode head, int k) {
        if (head == null) {
            return head;
        }
        int count = 1;
        ListNode last = head;
        while(count < k && last != null) {
            last = last.getNext();
            count += 1;
        }
        if (count < k || last == null) {
            return head;
        }
        ListNode tmp = last.getNext();
        reverseList(head, last);
        head.setNext(reverseKGroup(tmp, k));
        head = last;
        return head;
    }

    private void reverseList(ListNode head, ListNode last) {
        ListNode start = head;
        ListNode prev = null;
        ListNode tmp = start.getNext();
        start.setNext(null);
        while(prev != last && tmp != null) {
            ListNode next = tmp.getNext();
            tmp.setNext(start);
            start = tmp;
            prev = tmp;
            tmp = next;
        }
    }

    private ListNode createList() {
        ListNode head = new ListNode(1);
        ListNode tmp = head;
        for (int i = 2; i <= 11; i++) {
            tmp.setNext(new ListNode(i));
            tmp = tmp.getNext();
        }
        return head;
    }
    private class Node {
        int data;
        Node right, down;
        Node(int data)
        {
            this.data = data;
            right = null;
            down = null;
        }
    }

}
