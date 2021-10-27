package com.oracle.casb.CodeJam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Created By : abhijsri
 * Date  : 10/09/18
 **/
public class Contest101 {

    public static void main(String[] args) {
        Contest101 contest = new Contest101();
        //contest.testRelInit();
        contest.testRel();
    }

    private void testRel() {
        RLEIterator re = new RLEIterator(new int[]{3,8,0,9,2,5});
        System.out.println(re.next(2));
        System.out.println(re.next(1));
        System.out.println(re.next(1));
        System.out.println(re.next(2));
    }

    private void testRelInit() {
        int[] A = {3,8,0,9,2,5};
        /*int length = IntStream.range(0, A.length).filter(n -> n%2 == 0).map(e -> A[e]).sum();
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < A.length - 1; i += 2) {
            for (int j = 0; j < A[i]; j++) {
                list.add(A[i+1]);
            }
        }
        Integer[] array = new Integer[list.size()];
        array = list.toArray(array);*/
        List<Entry> list = new ArrayList<>();
        //Entry[] elemArray = new Entry[A.length/2];
        int start = 0;
        for (int i = 0; i < A.length - 1; i += 2) {
            if (A[i] != 0) {
                list.add(new Entry(start, start + A[i] - 1, A[i+1]));
            }
            start += A[i];
        }
        System.out.println(list);
     }

     private static int START = -1;
     private static int CURR = 0;

     public int next(int n, Entry[] array) {
        int ret = -1;
        int count = START + n;
        int traversed = 0;
        while (CURR < array.length) {
            if (array[CURR].lies(n)) {
                START += n;
                ret = array[CURR].num;
                break;
            } else {
                START += array[CURR].size();
                n -= array[CURR].size();
                CURR += 1;
            }
        }
        return ret;
     }

     class Entry {
        private int start;
        private int end;
        private int num;

         public Entry(int start, int end, int num) {
             this.start = start;
             this.end = end;
             this.num = num;
         }

         public boolean lies(int num) {
             return num >= start && num <= end;
         }

         public int size() {
             return (end - start) + 1;
         }

         public int getStart() {
             return start;
         }

         public int getEnd() {
             return end;
         }

         public int getNum() {
             return num;
         }
     }

}
