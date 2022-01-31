package com.oracle.casb.leetcode;

import java.security.SecureRandom;
import java.util.*;
import java.util.LinkedList;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created By : abhijsri
 * Date  : 19/11/18
 **/
public class Contest111 {

    public static void main(String[] args) {
        Contest111 cont = new Contest111();
        //cont.solveIsMountain();
        //cont.solveMaxMin();
        //cont.arrangeLogs();
        cont.testReservoir();
    }

    private  void testReservoir() {
        List<Integer> sample = IntStream.range(0, 1000).boxed()
                .collect(new ReservoirSampler<>(50));
        System.out.println("Streamed Sample :" + sample);
    }

    private void arrangeLogs() {
        String[] input = {"wpylev6cnqv8 447241070789889321113517804297550370",
                "2syod 60098540876848105552318 69698830167476212 2",
                "iuw2x1r qmxealfvosqgkv yunonsq nxcuwudndrpsroty h",
                "vclnqwkdr7h 5515 892770977116949342793820104705 3",
                "5y08u4f5ba swixvlwipfhtxavvzrdyxtnronckklvh f kzd",
                "2k63p1p 542447297738584 22237063423417308275099706",
                "qrj 467859 382 451796621324556 12022 72631305 0429",
                "vopck4 huqziggmwvcsermnujnpplihttviwei lsrqmbw b n",
                "s 7257018672440110203152567646 961657508453405583",
                "94j 1800907 54116251858 19612167 218608 1 504204 4",
                "u34lvgmoh 631217074786612695089137448 5635620839 5",
                "dsrojn8aeojx 27159799084241651870 76594680 195 051",
                "kteuav 77685739 6366458436088787165747310 78 3849",
                "dg8gco0sc2 10811560194867165521681 718 42498 1 52",
                "gdg 900670532316533434070453812 9115641245822 122",
                "ytlmfg 658910166131 170942932 70238 0783568 64777",
                "gi6d2lg8 ekwbnzeqrrzijgexvpcgfnhrkfhtmegaqon hsa u",
                "y8zhzn jjtbwpfrbcsuj zmseejb vcsovstaewtgtj nbsnlj",
                "cp1qsk5 dstuzhik alqxnmztxnwdve simoynsfffwyacl nr",
                "a11zjdza15gi zuighjavkfidjjx kgmbriwxbjcsv shtfjz",
                "3dpx3f28wa1 dhe jb uatgwooxclfj w t qaahvyiy lthj",
                "9ymabvjk4xq waujeijoltuy heoekaqmggmpdkynngne sl x",
                "n3l09gzpppgc dfnfxeaskknllixe tvtbemewtkwd bfbhm l",
                "one 143245418564431590 555274555077126490673 23406",
                "2dlvtxq57 11 0399853766553806651446400571374174 7",
                "l0xsyrtf9foe jcsjyzbux hpxxwwsyxwjcdqbuzrxuvdf n n",
                "o ufjxgmiohhacgwhprzqklpbleggurqygvmyrqtiwwusaa fq",
                "gr jmhsaanfrndkvkrdepfqvnathkheq bjtvzacabyfch xw",
                "12hrfmpyxql 509513107446443470266800090 12 36792 5",
                "ei mfmrujazj hvcaeoejhbvsxlnbcofdparedjvuqoigbwv h",
                "158mo1 fxwvcxyaz gimthvk t tbkpxnyomitu i foi t i",
                "1mpnz91abn8 857526216042344 529 86 555850 074136 6",
                "gvf69aycgz vd wzshq vqqcoscdfgtclfpoqz kcnbk yqrta",
                "05tv1dyuuln 3010253552744498232332 86540056 5488 0",
                "1kjt2sp 76661129172454994454966100212569762 877775",
                "k8fq mhahouacluusiypbhmbxknagj nrenkpsijov tspqd s",
                "a 05783356043073570183098305205075240023467 24 63",
                "0c 821 1288302446431573478713998604686702 0584599",
                "e 02985850443721 356058 49996149758367 64432663 32",
                "1zayns7ifztd kwmwsthxzxvvctzoejspeobtavhzxzpot u n",
                "o0sh3 qn nqjaghnmkckhvuauuknqbuxwalgva nt gfhqm en",
                "094qnly wgkmupkjobuup gshx wcblufmjumyuahsx n ai k",
                "j69r2ugwa6 zuoywue chhwsfdprfygvliwzmohqgrxn ubwtm",
                "2mkuap uqfwog jqzrkoorsompgkdlql wpauhkzvig ftb l",
                "x 929 4356109428379557082235487428356570127401 832",
                "jns07q8 idnlfsaezcojuafbgmancqpegbzy q qwesz rmy n",
                "phk1cna 086 027760883273 64658492093523655560824 2",
                "jbemfs9l9bs0 8147538504741452659388775 5 32 180 09",
                "ac9cwb9 524689619771630155 8125241949139653850678",
                "9eke perwsfqykyslfmcwnovenuiy urstqeqaezuankek czq"};
        String[] res = reorderLogFiles(input);
        System.out.println(Arrays.stream(res).collect(Collectors.joining(",")));
    }

    private void solveMaxMin() {
        int[] res = diStringMatch("IDDI");
        StringBuilder sb = new StringBuilder();
        for (int i : res) {
            sb.append(i).append(", ");
        }
        System.out.println(sb.substring(0, sb.length()-1));
    }
    private void solveIsMountain() {
        int[] arr = {9,8,7,6,5,4,3,2,1,0};
        boolean res = isMountain(arr);
        System.out.println("Array is " + (res ? "" : "not ") + "a mountain");
    }
    public int[] diStringMatch(String S) {
        int n = S.length(), left = 0, right = n;
        int[] res = new int[n + 1];
        for (int i = 0; i < n; ++i)
            res[i] = S.charAt(i) == 'I' ? left++ : right--;
        res[n] = left;
        return res;
    }


    public String[] reorderLogFiles1(String[] logs) {
        String[] res = new String[logs.length];
        List<String> letterLogs = new ArrayList<>();
        List<String> digitLogs = new ArrayList<>();
        for (String  log : logs) {
            if (Character.isDigit(log.charAt(log.indexOf(' ') + 1))) {
                digitLogs.add(log);
            } else {
                letterLogs.add(log);
            }
        }
        Collections.sort(letterLogs, (l1, l2) -> compareLogs1(l1, l2));
        for (int i = 0; i < letterLogs.size(); i++) {
            res[i] = letterLogs.get(i);
        }
        for (int i = letterLogs.size(), j = 0; j < digitLogs.size(); j++, i++) {
            res[i] = digitLogs.get(j);
        }
        return res;
    }

    private int compareLogs1(String l1, String l2) {
        String[] log1 = l1.split("\\s");
        String[] log2 = l2.split("\\s");
        int res = 0;
        int loop = Math.min(log1.length, log2.length);
        for (int i = 1; i < loop; i++) {
            res = log1[i].compareTo(log2[i]);
            if (res != 0) {
                break;
            }
        }
        if (res != 0) {
            return res;
        }
        if (log1.length == log2.length) {
            res = log1[0].compareTo(log2[0]);
        } else if (log1.length < log2.length) {
            res = 1;
        } else {
            res = -1;
        }
        return res;
    }

    private class Task {
        private int id;
        private int start;
        private int end;

        public Task(int id, int start, int end) {
            this.id = id;
            this.start = start;
            this.end = end;
        }

        public boolean overLaps(Task t) {
            /*int t1 = Math.max(start, t.start);
            int t2 = Math.min(end, t.end);
            return t1 <= t2;*/
            /*int cen1 = start + end;
            int cen2 = t.start + t.end;
            int rad1 = end -start;
            int rad2 = t.end - t.start;
            return Math.abs(cen1 - cen2) <= (rad1 + rad2);*/
            //return !(t.end < start || end < t.start);
            return start <= t.end && t.start <= end;
        }

        public int getId() {
            return id;
        }

        public int getStart() {
            return start;
        }

        public int getEnd() {
            return end;
        }
    }

    public String[] reorderLogFiles(String[] logs) {
        return Arrays.stream(logs).sorted((l1, l2) -> compareLogs(l1, l2)).toArray(String[]::new);
    }

    private int compareLogs(String l1, String l2) {
        String[] log1 = l1.split("\\s");
        String[] log2 = l2.split("\\s");
        try {
            if (Character.isDigit(log1[1].charAt(0)) && Character.isDigit(log2[1].charAt(0))) {
                return 0;
            } else if (Character.isDigit(log1[1].charAt(0)) && Character.isAlphabetic(log2[1].charAt(0))) {
                return -1;
            } else if (Character.isDigit(log2[1].charAt(0)) && Character.isAlphabetic(log1[1].charAt(0))) {
                return 1;
            } else {
                int res = 0;
                int loop = Math.min(log1.length, log2.length);
                for (int i = 1; i < loop; i++) {
                    res = log1[i].compareTo(log2[i]);
                    if (res != 0) {
                        break;
                    }
                }
                if (res != 0) {
                    return res;
                }
                if (log1.length == log2.length) {
                    res = log1[0].compareTo(log2[0]);
                } else if (log1.length < log2.length) {
                    res = 1;
                } else {
                    res = -1;
                }
                return res;
            }
        }catch (IllegalArgumentException ilaex) {
            System.err.printf("IllegalArgumentException for {%s} and {%s}\n", l1, l2 );
            ilaex.printStackTrace();
        }
        return 0;
    }

    private boolean isMountain(int[] arr) {
        boolean res = false;
        if (arr == null || arr.length < 3) {
            return res;
        }
        int curr = arr[0];
        int i = 1;
        while (i < arr.length && curr < arr[i]) {
            curr = arr[i];
            i += 1;
        }
        if (i == 1 || i == arr.length) {
            return res;
        }
        while (i < arr.length && curr > arr[i]) {
            curr = arr[i];
            i += 1;
        }
        if (i == arr.length) {
            res = true;
        }
        return res;
    }

    public class Solution {

        ListNode currentHead;
        int value;
        int visited = 0;
        final Random rand;

        public Solution(ListNode head) {
            this.value = head.val;
            currentHead = head;
            rand = new SecureRandom();
        }

        public int getRandom() {

            if (currentHead != null && visited > 0) {
                double random = rand.nextDouble() * (visited + 1);
                if (random > 0.5d) {
                    value = currentHead.val;
                }
            }
            currentHead = currentHead.next;
            visited += 1;
            return value;
        }
    }

    class ReservoirSampler<T> implements Collector<T, List<T>, List<T>> {

        final Random rand = new SecureRandom();
        final int sz;
        int c = 0;

        public ReservoirSampler(int size) {
            this.sz = size;
        }

        private void addIt(List<T> in, T s) {
            if (in.size() < sz) {
                in.add(s);
            }
            else {
                int replaceInIndex = (int) (rand.nextDouble() * (sz + (c++) + 1));
                if (replaceInIndex < sz) {
                    in.set(replaceInIndex, s);
                }
            }
        }
        @Override
        public Supplier<List<T>> supplier() {
            return ArrayList::new;
        }

        @Override
        public BiConsumer<List<T>, T> accumulator() {
            return this::addIt;
        }



        @Override
        public BinaryOperator<List<T>> combiner() {
            return (left, right) -> {
                left.addAll(right);
                return left;
            };
        }

        @Override
        public Function<List<T>, List<T>> finisher() {
            return (i) -> i;
        }

        @Override
        public Set<Characteristics> characteristics() {
            return EnumSet.of(Characteristics.UNORDERED, Characteristics.IDENTITY_FINISH);
        }
    }

    private int[] selectKItems(int stream[],  int k) {
        Random rand = new Random();
        int[] reservoir = new int[k];
        int i = 0;
        while(i < k) {
            reservoir[i] = stream[i];
            i += 1;
        }
        while(i < stream.length) {
            int reservoirIndex = rand.nextInt(k);
            reservoir[reservoirIndex] = stream[i];
            i += 1;
        }
        return reservoir;
    }
    public String alienOrder(String[] words) {
        Map<Character, Set<Character>> orderMap = new HashMap<>();
        Map<Character, Integer> indegree = new HashMap<>();
        for(String word: words) {
            for(char ch : word.toCharArray()) {
                orderMap.put(ch, new HashSet<Character>());
                indegree.put(ch, 0);
            }
        }
        Set<String> seen = new HashSet<>();
        for(int i = 1; i < words.length; i++) {
            String successor = words[i];
            String predecessor = words[i-1];
            if(successor.length() < predecessor.length()
                    && predecessor.startsWith(successor)) {
                return "";
            }
            for(int j = 0 ;j < Math.min(successor.length(), predecessor.length()); j++) {
                if(successor.charAt(j) == predecessor.charAt(j)) {
                    continue;
                }
                Character preceeds = predecessor.charAt(j);
                Character succeeds = successor.charAt(j);
                String key = preceeds + "_" + succeeds;
                String reverse = succeeds + "_" + preceeds;
                if(seen.contains(reverse)) {
                    return "";
                }else if(seen.contains(key)) {
                    break;
                }

                seen.add(key);
                orderMap.get(preceeds).add(succeeds);
                indegree.put(succeeds, indegree.get(succeeds) + 1);
                break;
            }
        }
        Queue<Character> queue = new LinkedList<>();
        for(Map.Entry<Character, Integer> entry : indegree.entrySet()) {
            if(entry.getValue() == 0) {
                queue.offer(entry.getKey());
            }
        }
        StringBuilder sb = new StringBuilder();
        while(!queue.isEmpty()) {
            Character current = queue.poll();
            sb.append(current);
            for(Character next : orderMap.get(current)) {
                indegree.put(next, indegree.get(next)-1);
                if(indegree.get(next) == 0) {
                    queue.offer(next);
                }
            }
        }
        return sb.length() < orderMap.size() ? "" : sb.toString();
    }

    public static int findIndex(int[] nums) {
        Random random = new Random();
        Map<Integer, Long> frequencyMap = IntStream
                .of(nums).mapToObj(Integer::valueOf)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        int maxFrequencyElement = nums[0];
        for(Map.Entry<Integer, Long> entry: frequencyMap.entrySet()) {
            if(entry.getValue() > frequencyMap.get(maxFrequencyElement)) {
                maxFrequencyElement = entry.getKey();
            }
        }
        int maxFrequency = frequencyMap.get(maxFrequencyElement).intValue();
        //Generate random number between
        int rand = random.nextInt(maxFrequency);
        for(int i = 0; i < nums.length; i++) {
            if(nums[i] == maxFrequencyElement) {
                if(rand == 0) {
                    return i;
                } else {
                    rand -= 1;
                }
            }
        }
        //System.out.printf("Nums: [%s]\n", IntStream.of(nums).mapToObj(String::valueOf).collect(Collectors.joining(", ")));
        return 0;
    }
    /**
     * Definition for singly-linked list.
    */
      public class ListNode {
          int val;
          ListNode next;
          ListNode(int x) { val = x; }
      }


}
