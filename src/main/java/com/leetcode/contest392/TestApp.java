package com.leetcode.contest392;

import java.util.Arrays;
import java.util.PriorityQueue;

public class TestApp {

    public static void main(String[] args) {
        TestApp ta = new TestApp();
        ta.testQueries();
    }

    private void testQueries() {
        int[][] grid = {{444424,409221,703419,11307,578382,330430,522887,38831,267101,315541,148425,360873},{353160,3217,718922,509568,494803,327636,715882,456279,374061,701863,711832,644822},{459535,264294,246310,405317,275802,948618,449015,176733,921040,56692,632708,556696},{167402,594284,8377,766746,728202,329140,399028,907843,68783,149661,244321,861358},{727577,582470,946680,222674,152875,128658,389710,581164,512061,367464,883657,78004},{463513,473823,328325,888670,267782,435621,153221,877511,900231,72761,825121,532939},{992835,33883,587426,680675,674055,682929,750368,241142,241026,369751,462134,785672},{915635,918034,398025,400424,695630,594801,748962,278900,705889,570212,42410,823342},{644602,961002,489119,606936,327139,664880,455045,231423,114466,315707,25092,961268},{962857,647428,139005,221262,469484,669734,66022,473118,258066,67408,545435,316643},{977028,938186,277400,756609,491213,704014,292941,392893,280499,650462,270100,477276},{393574,562825,637562,639836,8932,540799,758836,403682,79851,17885,851550,499020},{403665,119906,305796,88211,759076,441097,164887,709599,194,468995,922288,359913},{696749,265394,517399,161062,512967,205098,814158,627951,286474,763625,370987,798077},{166098,940946,871758,690278,903705,368584,576209,94794,25522,255261,209835,540769},{8088,89612,457088,492467,511285,900536,734726,683046,515695,14749,988608,977041},{76149,112648,515127,257871,912674,880020,32805,688253,722582,931114,734057,939655},{395351,377494,543729,368629,913310,69242,737795,849175,870860,278493,575561,111787}};
        int[] queries = {483649,690923,317026,408761,985459,619592,287085,302896,241756,557463,914140,994632,511904,377570,272415,840485,578955,797418,609746,388421,517504,170621,188489,169881,466574};
        //int[][] grid = {{1,2,3}, {2,5,7},{3,5,1}};
        //int[] queries = {5,6,2};
        int[] res = maxPoints(grid, queries);
        for(int i = 0; i < res.length; i++) {
            System.out.printf("res[%d] = %d\n", i , res[i]);
        }
    }

    public int[] maxPoints(int[][] grid, int[] queries) {
        int[][] dirs =  {
                {0, 1},
                {1, 0},
                {0,-1},
                {-1,0}
        };
        int m = grid.length, n = grid[0].length, qLen = queries.length;
        int[][] qArr = new int[qLen][2];
        for(int i = 0; i < qLen; i++) {
            qArr[i] = new int[]{queries[i], i};
        }
        int[] res = new int[qLen];
        Arrays.sort(qArr, (a, b) -> Integer.compare(a[0], b[0]));
        PriorityQueue<int[]> queue = new PriorityQueue<>(
                (a,b) -> Integer.compare(a[0], b[0])
        );
        boolean[][] visited = new boolean[m][n];
        queue.offer(new int[]{grid[0][0], 0, 0});
        visited[0][0]= true;
        int sum = 0;
        //int qIdx = 0;
        for(int[] query:qArr) {
            int qVal = query[0], qIdx= query[1];
            while (!queue.isEmpty() && queue.peek()[0] < qVal) {
                int[] curr = queue.poll();
                sum += 1;
                for (int[] dir : dirs) {
                    int x = curr[1] + dir[0], y = curr[2] + dir[1];
                    if (x >= 0 && x < m && y >= 0 && y < n && !visited[x][y]) {
                        queue.offer(new int[]{grid[x][y], x, y});
                        visited[x][y] = true;
                    }
                }
            }
            res[qIdx] = sum;
        }
        return res;
    }

    /**
     * Input String : "This  is Abhijeet"
     * Output String: "Abhijeet is This"
     */
    // Break String in token- Reverse token by token
    // Reverse entire String character by Character

    

    public void reverse(int l, int r, char[] arr) {
        while(l < r) {
            swap(l, r, arr);
            l += 1;
            r -= 1;
        }
    }
    public void swap(int i, int j, char[] arr) {
        char tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}
