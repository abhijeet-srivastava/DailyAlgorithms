package com.leetcode.mothly.dec21;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CourseSchedule {
    public static void main(String[] args) {
        CourseSchedule cs = new CourseSchedule();
//        cs.testMergeInterval();
//        cs.testClosestPoints();
        cs.testCalculate();
    }

    private void testCalculate() {
        String s = "1-1+1";
        int res = calculate(s);
        System.out.println(res);
    }

    private void testClosestPoints() {
        int k = 2;
        int[][] points = {{
            3, 3
        }, {
            5, -1
        },{
            -2, 4
        }};
        int[][] clsest = kClosest(points, 2);
    }

    private void testMergeInterval() {
        //int[][] intervals = {{1, 3}, {2, 6}, {8, 10}, {15, 18}};
        int[][] intervals = {{1, 4}, {0,2}, {3,5}};
        int[][] merged = merge(intervals);
        Arrays.stream(merged).map(e -> String.format("%d,%d", e[0], e[1])).forEach(System.out::println);
    }

    public int[] findOrderbfs(int numCourses, int[][] prerequisites) {
        int[] indegree = new int[numCourses];
        List<Integer>[] GRAPH = new List[numCourses];
        for(int i = 0; i < numCourses; i++) {
            GRAPH[i] = new ArrayList<>();
        }
        for(int[] prerequisite : prerequisites) {
            int src = prerequisite[1];
            int dest = prerequisite[0];
            GRAPH[src].add(dest);
            indegree[dest] += 1;
        }
        Queue<Integer> queue = new LinkedList<>();
        for(int courseNum = 0; courseNum < numCourses; courseNum++) {
            if(indegree[courseNum] == 0){
                queue.add(courseNum);
            }
        }
        int courseIndex = 0;
        int[] order = new int[numCourses];
        while(!queue.isEmpty()) {
            int currCourse = queue.poll();
            order[courseIndex++] = currCourse;
            for(int dependentOnCurr : GRAPH[currCourse]) {
                indegree[dependentOnCurr] -= 1;
                if(indegree[dependentOnCurr] == 0) {
                    queue.add(dependentOnCurr);
                }
            }
        }
        if(courseIndex < numCourses) {
            order = new int[0];
        }
        return order;
    }
    public int[] findOrderDfs(int numCourses, int[][] prerequisites) {
        int[] indegree = new int[numCourses];
        List<Integer>[] GRAPH = new List[numCourses];
        for(int i = 0; i < numCourses; i++) {
            GRAPH[i] = new ArrayList<>();
        }
        for(int[] prerequisite : prerequisites) {
            int src = prerequisite[1];
            int dest = prerequisite[0];
            GRAPH[src].add(dest);
            indegree[dest] += 1;
        }
        int[] visited = new int[numCourses];
        Arrays.fill(visited, -1);
        List<Integer> order = new ArrayList<>();
        boolean isPossible = true;
        for(int course = 0; course < numCourses; course++) {
            if(visited[course] < 0) {
                isPossible = dfsCourse(course, visited, order, GRAPH);
                if(!isPossible) {
                    return new int[0];
                }
            }
        }
        Collections.reverse(order);
        for(int i = 0; i < numCourses; i++) {
            indegree[i] = order.get(i);
        }
        return indegree;
    }
    private boolean dfsCourse(int currentCourse, int[] visited, List<Integer> order, List<Integer>[] GRAPH) {
        if(visited[currentCourse] >= 0) {
            return true;
        }
        visited[currentCourse] = 0;
        for(int dependentCourse : GRAPH[currentCourse]) {
            if(visited[dependentCourse] == 0) {
                return false;
            }
            boolean isPossible = dfsCourse(dependentCourse, visited, order, GRAPH);
            if(!isPossible) {
                return false;
            }
        }
        visited[currentCourse] = 1;
        order.add(currentCourse);
        return true;
    }

    public int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, (i, j) -> (i[0] == j[0] )? (i[1] - j[1]) : (i[0] - j[0]));
        Stack<int[]> stack = new Stack<>();
        for(int i = intervals.length-1; i >= 0; i--) {
            stack.push(intervals[i]);
        }
        List<int[]> merged = new ArrayList<>();
        while(!stack.isEmpty()) {
            int[] interval1 = stack.pop();
            if(stack.isEmpty()) {
                merged.add(0, interval1);
                continue;
            }
            int[] interval2 = stack.pop();
            if(isOverLap(interval1, interval2)) {
                stack.push(merge(interval1, interval2));
                //merged.add(0, merge(interval1, interval2));
            } else {
                merged.add(0, interval1);
                stack.push(interval2);
            }
        }
        return merged.toArray(new int[0][0]);
    }
    private boolean isOverLap(int[] interval1, int[] interval2) {
        return Math.max(interval1[0], interval2[0]) <  Math.min(interval1[1], interval2[1]);
    }
    private int[] merge(int[] interval1, int[] interval2) {
        return new int[]{Math.min(interval1[0], interval2[0]), Math.max(interval1[1], interval2[1])};
    }
    public List<Interval> employeeFreeTime(List<List<Interval>> schedule) {
        List<Interval> schedules = schedule.stream()
                .flatMap(List::stream)
                .sorted(Comparator.comparingInt((Interval i) -> i.start).thenComparing(i -> i.end))
                .collect(Collectors.toList());
        List<Interval> result = new ArrayList<>();
        Interval tmp = schedules.get(0);
        for(int index = 1; index < schedule.size(); index++) {
            Interval next = schedules.get(index);
            if(isOverLap(tmp, next)) {
                next.start = Math.min(tmp.start, next.start);
                next.end = Math.max(tmp.end, next.end);
            } else {
                result.add(new Interval(Math.min(tmp.end, next.end), Math.max(tmp.start, next.start)));
            }
            tmp = next;
        }
        return result;
    }

    private boolean isOverLap(Interval int1, Interval int2) {
        return Math.max(int1.start, int2.start) < Math.min(int2.end, int2.end);
    }

    class Interval {
        public int start;
        public int end;

        public Interval() {}

        public Interval(int _start, int _end) {
            start = _start;
            end = _end;
        }
    };

    public int[][] kClosest(int[][] points, int k) {
        PriorityQueue<int[]> queue = new PriorityQueue<>((a, b) -> (b[0]*b[0] + b[1]*b[1]) - (a[0]*a[0] + a[1] * a[1]));
        for(int[] point: points) {
            queue.offer(point);
            if(queue.size() > k) {
                int[] polled = queue.poll();
                System.out.printf("To inset {%d, %d}, Polled {%d, %d}\n", point[0], point[1], polled[0], polled[1]);
            }
        }
        return queue.stream().toArray(e ->new int[k][]);
    }
    public List<String> topKFrequent(String[] words, int k) {
        Map<String, Long> map = Arrays.stream(words).collect(Collectors.groupingBy(Function.identity(),
                LinkedHashMap::new,                                                                                   Collectors.counting()));
        return map.entrySet().stream()
                        .sorted((e1, e2) -> (e1.getValue().intValue() == e2.getValue().intValue())
                                ? e1.getKey().compareTo(e2.getKey())
                                : e2.getValue().intValue() - e1.getValue().intValue())
                .limit(k)
                .map(Map.Entry::getKey).collect(Collectors.toList());
    }

    public int calculate(String s) {
        Stack<String> stack = new Stack<>();
        for(int i = 0; i < s.length(); i++) {
            if(Character.isSpaceChar(s.charAt(i))) {
                continue;
            }else if(Character.isDigit(s.charAt(i))) {
                while(i < s.length() && Character.isSpaceChar(s.charAt(i))) {
                    i += 1;
                }
                int j = i;
                while(j < s.length() && Character.isDigit(s.charAt(j))) {
                    j += 1;
                }
                stack.push(s.substring(i, j));
                i = j-1;
            } else if( s.charAt(i) == '+' || s.charAt(i) == '-') {
                stack.push(String.valueOf(s.charAt(i)));
            } else {
                char operator = s.charAt(i);
                i += 1;
                while(i < s.length() && Character.isSpaceChar(s.charAt(i))) {
                    i += 1;
                }
                int j = i+1;
                while(j < s.length() && Character.isDigit(s.charAt(j))) {
                    j += 1;
                }
                int operand2 = Integer.parseInt(s.substring(i, j));
                int operand1 = Integer.parseInt(stack.pop());
                stack.push(apply(operand1, operand2, operator));
                i = j-1;
            }
        }
        int res = 0;
        while(!stack.isEmpty()) {
            int op1 = Integer.parseInt(stack.pop());
            if(stack.isEmpty()) {
                res = op1;
                break;
            }
            char operator = stack.pop().charAt(0);
            int op2 = Integer.parseInt(stack.pop());
            stack.push(apply(op2, op1, operator));
        }
        return res;
    }

    private String apply(int operand1, int operand2, char operator) {
        System.out.printf("Op1: %d Op2: %d Operator: %c\n", operand1, operand2, operator);
        if(operator == '-') {
            return String.valueOf(operand1 - operand2);
        } else if(operator == '+') {
            return String.valueOf(operand1 + operand2);
        } else if(operator == '/') {
            return String.valueOf(operand1 / operand2);
        } else {
            return String.valueOf(operand1 *operand2);
        }
    }
    public boolean asteroidsDestroyed(int mass, int[] asteroids) {
        TreeMap<Integer, Long> astCnt
                = IntStream.of(asteroids).mapToObj(Integer::valueOf)
                .collect(Collectors.groupingBy(Function.identity(), TreeMap::new, Collectors.counting()));
        //TreeSet<Integer> ast = IntStream.of(asteroids).mapToObj(Integer::valueOf).collect(Collectors.toCollection(TreeSet::new));
        while(astCnt.size() > 0) {
            Integer next = astCnt.floorKey(mass);
            System.out.printf("For mass[%d] gpt key: %s\n", mass, next == null ? "NULL": (next + " = " + astCnt.get(next)));
            if(next == null) {
                break;
            }
            mass += next;
            long remaining = astCnt.get(next) - 1;
            if(remaining == 0) {
                astCnt.remove(next);
            } else {
                astCnt.put(next, remaining);
            }
        }
        return astCnt.isEmpty();
    }
}
