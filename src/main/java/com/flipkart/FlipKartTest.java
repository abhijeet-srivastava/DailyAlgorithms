package com.flipkart;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class FlipKartTest {
    public static void main(String[] args) {
        FlipKartTest fkt = new FlipKartTest();
        //fkt.testCompressArray();
        //fkt.testRangeUpdate();
        fkt.testSnakeLadder();
    }

    private void testSnakeLadder() {
        int[][] board = {{-1,-1,-1,-1,-1,-1},{-1,-1,-1,-1,-1,-1},{-1,-1,-1,-1,-1,-1},{-1,35,-1,-1,13,-1},{-1,-1,-1,-1,-1,-1},{-1,15,-1,-1,-1,-1}};
        //int[][] board = {{1,1,-1},{1,1,1},{-1,1,1}};
        int minMoves = snakesAndLadders(board);
        System.out.printf("min: %d\n", minMoves);
    }

    private void testRangeUpdate() {
        int[][] updates = {{1,3,2},{2,4,3},{0,2,-2}};//5
        int[] val = getModifiedArray(5, updates);
        System.out.printf("");
    }

    private void testCompressArray() {
        //char[] arr = {'a', 'a', 'b', 'b', 'c', 'c', 'c'};
        char[] arr = {'a',  'b', 'c'};
        int res = compress(arr);
        System.out.printf("Res: %d\n", res);
    }

    public int compress(char[] chars) {
        int index = chars.length-2;
        int count = 1;
        char prev = chars[chars.length-1];
        int i = chars.length-1;
        while(index >= 0) {
            if(chars[index] == prev) {
                count += 1;
            } else {
                char tmp = chars[index];
                while(count > 0) {
                    chars[i] = (char)(count%10 + '0');
                    i -= 1;
                    count /= 10;
                }
                chars[i] = prev;
                i -= 1;
                prev = tmp;
                count = 1;
            }
            index -= 1;
        }
        while(count > 0) {
            chars[i] = (char)(count%10 + '0');
            i -= 1;
            count /= 10;
        }
        chars[i] = prev;
        count = chars.length - i;
        for(int j = 0; j < count; j++) {
            chars[j] = chars[i];
            i += 1;
        }
        return count;
    }
    public int[] sortByBits(int[] arr) {
        Integer[] objArr = new Integer[arr.length];
        for(int i = 0; i < arr.length; i++) {
            objArr[i] = arr[i];
        }
        Arrays.sort(objArr, Comparator.<Integer>comparingInt(a -> countSetBits(a)).thenComparingInt(a -> a));
        for(int i = 0; i < arr.length; i++) {
            arr[i] = objArr[i];
        }
        return arr;
    }
    private int countSetBits(int num) {
        int count = 0;
        while(num > 0) {
            count += num & 1;
            num = num >> 1;
        }
        return count;
    }
    public List<String> topKFrequent(String[] words, int k) {
        Map<String, Integer> counter = new HashMap<>();
        for(String word: words) {
            counter.merge(word, 1, Integer::sum);
        }
        PriorityQueue<String> pq = new PriorityQueue<>(
                (a, b) -> counter.get(a) == counter.get(b)
                ? b.compareTo(a) : counter.get(a) - counter.get(b)
        );
        for(String word: counter.keySet()) {
            pq.offer(word);
            if(pq.size() > k) {
                pq.remove();
            }
        }
        List<String> result = new ArrayList<>();
        while(!pq.isEmpty()) {
            result.add(0, pq.poll());
        }
        return result;
    }

    private class Interval {
        int start;
        int end;
        int val;
        public Interval(int s, int e, int i) {
            start = s;
            end = e;
            val = i;
        }
        public boolean isValid(){
            return this.start < this.end;
        }
    }
    public int[] getModifiedArray(int length, int[][] updates) {
        int[] arr = new int[length];
        Arrays.sort(updates,
                Comparator.<int[]>comparingInt(a -> a[0]).thenComparing(a -> a[1]));
        Deque<Interval> stack = new ArrayDeque<>();
        for(int[] update : updates) {
            Interval interval = new Interval(update[0], update[1], update[2]);
            while(!stack.isEmpty() && overlaps(stack.peek(), interval)) {
                List<Interval> merged = merge(stack.pop(), interval);
                for(Interval in: merged) {
                    if(in.isValid()) {
                        stack.push(in);
                        interval = in;
                    }
                }
            }
            stack.push(interval);
        }
        while(!stack.isEmpty()) {
            Interval in = stack.pop();
            for(int i = in.start; i <= in.end; i++) {
                arr[i] = in.val;
            }
        }
        return arr;
    }
    private List<Interval> merge(Interval i1, Interval i2) {
        List<Interval> list = new ArrayList<>();
        list.add(new Interval(i1.start, i2.start, i1.val));
        int mergedEnd = i1.end, postMergeVal = i2.val, postMergeEnd = i2.end;
        if(i2.end < i1.end) {
            mergedEnd = i2.end;
            postMergeVal = i1.val;
            postMergeEnd = i1.end;
        }
        list.add(new Interval(i2.start, mergedEnd, i1.val+i2.val));
        list.add(new Interval(mergedEnd, postMergeEnd, postMergeVal));
        return list;
    }
    private boolean overlaps(Interval i1, Interval i2) {
        return Math.max(i1.start, i2.start) < Math.min(i1.end, i2.end);
    }

    public int snakesAndLadders(int[][] board) {
        int n = board.length;
        int[] DP = new int[n*n+1];
        Arrays.fill(DP, -1);
        Map<Integer, Integer> map = new HashMap<>();
        int start = 1, end = n;
        int[] cols = IntStream.rangeClosed(start, end).toArray();
        for(int i = n-1; i >= 0; i--) {
            for(int j = 0; j < n; j++) {
                if(board[i][j] > -1){
                    map.put(cols[j], board[i][j]);
                }
            }
            start += n;
            end += n;
            cols = IntStream.rangeClosed(start, end).toArray();
            if((n-i) % 2 == 1) {
                reverse(cols);
            }
        }
        Deque<Integer> queue = new ArrayDeque<>();
        queue.offer(1);
        DP[1] = 0;
        while(!queue.isEmpty()) {
            int curr = queue.poll();
            for(int i = curr+1; i <= Math.min(curr+6, n*n); i++) {
                int destination = map.getOrDefault(i, i);
                if(DP[destination] == -1) {
                    DP[destination] = DP[curr] + 1;
                    queue.offer(destination);
                }
                if(destination == n*n) {
                    return DP[destination];
                }
            }
        }
        return DP[n*n];
    }
    private void reverse(int[] arr) {
        int i = 0, j = arr.length-1;
        while(i < j) {
            int tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
            i += 1;
            j -= 1;
        }
    }

    public int[] createTargetArray(int[] nums, int[] index) {
        List<Integer> list = new ArrayList<>();
        for(int i = 0; i < nums.length; i++) {
            list.add(index[i], nums[i]);
        }
        return list.stream().mapToInt(Integer::valueOf).toArray();
    }
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        Set[] GRAPH = new HashSet[n];
        for(int i = 0; i < n; i++) {
            GRAPH[1] = new HashSet<int[]>();
        }
        for(int[] flight: flights) {
            GRAPH[flight[0]].add(new int[]{flight[1], flight[2]});
        }
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.<int[]>comparingInt(p ->p[0]));
        pq.offer(new int[]{0, src, k+1});
        while (!pq.isEmpty()){
            int[] currentNode = pq.poll();
            int priceFromSource = currentNode[0], city = currentNode[1], remainingHopsToDest = k+1;
            if(city == dst) {
                return priceFromSource;
            } else if(remainingHopsToDest <= 0) {
                break;
            }
            for(int[] neighbor: (Set<int[]>)GRAPH[city]) {
                int[] nextCity = {priceFromSource + neighbor[1], neighbor[0], remainingHopsToDest-1};
                pq.offer(nextCity);
            }
        }
        return -1;
    }
    public int findCheapestPrice1(int n, int[][] flights, int src, int dst, int k) {
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = 0;
        for(int i = 0; i <= k; i++) {
            int[] tmp = Arrays.copyOf(dist, n);
            for(int[] flight: flights) {
                if(dist[flight[0]] == Integer.MAX_VALUE) {
                    continue;
                }
                tmp[flight[1]] = Math.min(tmp[flight[1]], dist[flight[0]] + flight[1]);
            }
            dist = tmp;
        }
        return dist[dst] == Integer.MAX_VALUE ? -1 : dist[dst];
    }
}
