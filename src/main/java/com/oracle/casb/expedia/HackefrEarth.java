package com.oracle.casb.expedia;

import java.util.*;
import java.util.stream.Collectors;

public class HackefrEarth {
    Map<Integer, Set<Character>> dict;

    public HackefrEarth() {
        dict = new HashMap<>();
        char ch = 'a';
        for (int i = 1; i < 10; i++) {
            Set<Character> set = new HashSet<>();
            if (i > 1) {
                for (int j = 0; j < 3; j++) {
                    ch += j;
                    set.add(Character.valueOf(ch));
                }
            }
            dict.put(Integer.valueOf(i), set);
        }
    }

    public static void main(String[] args) throws Exception {
        HackefrEarth he = new HackefrEarth();
        //he.testMaxlen();
        //he.testShiftedSortedSearch();
       // he.testLongestLexico();
        //he.testQuesries();
        he.testVertical();
    }

    private void testVertical() {
        TreeNode tree = createTree();
        List<List<Integer>> val = verticalTraversal(tree);
        val.stream().forEach(
                e -> System.out.println(e.stream().map(String::valueOf).collect(Collectors.joining(",")))
        );
    }

    private void testQuesries() {
        int[] A = {5,5,4};
        int[][] queries = {{0,1}, {1,0}, {4,1}};
        int[] res = sumEvenAfterQueries(A, queries);
        Arrays.stream(res).forEach(
                e -> System.out.println(e)
        );
    }


    public List<String> letterCombinations(String digits) {
        List<String> result = new ArrayList<>();
        if (digits.length() == 0) {
            return result;
        }
        for(Character ch : digits.toCharArray()) {
            result = appendAll(result, ch);
        }
        return result;
    }

    private List<String> appendAll(List<String> result, Character digit) {
        List<String> list = new ArrayList<>();
        for (String st : result ) {
            for(Character ch : dict.get(Character.getNumericValue(digit))) {
                list.add(st + ch);
            }
        }
        return list;
    }

    private void testShiftedSortedSearch() {
        int[] arr = {2,5,6,0,0,1,2};
        System.out.println(search(arr, 0));
    }

    public int search(int[] nums, int target) {
        if (nums.length == 0) {
            return -1;
        }
        int lo = 0;
        int hi = nums.length - 1;

        if (nums[lo] == target) {
            return lo;
        } else if (nums[hi] == target) {
            return hi;
        }else if (lo == hi ) {
            return nums[lo] == target ? lo : -1;
        }
        return search(nums, target, lo, hi);
    }

    public int search(int[] nums, int target, int lo, int hi) {
        if (lo >= hi) {
            return -1;
        }
        int mid = lo + ((hi - lo)/2);
        if (nums[mid] == target) {
            return mid;
        }
        if (nums[mid] > nums[lo]) {
            if (target >= nums[lo] && target <= nums[mid]) {
                return binarySearch(nums, target, lo, mid);
            } else {
                return search(nums, target, mid+1, hi);
            }
        } else {
            if (target >= nums[mid] && target <= nums[hi]) {
                return binarySearch(nums, target, mid + 1, hi);
            } else {
                return search(nums, target, lo, mid);
            }
        }
    }

    public int binarySearch(int[] nums, int target, int lo, int hi) {
        if (lo >= hi) {
            return -1;
        }
        int mid = lo + ((hi - lo)/2);
        if (nums[mid] == target) {
            return mid;
        } else if (nums[mid] < target) {
            return binarySearch(nums, target, mid+1, hi);
        } else {
            return binarySearch(nums, target, lo, mid);
        }
    }

    private void testMaxlen() {
        int[] arr = {2,9,4,6,18};
        System.out.println(getMax(arr, 3));
    }

    private int getMax(int[] arr, int k) {
        int maxLen = 0;
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : arr) {
            int len = 1;
            if (num % k == 0) {
                int div = num/k;
                len += map.getOrDefault(div, 0);
                maxLen = Math.max(maxLen, len);
            }
            map.put(num, len);
        }
        return maxLen;
    }

    private TreeNode createTree() {
        TreeNode root = new TreeNode(3);
        TreeNode left = new TreeNode(9);
        root.left = left;
        TreeNode right = new TreeNode(20);
        right.left = new TreeNode(15);
        right.right = new TreeNode(7);
        root.right = right;
        return root;
    }

    public void testLongestLexico() {
        TreeNode root = createTree();
        System.out.println(smallestFromLeaf(root));
    }



    public String smallestFromLeaf(TreeNode root) {
        StringBuilder  sb = new StringBuilder();
        smallestFromLeaf(root, sb);

        return sb.toString();

    }

    private void smallestFromLeaf(TreeNode root, StringBuilder sb) {
        if (root == null) {
            return;
        }
        if (root.left == null && root.right == null) {
            sb.append((char) ('a' + root.val));
            return;
        }
        StringBuilder sbLeft = new StringBuilder();
        StringBuilder sbRight = new StringBuilder();
        smallestFromLeaf(root.left, sbLeft);
        smallestFromLeaf(root.right, sbRight);
        if (leftIsGreater(sbLeft, sbRight))  {
            sb.append(sbRight);
        } else {
            sb.append(sbLeft);
        }
        sb.append((char) ('a' + root.val));
        return;
    }

    private boolean leftIsGreater(StringBuilder sbLeft, StringBuilder sbRight) {
        if (sbLeft.length() ==  0 && sbRight.length() > 0) {
            return true;
        } else if (sbLeft.length() >  0 && sbRight.length() == 0) {
            return false;
        }

        return sbLeft.toString().compareTo(sbRight.toString()) > 0;
    }

    public int[] sumEvenAfterQueries(int[] A, int[][] queries) {
        boolean[] map = new boolean[A.length];
        int[] res = new int[queries.length];
        int evenSum = 0;
        for (int i = 0; i < A.length; i++) {
            map[i] = isEven(A[i]);
            if (map[i]) {
                evenSum += A[i];
            }
        }
        for (int i = 0; i < queries.length; i++) {
            if (map[queries[i][1]] && isEven(queries[i][0])) {
                evenSum += queries[i][0];
            } else if (map[queries[i][1]] && !isEven(queries[i][0])) {
                map[queries[i][1]] = false;
                evenSum -= A[queries[i][1]];
            } else if (!map[queries[i][1]] && !isEven(queries[i][0])) {
                map[queries[i][1]] = true;
                evenSum += (A[queries[i][1]] + queries[i][0]);
            }
            A[queries[i][1]] += queries[i][0];
            res[i] = evenSum;
        }
        return res;
    }
    private boolean isEven(int num) {
        return (num & 1) == 0;
    }

    private void rotate(int[] arr, int position) {
        reverseSubArray(arr, 0, arr.length);
        reverseSubArray(arr, 0, position);
        reverseSubArray(arr, position+1, arr.length);
    }

    private void reverseSubArray(int[] arr, int start, int end) {
        if (start < 0 || end > arr.length) {
            return;
        }
        for (int i = start, j = end - 1; i < j; i++, j--) {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }

    public List<List<Integer>> verticalTraversal(TreeNode root) {
        List<List<Integer>> result = new ArrayList();
        if (root == null) {
            return result;
        }
        Map<Integer, List<Integer>> map = new TreeMap<>();
        Map<TreeNode, Integer> parents = new LinkedHashMap<>();
        parents.put(root, 0);
        bfs(parents, map);
        return new ArrayList<>(map.values());
    }

    private void bfs(Map<TreeNode, Integer> parents, Map<Integer, List<Integer>> map) {
        if (parents.isEmpty()) {
            return;
        }
        Map<TreeNode, Integer> child = new LinkedHashMap<>();
        for(Map.Entry<TreeNode, Integer> entry : parents.entrySet()) {
            int level = entry.getValue();
            TreeNode root = entry.getKey();
            if (map.get(level) == null) {
                List<Integer> list = new ArrayList<>();
                list.add(root.val);
                map.put(level, list);
            } else {
                map.get(level).add(root.val);
            }
            if (root.left != null) {
                child.put(root.left, level-1);
            }
            if (root.right != null) {
                child.put(root.right, level + 1);
            }
        }
        bfs(child, map);
    }

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    public class Interval {
      int start;
      int end;
      Interval() { start = 0; end = 0; }
      Interval(int s, int e) { start = s; end = e; }
  }

}