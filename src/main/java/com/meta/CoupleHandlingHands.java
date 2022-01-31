package com.meta;

public class CoupleHandlingHands {

    private class DSU {
        private int[] parent;
        private int[] rank;
        private int componentCount;
        public DSU(int n) {
            this.parent = new int[n];
            this.rank = new int[n];
            for(int i = 0; i < n; i++ ) {
                this.parent[i] = i;
                this.rank[i] = 1;
            }
            this.componentCount = n;
        }
        private int find(int i) {
            while(i != parent[i]) {
                parent[i] = parent[parent[i]];
                i = parent[i];
            }
            return parent[i];
        }
        private void union(int x, int y) {
            int px = find(x);
            int py = find(y);
            if(px == py) {
                return;
            }else if(rank[px] < rank[py]) {
                parent[px] = py;
            } else if(rank[px] > rank[py]) {
                parent[py] = px;
            } else {
                parent[py] = px;
                rank[px] += 1;
            }
            this.componentCount-= 1;
        }

        public int getComponentCount() {
            return componentCount;
        }
    }


    private int minSwapsCouples(int[] row) {
        int totalCouples = row.length/2;
        DSU dsu = new DSU(totalCouples);
        for(int i = 0; i < totalCouples; i++) {
            int x = row[2*i];
            int y = row[2*i+1];
            dsu.union(x/2, y/2);
        }
        return totalCouples - dsu.getComponentCount();
    }
}
