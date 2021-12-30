package com.walmart.dsu;

public class RestrictedFriends {

    public static void main(String[] args) {
        RestrictedFriends rf = new RestrictedFriends();
        rf.testFriends();
    }

    private void testFriends() {
    }

    class DSU {
        int[] parent;
        int[] rank;

        public DSU(int n) {
            this.parent = new int[n];
            this.rank = new int[n];
            for(int i = 0; i < n; i++) {
                parent[i] = i;
                rank[i] = 1;
            }
        }
        public int find(int x) {
            while(x != parent[x]) {
                parent[x] = parent[parent[x]];
                x = parent[x];
            }
            return x;
        }

        public void  merge(int x, int y) {
            int px = find(x);
            int py = find(y);
            if(px == py) {
                return;
            }
            if(rank[px] > rank[py]) {
                parent[py] = px;
            } else {
                parent[px] = py;
                if(rank[px] == rank[py]) {
                    rank[py] += 1;
                }
            }
        }
    }
    public boolean[] friendRequests(int n, int[][] restrictions, int[][] requests) {
        DSU dsu = new DSU(n);
        boolean[] result = new boolean[requests.length];
        for(int i = 0; i < requests.length; i++) {
            int p1 = requests[i][0];
            int p2 = requests[i][1];

            int g1 = dsu.find(p1);
            int g2 = dsu.find(p2);
            String key = getKey(g1, g2);
            boolean canBeFriend = true;
            for(int[] restriction : restrictions) {
                int rg1 = dsu.find(restriction[0]);
                int rg2 = dsu.find(restriction[1]);
                String resKey = getKey(rg1, rg2);
                if(resKey.equals(key)) {
                    canBeFriend = false;
                    break;
                }
            }
            if(canBeFriend)
                dsu.merge(p1, p2);
            result[i] = canBeFriend;
        }
        return result;
    }

    private String getKey(int g1, int g2) {
        if(g1 < g2) {
            return g1 + "_" + g2;
        } else {
            return g2 + "_" + g1;
        }
    }
}
