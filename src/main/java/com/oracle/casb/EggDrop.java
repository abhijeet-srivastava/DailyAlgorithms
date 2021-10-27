package com.oracle.casb;

/**
 * Created By : abhijsri
 * Date  : 06/08/18
 **/
public class EggDrop {

    public static void main(String[] args) {
        EggDrop ed = new EggDrop();
        int numEggs = 4;
        int numFloors = 100;
        System.out.printf("Max drop for %d eggs and %d floors are - %d%c", numEggs, numFloors, ed.getEggDrop(numEggs, numFloors), '\n');
    }

    private int getEggDrop(int numEggs, int numFloors) {
        int[][] EGG_DROPS = new int[numEggs+1][numFloors+1];
        /**
         * 0  Floor required-  0 trials
         * 1  Floor required - 1 trials
         */
        for (int i = 0; i <= numEggs; i++) {
            EGG_DROPS[numEggs][0] = 0;
            EGG_DROPS[numEggs][1] = 1;
        }

        /**
         * 0 Egg - Infinite trials
         * 1 Egg - j trial for each floor
         */
        for (int j = 0; j <= numFloors; j++) {
            EGG_DROPS[0][j] = Integer.MAX_VALUE;
            EGG_DROPS[1][j] = j;
        }

        for (int i = 2; i <= numEggs;i++) {
            for (int j = 2; j <= numFloors; j++) {
                EGG_DROPS[i][j] = Integer.MAX_VALUE;
                for(int x = 1; x <= j;  x++) {
                    int res = 1 + Integer.max(EGG_DROPS[i-1][x-1], EGG_DROPS[i][j-x]);
                    if (res < EGG_DROPS[i][j])  {
                        EGG_DROPS[i][j] = res;
                    }
                }
            }
        }
        return EGG_DROPS[numEggs][numFloors];
    }
}
