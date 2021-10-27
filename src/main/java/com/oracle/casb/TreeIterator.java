package com.oracle.casb;

import com.oracle.casb.common.TreeNode;

import java.util.Iterator;
import java.util.Stack;

/**
 * Created By : abhijsri
 * Date  : 25/05/18
 **/
public class TreeIterator<T extends Comparable<T>> implements Iterator<TreeNode<T>> {

    private Stack<TreeNode<T>> stack;
    private TreeNode<T> current;

    public TreeIterator(TreeNode<T> root) {
        this.current = root;
        stack = new Stack<>();
    }

    @Override public boolean hasNext() {
        return !(current == null && stack.isEmpty());
    }

    @Override public TreeNode<T> next() {
        while (current != null) {
            stack.push(current);
            current = current.getLeft();
        }
        TreeNode<T> node = stack.pop();
        current = node.getRight();
        return node;
    }
}
