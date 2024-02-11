package com.denero.challenge;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class GenericStream {

    public double findMedianSortedArrays1(int[] nums1, int[] nums2) {
        if(nums1.length > nums2.length) {
            findMedianSortedArrays1(nums2, nums1);
        }
        int m = nums1.length, n = nums2.length;
        int total = (m+n+1) >> 1;
        boolean isOdd = (m+n)%2 == 0;
        int left = 0, right = m;
        while (left <= right) {
            int pa = left + ((right-left)>>1);
            int pb = total - pa;
            int maxLeftA = pa == 0 ? Integer.MIN_VALUE : nums1[pa-1];
            int minRightA = pa == m ? Integer.MAX_VALUE: nums1[pa];
            int maxLeftB = pb == 0 ? Integer.MIN_VALUE: nums2[pb-1];
            int minRightB = pb == n ? Integer.MAX_VALUE : nums2[pb];
            if(maxLeftA <= minRightB && maxLeftB <= minRightA) {
                if(isOdd) {
                    return 0.5* (Math.max(maxLeftA, maxLeftB) + Math.min(minRightA, minRightB));
                } else {
                    return Math.max(maxLeftA, maxLeftB);
                }
            } else if(maxLeftA > minRightB) {
                right = pa-1;
            } else {
                left = pa+1;
            }
        }
        return 0.0d;
    }
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length, n = nums2.length;
        int total = m+n;
        int k = total >> 1;
        if((k << 1) == total) {
            double val1 = find(k, 0, m-1, nums1, 0, n-1, nums2);
            double val2 = find(k-1, 0, m-1, nums1, 0, n-1, nums2);
            return 0.5*(val1+val2);
        } else {
            return find(k, 0, m-1, nums1, 0, n-1, nums2);
        }
    }

    private double find(int k, int l1, int r1, int[] A, int l2, int r2, int[] B) {
        if(l1 > r1) {
            return B[k-l1];
        } else if(l2 > r2) {
            return A[k-l2];
        }
        int m1 = (l1+r1)>>1, m2 = (l2+r2)>>1;
        int total = m1+m2;
        if(total < k) {//Remove first smaller half
            if(A[m1] < B[m2]) {//Remove first smaller half from A
                return find(k, m1+1, r1, A, l2, r2, B);
            } else {//Remove first smaller half from B
                return find(k, l1, r1, A, m2+1, r2, B);
            }
        } else {//Remove second Bigger half
            if(A[m1] < B[m2]) {//Remove bigger half from B
                return find(k, l1, r1, A, l2, m2-1, B);
            } else {//Remove bigger half from A
                return find(k, l1, m1-1, A, l2, r2, B);
            }
        }
    }


    public static void main(String[] args) {
        List<String> list1 = GenericStream.get(new ArrayList<>(), new String("1"));
        List<Object> list2 = GenericStream.get("Homer", Double.valueOf("4"));
        Stream<Object> concat = Stream.concat(list1.stream(), list2.stream());
        concat.forEach(System.out::print);
    }

    public static <T> List<T> get(List<T> list, T element) {
        list.add(element);
        return list;
    }
    public static <T, R extends T> List<T> get(T element1, R element2) {
        List<T> list = new ArrayList<>();
        list.add(element1);
        list.add(element2);
        return list;
    }

}
