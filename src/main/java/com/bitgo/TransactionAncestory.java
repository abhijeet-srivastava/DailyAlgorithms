package com.bitgo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TransactionAncestory {


    public static void main(String[] args) {
        TransactionAncestory ta = new TransactionAncestory();
        ta.testTransactionAncestory();
    }

    private void testTransactionAncestory() {
        Map<String, List<String>> txnHierarchy = Map.of(
                "A", Arrays.asList("B", "C", "Y"),
                "B", Arrays.asList("C", "X"),
                "C", Arrays.asList("D", "E"),
                "D", Arrays.asList(),
                "E", Arrays.asList("F"),
                "F", Arrays.asList("U", "I")
        );
        List<TransactionAncestors> res = findAncestors(txnHierarchy);
        for(TransactionAncestors ta: res) {
            System.out.printf("%s -> %d\n", ta.id, ta.count);
        }
    }

    /**
     * A-> [ B, C, Y ]
     *
     * B-> [C, X]
     *
     * C → [D, E]
     *
     * D -> [W, Y]
     *
     * E -> [F]
     *
     * F-> [U, I]
     *
     *
     * A -> B, C, D, E, F
     * B -> C, D, E, F
     * C -> D, E, F
     * D ->
     * E -> F
     * F ->
     *
     * Output:
     * A->5
     * B->4
     * C->3
     * E->1
     * D->0
     * F->0
     * @param transactions
     * @return
     */
    private List<TransactionAncestors> findAncestors(Map<String, List<String>> transactionHierarchy) {
        Map<String, Integer> ancestorsCount = new HashMap<>();
        Set<String> visited = new HashSet<>();
        Map<String, Integer> ancestorcount = new HashMap<>();
        for(String txn: transactionHierarchy.keySet()) {
            if(visited.contains(txn)) {
                continue;
            }
            int count = dfs(txn, visited, transactionHierarchy, ancestorcount);
            ancestorcount.put(txn, count-1);
        }
        for(var t: ancestorcount.entrySet()) {
            System.out.printf("%s - %d\n", t.getKey(), t.getValue());
        }
        List<TransactionAncestors> res = new ArrayList<>();
        return res;
    }

    private int dfs(String txn, Set<String> visited,
                     Map<String, List<String>> transactionHierarchy, Map<String, Integer> ancestorcount) {
        visited.add(txn);
        int count = 1;
        for(String anc: transactionHierarchy.get(txn)) {
            if(!transactionHierarchy.containsKey(anc)) {
                continue;
            }
            int curr = dfs(anc, visited, transactionHierarchy, ancestorcount);
            ancestorcount.merge(anc, curr, Integer::sum);
            count += curr;
        }
        return count;
    }

    private void findAncestors(String txn, Map<String, List<String>> transactionHierarchy, Set<String> ancestors) {
        if(!transactionHierarchy.containsKey(txn)) {
            return;
        }
        ancestors.add(txn);
        for(String dependent: transactionHierarchy.get(txn)) {
            if(!transactionHierarchy.containsKey(dependent)) {
                continue;
            }
            findAncestors(dependent, transactionHierarchy, ancestors);
        }
    }

    private record TransactionAncestors(String id, Integer count) {};
}
