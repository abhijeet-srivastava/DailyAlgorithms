package com.amazon;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LeetCodeApp {
    public static void main(String[] args) {
        LeetCodeApp lca = new LeetCodeApp();
        //lca.testMinEquivalent();
        //lca.testMaxOperations();
        //lca.testInsertInterval();
        //lca.testNextMeetingRoom();
        //lca.testCountIntervals();
        //lca.testMaxJumps();
        lca.testTrie();
    }

    private void testTrie() {
        Trie trie = new Trie();
        trie.insert("apple");
        boolean res = trie.search("apple");
        res = trie.startsWith("app");
    }

    private void testMaxJumps() {
        int[] jumps = {2,3,1,1,4};
        int min = jump(jumps);
        System.out.printf("Min: %d\n", min);
    }

    private void testCountIntervals() {
        CountIntervals ci = new CountIntervals();
        ci.add(2,3);
        ci.add(7,10);
        int cnt = ci.count();
        System.out.printf("Count: %d\n", cnt);
        ci.add(5,8);
        cnt = ci.count();
        System.out.printf("Count: %d\n", cnt);
    }

    private void testNextMeetingRoom() {
        int[][] meetings = {{0,10},{1,5},{2,7},{3,4}};
        int n = 2;
        int mostBookedRoom = mostBooked(n, meetings);
        System.out.printf("Most Booked room: %d\n", mostBookedRoom);
    }

    private void testInsertInterval() {
        int[][] intervals = {{1,3},{6,9}};
        int[] ni = {2, 5};
        int[][] merged = insert(intervals, ni);
    }

    private void testMaxOperations() {
        int[] nums = {9};
        int val =  minimumSize(nums, 2);
        System.out.printf("Min Op: %d\n", val);
    }

    private void testMinEquivalent() {
        String s1 = "leetcode";
        String s2 = "programs";
        String smallest = smallestEquivalentString(s1, s2, "sourcecode");
    }

    public String smallestEquivalentString(String s1, String s2, String baseStr) {
        Set[] mappedTo = new Set[26];
        for(int i = 0; i < 26; i++) {
            mappedTo[i] = new HashSet<Character>();
        }
        for(int i = 0; i < s1.length(); i++) {
            char c1 = s1.charAt(i);
            char c2 = s2.charAt(i);
            mappedTo[c1-'a'].add(c2);
            mappedTo[c2-'a'].add(c1);
        }
        char[] mapping = new char[26];
        
        char[] res = new char[baseStr.length()];
        for(int i = 0; i < baseStr.length(); i++) {
            char curr = baseStr.charAt(i);
            char mapped = mapping[curr - 'a'];
            if(mapped == '\u0000') {
                res[i] = curr;
            } else {
                res[i] = mapped;
            }
        }
        return String.valueOf(res);
    }
    private char getMin(char ch1, char ch2, char ch3, char ch4) {
        char[] arr = {ch1, ch2, ch3, ch4};
        char min = Character.MAX_VALUE;
        for(char ch: arr) {
            if(ch == '\u0000') {
                continue;
            } else if(ch < min) {
                min = ch;
            }
        }
        return min;
    }
    public long minimumTime(int[] time, int totalTrips) {
        int min = IntStream.of(time).min().getAsInt();
        long lo = 1, hi = (long)min*totalTrips;
        long res = hi;
        while(lo <= hi) {
            long mid = lo + (hi-lo)/2;
            if(canComplete(totalTrips, mid, time)) {
                res = mid;
                hi = mid-1;
            } else {
                lo = mid+1;
            }
        }
        return res;
    }

    private boolean canComplete(int tripsReq, long timeAllocated, int[] time) {
        int tripTaken = 0;
        for(int t: time) {
            tripTaken += timeAllocated/t;
            if(tripTaken >= tripsReq) {
                break;
            }
        }
        return tripTaken >= tripsReq;
    }
    public int minimumSize(int[] nums, int maxOperations) {
        long sum = 0;
        for(int num : nums) {
            sum += num;
        }
        long lo = 1, hi = sum;
        long res = hi;
        while(lo <= hi) {
            long mid = lo + (hi-lo)/2;
            if(canReduceTo(mid, maxOperations, nums)) {
                res = mid;
                hi = mid -1;
            } else {
                lo = mid+1;
            }
        }
        return (int)res;
    }

    private boolean canReduceTo(long size, int maxAllowed, int[] nums) {
        int countOp = 0;
        for(int num: nums) {
            if(num <= size) {
                continue;
            }
            long opr = num/size;
            if(opr*size == num) {
                opr -= 1;
            }
            countOp += opr;
            if(countOp > maxAllowed) {
                break;
            }
        }
        return countOp <= maxAllowed;
    }

    public int[][] insert(int[][] intervals, int[] newInterval) {
        //Left most index for newInterval
        int index = getUpperBound(intervals, newInterval);
        List<int[]> intervalList = new ArrayList<>(Arrays.asList(intervals));
        intervalList.add(index, newInterval);
        Deque<int[]> stack = new ArrayDeque<>();
        for(int[] interval: intervalList) {
            while (!stack.isEmpty() && overlaps(interval, stack.peek())) {
                interval = merge(interval, stack.pop());
            }
            stack.push(interval);
        }
        int[][] result = new int[stack.size()][2];
        for(int i = stack.size()-1; i >= 0; i--) {
            result[i] = stack.pop();
        }
        return result;
    }

    private int[] merge(int[] a, int[] b) {
        return new int[] {
            Math.min(a[0], b[0]),
            Math.max(a[1], b[1])
        };
    }

    public int minMeetingRooms(int[][] intervals) {
        int[][] points = new int[intervals.length*2][2];
        for(int i = 0; i < intervals.length; i++) {
            points[i*2][0] =  intervals[i][0];
            points[i*2][1] =  1;
            points[i*2 + 1][0] =  intervals[i][1];
            points[i*2 + 1][1] =  -1;
        }
        Arrays.sort(points, Comparator.<int[]>comparingInt(a -> a[0]).thenComparing(a -> a[1]).reversed());
        int count = 0, max = 0;
        for(int[] point: points) {
            count += point[1];
            max = Math.max(max, count);
        }
        return max;
    }

    public int mostBooked(int n, int[][] meetings) {
        int[] rooms = new int[n];
        PriorityQueue<Integer> available = new PriorityQueue<>();
        PriorityQueue<int[]> busy = new PriorityQueue<>(
                Comparator.<int[]>comparingInt(a -> a[1]).thenComparing(a -> a[0])
        );
        for(int i = 0; i < n; i++) {
            available.add(i);
        }
        Arrays.sort(meetings, Comparator.<int[]>comparingInt(a -> a[0]));
        for(int[] meeting: meetings) {
            //Claim all busy rooms, who had got free before this meeting start
            while(!busy.isEmpty() && busy.peek()[1] <= meeting[0]) {
                int[] freedRoom = busy.poll();
                available.offer(freedRoom[0]);
            }
            if(!available.isEmpty()) {
                //Free room is available
                int nextAvailableRoom = available.poll();
                rooms[nextAvailableRoom] += 1;
                busy.offer(new int[] {
                        nextAvailableRoom, meeting[1]
                });
                //System.out.printf("1. Assign meeting (%d, %d) to %d\n", meeting[0], meeting[1], nextAvailableRoom);
            } else {
                //No free room, get next room from busy, and assign to it
                int[] nextGoingToFree = busy.poll();
                rooms[nextGoingToFree[0]] += 1;
                busy.offer(new int[] {
                        nextGoingToFree[0], nextGoingToFree[1] + meeting[1] - meeting[0]
                });
                //System.out.printf("2. Assign meeting (%d, %d) to %d\n", meeting[0], meeting[1], nextGoingToFree[0]);
            }
        }
        int mostBooked = 0;
        for(int i = 0; i < n; i++) {
            if(rooms[i] > rooms[mostBooked]) {
                mostBooked = i;
            }
        }
        return mostBooked;
    }

    private int getUpperBound(int[][] intervals, int[] newInterval) {
        int lo = 0, hi = intervals.length-1, res = intervals.length;
        while(lo <= hi) {
            int mid = lo + (hi-lo)/2;
            if(intervals[mid][0] > newInterval[0]) {
                res = mid;
                hi = mid - 1;
            } else {
                lo = mid+1;
            }
        }
        return res;
    }

    private boolean overlaps(int[] a, int[] b) {
        return Math.max(a[0], b[0]) <= Math.min(a[1], b[1]);
    }

    public int jump(int[] nums) {
        int len = nums.length;
        if(len <= 1) {
            return 0;
        }
        int maxReach = nums[0];
        int remainingSteps = nums[0];
        int jumps = 1;
        for(int i = 1; i < len; i++) {
            if(i == len-1) {
                break;
            }
            maxReach = Math.max(maxReach, i+nums[i]);
            remainingSteps -= 1;
            if(remainingSteps == 0) {
                jumps += 1;
                remainingSteps = maxReach - 1;
            }
        }
        return jumps;
    }

    private String maskedNum(String s) {
        StringBuilder sb = new StringBuilder();
        for(char ch : s.toCharArray()) {
            if(ch == '+' || (ch >= '0' && ch <= '9')) {
                sb.append(ch);
            }
        }
        int len = sb.length();
        s = sb.toString();
        sb.setLength(0);
        if(len == 13) {
            sb.append("+***-***-***-");
        } else if(len == 12) {
            sb.append("+**-***-***-");
        } else if(len == 11) {
            sb.append("+*-***-***-");
        } else {
            sb.append("***-***-");
        }
        sb.append(len-4);
        return sb.toString();
    }

}
