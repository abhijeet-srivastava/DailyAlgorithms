package com.walmart;

import java.util.*;
import java.util.stream.Collectors;

//https://leetcode.com/problems/cinema-seat-allocation/
public class CinemaReserveSeat {

    public static void main(String[] args) {
        CinemaReserveSeat crs = new CinemaReserveSeat();
        //crs.testAvailableSlots();
        //crs.testReverseLLinGroup();
        crs.testDecoding();
    }

    private void testReverseLLinGroup() {
        int[] array = {0, 4, 2, 1, 3};
        ListNode head = createTest1List(array);
        ListNode result = reverseEvenLengthGroups(head);
        String str = printList(result);
        System.out.printf("Result: %s\n", str);
    }

    private String printList(ListNode result) {
        StringBuilder sb  = new StringBuilder();
        while(result != null) {
            sb.append(result.val);
            result = result.next;
            if(result != null) {
                sb.append(',');
            }
        }
        return sb.toString();
    }

    private ListNode createTest1List(int[] array ) {
        ListNode head = null;
        ListNode current = null;
        for(int num : array) {
            ListNode node = new ListNode(num);
            if(head == null) {
                head = node;
                current = node;
            } else {
                current.next = node;
                current = current.next;
            }
        }
        return head;
    }

    private void testAvailableSlots() {
        int n = 3;
        int[][] reservedSeats = {{1,2},{1,3},{1,8},{2,6},{3,1},{3,10}};
        int countSlots = maxNumberOfFamilies(n, reservedSeats);
        System.out.printf("Available Slots: %d\n", countSlots);
    }

    public int maxNumberOfFamilies(int n, int[][] reservedSeats) {
        //Reserved Seat combinations are
        //2 3,4, 5
        int left = (1 << 2) | (1 << 3) | (1 << 4) | (1 << 5);
        //6,7,8,9
        int right = (1 << 6) | (1 << 7) | (1 << 8) | (1 << 9);
        //4,5,6,7
        int middle = (1 << 4) | (1 << 5) | (1 << 6) | (1 << 7);
        //Bit Map for reserved Seats
        Map<Integer, Integer> reservedMap = new HashMap<>();
        for(int[] reserved : reservedSeats) {
            //For each row: Set Bit for number of columm reserved
            reservedMap.put(reserved[0], reservedMap.getOrDefault(reserved[0], 0) | ( 1 << reserved[1]));
        }
        int availableSlots = 0;
        for(int row : reservedMap.keySet()) {
            //Get reserved Bits in current row
            int reservedBits = reservedMap.get(row);
            int currRowSlots = 0;
            //Wether left Slot can be reserved
            if((reservedBits & left) == 0) {
                currRowSlots += 1;
            }
            //Wether right Slot can be reserved
            if((reservedBits & right) == 0) {
                currRowSlots += 1;
            }
            //If both right and left slots are unreserved and middle slot can be reserved
            if((currRowSlots == 0) && ((reservedBits & middle) == 0)) {
                currRowSlots += 1;
            }
            availableSlots += currRowSlots;

        }
        /*for(int i = 1; i <= n; i++) {
            if(reservedMap.containsKey(i)) {
                //Get reserved Bits in current row
                int reservedBits = reservedMap.get(i);
                int currRowSlots = 0;
                //Wether left Slot can be reserved
                if((reservedBits & left) == 0) {
                    currRowSlots += 1;
                }
                //Wether right Slot can be reserved
                if((reservedBits & right) == 0) {
                    currRowSlots += 1;
                }
                //If both right and left slots are unreserved and middle slot can be reserved
                if((currRowSlots == 0) && ((reservedBits & middle) == 0)) {
                    currRowSlots += 1;
                }
                availableSlots += currRowSlots;
            } else {
                //No reserved seat in row, Both left and right slots can be reserved
                availableSlots += 2;
            }
        }*/
        return availableSlots + 2*(n-reservedMap.size());
    }
    public List<String> alertNames(String[] keyName, String[] keyTime) {
        Map<String, TreeSet<Integer>> keyMap = new HashMap<>();
        Set<String> result = new TreeSet<>();

        for(int  i = 0; i < keyName.length; i++) {
            Integer currTimeAsInt = convertToTimePassed(keyTime[i]);
            System.out.printf("Current user: %s nad time: %d\n", keyName[i], currTimeAsInt);
            keyMap.computeIfAbsent(keyName[i], x -> new TreeSet<>()).add(currTimeAsInt);
            TreeSet<Integer> userAccess = keyMap.get(keyName[i]);
            Set<Integer> lastOneHour = userAccess.tailSet(currTimeAsInt-60, true);
            String str = lastOneHour.stream().map(String::valueOf).collect(Collectors.joining(", "));
            System.out.printf("Number of access in last one hour: %d at [%s]\n", lastOneHour.size(), str);

            if(lastOneHour.size() >= 3) {
                result.add(keyName[0]);
            }
        }
        return new ArrayList<>(result);
    }

    private int convertToTimePassed(String timeStr) {
        String[] arr = timeStr.split(":");
        return (60 * Integer.valueOf(arr[0])) + Integer.valueOf(arr[1]);
    }
    public class ListNode {
         int val;
         ListNode next;
         ListNode() {}
         ListNode(int val) { this.val = val; }
         ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }
    public ListNode reverseEvenLengthGroups(ListNode head) {
        return reverseEvenLengthGroups(head, 1, 1);
    }
    private ListNode reverseEvenLengthGroups(ListNode head, int groupNum, int expectedSize) {
        if(head == null) {
            return head;
        }
        ListNode current = head;
        ListNode prev = null;
        int size = 0;
        while(current != null && size < expectedSize) {
            prev = current;
            current = current.next;
            size += 1;
        }
        ListNode nextHead = reverseEvenLengthGroups(current, groupNum+1, expectedSize+1);
        if(size%2 == 0) {
            current = head;
            ListNode next = current.next;
            while(size-- > 1) {
                ListNode tmp = next != null ? next.next: null;
                next.next = current;
                current = next;
                next = tmp;
            }
            head.next = nextHead;
            return current;
        } else {
            prev.next = nextHead;
            return head;
        }
    }

    private void testDecoding() {
        String encoded = "iveo    eed   l te   olc";
        String decoded = decodeCiphertext(encoded, 4);
        System.out.printf("Decode: %s\n", decoded);
    }
    public String decodeCiphertext(String encodedText, int rows) {
        if(rows == 1 || encodedText == null || encodedText.length() <= 0) {
            return encodedText;
        }
        int len = encodedText.length();
        int columns = len/rows;
        StringBuilder sb = new StringBuilder();
        int row = 0;
        int col = 0;
        int nextIndex = row*columns + col;
        while(nextIndex < len) {
            sb.append(encodedText.charAt(nextIndex));
            row += 1;
            col += 1;
            if(row == rows) {
                row = 0;
                col -= rows;
                col += 1;
            }
            nextIndex = row*columns + col;
        }
        return ltrim(sb.toString());
    }

    private String ltrim(String str) {
        int i = str.length() - 1;
        while (Character.isSpaceChar(str.charAt(i))) {
            i -= 1;
        }
        return str.substring(0, i+1);
    }
}
