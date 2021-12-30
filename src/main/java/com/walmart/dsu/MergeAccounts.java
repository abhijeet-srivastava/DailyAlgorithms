package com.walmart.dsu;

import java.util.*;
import java.util.stream.Collectors;

public class MergeAccounts {

    public static void main(String[] args) {
        MergeAccounts ma = new MergeAccounts();
        ma.test();
    }

    private void test() {
        List<List<String>> accounts = Arrays.asList(
                Arrays.asList("David","David0@m.co","David1@m.co"),
                Arrays.asList("David","David3@m.co","David4@m.co"),
                Arrays.asList("David","David4@m.co","David5@m.co"),
                Arrays.asList("David","David2@m.co","David3@m.co"),
                Arrays.asList("David","David1@m.co","David2@m.co")
        );
        List<List<String>> merged = accountsMergeDSU(accounts);
        for(List<String> acc: merged) {
            System.out.printf("%s: :%s\n", acc.get(0), acc.subList(1, acc.size()).stream().collect(Collectors.joining(", ", "[", "]")));
        }
    }

    class Account {
        String accountName;
        Set<String> accountMails;
        public Account(List<String> account) {
            this.accountName = account.get(0);
            accountMails = new TreeSet<>(account.subList(1, account.size()));
        }

        public boolean containsCommonEmail(Account other) {
            Set<String> unionMails = new HashSet<>(this.accountMails);
            unionMails.addAll(other.accountMails);
            return ((this.accountMails.size() + other.accountMails.size()) >  unionMails.size());
        }
        public void merge(Account other) {
            this.accountMails.addAll(other.accountMails);
        }
        public List<String> result() {
            List<String> res = new ArrayList<>(this.accountMails);
            res.add(0, this.accountName);
            return res;
        }
    }

    class DSU {
        int representative [];
        int size [];

        DSU(int sz) {
            representative = new int[sz];
            size = new int[sz];

            for (int i = 0; i < sz; ++i) {
                // Initially each group is its own representative
                representative[i] = i;
                // Intialize the size of all groups to 1
                size[i] = 1;
            }
        }

        // Finds the representative of group x
        public int findGroupRoot(int x) {
            if (x == representative[x]) {
                return x;
            }

            // This is path compression
            return representative[x] = findGroupRoot(representative[x]);
        }

        // Unite the group that contains "a" with the group that contains "b"
        public void merge(int a, int b) {
            int representativeA = findGroupRoot(a);
            int representativeB = findGroupRoot(b);

            // If nodes a and b already belong to the same group, do nothing.
            if (representativeA == representativeB) {
                return;
            }

            // Union by size: point the representative of the smaller
            // group to the representative of the larger group.
            if (size[representativeA] >= size[representativeB]) {
                size[representativeA] += size[representativeB];
                representative[representativeB] = representativeA;
            } else {
                size[representativeB] += size[representativeA];
                representative[representativeA] = representativeB;
            }
        }
    }
    private class DSU1 {
        int[] parent;
        int[] rank;

        public DSU1(int size) {
            this.parent = new int[size];
            this.rank = new int[size];
            for(int i = 0; i < size; i++) {
                this.parent[i] = i;
                this.rank[i] = 1;
            }
        }

        private int findGroupRoot(int x) {
            while(x != parent[x]) {
                parent[x] = parent[parent[x]];
                x = parent[x];
            }
            return x;
        }

        private void merge(int x, int y) {
            int px = findGroupRoot(x);
            int py = findGroupRoot(y);
            if(px == py) {
                return;
            }
            if(rank[px] < rank[py]) {
                parent[px] = py;
            } else {
                parent[py] = px;
                if(rank[px] == rank[py]) {
                    rank[px] += 1;
                }
            }
        }
    }
    public List<List<String>> accountsMergeDSU(List<List<String>> accounts) {
        Map<String, Integer> emailGroupMap = new HashMap<>();
        DSU1 dsu = new DSU1(accounts.size());
        for(int i = 0;i < accounts.size(); i++) {
            for(int j = 1; j < accounts.get(i).size(); j++) {
                if(emailGroupMap.containsKey(accounts.get(i).get(j))) {
                    dsu.merge(i, emailGroupMap.get(accounts.get(i).get(j)));
                } else {
                    emailGroupMap.put(accounts.get(i).get(j), i);
                }
            }
        }
        Map<Integer, Set<String>> groupEmailMap = new HashMap<>();
        for(Map.Entry<String, Integer> emailGroupEntry : emailGroupMap.entrySet()) {
            int groupRep = dsu.findGroupRoot(emailGroupEntry.getValue());
            groupEmailMap.computeIfAbsent(groupRep, x -> new TreeSet<>()).add(emailGroupEntry.getKey());
        }
        List<List<String>> mergedAccount = new ArrayList<>();
        for(Map.Entry<Integer, Set<String>> groupEmailEntry: groupEmailMap.entrySet()) {
            List<String> emails = new ArrayList<>(groupEmailEntry.getValue());
            emails.add(0, accounts.get(groupEmailEntry.getKey()).get(0));
            mergedAccount.add(emails);
        }
        return mergedAccount;
    }
    public List<List<String>> accountsMergeDFS(List<List<String>> accounts) {
        Map<String, Set<String>> GRAPH = new HashMap<>();
        for(List<String> account: accounts) {
            for(int j = 2; j < account.size(); j++) {
                GRAPH.computeIfAbsent(account.get(1), x -> new HashSet<>()).add(account.get(j));
                GRAPH.computeIfAbsent(account.get(j), x -> new HashSet<>()).add(account.get(1));
            }
        }
        List<List<String>> mergedAccounts = new ArrayList<>();
        HashSet<String> visited = new HashSet<>();
        for(List<String> account :  accounts) {
            String accountName = account.get(0);
            String firstEmail = account.get(1);
            if(visited.contains(firstEmail)) {
                continue;
            }
            List<String> mergedEmails = new ArrayList<>();
            dfs(firstEmail, GRAPH, visited, mergedEmails);
            Collections.sort(mergedEmails);
            mergedEmails.add(0, accountName);
            mergedAccounts.add(mergedEmails);
        }
        return mergedAccounts;
    }

    private void dfs(String currEmail, Map<String, Set<String>> GRAPH, HashSet<String> visited, List<String> mergedEmails) {
        if(visited.contains(currEmail)) {
            return;
        }
        visited.add(currEmail);
        mergedEmails.add(currEmail);
        Set<String> neighbours = GRAPH.getOrDefault(currEmail, Collections.<String>emptySet());
        for(String neighbour: neighbours) {
            dfs(neighbour, GRAPH, visited, mergedEmails);
        }
    }

    public List<List<String>> accountsMerge1(List<List<String>> accounts) {
        Account[] accList = accounts.stream().map(acc -> new Account(acc)).toArray(Account[]::new);
        int[] parent = new int[accList.length];
        int[] rank = new int[accList.length];
        for(int i = 0; i < accList.length; i++) {
            parent[i] = i;
            rank[i] = 1;
        }
        for(int i = 0; i < accList.length; i++) {
            for(int j = i+1; j < accList.length; j++) {
                if(i == j) {
                    continue;
                }
                if(accList[i].containsCommonEmail(accList[j])) {
                    merge(i, j, parent, rank, accList);
                }
            }
        }
        List<List<String>> result = new ArrayList<>();
        for(int i = 0; i < accList.length; i++) {
            if(parent[i] == i) {
                result.add(accList[i].result());
            }
        }
        return result;
    }

    private void merge(int x, int y, int[] parent, int[] rank, Account[] accList) {
        int px = find(x, parent);
        int py = find(y, parent);
        if(rank[px] > rank[py]) {
            parent[py] = px;
            accList[x].merge(accList[y]);
        } else {
            parent[px] = py;
            accList[y].merge(accList[x]);
            if(rank[px] == rank[py]) {
                rank[py] += 1;
            }
        }
    }

    private int find(int x, int[] parent) {
        while(x != parent[x]) {
            parent[x] = parent[parent[x]];
            x = parent[x];
        }
        return parent[x];
    }

}
