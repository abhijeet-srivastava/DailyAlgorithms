package com.oracle.casb.leetcode;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class StreamChecker {

    Map<Character, Node> tries;

    public StreamChecker() {
        tries = new HashMap<>();
    }

    public static void main(String[] args) {
        StreamChecker stremaChecker = new StreamChecker();
        stremaChecker.buildStream(new String[] {"cap", "captain", "capital"});
    }

    private void buildStream(String[] strings) {
        for (String str: strings) {
            if (tries.get(str.charAt(0)) == null) {
                tries.put(str.charAt(0), new Node(str));
            } else {
                tries.get(str.charAt(0)).mergeString(str);
            }
        }
        System.out.printf("end");
    }


    private class Node {
        Character ch;
        boolean idEnd;
        Map<Character, Node> nextChars;


        public Node(char[] chArr, int index) {
            if (index != chArr.length) {
                this.ch = chArr[index];
                this.idEnd = this.idEnd || index == chArr.length - 1;
                if (index < chArr.length - 1) {
                    if (nextChars == null) {
                        nextChars = new HashMap<>();
                    }
                    if(nextChars.get(chArr[index + 1]) == null) {
                        nextChars.put(chArr[index + 1], new Node(chArr, index+1));
                    }
                }
            }
        }

        public Node(String str) {
            this(str.toCharArray(), 0);
            char[] arr = str.toCharArray();
            this.ch = arr[0];
            this.idEnd = this.idEnd || arr.length == 1;
            this.nextChars = new HashMap<>();
        }

        public void mergeString(String str) {
            char[] array = str.toCharArray();
            mergeString(array, 0);
        }

        private void mergeString(char[] array, int index) {
            if(this.ch != array[index]) {
                throw new RuntimeException();
            }
            Node next = nextChars.get(array[index+1]);
            if(next == null) {
                nextChars.put(array[index+1], new Node(array, index+1));
            } else {
                next.mergeString(array, index+1);
            }
        }
    }
}
