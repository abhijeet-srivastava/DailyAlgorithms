package com.leetcode.mothly.june24;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Solution {
    public static void main(String[] args) {
        Solution sol = new Solution();
        //sol.test20June();
        //sol.testTextJustify();
        //sol.testCreateBouquet();
        //sol.testNumSubArrays();
        sol.testAncestors();
    }

    private void testAncestors() {
        int n = 8;
        int[][] edges = {{0,3},{0,4},{1,3},{2,4},{2,7},{3,5},{3,6},{3,7},{4,6}};
        List<List<Integer>> res = getAncestors(n, edges);
        for(int i = 0; i < n; i++) {
            System.out.printf("%d = [%s]\n", i, res.get(i).stream().map(String::valueOf).collect(Collectors.joining(",")));
        }
    }
    public List<List<Integer>> getAncestors(int n, int[][] edges) {
        Set[] GRAPH = new Set[n];
        List<List<Integer>> res = new ArrayList<>();
        for(int i = 0; i < n; i++) {
            GRAPH[i] = new HashSet<Integer>();
            res.add(new ArrayList<>());
        }
        for(int[] edge: edges) {
            GRAPH[edge[0]].add(edge[1]);
        }
        for(int i = 0; i < n; i++) {
            dfsAncestors(i,  res, i, GRAPH);
        }
        return res;
    }
    private void dfsAncestors(int root, List<List<Integer>> res, int curr, Set[] GRAPH) {
        Set<Integer> childrens = GRAPH[curr];
        for(int child: childrens) {
            if(res.get(child).isEmpty()
                    || res.get(child).get(res.get(child).size()-1) != root) {
                res.get(child).add(root);
                dfsAncestors(root, res, child, GRAPH);
            }
        }
    }

    public List<List<Integer>> getAncestors1(int n, int[][] edges) {
        Set[] GRAPH = new Set[n];
        List<Set<Integer>> res = new ArrayList<>();
        for(int i = 0; i < n; i++) {
            GRAPH[i] = new HashSet<Integer>();
            res.add(new HashSet<>());
        }
        for(int[] edge: edges) {
            GRAPH[edge[1]].add(edge[0]);
        }
        Map<Integer, Set<Integer>> map = new HashMap<>();
        for(int i = 0; i < n; i++) {
            Set<Integer> ancestors = GRAPH[i];
            Set<Integer> curr = new HashSet<>();
            for(int ancestor: ancestors) {
                if(map.containsKey(ancestor)) {
                    curr.add(ancestor);
                    curr.addAll(map.get(ancestor));
                } else {
                    dfs(ancestor,  curr, GRAPH);
                }
            }
            map.put(i, curr);
            //res.add(curr.stream().sorted().toList());
        }
        List<List<Integer>> result = new ArrayList<>();
        for(int i = 0; i < n; i++) {
            result.add(map.get(i).stream().sorted().toList());
        }
        return result;
    }
    private void dfs(int node,  Set<Integer> curr, Set[] GRAPH) {
        curr.add(node);
        Set<Integer> ancestors = GRAPH[node];
        for(int ancestor: ancestors) {
            dfs(ancestor,  curr, GRAPH);
        }
    }
    private void testNumSubArrays() {
        int[] arr = {1,1,2,1,1};
        int count = numberOfSubarrays(arr, 3);
        System.out.printf("count: %d\n", count);
    }

    public int numberOfSubarrays(int[] nums, int k) {
        return numsAtMostK(nums, k) - numsAtMostK(nums, k-1);
    }
    private int numsAtMostK(int[] nums, int k) {
        int count = 0, res = 0;
        for(int l = 0, r = 0; r < nums.length; r++) {
            if(nums[r]%2 == 1) {
                count += 1;
            }
            while(count > k) {
                if(nums[l]%2 == 1) {
                    count -= 1;
                }
                l += 1;
            }
            res += r-l+1;
        }
        return res;
    }

    private void testCreateBouquet() {
        int[] bloomDay = {1,10,3,10,2};
        int m = 3, k =1;
        int days = minDays(bloomDay, m, k);
    }

    public int minDays(int[] bloomDay, int m, int k) {
        int len = bloomDay.length;
        if(len < m*k) {
            return -1;
        }
        int lo = 1, hi = Arrays.stream(bloomDay).max().getAsInt();
        int min = hi;
        while(lo <= hi) {
            int mid = lo + (hi-lo)/2;
            if(canCreatBouquet(mid, bloomDay, m, k)) {
                min = mid;
                hi = mid-1;
            } else {
                lo = mid+1;
            }
        }
        return min;
    }
    private boolean canCreatBouquet(int days, int[] bloomDay, int m, int k) {
        int count = 0;
        for(int idx = 0; idx < bloomDay.length; ) {
            boolean bouquetCreted = true;
            int bc = 0;
            for(; bc < k; bc++) {
                if(bloomDay[idx+bc] > days) {
                    bouquetCreted = false;
                    bc += 1;
                    break;
                }
            }
            if(bouquetCreted) {
                count += 1;
                idx += bc;
            } else {
                idx += bc;
            }

            if(count >= m) {
                break;
            }

        }

        return count >= m;
    }

    private void testTextJustify() {
        String[] words = {"This", "is", "an", "example", "of", "text", "justification."};
        List<String> res = fullJustify(words, 16);
        for(String line: res) {
            System.out.printf("%s\n", line);
        }
    }

    public List<String> fullJustify(String[] words, int maxWidth) {
        List<String> res = new ArrayList<>();
        int li = 0;
        while(li < words.length) {
            int ri = findRightBoundry(li, words, maxWidth);
            res.add(justify(li, ri, words, maxWidth));
            li = ri + 1;
        }
        return res;
    }
    private String justify(int li, int ri, String[] words, int width) {
        if(li == ri) {
            return rpad(words[li], width);
        }
        int spaces = ri-li;
        boolean isLastLine = ri == words.length-1;
        int totalSpaces = (width - length(li, ri, words));
        int spaceCount  = isLastLine ? 1 : totalSpaces/spaces;
        int rem = isLastLine ? 0 :totalSpaces%spaces;
        String SPACE_STR = blanks(spaceCount);
        StringBuilder sb = new StringBuilder();
        while(li <= ri) {
            sb.append(words[li]);
            sb.append(SPACE_STR);
            if(rem > 0) {
                sb.append(" ");
                rem -= 1;
            }
            li += 1;
        }
        return  rpad(sb.toString().trim(), width);
    }
    private int length(int l, int r, String[] words) {
        int len = 0;
        while(l <= r) {
            len += words[l++].length();
        }
        return len;
    }

    private String blanks(int count) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < count; i++) {
            sb.append(" ");
        }
        return sb.toString();
    }

    private String rpad(String str, int width) {
        StringBuilder sb = new StringBuilder(str.trim());
        while(sb.length() < width) {
            sb.append(" ");
        }
        return sb.toString();
    }
    private int findRightBoundry(int li, String[] words, int width) {
        int len = 0;
        while(li < words.length && len + words[li].length() <= width) {
            len += words[li].length() + 1;
            li += 1;
        }
        return li-1;
    }
    private void test20June() {
        int[] position = {5,4,3,2,1,1000000000};
        int maxForce = maxDistance(position, 2);
        System.out.printf("MaxForce: %d\n", maxForce);

    }
    public int maxDistance(int[] position, int m) {
        int len = position.length;
        Arrays.sort(position);
        int min = Integer.MAX_VALUE, max = position[len-1] - position[0];
        for(int i = 1; i < len; i++) {
            min = Math.min(min, position[i] - position[i-1]);
        }
        int res = min;
        while(min <= max) {
            int mid = min + (max-min)/2;
            if(canAchieve(mid, m, position)) {
                res = mid;
                min = mid + 1;
            } else {
                max = mid-1;
            }
        }
        return res;
    }
    private boolean canAchieve(int maxForce, int ballCount, int[] position) {
        int currCount = 0;
        for(int idx = 0; idx < position.length; ) {
            int nextIdx = idx+1;
            while(nextIdx < position.length && position[nextIdx] - position[idx] < maxForce) {
                nextIdx += 1;
            }
            idx = nextIdx - 1;
            if(nextIdx >= position.length) {
                break;
            }
            currCount += 1;
            if(currCount >= ballCount) {
                break;
            }
        }
        return currCount >= ballCount;
    }
}
