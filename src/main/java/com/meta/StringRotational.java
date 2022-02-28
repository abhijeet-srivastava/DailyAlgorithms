package com.meta;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StringRotational {
    public static void main(String[] args) {
        StringRotational sr = new StringRotational();
        //sr.testRotate();
        //sr.testContiguosArr();
        //sr.test2Sum();
        /*for(int i = 0; i < 10; i++) {
            char ch = Character.forDigit(i, 10);
            System.out.printf("As Char: %c\n", ch);
            int digit = Character.getNumericValue(ch);
            System.out.printf("As number: %d\n", digit);
        }*/
        //sr.testRotation();
        //sr.testMissingPosetiveNumber();
        //sr.testNextHighestNum();
        sr.testRemoveDuplicates();
    }

    private void testRemoveDuplicates() {
        //int[] nums = {0,0,1,1,1,1,2,3,3};
        int[] nums = {1,2,2,2,3,4,4,4,5};
        int res  = removeDuplicates2(nums);
        for(int i = 0; i < res; i++) {
            System.out.printf("%d ", nums[i]);
        }
        System.out.println();
    }

    private void testNextHighestNum() {
        String s = "61892795431";
        String st = nextHigest(s, 4);
        System.out.printf("%s ==> %s\n", s, st);
    }

    public int removeDuplicates(int[] nums) {
        int i = 0;
        for(int n : nums)
            if(i < 1 || n > nums[i - 1])
                nums[i++] = n;
        return i;
    }
    public int removeDuplicates2(int[] nums) {
        int i = 0;
        for(int num: nums) {
            if(i < 2 || num > nums[i-2]) {
                nums[i] = num;
                i += 1;
            }
        }
        return i;
    }
    private String nextHigest(String s, int k) {
        int[] nums = new int[s.length()];
        for(int i = 0; i < s.length(); i++) {
            nums[i] = Character.getNumericValue(s.charAt(i));
        }
        for(int i = 0; i < s.length(); i++) {
            int highest = i;
            for(int j = i; j < s.length(); j++) {
                if(nums[j] >= nums[highest]) {
                    highest = j;
                }
            }
            if(nums[i] >= nums[highest]) {
                continue;
            }
            swap(nums, i, highest);
            k -= 1;
            if(k == 0) {
                break;
            }
        }
        return IntStream.of(nums).mapToObj(String::valueOf).collect(Collectors.joining());
    }

    private void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

    private void testMissingPosetiveNumber() {
        int[] arr = {7,7,1,2,6,5, 4};
        int fp = firstMissingPositive(arr);
    }

    private void testRotation() {
        String[] deadends = {"0201","0101","0102","1212","2002"};
        String target = "0202";
        int trials = openLock(deadends, target);
    }

    private void test2Sum() {
        int[] arr = {1,5,3,3,3, 3, 3,2,4};
        int count = numberOfWays(arr, 6);
        System.out.printf("Count: %d\n", count);
    }

    private void testContiguosArr() {
        int[] arr = {3, 4, 1, 6, 2};
        int[] res = countSubarrays(arr);
        System.out.printf("%s\n", IntStream.of(res).mapToObj(Integer::valueOf).map(String::valueOf)
                .collect(Collectors.joining(", ", "[", "]")));
    }

    private void testRotate() {
        String input = "All-convoYs-9-be:Alert1.";
        int rotationFactor = 4;
        String rotated = rotationalCipher(input, rotationFactor);
        System.out.printf("After rotation: %s\n", rotated);
    }

    public int firstMissingPositive(int[] nums) {
        if(nums == null || nums.length == 0) return 1;          //case: nums == null or nums == [], return 1
        for(int i = 0;i < nums.length;i++){                     //use nums array itself, the ideal array should be {1,2,3,4}
            int curr = nums[i];                                 //swap if nums[index] != index + 1;
            while(curr - 1 >= 0 && curr - 1 < nums.length && curr != nums[curr-1]){
                int next = nums[curr-1];
                nums[curr-1] = curr;
                curr = next;
            }
        }
        for(int i = 0;i< nums.length;i++){                      //check if nums[index] == index + 1;
            if(nums[i] != i+1) return i+1;
        }
        return nums.length+1;                                   //corner case: {1,2,3,4} return 5
    }

    private String rotationalCipher(String input, int rotationFactor) {
        // Write your code here
        int alphRotational = rotationFactor%26;
        int intRotational = rotationFactor%10;
        StringBuilder sb = new StringBuilder();
        for(char ch: input.toCharArray()) {
            if(Character.isDigit(ch)) {
                int val = Character.digit(ch,10);
                val += intRotational;
                val %= 10;
                System.out.printf("Replace: %d with: %d\n", Character.digit(ch,10), val);
                sb.append(val);
            }else if(Character.isLetter(ch)) {
                int val = ch - (Character.isUpperCase(ch) ? 'A':'a');
                val += alphRotational;
                val %= 26;
                val += (Character.isUpperCase(ch) ? 'A':'a');
                System.out.printf("Replace: %c with: %c\n", ch, (char)val);
                sb.append((char)val);
            } else {
                sb.append(ch);
            }
        }
        return sb.toString();
    }

    private int[] countSubarrays(int[] arr) {
        // Write your code here
        int len = arr.length;
        int[] result = new int[arr.length];
        Stack<Integer> stack = new Stack<>();
        for(int i = 0; i < arr.length; i++) {
            while(!stack.isEmpty() && arr[stack.peek()] < arr[i]) {
                stack.pop();
            }
            if(stack.isEmpty()) {
                result[i] = (i + 1);
            } else {
                result[i] = i - stack.peek();
            }
            stack.push(i);
        }

        stack.clear();
        for(int i = len-1; i >= 0; i--) {
            while(!stack.isEmpty() && arr[stack.peek()] < arr[i]) {
                stack.pop();
            }
            if(stack.isEmpty()) {
                result[i] += (len-1 - i);
            } else {
                result[i] += (stack.peek() - i - 1);
            }
            stack.push(i);
        }
        return result;
    }
    private int numberOfWays(int[] arr, int k) {
        // Write your code here
        Arrays.sort(arr);
        int count = 0;
        int left = 0;
        int right = arr.length-1;
        while(left < right) {
            int l = arr[left];
            int r = arr[right];
            int sum = l+r;
            if(sum < k) {
                left += 1;
                continue;
            } else if(sum > k) {
                right -= 1;
                continue;
            }
            if(l == r) {
                int countSimilar = right - left + 1;
                count += getPermutations(countSimilar);
                break;
            } else  {
                int lCount = 0;
                int rCount = 0;
                while(l == arr[left]) {
                    lCount += 1;
                    left += 1;
                }
                while(r == arr[right]) {
                    rCount += 1;
                    right -= 1;
                }
                count += (lCount*rCount);
            }
        }
        return count;

    }

    private int getPermutations(int num) {
        int perm = 1;
        while(num > 2) {
            perm *= num;
            num -= 1;
        }
        return perm;
    }

    public int openLock(String[] deadends, String target) {
        int trials = 0;
        Set<String> deadEnds = Arrays.stream(deadends).collect(Collectors.toSet());
        String source = "0000";
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        queue.offer(source);
        visited.add(source);
        while(!queue.isEmpty()) {
            int size = queue.size();
            while(size-- > 0) {
                String current = queue.poll();
                if(current.equals(target)) {
                    return trials;
                }
                Set<String> nextCombinations = nextCombinations(current, deadEnds);
                for(String next : nextCombinations) {
                    if(!visited.contains(next)) {
                        queue.add(next);
                        visited.add(next);
                    }
                }
            }
            trials += 1;
        }
        return -1;
    }

    private Set<String> nextCombinations(String currentCombination, Set<String> deadEnds) {
        int[] comb = {1, -1};
        Set<String> combinations = new HashSet<>();
        char[] arr = currentCombination.toCharArray();
        for(int i = 0; i < 4; i++) {
            char current = arr[i];
            int val = Character.getNumericValue(current);
            for(int j: comb) {
                int next = (val + j + 10)%10;
                arr[i] = Character.forDigit(next, 10);
                String nextCombination = String.valueOf(arr);
                if(!deadEnds.contains(nextCombination))  {
                    combinations.add(nextCombination);
                }
            }
            arr[i] = current;
        }
        return combinations;
    }
    private int lengthOfLongestSubstring(String s) {
        if(s == null || s.length() == 0) {
            return 0;
        } else if(s.length() == 1) {
            return 1;
        }
        int maxLen = 1;
        int l = 0;
        Map<Character, Integer> indexMap = new HashMap<>();
        indexMap.put(s.charAt(0), 0);
        for(int r = 1; r < s.length(); r++) {
            char current = s.charAt(r);
            if (!indexMap.containsKey(current)) {
                //current char is not encountered earlier
                maxLen = Math.max(maxLen, r - l + 1);
            } else {
                //If last index of current, outside  window, we can increase window
                if(indexMap.get(current)  < l) {
                    maxLen = Math.max(maxLen, r - l + 1);
                } else {
                    //If last index of current, with in window
                    l = indexMap.get(current) + 1;
                }
            }
            indexMap.put(current, r);
        }
        return maxLen;
    }

    private int atMostKDistinct(int[] nums, int k)  {
        int left = 0;
        int right = 0;
        Map<Integer, Integer> countMap = new HashMap<>();
        int count = 0;
        while(right < nums.length) {
            countMap.put(nums[right], countMap.getOrDefault(nums[right], 0) + 1);
            while(countMap.size() > k) {
                //Reduce left window
                countMap.put(nums[left], countMap.get(nums[left]) - 1);
                if(countMap.get(nums[left]) == 0) {
                    countMap.remove(nums[left]);
                }
                left += 1;
            }
            count += (right-left+1);
            right += 1;
        }
        return count;
    }
}
