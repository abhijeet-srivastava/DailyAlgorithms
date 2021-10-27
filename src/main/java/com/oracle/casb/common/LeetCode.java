package com.oracle.casb.common;

import com.oracle.casb.ListUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created By : abhijsri
 * Date  : 18/05/18
 **/
public class LeetCode {

    public static void main(String[] args) {
        LeetCode lc = new LeetCode();
        //lc.addLists();
        //lc.simplifyPath("/a/./b///../c/../././../d/..//../e/./f/./g/././//.//h///././/..///");
        lc.simplifyPath("/a/./b/../../c/");
    }

    private void addLists() {
        /*ListNode first = ListUtils.createRandomList();
        ListNode second = ListUtils.createRandomList();*/
        ListNode first = ListUtils.createGivenList(new int[]{2, 6, 2, 0, 5, 3, 4, 4, 4, 9});
        ListNode second = ListUtils.createGivenList(new int[]{1,1,6,7});
        ListNode sumList = sumLists(first, second);
    }

    private ListNode sumLists(ListNode first, ListNode second) {
        int len1 = ListUtils.getLength(first);
        int len2 = ListUtils.getLength(second);
        ListNode smaller, longer;
        int diff = 0;
        if (len1 > len2) {
            longer = first;
            smaller = second;
            diff = len1 - len2;
        } else {
            longer = second;
            smaller = first;
            diff = len2 - len1;
        }
        ListNode result = null;
        Object[] isCarry
                = sumLists(smaller, longer, diff, 0);
        if (Boolean.valueOf(isCarry[0].toString())) {
            ListNode temp = new ListNode(1);
            temp.setNext((ListNode) isCarry[1]);
            result = temp;
        } else {
            result = (ListNode) isCarry[1];
        }
        return result;
    }

    private Object[] sumLists(ListNode smaller, ListNode longer, int diff, int covered) {
        Object[] retVal = new Object[2];
        if (smaller == null || longer == null) {
            return new Object[]{"false", null};
        }
        ListNode result = null;
        Boolean isCarry = false;
        if (covered < diff) {
            int value = longer.getValue();
            Object[] valRes = sumLists(smaller, longer.getNext(), diff, covered+1);
            isCarry = Boolean.valueOf(valRes[0].toString());
            if (isCarry) {
                value = longer.getValue() + 1;
                if (value >= 10) {
                    value -= 10;
                } else  {
                    isCarry = false;
                }
            }
            result = new ListNode(value);
            result.setNext((ListNode) valRes[1]);
        } else {
            Object[] valRes  = sumLists(smaller.getNext(), longer.getNext(), diff, covered+1);
            int value = smaller.getValue() + longer.getValue();
            isCarry = Boolean.valueOf(valRes[0].toString());
            if (isCarry) {
                value += 1;
            }
            if (value >= 10) {
                value -= 10;
                isCarry = true;
            } else  {
                isCarry = false;
            }
            result = new ListNode(value);
            result.setNext((ListNode) valRes[1]);
        }
        return new Object[] {isCarry.toString(), result};
    }


    public String simplifyPath(String path) {
        StringBuilder sb = new StringBuilder();
        String[] array = path.split("/");
        List<String> list = new ArrayList<>();
        int index = -1;
        for (int i = 0; i < array.length; i++) {
            String str = array[i].trim();
            if (str.isEmpty()) {
                continue;
            } else if (str.equals(".")) {
                continue;
            } else if (str.equals("..")) {
                index -= 1;
            } else {
                if (index <= -1) {
                    list.clear();
                    index = -1;
                }
                list.add(++index, str);
            }
        }
        for (int i = 0; i <= index ; i++) {
            sb.append("/");
            sb.append(list.get(i));
        }
        if (sb.length() == 0) {
            sb.append("/");
        }
        return sb.toString();
    }

}
