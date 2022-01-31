package com.oracle.casb;

import java.util.Arrays;
import java.util.TreeMap;

public class BinarySearch {

    public static void main(String[] args) {
        BinarySearch bs  = new BinarySearch();
        //bs.testBananaEat();
        bs.testWorkerTasks();
    }

    private void testWorkerTasks() {
        int[] tasks = {10,15,30};
        int[] workers = {0,10,10,10,10};
        int maxTasks = maxTaskAssign(tasks, workers, 10, 10);
    }

    private void testBananaEat() {
        int[] piles = {3,6,7,11};
        int minSpeed = minEatingSpeed(piles,  8);
        System.out.printf("Speed: %s\n", minSpeed);
    }

    public int maxTaskAssign(int[] tasks, int[] workers, int pills, int strength) {
        Arrays.parallelSort(tasks);
        for(int i = 0, j = tasks.length-1; i < j; i++,  j--) {
            int tmp = tasks[i];
            tasks[i] = tasks[j];
            tasks[j] = tmp;
        }
        int lo = 0;
        int hi = Math.min(tasks.length, workers.length);
        int res = 0;
        while(lo <= hi) {
            int mid = (lo+hi) >> 1;
            if(canComplete(mid, pills, strength, tasks, workers)) {
                res = mid;
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }

        return res;
    }

    private boolean canComplete(int taskCount, int pills, int strength, int[] tasks, int[] workers) {
        int count = 0;
        TreeMap<Integer, Integer> workerMap = new TreeMap<>();
        for(int i = 0; i < workers.length; i++) {
            workerMap.put(workers[i], workerMap.getOrDefault(workers[i], 0) + 1) ;
        }
        for(int i = 0; i < tasks.length; i++) {
            Integer worker = workerMap.ceilingKey(tasks[i]);
            if(worker == null && pills > 0) {
                worker = workerMap.ceilingKey(tasks[i] - strength);
                if(worker != null) {
                    pills -= 1;
                }
            }
            if(worker == null) {
                continue;
            }
            int avlblWorkers = workerMap.get(worker);
            count += 1;
            avlblWorkers -= 1;
            if(avlblWorkers == 0) {
                workerMap.remove(worker);
            } else {
                workerMap.put(worker, avlblWorkers);
            }

            if(count >= taskCount) {
                break;
            }
        }
        return count >= taskCount;
    }
    public int minEatingSpeed(int[] piles, int h) {
        int len = piles.length;
        Arrays.sort(piles);
        int left = 1;
        int right = piles[len-1];
        while(left < right) {
            int mid = left +  (right-left) /2;
            System.out.printf("Left: %d, Right: %d, mid: %d\n", left, right, mid);
            int requiredHourBySpeed = calculateRequiredHourBySpeedk(mid, piles);
            System.out.printf("With speed: %d Time Req: %d\n", mid, requiredHourBySpeed);
            if(requiredHourBySpeed <= h) {
                right = mid;
            } else {
                left = mid+1;
            }
        }
        return right;
    }

    private int calculateRequiredHourBySpeedk(int middle, int[] piles) {
        int hourSpent = 0;

        // Iterate over the piles and calculate hourSpent.
        // We increase the hourSpent by ceil(pile / middle)
        for (int pile : piles) {
            hourSpent += Math.ceil((double) pile / middle);
        }
        return hourSpent;
    }

    private int calculateRequiredHourBySpeedk1(int k, int[] piles) {
        int h = 0;
        int currentHourEat = 0;
        int i = 0;
        while(i < piles.length) {
            int cp = piles[i];
            while(cp > 0) {
                h += 1;
                cp -= k;
            }
            i+=1;
        }
        return h;
    }
}
