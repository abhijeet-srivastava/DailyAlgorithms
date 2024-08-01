package com.prep24;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SolutionTree {

    public int countPairs(TreeNode root, int distance) {
        Map<TreeNode, TreeNode> parents = new HashMap<>();
        List<TreeNode> leaves = new ArrayList<>();
        dfs(root, null, parents, leaves);
        int res = 0;
        for(TreeNode leaf: leaves) {
            res += bfs(leaf, parents, distance);
        }
        return (res >> 1);
    }
    private int bfs(TreeNode node, Map<TreeNode, TreeNode> parents, int dist) {
        int res = 0;
        int level = 0;
        System.out.printf("Start bfs from %d\n", node.val);
        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(node);
        Set<TreeNode> visited = new HashSet<>();
        while(!queue.isEmpty() && level <= dist) {
            int size = queue.size();
            while(size-- > 0) {
                TreeNode curr = queue.poll();
                if(curr != node && curr.left == null && curr.right == null) {
                    System.out.printf("From %d, %d at dist %d\n", node.val, curr.val, level);
                    res += 1;
                }
                if(curr.left != null && !visited.contains(curr.left) ) {
                    queue.offer(curr.left);
                    visited.add(curr.left);
                }
                if(curr.right != null && !visited.contains(curr.right) ) {
                    queue.offer(curr.right);
                    visited.add(curr.right);
                }
                if(parents.containsKey(curr) && !visited.contains(parents.get(curr)) ) {
                    queue.offer(parents.get(curr));
                    visited.add(parents.get(curr));
                }
            }
            level += 1;
        }
        return res;
    }
    private void dfs(TreeNode curr, TreeNode parent, Map<TreeNode, TreeNode> parents, List<TreeNode> leaves) {
        if(curr == null) {
            return;
        }
        if(curr.left == null && curr.right == null) {
            leaves.add(curr);
        }
        if(parent != null) {
            parents.put(curr, parent);
        }
        dfs(curr.left, curr, parents, leaves);
        dfs(curr.right, curr, parents, leaves);
    }

     //Definition for a binary tree node.
     public class TreeNode {
         int val;
         TreeNode left;
         TreeNode right;
         TreeNode() {}
         TreeNode(int val) { this.val = val; }
         TreeNode(int val, TreeNode left, TreeNode right) {
             this.val = val;
             this.left = left;
             this.right = right;
         }
     }

}
