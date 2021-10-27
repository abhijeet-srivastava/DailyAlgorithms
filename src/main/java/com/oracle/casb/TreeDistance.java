package com.oracle.casb;

import com.oracle.casb.common.TreeNode;

import java.util.*;

/**
 * Created By : abhijsri
 * Date  : 02/07/18
 **/
public class TreeDistance {

    public static void main(String[] args) {
        TreeDistance td = new TreeDistance();
        //td.printAllNodesWithDistance();
        td.printDistantNodes();
    }

    private void printDistantNodes() {
        //TreeNode<Integer> root = createTree(new Integer[] { 3, 5, 1, 6, 2, 0, 8, null, null, 7, 4 });
        TreeNode<Integer> root = createRandomTree(new Integer[] { 0,null,1,null,2,null,3,4});
        TreeNode<Integer> target = root.getRight().getRight();
        List<TreeNode<Integer>> nodeList = new ArrayList<>();
        getKDistantNodes(root, target, nodeList, 2);

        for (TreeNode<Integer> node : nodeList) {
            System.out.println(node.getValue());
        }
    }

    private TreeNode<Integer> createRandomTree(Integer[] values) {
        TreeNode<Integer> root = new TreeNode<>(values[0]);
        Queue<TreeNode<Integer>> queue = new LinkedList<>();
        queue.add(root);
        for (int i = 0; i < values.length - 1; ) {
            TreeNode<Integer> current = queue.peek();
            queue.remove();
            if (i < (values.length - 1) && values[++i] != null) {
                current.setLeft(new TreeNode<>(values[i]));
                ((LinkedList<TreeNode<Integer>>) queue).push(current.getLeft());
            }
            if (i < (values.length - 1) && values[++i] != null) {
                current.setRight(new TreeNode<>(values[i]));
                ((LinkedList<TreeNode<Integer>>) queue).push(current.getRight());
            }

        }
        return root;
    }

    private void printAllNodesWithDistance() {
        TreeNode<Integer> root = createTree(new Integer[] { 3, 5, 1, 6, 2, 0, 8, null, null, 7, 4 });
        TreeNode<Integer> target = root.getLeft();
        List<Integer> distance = distanceK(root, target, 2);
        distance.stream().forEach(System.out::println);
    }

    private TreeNode<Integer> createTree(Integer[] input) {
        if (input == null || input.length == 0) {
            return null;
        }
        TreeNode<Integer> root = new TreeNode<>(input[0]);
        int current = 0;
        root.setLeft(createTree(input, 2 * current + 1));
        root.setRight(createTree(input, 2 * (current + 1)));
        return root;
    }

    private TreeNode<Integer> createTree(Integer[] input, int index) {
        if (index >= input.length || input[index] == null) {
            return null;
        }
        TreeNode<Integer> root = new TreeNode<>(input[index]);
        root.setLeft(createTree(input, 2 * index + 1));
        root.setRight(createTree(input, 2 * (index + 1)));
        return root;
    }

    public List<Integer> distanceK(TreeNode<Integer> root, TreeNode<Integer> target, int K) {
        List<Integer> distantNodes = new ArrayList<>();
        getDistantNodes(root, target, distantNodes, new int[] { K }, new boolean[] { Boolean.FALSE });
        return distantNodes;
    }

    public int getKDistantNodes(TreeNode<Integer> root, TreeNode<Integer> target,
            List<TreeNode<Integer>> distantNodes, int K) {
        if (root == null
                || K < 0) {
            return -1;
        }
        if (root == target) {
            getDistantNodesDown(root, distantNodes, K);
            return 0;
        }
        int dl = getKDistantNodes(root.getLeft(), target, distantNodes, K);
        int dr = getKDistantNodes(root.getRight(), target, distantNodes, K);

        if (dl != -1) {
            if (dl + 1 == K) {
                distantNodes.add(root);
            } else {
                getDistantNodesDown(root.getRight(), distantNodes, K-dl-2);
            }
            return 1+dl;
        } else if (dr != -1) {
            if (dr + 1 == K) {
                distantNodes.add(root);
            } else {
                getDistantNodesDown(root.getLeft(), distantNodes, K-dr-2);
            }
            return 1+dr;
        }
        return -1;
    }

    private void getDistantNodesDown(TreeNode<Integer> root, List<TreeNode<Integer>> distantNodes, int K) {
        if (root == null || K < 0) {
            return;
        }
        if (K == 0) {
            distantNodes.add(root);
        } else {
            getDistantNodesDown(root.getLeft(), distantNodes, K-1);
            getDistantNodesDown(root.getRight(), distantNodes, K-1);
        }
    }


    private void getDistantNodes(TreeNode<Integer> root, TreeNode<Integer> target, List<Integer> distantNodes,
            int[] distance, boolean[] found) {
        if (root == null) {
            return;
        }
        if (root == target) {
            found[0] = Boolean.TRUE;
        }
        if (found[0]) {
            if (distance[0] == 0) {
                distantNodes.add(root.getValue());
                return;
            }
            int[] newDistance = new int[] {distance[0] - 1};
            distance = newDistance;
            //return;
        }

        getDistantNodes(root.getLeft(), target, distantNodes, distance, found);
        getDistantNodes(root.getRight(), target, distantNodes, distance, found);
    }
}
