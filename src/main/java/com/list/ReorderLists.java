package com.list;

public class ReorderLists {

    public static void main(String[] args) {
        ReorderLists rl = new ReorderLists();
        rl.testReorder();
    }

    private void testReorder() {
        ListNode list = createTmpList();
        reorderList(list);
        printList(list);
    }

    private ListNode createTmpList() {
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        //head.next.next.next.next = new ListNode(5);
        //head.next.next.next.next.next = new ListNode(6);
        return head;
    }

    public void reorderList(ListNode head) {
        if(head == null || head.next == null) {
            return;
        }
        ListNode oddHead = head;
        ListNode evenHead = head.next;
        ListNode oddCurr = oddHead;
        ListNode evenCurr = evenHead;
        ListNode current = evenHead.next;
        while(current != null) {
            oddCurr.next = current;
            current = current.next;
            oddCurr = oddCurr.next;
            if(current != null) {
                evenCurr.next = current;
                current = current.next;
                evenCurr = evenCurr.next;
            }
        }
        evenCurr.next = null;
        oddCurr.next = null;
        printList(evenHead);
        printList(oddHead);
        evenHead = reverse(evenHead);
        printList(evenHead);
        oddCurr = oddHead;
        evenCurr = evenHead;
        while(evenCurr != null) {
            ListNode oddNext = oddCurr.next;
            oddCurr.next = evenCurr;
            oddCurr = oddNext;
            ListNode evenNext = evenCurr.next;
            evenCurr.next = oddCurr;
            evenCurr = evenNext;
        }
        printList(head);
    }
    private ListNode reverse(ListNode head) {
        if(head == null || head.next == null) {
            return head;
        }
        ListNode prev = head;
        ListNode current = prev.next;
        prev.next = null;
        while(current != null) {
            ListNode tmp = current.next;
            current.next = prev;
            prev = current;
            current = tmp;
        }
        return prev;
    }

    private void printList(ListNode head) {
        while(head.next != null) {
            System.out.printf("%d ->", head.val);
            head = head.next;
        }
        System.out.printf("%d\n", head.val);
    }
}
