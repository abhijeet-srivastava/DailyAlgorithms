package com.oracle.casb.leetcode;

import com.google.common.collect.ImmutableMap;
import com.oracle.casb.common.ListNode;
import com.oracle.casb.common.TreeNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.stream.Collectors;


/**
 * Created By : abhijsri
 * Date  : 17/09/18
 **/
public class Contest102 {

    public static void main(String[] args) {
        Contest102 ct = new Contest102();
        //ct.testSum();
        //ct.testLambda();
        //ct.testMaxFruit();
        //ct.getMaxPath();
        //ct.testClosestPoints();
        //ct.testPascalRow();
        //ct.test2Str();
        //ct.testSort();
        //ct.testSortList();
        //ct.testMerge();
       // ct.testMaxDiff();
        //ct.testIsMatch();
        ct.testPath();
    }

    private void testPath() {
        String path = simplifyPath("/a/./b/../c/");
        System.out.printf("Simplyfied path %s \n", path);
    }
    private void testIsMatch() {
        System.out.println(isMatch("aab", "c*a*b"));
    }
    public boolean isMatch(String s, String p) {
        int strLen = s.length();
        int pLen = p.length();
        char[] sArr = s.toCharArray();
        char[] pArr = p.toCharArray();
        int pPtr = 0;
        for (int sPtr = 0; sPtr < strLen; sPtr++) {
            if(pPtr < pLen && (sArr[sPtr] == pArr[pPtr] || pArr[pPtr] == '.') ) {
                pPtr += 1;
            } else if (pPtr < pLen && pArr[pPtr] == '*') {
                if (sArr[sPtr] != sArr[sPtr-1] && pArr[pPtr-1] != '.') {
                    pPtr += 1;
                    sPtr -= 1;
                }
            } else {
                return false;
            }
        }
        return (pPtr == pLen || ((pPtr == pLen - 1) && pArr[pPtr] == '*'));
    }

    public String simplifyPath(String A) {
        String[] arr = A.split("/");
        Stack<String> ps = new Stack<>();
        for (String dir : arr) {
            if (dir.trim().isEmpty() || dir.equals(".")) {
                continue;
            } else if (dir.equals("..")) {
                if (!ps.isEmpty()) {
                    ps.pop();
                };
            } else  {
                ps.push(dir);
            }
        }
        List<String> path = new ArrayList<>();
        while (!ps.isEmpty()) {
            path.add(0, ps.pop());
        }
        return path.stream().collect(Collectors.joining("/", "/", ""));
    }
    private void testMaxDiff() {
        TreeNode<Integer> root = createTree1();
        int value = maxAncestorDiff(root);
        System.out.printf("Value %d\n", value);
    }

    private TreeNode<Integer> createTree1() {
        TreeNode<Integer> root = new TreeNode<>(8);
        root.setLeft(new TreeNode<>(3));
        root.getLeft().setLeft(new TreeNode<>(1));
        root.getLeft().setRight(new TreeNode<>(6));
        root.getLeft().getRight().setLeft(new TreeNode<>(4));
        root.getLeft().getRight().setRight(new TreeNode<>(7));

        root.setRight(new TreeNode<>(10));
        root.getRight().setRight(new TreeNode<>(14));
        root.getRight().getRight().setLeft(new TreeNode<>(13));
        return root;
    }

    private void testMerge() {
        ListNode[] array = createLists();
        ListNode merged = mergeKLists(array);
        System.out.println("Merged " + merged.getValue());
    }

    private ListNode[] createLists() {
        ListNode[] array = new ListNode[7];
        ListNode head1 = new ListNode(-10);
        head1.setNext(new ListNode(-9));
        head1.getNext().setNext(new ListNode(-9));
        head1.getNext().getNext().setNext(new ListNode(-3));
        head1.getNext().getNext().getNext().setNext(new ListNode(-1));
        head1.getNext().getNext().getNext().getNext().setNext(new ListNode(-1));
        head1.getNext().getNext().getNext().getNext().getNext().setNext(new ListNode(0));
        array[0] = head1;



        ListNode head2 = new ListNode(-5);
        array[1] = head2;

        ListNode head3 = new ListNode(4);
        array[2] = head3;

        ListNode head4 = new ListNode(-8);
        array[3] = head4;

        ListNode head5 = new ListNode(-9);
        head5.setNext(new ListNode(-6));
        head5.getNext().setNext(new ListNode(-5));
        head5.getNext().getNext().setNext(new ListNode(-4));
        head5.getNext().getNext().getNext().setNext(new ListNode(-2));
        head5.getNext().getNext().getNext().getNext().setNext(new ListNode(2));
        head5.getNext().getNext().getNext().getNext().getNext().setNext(new ListNode(3));
        array[5] = head5;


        ListNode head6 = new ListNode(-3);
        head6.setNext(new ListNode(-3));
        head6.getNext().setNext(new ListNode(-2));
        head6.getNext().getNext().setNext(new ListNode(-1));
        head6.getNext().getNext().getNext().setNext(new ListNode(0));

        array[6] = head6;

        for (int i = 0; i < array.length; i++) {
            System.out.printf("List %d - \t[%s]", i, printList(array[i]));
        }
        return array;
    }

    private void testSortList() {
        ListNode head = createList();
        ListNode res = sortList(head);
        System.out.printf("res %d\n", res.getValue());
    }

    private ListNode createList() {
        ListNode head = new ListNode(4);
        head.setNext(new ListNode(2));
        head.getNext().setNext(new ListNode(1));
        head.getNext().getNext().setNext(new ListNode(3));
        return head;
    }

    private void testSort() {
        int[] arr = {13, 10, 6, 3,5,8,4, 2,12, 7};
        bubbleSort(arr);
        System.out.println(Arrays.stream(arr).boxed().map(String::valueOf).collect(Collectors.joining(", ")));
    }

    private void test2Str() {
        System.out.println(intToStr(25));
    }

    public String intToStr(int number) {
        ImmutableMap.Builder<Integer, String> mapBuilder
                = ImmutableMap.builder();
        mapBuilder.put(0, "");
        mapBuilder.put(1, "One");
        mapBuilder.put(2, "Two");
        mapBuilder.put(3, "Three");
        mapBuilder.put(4, "Four");
        mapBuilder.put(5, "Five");
        mapBuilder.put(6, "Six");
        mapBuilder.put(7, "Seven");
        mapBuilder.put(8, "Eight");
        mapBuilder.put(9, "Nine");
        mapBuilder.put(10, "Ten");
        mapBuilder.put(11, "Eleven");
        mapBuilder.put(12, "Twelve");
        mapBuilder.put(13, "Thirteen");
        mapBuilder.put(14, "Forteen");
        mapBuilder.put(15, "Fifteen");
        mapBuilder.put(16, "Sixteen");
        mapBuilder.put(17, "Seventeen");
        mapBuilder.put(18, "Eighteen");
        mapBuilder.put(19, "Ninteen");
        mapBuilder.put(20, "Twenty");
        mapBuilder.put(30, "Thirty");
        mapBuilder.put(40, "Forty");
        mapBuilder.put(50, "Fifty");
        mapBuilder.put(60, "Sixty");
        mapBuilder.put(70, "Seventy");
        mapBuilder.put(80, "Eighty");
        mapBuilder.put(90, "Ninety");
        Map<Integer, String> toStr = mapBuilder.build();

        Map<Integer, String> value = ImmutableMap.of(
                0, "",
                1, "Hundred",
                2, "Thousand",
                3, "Lakh",
                4, "Crore"
        );
        int pos = 0;
        List<String> list = new ArrayList<>();
        int temp = number;
        while (temp != 0) {
            if (pos == 1) {
                int val = temp % 10;
                temp /= 10;
                if (val != 0) {
                    list.add(0, toStr.get(val) + " " + value.get(pos));
                }
            } else {
                int val = temp % 100;
                temp /= 100;
                if (val != 0) {
                    if (val % 10 == 0 || val < 20) {
                        list.add(0, toStr.get(val) + " " + value.get(pos));
                    } else {
                        int unit = val % 10;
                        int tenth = val / 10;
                        list.add(0, toStr.get(tenth * 10) + toStr.get(unit) + " " + value.get(pos));
                    }
                }
            }
            pos += 1;
        }
        return list.stream().collect(Collectors.joining(" ")).trim();
    }

    private void bubbleSort(int[] arr) {
        for (int i = arr.length - 1; i >= 0; i--) {
            for (int j = 0; j < i; j++) {
                if (arr[j] > arr[j+1]) {
                    int tmp = arr[j+1];
                    arr[j+1] = arr[j];
                    arr[j] = tmp;
                }
            }
        }
    }
    private void insertionSort(int[] arr) {
        //int len = arr.length;
        for (int i = 1; i < arr.length; i++) {
            int temp = arr[i];
            int j = i-1;
            for (; j >= 0 && arr[j] > temp; j--) {
                arr[j+1] = arr[j];
            }
            arr[j+1] = temp;
        }
    }

    private void testPascalRow() {
        List<Integer> row = getRow(7);
        String str = row.stream().map(String::valueOf).collect(Collectors.joining(","));
        System.out.println(str);
    }

    private void testClosestPoints() {
        int[] array = {3,6,2,3};
        int peri =largestPerimeter(array);
        System.out.println(peri);
    }


    public int maxAncestorDiff(TreeNode<Integer> root) {
        int[] res = {0};
        if (root == null) {
            return 0;
        }
        int[] max = {root.getValue()};
        int[] min = {root.getValue()};
        maxAncestorDiff(root, max, min, res);
        return res[0];
    }

    private void maxAncestorDiff(TreeNode<Integer> root, int[] max, int[] min, int[] res) {
        if (root == null) {
            return;
        }
        System.out.printf("Root: %d, Max: %d, Min: %d, res: %d\n", root.getValue(), max[0], min[0], res[0]);
        res[0] = Math.max(res[0], Math.abs(max[0] - root.getValue()));
        res[0] = Math.max(res[0], Math.abs(min[0] - root.getValue()));
        max[0] = Math.max(max[0], root.getValue());
        min[0] = Math.min(min[0], root.getValue());

        int[] left = {0};
        maxAncestorDiff(root.getLeft(), max, min, left);
        int[] right = {0};
        maxAncestorDiff(root.getRight(), max, min, res);
        res[0] = Math.max(res[0], left[0]);
        res[0] = Math.max(res[0], right[0]);
    }

    public int largestPerimeter(int[] A) {
        Integer[] array = Arrays.stream(A).boxed().sorted(Comparator.reverseOrder()).toArray(Integer[]::new);
        int i = 0;
        int j = 1;
        int k = 2;

        while (i < A.length && j < A.length && k < A.length && array[i] >= (array[j] + array[k])) {
            i += 1;
            j += 1;
            k += 1;
        }
        return  (k >= A.length || (array[i] >= (array[j] + array[k]))) ? 0 :  (array[i] + array[j] + array[k]);
    }

    public int[][] kClosest(int[][] points, int K) {
        PriorityQueue<int[]> queue = new PriorityQueue<>(
                (a,b) -> (b[0] * b[0] + b[1] * b[1]) - (a[0] * a[0] + a[1] * a[1])
        );

        for (int[] point : points) {
            if (queue.size() <= K) {
                queue.add(point);
            }
            if (queue.size() > K) {
                queue.remove();
            }
        }
        int[][] res = new int[K][2];
        int i = 0;
        for (int[] point : queue) {
            res[i++] = point;
        }
        return res;
    }

    private void getMaxPath() {
        TreeNode<Integer> root = createTree();
        int maxLen = maxPathSum(root);
        System.out.println("Max path len " + maxLen);
    }

    private TreeNode<Integer> createTree() {
        TreeNode<Integer> root = new TreeNode<>(1);
        root.setLeft(new TreeNode<>(2));
        root.setRight(new TreeNode<>(3));
        return root;
    }

    public int maxPathSum(TreeNode<Integer> root) {
        int[] res = findPathSum(root);
        return Math.max(res[0], res[1]);
    }

    public int[] findPathSum(TreeNode<Integer> root) {
        int[] res = new int[2];
        if (root == null) {
            return res;
        }
        int[] left = findPathSum(root.getLeft());
        int[] right = findPathSum(root.getRight());

        res[0] = Math.max(left[0], right[0]) + root.getValue();
        int max = Math.max(left[1], right[1]);
        res[1] = Math.max(max, left[0] + right[0] + root.getValue());
        return res;


    }


    public int max(int[] arr) {
        int res = arr[0];
        for (int a : arr) {
            if (a > res) {
                res = a;
            }
        }
        return res;
    }

    public int sumSubarrayMins(int[] A) {
        int MOD = 1_000_000_007;
        int[] prev = new int[A.length];
        int[] next = new int[A.length];

        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < A.length; i++) {
            while (!stack.isEmpty() && A[stack.peek()] >= A[i]) {
                stack.pop();
            }
            prev[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(i);
        }
        stack = new Stack<>();
        for (int i = A.length-1; i >= 0; i--) {
            while (!stack.isEmpty() && A[stack.peek()] > A[i]) {
                stack.pop();
            }
            next[i] = stack.isEmpty() ? A.length : stack.peek();
            stack.push(i);
        }
        long count = 0;
        for (int i = 0; i < A.length; i++) {
            count += (i - prev[i])*(next[i] - i) % MOD * A[i]%MOD;
        }
        return Long.valueOf(count).intValue();
    }

    private void testMaxFruit() {
        int[] trees = {3,3,3,1,2,1,1,2,3,3,4};
        int fruits = totalFruit(trees);
        System.out.println(fruits);
    }

    public int totalFruit(int[] tree) {
        int ans = 0, i = 0;
        Counter count = new Counter();
        for (int j = 0; j < tree.length; ++j) {
            count.add(tree[j], 1);
            while (count.size() >= 3) {
                count.add(tree[i], -1);
                if (count.get(tree[i]) == 0)
                    count.remove(tree[i]);
                i++;
            }

            ans = Math.max(ans, j - i + 1);
        }

        return ans;
    }

    private void testLambda() {
        int[] array = {29959,29867,29822,29704,29676,29650,29577,29488,29286,29255,29232,29207,29071,29034,28925,28849,28731,28693,28624,28606,28591,28397,28357,28308,28295,28210,28119,28090,28004,27903,27845,27830,27777,27736,27640,27540,27506,27428,27341,27308,27182,27152,27122,27029,26928,26872,26796,26765,26663,26597,26580,26530,26498,26475,26436,26406,26382,26312,26213,26134,26088,26025,25943,25912,25875,25845,25810,25702,25638,25614,25531,25524,25488,25470,25444,25402,25283,25262,25121,24988,24958,24886,24769,24697,24635,24595,24490,24456,24453,24346,24313,24248,24200,24148,24107,24052,24044,24021,23970,23908,23897,23835,23752,23741,23714,23661,23596,23545,23509,23470,23439,23409,23350,23215,23166,23155,23100,23024,22923,22825,22793,22627,22613,22536,22450,22383,22312,22268,22205,22175,22136,22028,21971,21900,21824,21769,21726,21583,21546,21513,21494,21428,21327,21264,21254,21174,21140,21112,21000,20921,20902,20830,20817,20783,20735,20657,20616,20573,20485,20378,20363,20305,20259,20210,20114,20002,19846,19785,19747,19667,19645,19622,19610,19580,19542,19516,19454,19392,19310,19277,19194,19131,19090,19004,18883,18845,18791,18781,18668,18591,18518,18475,18368,18331,18310,18287,18217,18114,18092,18048,17990,17964,17912,17836,17740,17704,17630,17613,17573,17428,17356,17341,17300,17260,17180,17174,17126,17071,17041,16866,16850,16828,16672,16618,16577,16499,16407,16357,16318,16293,16202,16150,16075,16041,15948,15921,15844,15843,15785,15764,15668,15626,15579,15473,15387,15255,15190,15139,15062,14996,14954,14918,14907,14902,14867,14851,14817,14799,14751,14720,14536,14506,14474,14353,14303,14280,14185,14107,14012,13932,13858,13781,13585,13563,13533,13451,13412,13362,13249,13208,13181,13064,13037,12961,12926,12892,12786,12731,12611,12573,12506,12502,12496,12470,12443,12370,12262,12182,12153,12069,12000,11847,11806,11781,11708,11687,11593,11550,11445,11372,11329,11308,11291,11268,11241,11191,11027,10982,10879,10862,10776,10695,10603,10502,10464,10350,10338,10305,10273,10176,10124,10094,10038,9953,9935,9812,9786,9743,9728,9508,9472,9383,9349,9236,9215,9130,9124,9042,9008,8988,8901,8833,8809,8780,8716,8580,8462,8334,8321,8305,8280,8257,8246,8137,8077,8043,8016,7984,7955,7927,7906,7746,7663,7653,7572,7542,7530,7489,7420,7390,7361,7337,7245,7210,7188,7175,7096,6898,6846,6745,6675,6569,6478,6427,6363,6284,6260,6243,6206,6154,6135,6078,6061,6017,5995,5917,5863,5836,5793,5763,5743,5678,5572,5532,5459,5384,5341,5299,5251,5231,4995,4933,4861,4740,4672,4625,4496,4445,4361,4282,4215,4135,4097,4028,3917,3862,3711,3553,3498,3410,3388,3384,3288,3279,3244,3221,3181,3171,3150,3060,3035,2975,2965,2834,2760,2637,2584,2533,2440,2383,2311,2285,2255,2211,2192,2121,2054,2010,1964,1850,1724,1642,1577,1411,1409,1332,1296,1265,1256,1220,1195,937,903,880,811,739,720,650,609,547,533,459,434,384,279,231,163,102,78,30,5,52,100,155,217,277,328,389,446,473,546,583,649,702,734,768,857,882,912,1043,1219,1243,1258,1290,1325,1359,1409,1567,1642,1679,1726,1873,1965,2017,2088,2172,2204,2226,2273,2288,2316,2434,2522,2558,2622,2678,2790,2933,2965,3025,3037,3071,3167,3180,3194,3233,3269,3282,3383,3387,3401,3465,3528,3595,3801,3910,4020,4078,4128,4213,4271,4295,4420,4472,4612,4663,4739,4845,4891,4980,5109,5241,5284,5335,5379,5388,5478,5546,5639,5705,5751,5766,5803,5855,5879,5975,6000,6024,6070,6093,6137,6156,6212,6256,6276,6304,6421,6441,6537,6614,6743,6844,6893,7087,7169,7183,7200,7237,7262,7352,7376,7398,7441,7491,7541,7564,7602,7656,7707,7814,7924,7940,7958,8014,8036,8048,8132,8141,8250,8279,8288,8321,8331,8374,8515,8655,8723,8807,8825,8878,8953,8990,9011,9077,9128,9172,9219,9276,9383,9420,9499,9535,9736,9744,9801,9900,9951,10038,10093,10119,10147,10265,10301,10314,10340,10456,10499,10564,10622,10767,10802,10876,10882,10997,11063,11217,11243,11276,11299,11314,11365,11407,11456,11587,11627,11705,11751,11792,11831,11901,12012,12118,12180,12240,12296,12385,12469,12473,12497,12503,12537,12578,12723,12778,12858,12901,12936,13020,13048,13136,13195,13232,13325,13377,13424,13493,13547,13564,13724,13856,13911,13938,14075,14151,14234,14300,14353,14395,14499,14507,14705,14724,14796,14802,14823,14858,14882,14905,14914,14936,14962,15049,15114,15161,15237,15272,15399,15565,15587,15666,15749,15778,15830,15843,15864,15928,16039,16075,16141,16163,16246,16315,16333,16389,16415,16526,16601,16650,16798,16845,16861,16991,17046,17090,17140,17178,17186,17292,17305,17343,17419,17456,17610,17617,17693,17728,17783,17909,17918,17970,18032,18083,18104,18114,18223,18296,18330,18363,18428,18496,18578,18660,18733,18782,18792,18861,18929,19069,19127,19184,19269,19279,19355,19394,19494,19539,19559,19599,19612,19643,19666,19745,19760,19815,19864,20012,20141,20231,20270,20330,20370,20380,20500,20595,20617,20690,20751,20811,20824,20843,20910,20925,21044,21126,21165,21198,21260,21280,21343,21467,21505,21531,21564,21640,21755,21817,21885,21929,22010,22103,22159,22196,22229,22270,22368,22414,22515,22570,22615,22630,22806,22864,22951,23030,23107,23155,23191,23226,23399,23438,23464,23487,23524,23559,23634,23667,23719,23747,23764,23869,23901,23936,24012,24022,24045,24074,24141,24185,24204,24272,24327,24452,24455,24490,24560,24615,24641,24734,24815,24890,24963,25025,25242,25282,25283,25414,25446,25475,25489,25527,25586,25636,25640,25771,25844,25848,25883,25923,26005,26048,26106,26157,26312,26359,26395,26429,26465,26491,26513,26558,26584,26601,26667,26770,26864,26900,26996,27118,27129,27176,27272,27313,27389,27478,27517,27580,27700,27761,27811,27844,27848,27967,28051,28108,28176,28264,28302,28332,28380,28525,28591,28617,28681,28727,28744,28874,28994,29047,29123,29221,29239,29274,29347,29493,29596,29668,29694,29717,29847,29871};
        int[] res = Arrays.stream(array).boxed().sorted((a,b) -> Integer.compare(a%2, b%2)).mapToInt(a -> a).toArray();
        System.out.println(res);

    }

    private void testSum() {
        int sum = sumSubarrayMins1(new int[]{29959,29867,29822,29704,29676,29650,29577,29488,29286,29255,29232,29207,29071,29034,28925,28849,28731,28693,28624,28606,28591,28397,28357,28308,28295,28210,28119,28090,28004,27903,27845,27830,27777,27736,27640,27540,27506,27428,27341,27308,27182,27152,27122,27029,26928,26872,26796,26765,26663,26597,26580,26530,26498,26475,26436,26406,26382,26312,26213,26134,26088,26025,25943,25912,25875,25845,25810,25702,25638,25614,25531,25524,25488,25470,25444,25402,25283,25262,25121,24988,24958,24886,24769,24697,24635,24595,24490,24456,24453,24346,24313,24248,24200,24148,24107,24052,24044,24021,23970,23908,23897,23835,23752,23741,23714,23661,23596,23545,23509,23470,23439,23409,23350,23215,23166,23155,23100,23024,22923,22825,22793,22627,22613,22536,22450,22383,22312,22268,22205,22175,22136,22028,21971,21900,21824,21769,21726,21583,21546,21513,21494,21428,21327,21264,21254,21174,21140,21112,21000,20921,20902,20830,20817,20783,20735,20657,20616,20573,20485,20378,20363,20305,20259,20210,20114,20002,19846,19785,19747,19667,19645,19622,19610,19580,19542,19516,19454,19392,19310,19277,19194,19131,19090,19004,18883,18845,18791,18781,18668,18591,18518,18475,18368,18331,18310,18287,18217,18114,18092,18048,17990,17964,17912,17836,17740,17704,17630,17613,17573,17428,17356,17341,17300,17260,17180,17174,17126,17071,17041,16866,16850,16828,16672,16618,16577,16499,16407,16357,16318,16293,16202,16150,16075,16041,15948,15921,15844,15843,15785,15764,15668,15626,15579,15473,15387,15255,15190,15139,15062,14996,14954,14918,14907,14902,14867,14851,14817,14799,14751,14720,14536,14506,14474,14353,14303,14280,14185,14107,14012,13932,13858,13781,13585,13563,13533,13451,13412,13362,13249,13208,13181,13064,13037,12961,12926,12892,12786,12731,12611,12573,12506,12502,12496,12470,12443,12370,12262,12182,12153,12069,12000,11847,11806,11781,11708,11687,11593,11550,11445,11372,11329,11308,11291,11268,11241,11191,11027,10982,10879,10862,10776,10695,10603,10502,10464,10350,10338,10305,10273,10176,10124,10094,10038,9953,9935,9812,9786,9743,9728,9508,9472,9383,9349,9236,9215,9130,9124,9042,9008,8988,8901,8833,8809,8780,8716,8580,8462,8334,8321,8305,8280,8257,8246,8137,8077,8043,8016,7984,7955,7927,7906,7746,7663,7653,7572,7542,7530,7489,7420,7390,7361,7337,7245,7210,7188,7175,7096,6898,6846,6745,6675,6569,6478,6427,6363,6284,6260,6243,6206,6154,6135,6078,6061,6017,5995,5917,5863,5836,5793,5763,5743,5678,5572,5532,5459,5384,5341,5299,5251,5231,4995,4933,4861,4740,4672,4625,4496,4445,4361,4282,4215,4135,4097,4028,3917,3862,3711,3553,3498,3410,3388,3384,3288,3279,3244,3221,3181,3171,3150,3060,3035,2975,2965,2834,2760,2637,2584,2533,2440,2383,2311,2285,2255,2211,2192,2121,2054,2010,1964,1850,1724,1642,1577,1411,1409,1332,1296,1265,1256,1220,1195,937,903,880,811,739,720,650,609,547,533,459,434,384,279,231,163,102,78,30,5,52,100,155,217,277,328,389,446,473,546,583,649,702,734,768,857,882,912,1043,1219,1243,1258,1290,1325,1359,1409,1567,1642,1679,1726,1873,1965,2017,2088,2172,2204,2226,2273,2288,2316,2434,2522,2558,2622,2678,2790,2933,2965,3025,3037,3071,3167,3180,3194,3233,3269,3282,3383,3387,3401,3465,3528,3595,3801,3910,4020,4078,4128,4213,4271,4295,4420,4472,4612,4663,4739,4845,4891,4980,5109,5241,5284,5335,5379,5388,5478,5546,5639,5705,5751,5766,5803,5855,5879,5975,6000,6024,6070,6093,6137,6156,6212,6256,6276,6304,6421,6441,6537,6614,6743,6844,6893,7087,7169,7183,7200,7237,7262,7352,7376,7398,7441,7491,7541,7564,7602,7656,7707,7814,7924,7940,7958,8014,8036,8048,8132,8141,8250,8279,8288,8321,8331,8374,8515,8655,8723,8807,8825,8878,8953,8990,9011,9077,9128,9172,9219,9276,9383,9420,9499,9535,9736,9744,9801,9900,9951,10038,10093,10119,10147,10265,10301,10314,10340,10456,10499,10564,10622,10767,10802,10876,10882,10997,11063,11217,11243,11276,11299,11314,11365,11407,11456,11587,11627,11705,11751,11792,11831,11901,12012,12118,12180,12240,12296,12385,12469,12473,12497,12503,12537,12578,12723,12778,12858,12901,12936,13020,13048,13136,13195,13232,13325,13377,13424,13493,13547,13564,13724,13856,13911,13938,14075,14151,14234,14300,14353,14395,14499,14507,14705,14724,14796,14802,14823,14858,14882,14905,14914,14936,14962,15049,15114,15161,15237,15272,15399,15565,15587,15666,15749,15778,15830,15843,15864,15928,16039,16075,16141,16163,16246,16315,16333,16389,16415,16526,16601,16650,16798,16845,16861,16991,17046,17090,17140,17178,17186,17292,17305,17343,17419,17456,17610,17617,17693,17728,17783,17909,17918,17970,18032,18083,18104,18114,18223,18296,18330,18363,18428,18496,18578,18660,18733,18782,18792,18861,18929,19069,19127,19184,19269,19279,19355,19394,19494,19539,19559,19599,19612,19643,19666,19745,19760,19815,19864,20012,20141,20231,20270,20330,20370,20380,20500,20595,20617,20690,20751,20811,20824,20843,20910,20925,21044,21126,21165,21198,21260,21280,21343,21467,21505,21531,21564,21640,21755,21817,21885,21929,22010,22103,22159,22196,22229,22270,22368,22414,22515,22570,22615,22630,22806,22864,22951,23030,23107,23155,23191,23226,23399,23438,23464,23487,23524,23559,23634,23667,23719,23747,23764,23869,23901,23936,24012,24022,24045,24074,24141,24185,24204,24272,24327,24452,24455,24490,24560,24615,24641,24734,24815,24890,24963,25025,25242,25282,25283,25414,25446,25475,25489,25527,25586,25636,25640,25771,25844,25848,25883,25923,26005,26048,26106,26157,26312,26359,26395,26429,26465,26491,26513,26558,26584,26601,26667,26770,26864,26900,26996,27118,27129,27176,27272,27313,27389,27478,27517,27580,27700,27761,27811,27844,27848,27967,28051,28108,28176,28264,28302,28332,28380,28525,28591,28617,28681,28727,28744,28874,28994,29047,29123,29221,29239,29274,29347,29493,29596,29668,29694,29717,29847,29871});
        System.out.println("Sum " + sum);
    }

    private int sumSubarrayMins1(int[] A) {
        int MOD = 1_000_000_007;
        int N = A.length;

        // prev has i* - 1 in increasing order of A[i* - 1]
        // where i* is the answer to query j
        Stack<Integer> stack = new Stack();
        int[] prev = new int[N];
        for (int i = 0; i < N; ++i) {
            while (!stack.isEmpty() && A[i] <= A[stack.peek()])
                stack.pop();
            prev[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(i);
        }

        // next has k* + 1 in increasing order of A[k* + 1]
        // where k* is the answer to query j
        stack = new Stack();
        int[] next = new int[N];
        for (int k = N-1; k >= 0; --k) {
            while (!stack.isEmpty() && A[k] < A[stack.peek()])
                stack.pop();
            next[k] = stack.isEmpty() ? N : stack.peek();
            stack.push(k);
        }

        // Use prev/next array to count answer
        long ans = 0;
        for (int i = 0; i < N; ++i) {
            ans += (i - prev[i]) * (next[i] - i) % MOD * A[i] % MOD;
            ans %= MOD;
        }
        return (int) ans;

    }

    class Counter {
        Map<Integer, Integer> map;
        public  Counter() {
            map = new HashMap<>();
        }

        public int get(int key) {
            return map.containsKey(key) ? map.get(key) : 0;
        }

        public void add(int key, int value) {
            map.put(key, get(key) + value);
        }
        public int size() {
            return map.size();
        }

        public void remove(int key) {
            map.remove(key);
        }
    }

    public List<Integer> getRow(int rowIndex) {
        List<Integer> list = new ArrayList<>(rowIndex);
        list.add(1);
        int n = rowIndex;
        for (int i = 1; i <= rowIndex; i++) {
            //C(n, k+1) = C(n, k) * (n-k)/(k+1)
            int k = i-1;
            long perm =   (list.get(k) * (n-k)/(k+1));
            list.add(Long.valueOf(perm).intValue());
        }
        return list;
    }

    public int subarraysDivByK(int[] A, int K) {
        int[] mod = new int[K];
        mod[0] = 1;
        int prefix = 0;
        int res = 0;
        for (int a : A) {
            prefix = (prefix + a%K + K)%K;
            res += mod[prefix];
            mod[prefix] += 1;
        }
        return res;
    }

    public ListNode mergeKLists(ListNode[] lists) {
        int n = lists.length;
        if(n == 0) {
            return null;
        } else if (n == 1) {
            return lists[0];
        }
        return mergeKLists(lists, 0, n-1);
    }

    private ListNode mergeKLists(ListNode[] lists, int start, int end) {
        System.out.printf("Start: %d, End %d\n", start, end);
        int len = end - start + 1;
        if (len == 0 || end < start) {
            return null;
        } else if (len == 1) {
            return lists[start];
        } else if (len == 2) {
            return merge2Lists(lists[start], lists[start + 1]);
        } else {
            int mid = start + len/2;
            ListNode left = mergeKLists(lists, start, mid);
            ListNode right = mergeKLists(lists, mid+1, end);
            return  merge2Lists(left, right);
        }
    }

    private ListNode merge2Lists(ListNode left, ListNode right) {
        ListNode head = null;
        ListNode current = null;
        while(left != null || right != null) {
            int leftVal = (left != null) ? left.getValue() : Integer.MAX_VALUE;
            int rightVal = (right != null) ? right.getValue(): Integer.MAX_VALUE;
            ListNode next = null;
            if (leftVal < rightVal) {
                next = left;
                left = left.getNext();
            } else {
                next = right;
                right = right.getNext();
            }
            if(head == null) {
                head = current = next;
            } else {
                current.setNext(next);
                current = current.getNext();
            }
        }
        System.out.printf("Merged list: %s\n", printList(head));
        return head;
    }
    private String printList(ListNode head) {
        StringBuilder sb = new StringBuilder();
        while(head != null) {
            sb.append(head.getValue());
            head = head.getNext();
            sb.append(", ");
        }
        return sb.toString();
    }

    public ListNode sortList(ListNode head) {
        if (head == null || head.getNext() == null) {
            return head;
        }
        ListNode[] res = partition(head);
        //System.out.printf("left:  %d, right: %d\n",left.getValue(), right.getVal());
        ListNode left = sortList(res[0]);
        ListNode right = sortList(res[1]);
        return merge( left, right);
    }
    private ListNode merge(ListNode left, ListNode right) {
        ListNode current = null;
        ListNode head = null;
        while (left != null || right != null) {
            int leftVal = (left != null) ? left.getValue() : Integer.MAX_VALUE;
            int rightVal = (right != null) ? right.getValue(): Integer.MAX_VALUE;

            ListNode next = null;
            if (leftVal < rightVal) {
                next = left;
                left = left.getNext();
            } else  {
                next = right;
                right = right.getNext();
            }
            if(head == null) {
                head = next;
                current = head;
            } else {
                current.setNext(next);
                current = current.getNext();
            }
        }
        return head;
    }

    private ListNode[] partition(ListNode head) {
        ListNode fast = head;
        ListNode right = head;
        ListNode prev = null;
        while (fast != null) {
            //prev = right;
            fast = fast.getNext();
            if (fast != null) {
                prev = right;
                right = right.getNext();
                //prev = fast;
                fast = fast.getNext();
            }
        }
        prev.setNext(null);
        return new ListNode[]{head, right};
    }




    private List<Integer> solveNQueen(int queenCount) {
        List<Integer> board = new ArrayList<>(queenCount);
        /*int count = 0;
        while (count < queenCount) {
            if(placeCurrentOnBoard(count, board)) {
                count += 1;
            }
        }*/

        return board;
    }

    public boolean checkInclusion(String s1, String s2) {
        if (s1.length() > s2.length())
            return false;
        int[] s1map = new int[26];
        for (int i = 0; i < s1.length(); i++)
            s1map[s1.charAt(i) - 'a']++;
        for (int i = 0; i <= s2.length() - s1.length(); i++) {
            int[] s2map = new int[26];
            for (int j = 0; j < s1.length(); j++) {
                s2map[s2.charAt(i + j) - 'a']++;
            }
            if (matches(s1map, s2map))
                return true;
        }
        return false;
    }
    public boolean matches(int[] s1map, int[] s2map) {
        for (int i = 0; i < 26; i++) {
            if (s1map[i] != s2map[i])
                return false;
        }
        return true;
    }
    public int lengthOfLongestSubstringKDistinct(String s, int k) {
        int result = 0;
        int i=0;
        HashMap<Character, Integer> map = new HashMap<Character, Integer>();

        for(int j=0; j<s.length(); j++){
            char c = s.charAt(j);
            if(map.containsKey(c)){
                map.put(c, map.get(c)+1);
            }else{
                map.put(c, 1);
            }
            if(map.size()<=k){
                result = Math.max(result, j-i+1);
            }else{
                while(map.size()>k){
                    char l = s.charAt(i);
                    int count = map.get(l);
                    if(count==1){
                        map.remove(l);
                    }else{
                        map.put(l, map.get(l)-1);
                    }
                    i++;
                }
            }
        }
        return result;
    }
}
