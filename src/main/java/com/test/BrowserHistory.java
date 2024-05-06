package com.test;

public class BrowserHistory {
    HistoryNode curr;

    public BrowserHistory(String homepage) {
        curr = new HistoryNode(homepage);
    }

    public void visit(String url) {
        HistoryNode next = new HistoryNode(url);
        curr.next = next;
        next.prev = curr;
        curr = next;
    }

    public String back(int steps) {
        while(steps > 0 && curr.prev != null) {
            curr = curr.prev;
            steps -= 1;
        }
        return curr.url;
    }

    public String forward(int steps) {
        while(steps > 0 && curr.next != null) {
            curr = curr.next;
            steps -= 1;
        }
        return curr.url;
    }

    class HistoryNode {
        String url;
        HistoryNode prev;
        HistoryNode next;
        public HistoryNode(String url) {
            this.url = url;
        }
    }
}
