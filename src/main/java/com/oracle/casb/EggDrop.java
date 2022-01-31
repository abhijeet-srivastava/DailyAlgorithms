package com.oracle.casb;

import java.util.Arrays;

/**
 * Created By : abhijsri
 * Date  : 06/08/18
 **/
public class EggDrop {

    public static void main(String[] args) {
        EggDrop ed = new EggDrop();
        int numEggs = 7;
        int numFloors = 10000;
        System.out.printf("Max drop for %d eggs and %d floors are - %d%c", numEggs, numFloors, ed.getEggDrop(numEggs, numFloors), '\n');
    }

    private int getEggDrop(int numEggs, int numFloors) {
        int[][] EGG_DROPS = new int[numEggs+1][numFloors+1];
        /**
         * 0  Floor required-  0 trials
         * 1  Floor required - 1 trials
         */

        for (int i = 0; i <= numEggs; i++) {
            Arrays.fill(EGG_DROPS[i], Integer.MAX_VALUE);
            EGG_DROPS[i][0] = 0;
            EGG_DROPS[i][1] = 1;
        }

        /**
         * 0 Egg - Infinite trials
         * 1 Egg - j trial for each floor
         */
        for (int j = 0; j <= numFloors; j++) {
            EGG_DROPS[1][j] = j;
        }

        for (int eggCount = 2; eggCount <= numEggs;eggCount++) {
            for (int floorNumber = 2; floorNumber <= numFloors; floorNumber++) {
                int lower = 1;
                int higher = floorNumber;
                //int mid = lower + (higher-lower) >> 1;
                /*while((higher - lower) > 1) {
                    System.out.printf("Lower:%d, Higher: %d, Mid: %d\n", lower, higher, mid);
                    //If egg breaks at mid floor, remaining egg = eggCount-1, Remaining floors = mid-1
                    int funct1 = EGG_DROPS[eggCount-1][mid-1];//Increasing function of mid
                    //If egg do not break at floor mid, remaining egg = eggCount, Remaining floors = floorNumber-mid
                    int funct2 = EGG_DROPS[eggCount][floorNumber-mid];//Decreasing function
                    System.out.printf("funct1(mid): %d, funct2(mid):%d\n", funct1, funct2);
                    if(funct1 < funct2) {
                        //mid is too low
                        lower = mid;
                    } else if(funct1 > funct2) {
                        //Mid is too high
                        higher = mid;
                    } else {
                        //Optimum value of mid
                        lower = higher = mid;
                    }
                    mid = lower + (higher-lower) >> 1;
                }
                int res = 1 + Integer.max(EGG_DROPS[eggCount-1][mid-1], EGG_DROPS[eggCount][floorNumber-mid]);
                EGG_DROPS[eggCount][floorNumber] = Math.min(EGG_DROPS[eggCount][floorNumber], res);*/

                for(int mid = EGG_DROPS[eggCount][floorNumber-1]; mid <= floorNumber;  mid++) {
                    /**
                     * Max of-
                     * 1. Current egg  breaks- i-1 eggs and x-1 floors
                     * 2. Egg do not break: i eggs and remaining floors(i.e. j-x)
                     **/
                    int res = 1 + Integer.max(EGG_DROPS[eggCount-1][mid-1], EGG_DROPS[eggCount][floorNumber-mid]);
                    EGG_DROPS[eggCount][floorNumber] = Math.min(EGG_DROPS[eggCount][floorNumber], res);
                }
            }
        }
        return EGG_DROPS[numEggs][numFloors];
    }

    public int superEggDrop(int K, int N) {
        /**
         * DP[M][K]= Maximum floors to test with K eggs and M moves
         * DP[M][K] = 1  (Testing current floor)
         *            + DP[M-1][K-1](Egg breaks, With M-1 moves and K-1 eggs search below current floor)
         *            + DP[M-1][K] (Egg do not break, With M-1 moves and K eggs search above current floor)
         *  Max Eggs = K
         *  Max Moves = N (1 move for each floor)
         */
        int[][] DP = new int[N+1][K+1];
        /**
         * If move == 0, floors == 0
         * If eggs == 0, floors == 0
         * So start for 1
         */
        int moveCount = 0;
        while(DP[moveCount][K] < N) {//Maximum floor to check is N
            moveCount -= 1;
            /**
             * For egg count 1...K, How many floors are possible
             */
            for(int eggCount = 1; eggCount <= K; eggCount++) {
                DP[moveCount][eggCount] = 1 + DP[moveCount-1][eggCount-1] + DP[moveCount-1][eggCount];
            }
        }
        return moveCount;
    }
}
