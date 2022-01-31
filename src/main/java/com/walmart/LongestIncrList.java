package com.walmart;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LongestIncrList {

    public static void main(String[] args) {
        LongestIncrList lil = new LongestIncrList();
        //lil.testCiel();

        //String res = lil.customSortString("exv", "xwvee");
        //System.out.println(res);
        //int[] nums = {1,1};
        //List<Integer> res = lil.maxScoreIndices(nums);
        String str = lil.subStrHash("leetcode", 7, 20, 2, 0);
    }

    public String subStrHash(String s, int power, int mod, int k, int hashValue) {
        int hv = 0;
        int pow = 1;
        int len = s.length();
        int[] arr = new int[len-k];
        for(int i = 0; i < k; i++) {
            int val = s.charAt(len-i-1) - 'a' + 1;
            hv = ((hv*power)%mod + val)%mod;
            pow *= power;
        }
        pow /= power;
        pow %= mod;
        arr[len - k - 1] = hv;
        for(int i = len-k-2; i >= 0; i--) {
            int oow = s.charAt(i+k+1) - 'a' + 1;
            hv = (hv - oow*pow)%mod;
            hv += (s.charAt(i) - 'a' + 1);
            arr[i] = hv;
        }
        for(int i = 0; i < arr.length; i++) {
            if(arr[i] == hashValue) {
                return s.substring(i, i+k);
            }
        }
        return "";
    }
    public List<Integer> maxScoreIndices(int[] nums) {
        int[] arr = new int[nums.length+1];
        for(int i = 1; i <= nums.length; i++) {
            int val = (nums[i-1] == 0) ? 1 : 0 ;
            arr[i] = arr[i-1] + val;
        }
        int[] right = new int[nums.length+1];
        for(int i = nums.length-1; i >= 0; i--) {
            int val = nums[i] == 1 ? 1:0;
            right[i] = right[i+1] + val;
        }
        for(int i = 0; i <= nums.length; i++) {
            arr[i] += right[i];
        }
        int max = IntStream.of(arr).max().getAsInt();
        List<Integer> res = new ArrayList<>();
        for(int i = 0; i < arr.length; i++) {
            if(arr[i] == max) {
                res.add(i);
            }
        }
        return res;
    }
    private void testCiel() {
        int arr[] = { 2, 5, 3, 7, 11, 8, 10, 13, 6 };
        //int arr[] = {0, 8, 4, 12, 2, 10, 6, 14, 1, 9, 5, 13, 3, 11, 7, 15};
        //int lis = longestIncreasingSeq(arr);
        int lis = lds(arr);
        System.out.printf("LIS: %d\n", lis);
        /*int arr[] = { 2, 3,5, 7,8, 10,11, 13};
        int i = 1;
        int cielInd = ceilIndex(arr,-1, arr.length-1, i);
        System.out.printf("Ciel index of %d: %d; Ciel Element %d\n", i, cielInd, arr[cielInd]);
        i = 6;
        cielInd = ceilIndex(arr,-1, arr.length-1, i);
        System.out.printf("Ciel index of %d: %d; Ciel Element %d\n", i, cielInd, arr[cielInd]);
        i = 9;
        cielInd = ceilIndex(arr,-1, arr.length-1, i);
        System.out.printf("Ciel index of %d: %d; Ciel Element %d\n", i, cielInd, arr[cielInd]);
        i = 11;
        cielInd = ceilIndex(arr,-1, arr.length-1, i);
        System.out.printf("Ciel index of %d: %d; Ciel Element %d\n", i, cielInd, arr[cielInd]);

        i = 15;
        int floorIndex = floorIndex(arr,0, arr.length, i);
        System.out.printf("Floor index of %d: %d - Floor Element %d\n", i, floorIndex, arr[floorIndex]);

        i = 9;
        floorIndex = floorIndex(arr,0, arr.length, i);
        System.out.printf("Floor index of %d: %d - Floor Element %d\n", i, floorIndex, arr[floorIndex]);

        i = 6;
        floorIndex = floorIndex(arr,0, arr.length, i);
        System.out.printf("Floor index of %d: %d - Floor Element %d\n", i, floorIndex, arr[floorIndex]);*/
    }
    private int lis(int[] arr) {
        int[] DP = new int[arr.length];
        int len = 0;
        for(int i : arr) {
            int index = Arrays.binarySearch(DP,  0, len, i);
            if(index < 0) {
                index = -(index + 1);
            }
            DP[index] = i;
            if(index == len) {
                len += 1;
            }
        }
        return len;
    }

    private int lds(int[] nums) {
        int len = 0;
        int[] DP = new int[nums.length];
        for(int num : nums) {
            int index = insertionPoint(DP, 0, len, num);
            DP[index] = num;
            if(index == len) {
                len += 1;
            }
        }
        String lis = IntStream.of(DP).mapToObj(Integer::valueOf)
                .map(String::valueOf).collect(Collectors.joining(", "));
        return len;
    }

    private int insertionPoint(int[] arr, int left, int right, int key) {
        //9, 7, 5, 3 , 4
        while(right - left > 1) {
            int mid = left + (right - left)/2;
            if(arr[mid] > key) {
                left = mid;
            } else {
                right = mid;
            }
        }
        return right;
    }

    private int longestIncreasingSeq(int[] arr) {
        int len = 1;
        int[] tt = new int[arr.length];
        tt[0] = arr[0];
        for(int i =1; i < arr.length; i++) {
            if(arr[i] < tt[0]) {
                tt[0] = arr[i];
            } else if(arr[i] > tt[len-1]) {
                tt[len] = arr[i];
                len += 1;
            } else {
                int cielIndex = ceilIndex(tt, -1, len-1, arr[i]);
                tt[cielIndex] = arr[i];
            }
        }
        return len;
    }


    private int ceilIndex(int[] arr, int left, int right, int key) {
        while((right - left) > 1) {
            int mid = left + (right-left)/2;
            if(arr[mid] < key) {
                left = mid;
            } else {
                right = mid;
            }
        }
        return right;
    }

    private int floorIndex(int[] arr, int left, int right, int key) {
        while ((right - left) > 1) {
            int mid = left + (right-left)/2;
            if(arr[mid] > key) {
                right = mid;
            } else {
                left = mid;
            }
        }
        return left;
    }

    public String customSortString(String order, String str) {
        Map<Character, Integer> ordering = new HashMap<>();
        int i = 1;
        for(char ch : order.toCharArray()) {
            ordering.put(ch, i++);
        }
        Character[] array = new Character[str.length()];
        i = 0;
        for(char ch : str.toCharArray()) {
            array[i++] = ch;
        }
        Arrays.sort(array,
                (a,b) -> comparator(a, b, ordering));
        return Arrays.stream(array)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    private int comparator(Character a, Character b, Map<Character, Integer> ordering) {
        int val = 0;
        if(ordering.containsKey(a) && ordering.containsKey(b)) {
            val = ordering.get(a) - ordering.get(b);
        } else if(ordering.containsKey(a)) {
            val = 1;
        } else if(ordering.containsKey(b)) {
            val = -1;
        }
        return val;
    }
}

