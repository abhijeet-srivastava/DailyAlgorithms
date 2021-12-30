package com.tree;

import java.util.List;
import java.util.Objects;

public class NryDepth {

    class Node {
        public int val;
        public List<Node> children;

        public Node() {}

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, List<Node> _children) {
            val = _val;
            children = _children;
        }
    };
    public int maxDepth(Node root) {
        if(root == null) {
            return 0;
        } else if(root.children == null
                || root.children.isEmpty()
                || !root.children.stream().filter(Objects::nonNull).findAny().isPresent()) {
            return 1;
        } else {
                return root.children.stream().map(e -> maxDepth(e)).mapToInt(v -> v).max().getAsInt() + 1;
        }
    }
}
