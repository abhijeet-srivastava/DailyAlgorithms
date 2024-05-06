package com.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class InversionCount {

    public static void main(String[] args) throws IOException {
        InversionCount ic = new InversionCount();
        //ic.testInversionCountUsingMergeSort();
        ic.testInversionCountFileStream();
        //ic.testCountSmaller();
    }

    private void testInversionCountFileStream() throws IOException {
        InputStream stream  = new FileInputStream(new File("/Users/abhijeetsrivastava/workspace/DailyAlgorithms/src/main/resources/InversionControlInput.txt"));
        testInversionCount(stream);
    }

    private void testInversionCount(InputStream stream) throws IOException {
        //InputReader ir = new InputReader(stream);
        InputReader ir = new InputReader(System.in);
        int tc = ir.nextInt();
        for(int ti = 0; ti < tc; ti++) {
            ir.nextLine();
            int size = ir.nextInt();
            int[] arr = new int[size];
            for(int i =0; i < size;i++) {
                arr[i] = ir.nextInt();
            }
            int ic = inversionCount(arr);
            System.out.printf("%d\n", ic);
        }
    }

    private void testCountSmaller() {
        int[] nums = {5,2,6,1};
        List<Integer> res = countSmaller(nums);
    }

    public List<Integer> countSmaller(int[] nums) {
        int n = nums.length;
        int[][] arr = new int[n][2];
        for(int i = 0; i < n; i++) {
            arr[i] = new int[]{i, nums[i]};
        }
        int[] res = new int[n];
        //List<Integer> res = new ArrayList<>();
        mergeSort(0, n-1, arr, res);
        return Arrays.stream(res).boxed().peek(System.out::println).collect(Collectors.toList());
        //return IntStream.of(res).boxed().collect(Collectors.toList());
    }
    private void mergeSort(int l, int r, int[][] arr, int[] res) {
        if(l >= r) {
            return;
        }
        int mid = l + (r-l)/2;
        mergeSort(l, mid, arr, res);
        mergeSort(mid+1, r, arr, res);

        int[][] tmp = new int[r-l+1][3];
        int i = l, j = mid+1, idx = 0;
        int numElemsRightArrayLessThanLeftArray = 0;
        while(i <= mid && j <= r) {
            if(arr[i][1] <= arr[j][1]) {
                res[arr[i][0]] = numElemsRightArrayLessThanLeftArray;
                tmp[idx++] = arr[i++];
            } else {
                //Inversions = mid-i+1
                numElemsRightArrayLessThanLeftArray += 1;
                tmp[idx++] = arr[j++];
            }
        }
        while(i <= mid) {
            tmp[idx++] = arr[i++];
        }
        while(j <= r) {
            tmp[idx++] = arr[j++];
        }
        idx = 0;
        while(idx <= r-l) {
            arr[l+idx] = tmp[idx];
            idx += 1;
        }
    }

    private void testInversionCountUsingMergeSort() {
        int[] arr ={ 4, 1 , 5 , 2 , 3, 7, 8};
        int ic = inversionCount(arr);
        System.out.printf("Inversions: %d\n", ic);
    }

    private int inversionCount(int[] arr) {
        int l = 0, r = arr.length-1;
        int[] ic = {0};
        mergeSort(l, r, arr, ic);
        return ic[0];
    }

    private void mergeSort(int l, int r, int[] arr, int[] ic) {
        if(l >= r) {
            return;
        }
        int mid = l + (r-l)/2;
        mergeSort(l, mid, arr, ic);
        mergeSort(mid+1, r, arr, ic);
        merge(l, mid, r, arr, ic);
    }

    private void merge(int l, int mid, int r, int[] arr, int[] ic) {
        int[] tmp = new int[r-l+1];
        int i = l, j = mid+1, idx = 0;
        while(i <= mid && j <= r) {
            if(arr[i] <= arr[j]) {
                tmp[idx++] = arr[i++];
            } else {
                //Inversion
                ic[0] += (mid-i+1);
                tmp[idx++] = arr[j++];
            }
        }
        while(i <= mid) {
            tmp[idx++] = arr[i++];
        }
        while(j <= r) {
            tmp[idx++] = arr[j++];
        }
        idx = 0;
        while(idx <= r-l) {
            arr[l+idx] = tmp[idx];
            idx += 1;
        }
    }


}
