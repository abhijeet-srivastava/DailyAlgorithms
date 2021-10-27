package com.oracle.casb.CodeJam;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created By : abhijsri
 * Date  : 05/10/18
 **/
public class SampleCodality {


    public static void main(String[] args) {
        /*System.out.println("Contains " + Boolean.logicalXor(Boolean.TRUE, true));
        System.out.println("Not Contains " + Boolean.logicalXor(Boolean.TRUE, false));*/
        SampleCodality sc = new SampleCodality();
        sc.testSol();
        //sc.testLen();
    }

    private void testSol() {
        int[] A = {2,5,1};
        int[] B = {4,7,4};
        int[] res = solution(A, B, 7);
        System.out.println(Arrays.asList(res));
    }

    public int[] solution(int[] A, int[] B, int N) {
        // write your code in Java SE 8
        City[] distance = new City[N];
        for (int i = 0; i < N; i++) {
            distance[i] = new City(i+1, i);
        }
        int[] res = new int[A.length];
        for (int i = 0; i< A.length; i++) {
            int source = A[i] -1;
            int target = B[i] -1;
            distance[target].minDistance = Math.min(distance[target].minDistance ,
                    distance[source].minDistance + 1);
            distance[target].roadsEnding.add(i);
            for (int j = target+1; j < N; j++) {
                int currDistance = Math.min(distance[j].minDistance, distance[j-1].minDistance+1);
                for (int road : distance[j].roadsEnding) {
                    int newDistance = distance[A[road]-1].minDistance + 1;
                    currDistance = Math.min(currDistance, newDistance);
                }
                distance[j].minDistance = currDistance;
            }
            res[i] = distance[N-1].minDistance;
            /*distance[target] = Math.min(distance[target], distance[source] + 1);
            for (int j = target+1; j < N; j++) {
                distance[j] = Math.min(distance[j], distance[j-1] + 1);
            }
            res[i] = distance[N-1];*/
        }
        return res;
    }
    private class City {
        int name;
        int minDistance;
        Set<Integer> roadsEnding;

        public City(int name, int minDistance) {
            this.name = name;
            this.minDistance = minDistance;
            roadsEnding = new HashSet<>();
        }
    }
    private void testLen() {
        int[] array = {1,2,3};
        int res = solution(array);
        System.out.println(res);
    }


    public int solution(int[] A) {
        Arrays.sort(A);
        int num = 1;
        int i = 0;
        while (i < A.length && A[i] <= 0) {
            i += 1;
        }
        if (i < A.length) {
            for (; i < A.length; ) {
                if (A[i] == num) {
                    while(i < A.length && A[i] == num  ) {
                        i += 1;
                    }
                    num += 1;
                } else if (num < A[i]) {
                    break;
                }
            }
        }
        return num;
    }
}
