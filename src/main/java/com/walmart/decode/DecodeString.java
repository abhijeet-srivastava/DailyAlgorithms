package com.walmart.decode;

import java.util.*;

public class DecodeString {

    public static void main(String[] args) {
        DecodeString ds = new DecodeString();
        //ds.solve();
        ds.solveMinDiff();
    }

    private void solveMinDiff() {
        int[] arr = {4,2,1,3};
        List<List<Integer>> res = minimumAbsDifference(arr);
        System.out.printf("res\n");
    }

    private void solve() {
       String encoded =  "3[a]2[bc]";
       String decoded = decodeString(encoded);
        System.out.printf("Decode : %s\n", decoded);
    }

    public String decodeString(String s) {
        Stack<String> stack = new Stack<>();
        int i = s.length();
        while(i-- > 0) {
            char ch = s.charAt(i);
            if(ch == '[') {
                String str = "";
                while(!stack.peek().equals("]"))  {
                    str += stack.pop();
                }
                stack.pop();
                stack.push(str);
            } else if(Character.isDigit(ch)) {
                int exp = 1;
                int val = 0;
                while(i >= 0 && Character.isDigit(ch)) {
                    val += (exp * Character.getNumericValue(ch));
                    i-= 1;
                    if(i < 0) {
                        break;
                    }
                    ch = s.charAt(i);
                    exp *= 10;
                }
                i+= 1;
                String str = stack.pop();
                stack.push(repeat(str, val));
            } else {
                stack.push(String.valueOf(ch));
            }
        }
        StringBuilder sb = new StringBuilder();
        while(!stack.isEmpty()) {
            sb.append(stack.pop());
        }
        return sb.toString();
    }

    private String repeat(String s, int n) {
        StringBuilder sb = new StringBuilder();
        while(n-- > 0) {
            sb.append(s);
        }
        return sb.toString();
    }

    public List<List<Integer>> minimumAbsDifference(int[] arr) {
        Arrays.sort(arr);
        int minDiff = arr[arr.length-1] - arr[0];
        for(int i = 1; i < arr.length; i++) {
            minDiff = Math.min(minDiff, arr[i] - arr[i-1]);
        }
        List<List<Integer>> result = new ArrayList<>();
        for(int i = 1; i < arr.length; i++) {
            if((arr[i] - arr[i-1]) == minDiff) {
                List<Integer> res = new ArrayList<>();
                res.add(arr[i-1]);
                res.add(arr[i]);
                result.add(res);
            }
        }
        return result;
    }
    public List<Integer> findMinHeightTrees(int n, int[][] edges) {
        if(n == 1) {
            return Collections.singletonList(0);
        }
        List<Set<Integer>> GRAPH = new ArrayList<>();
        for(int i = 0; i < n; i++) {
            GRAPH.add(i, new HashSet<>());
        }
        for(int[] edge : edges) {
            GRAPH.get(edge[0]).add(edge[1]);
            GRAPH.get(edge[1]).add(edge[0]);
        }
        List<Integer> leaves = new ArrayList<>();
        for(int i = 0; i < n; i++) {
            if(GRAPH.get(i).size() == 1) {
                leaves.add(i);
            }
        }
        while (n > 2) {
            n -= leaves.size();
            List<Integer> newLeaves = new ArrayList<>();
            for(int leave : leaves) {
                System.out.printf("Processing leave: %d\n", leave);
                int neighbour = GRAPH.get(leave).iterator().next();
                GRAPH.get(neighbour).remove(leave);
                if(GRAPH.get(neighbour).size() == 1) {
                    System.out.printf("Adding  new leave: %d\n", neighbour);
                    newLeaves.add(neighbour);
                }
            }
            leaves = newLeaves;
        }
        return leaves;
    }
    public boolean isPowerOfTwo(int n) {
        while(((n & 1) == 0) && (n > 1)) {
            n >>= 1;
        }
        return n == 1;
        //return ((n != 0) && ((n & (n-1)) == 0));
    }
}
