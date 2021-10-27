package com.oracle.casb.leetcode;

import com.oracle.casb.common.ListNode;

import java.util.Stack;

/**
 * Created By : abhijsri
 * Date  : 2019-01-14
 **/
public class PrintAlternate {
    public static void main(String[] args) {
        PrintAlternate pa = new PrintAlternate();
        pa.test();
    }

    private void test() {
        ListNode head = createList(50);
        ListNode alterRev = reverseAlternate(head);
        printList(alterRev);
    }

    private void printList(ListNode head) {
        while(head != null) {
            System.out.println(head.getValue());
            head = head.getNext();
        }
    }

    private ListNode reverseAlternate(ListNode head) {
        Stack<ListNode> stack = new Stack<>();
        ListNode temp = head;
        while (temp != null) {
            stack.push(temp);
            temp = temp.getNext();
        }
        temp = head;
        ListNode result = head;
        ListNode resHead = result;
        while(true) {
            if (temp == null || stack.empty()) {
                //result.setNext(null);
                break;
            }
            temp = temp.getNext();
            ListNode temp2 = stack.pop();
            if (temp == temp2) {
                //result.setNext(null);
                break;
            }
            result.setNext(temp2);
            result = result.getNext();
            result.setNext(temp);
            result = result.getNext();
            //result.setNext(null);
        }
        result.setNext(null);
        return resHead;

    }

    private ListNode createList(int count) {
        ListNode head = new ListNode(1);
        ListNode temp = head;
        for (int i = 2; i <= count; i++) {
            ListNode node = new ListNode(i);
            temp.setNext(node);
            temp = temp.getNext();
        }
        return head;
    }
}
