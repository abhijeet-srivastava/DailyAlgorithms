package com.leetcode;

import java.util.*;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Weekly284 {
    public static void main(String[] args) {
        Weekly284 w284 = new Weekly284();
        //w284.testLongest();
        //w284.testAsteroids();
        //w284.testLongestRepeating();
        //w284.testMergeIntervaals();
        //w284.testUniquCharStr();
        //w284.wordSuggestions();
        //w284.testPrimeFactors();
        //w284.testCombinations(2, 3);
        w284.testSecret();
    }

    private void testSecret() {
        int n = 6, firstPerson = 1;
        int[][] meetings = {{3,4,2},{1,2,1},{2,3,1}};//{{3,1,3},{1,2,2},{0,3,3}};//{{1,2,5},{2,3,8},{1,5,10}};
        List<Integer> res = findAllPeople(5, meetings, 1);
        System.out.printf("[%s]\n", res.stream().map(String::valueOf).collect(Collectors.joining(", ")));
    }

    public List<Integer> findAllPeople(int n, int[][] meetings, int firstPerson) {
        int[] earliestTime = new int[n];
        Arrays.fill(earliestTime, Integer.MAX_VALUE);
        earliestTime[0] = 0;
        earliestTime[firstPerson] = 0;
        Set[] GRAPH = new Set[n];
        for(int i = 0; i < n; i++) {
            GRAPH[i] = new HashSet<int[]>();
        }
        for(int[] meeting: meetings) {
            GRAPH[meeting[0]].add(new int[]{meeting[1], meeting[2]});
            GRAPH[meeting[1]].add(new int[]{meeting[0], meeting[2]});
        }
        Deque<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[]{0, 0});
        queue.offer(new int[]{firstPerson, 0});
        while(!queue.isEmpty()) {
            int[] person = queue.poll();
            Set<int[]> nextPersons = GRAPH[person[0]];
            for(int[] np: nextPersons) {
                if(person[1] > np[1] || earliestTime[person[0]] > np[1] || earliestTime[np[0]] < np[1]) {
                    continue;
                }
                earliestTime[np[0]] = Math.min(earliestTime[np[0]], np[1]);
                queue.offer(new int[]{np[0], earliestTime[np[0]]});
            }
        }
        List<Integer> res = new ArrayList<>();
        for(int i = 0; i < n; i++) {
            if(earliestTime[i] != Integer.MAX_VALUE) {
                res.add(i);
            }
        }
        return res;
    }

    BitSet sieve = getPrimes(1_000_000);
    private int testCombinations(int n, int m) {

        Map<Integer, Integer> factorial_n = factorial(n);
        Map<Integer, Integer> factorialM_n = factorial(m+n-1);

        factorial_n.forEach((k, v) ->
                factorialM_n.merge(k, v, (v1, v2) -> v1 - 2*v2)
        );
        int res = 1;
        for(var t: factorialM_n.entrySet()) {
            int count = t.getValue();
            while(count-- > 1) {
                res *= t.getKey();
            }
        }
        return res;
    }

    private Map<Integer, Integer> factorial(int n) {
        Map<Integer, Integer> map = new HashMap<>();
        for(int i = 2; i <= n; i++) {
            if(sieve.get(i)) {
                map.put(i, 1);
            } else {
                Map<Integer, Integer> primeFactors = getPrimeFactors(i);
                primeFactors.forEach((key, value) ->
                        map.merge(key, value, (v1, v2) -> v1+v2)
                );
            }
        }
        return map;
    }

    private Map<Integer, Integer> getPrimeFactors(int num) {
        Map<Integer, Integer> primeFactors =  new HashMap<>();
        for(int i = 2; i <= num; i++) {
            if(sieve.get(i) && num%i == 0)  {
                int count = 0;
                while(num%i == 0) {
                    num /= i;
                    count += 1;
                }
                primeFactors.put(i, count);
            }
        }
        return primeFactors;
    }

    private void testPrimeFactors() {
        BitSet sieve = getPrimes(1_000_000);
        int num = 2980;
        Map<Integer, Integer> primeFactors =  new HashMap<>();
        for(int i = 2; i <= num; i++) {
            if(sieve.get(i) && num%i == 0)  {
                int count = 0;
                while(num%i == 0) {
                    num /= i;
                    count += 1;
                }
                primeFactors.put(i, count);
            }
        }
        int i = 0;
        for(var t: primeFactors.entrySet()) {
            System.out.printf("%d: Factor:%d, count: %d is True: %b\n", i++, t.getKey(), t.getValue(), sieve.get(t.getKey()));
        }
    }

    private void wordSuggestions() {
        String[] products = {"ilpxatcgvfbmfunpljkodnbfaowi","ilpsokmmniywxgbeqrpsaqeqn","talrnwemajlqicbkxjyf","sbqhbuvkvntmsutdpyavojqwinxofhvhecbtjjkwdkaazftxvgvicgio","ilpxatcgvfblxfvbnoxgmmpvimowoccdedkaqiexrcvxpuaiypkycxoeirqztcaiameutvrtqwucjp","l","ilpxywtmdnlil","rnjutrkyojwumoyrgzx","ilpxatcgvfblxfvbnoxgmmpvimowoccdedkaqiexrcvxpuaiyrhxnvfqsuymujmvkwctobnuvrusbqt","ilpxatcgvfblxfvbnoxgmmpvqqvxqyuecwpbjtlzwmmcwspztjjxevjpxdnzcectypljpkjoilnvur","ilpxatcgvfblxfvbnoxgmmpvimowoccdedkaqiexrcvxpuaiypkycxoeirqztcaiamkdetaiukcnwdkwrfizkvef","ffngjbmfkxtstjbzalnutfiybfy","ilpxatcgvfblxfvbnoxgmmpvimowoccdedkaqiexrcvxpuaiypkycxoeirqztcaiamkdetaipwtaovnfhuzu","ilpxatcgvfblxfvbnoxgmmpvimowoccdedkaqiexrcvxpuamwsmzfbbtgnfsbujeotpndobpdg","ilpxatcgvfblxfvbnoxgmmpvimowoccdedkaqiexrcvxpuaiypkycxoeirqztcaiamkdetaiukcnwdnxdqkbl","ilpxatcfeexjqxghbengdcvsljajqaxidzitqjfjpovxmijvofntfelqidcbekzecqodiralswkjqckrpz","ilpxatcgvfblxfvbnoxgmmpvimowoccdedkaqiexrklmagbqeadtwhndgodzgfejjcs","hp","ilpxatcgvfblxfvbnoxgmmpvimowoccdedkaqiexrcvxpuaiypkycxovkmfvxdiuceqbvczetytkfvcfykt","ktihatqubvuvnszewdlcyfqclgwhwsrawtcpdvxwhmvaftzkrvu","ilpxatcgvfblxfvbnoxgmmpvimowoccdedkaqiexgkoeppszhfbajvcxuaplxeobzyqe","ilpxatcgvfblxfvbnoxgmmpvdxthphkvtcfhpevifbuzgcmxqjvtukbgeppeaufwgjbdfppxwszavitpctqthp","ilpxatcgvfblxfvbnoxgmmpvimowoccdedkaqiexrcvxpuaiypkycxoeirqzxnhaplrwcgwjkr","ilpxatcgvfkvqfnblpdvtesdandbeynurlcjwwrellxigbsfjccihrvfbsbtcscblctnzededcajrywkj","ilpxatcgvfblxfvbnoxgmmpvimowoccdedkaqiexrcvxpuaiypexgkrbzdzmsbimuycjfk","ilpxatcgvfblxfvbnoxgmmpvimowoccdedkaqiexrcvxpuaiypkycxoeirysflyamgemsnjibnfxbke","ilpxatcgvfblxfvbnoxgmmpvimowoccdedkaqiexrcvxpuaiypkycxoeirqztcaiamkdetvkcqmhquleujnmi","edmasudelavobnlbrj","zgjrwqhoqvmaaasqmggcuahifrwwrfrtpvchkuvncpvugmizhpfiokijxwlssapntpecbdgwteqvfdzwu","ilpxatcgvfblxfvbnoxgmmpvimowoccdedopz","ilpxatcgvfblxfvbnoxgmmpvimowoccdedkaqiexrcvxpuaiypkycxoeirqztcaiamkdetaiukcnwhchcgi","ilpxatcgvfblxfvbnoxgmmpvimowoccdedkaqiexrcvxpuaiypkycxoeirqztcaiamkdetaiukcnwdtdvk","lnpadggtvd","xbnfiostesudukxgghwolzawxqlovbnjweddrqcisujmuhsazvetxrsyqdaidrmfomwmanghqn","zgmmrzerbzujlgomosxmgihordwpzvbnywczhosikslzlaqfyqnrjfbqzqwvihzojyzswbdxczknclmuyhdc","ilpxatcgvfblxfvbnoxgmmpvimowoccdedkaqiexrcvxpuaiypkycxoeirqztcaiamclkswqd","gdqdyysurpvmakyesqoeipuvxlhnffgemhvswyvkbgatxuufhbuitjwnvvigwfweiordqdhtnzagjputzale","ilpxatcgvfblxfvbnoxgmmpvimowoccdedkaqiexrcvxpuaiypkycxoeirqztcaiamkdetaiukcnr","ilpxatcgvfblxfvbnoxgmmpvimowoccdedkaqiexrcvxpuaxcp","ilpxatcgvfblxfvbnoxgmmkmnlolyewtuuosejmgconmcjep","ilpxatcgvfblxfvbnoxgmmpvimowocjrj","ilpxatcgvfblxfvbnoxgmmpvimowoccbsqkdevdkizvlnrl","wyeisnjhrhossmnpnexjqbkcsorccgawfgifsmwfdqqadgqdgpfoiagyxperbshwzbnoqqyjr","ilpxatcgvfblxfvbnoxgmmpvimowoccdedkaqiexrcvxpuaiypkycxoeirqztcaiamkdetaiukcmsdqhbizk","ilpxatcgvfblxfvbnoxgmmpvimowoccdedkaqiexrcvxpuaiypkycxoeirqztcaiamkdetaiukcnwdqlhdjfflb","ilpxatcgvfblxfvbnoxgmmpvimowoccdedkaqiexrcvxpuaiypkycxoeirqztcaiamkdetaiukcnwdnxiedjqmul","tzkwqsmwdhczbqnimbzgguyqvtywcyzkqhnkdtknumpuikfexldysotnndztwootn","ilpxatcsfwrpusskcsmzunvejmfymcvs","usoaljtodfybvquycywtnvivvdvbvymivvc","ilpxatcgvfblxfvbnoxgmmpvimowoccdedkaqiobdewutljgvugibawjskriuxeiwishfimbsinzt","orrcvinutuqbzlaxrfhsbpjoonsjrubyrbbdghhqnwjz","ilpxatcgvfblxfvbnoxgmmpvimowoccdedkaqiexrcvxpuaiypkycxoeirqncvydjmnouqktixtgjtvkmwciiuso","jponpctybxuxtnigdqortyqjiobgdaehgybozbtvxalhyubibburfsjukhalynnkvjr","ilpxatcgvfblxfvbnoxgmmpvimowoccdedkaqiexrcvxpuaiypkycxofpsbqljapaqco","ilpxatcgvfblxfvbnoxgmmpvimowoccdedkaegtshdqrhtimpvnbymwrlmvghl","ilpxatcgvfblxfvbnoxgmmpvimowoccdedkaqiexrcvxgcyqcrmwlrjlbnqqfuhypqgiurkyapxzrdsnbeifik","ilpxatcgvfblxfvbnoxgmmiiubtvodpzwgdgoobsbbnlfjldpqydwejvfysrwhmhg","ilpxatcgvfblxfvbnoxgmmpvimowoccdedkaqiexrcvxpuaiypkycxoeirqztcaiamkdetaiukcnwdnxdqp","dpwgvxdrbzlh","ilpxatcgvfblxfvbnoxgmmpvimowoccdedkaqiexrcvxpufytsqrkyhiehtjkgohchjkmxcx","ilpxatcgvfblxfvbnoxgmmpvimowoccdedkaqiexrcvxpuaiiwstxiirty","irjbjafrvdcpqanwt","ilpxatcgvn","ilpxatcgvfblxfvbnoxuherdhypducgxsuftiappkjfcqvorxizvdlgswfrqveuhlzitltnushzs","ilpxatcgvfblxfvbnoxgmmpvimowoccdedkaqiexrypwuijbjublekmxhchhnaxbhbgdaqifvdrfvheqiqcswrskd","ilpxatcgvtqdvgovjgabbmkdicgymudbikvhihpxfmpanjyp","ilpxatcgvfblxfvbnoxgmmpvimowoccdedkaqiexrcvxpuaiypkycxoeirqztcaiamiuzrrvibhkwigjgrqfhn","ilpxatcgvfblxfvbnoxgmmpvimowoccdedkaqiexrcvxpuaiypkycxoeirqztcaiamkdetaiukcnwdnxdqkbrlym","ilpxatcgvfblxfvbnoxgmmpvimowoccdedkaqismi","jtbuygbbtdyasdtcuswpbquuhswfciyndfton","ilpxxishsvmt","ilpxatcgvfblxfvbnoxgmmpvimowoccdedkaqiexrcvfgdtlfrwklzcwqmbmefnxwrtkpak","ilpxatcgvfblxfvbnoxgmmpvimowoumiglwjrzletaydenogpldiblrwpyjeqjeow","hlwga","ilpxatcgvfblxfvbnjyryxxfayspcrqkvyopfzspzmmhhalwjvfhsgybgkzctlqtr","ilpxatcgvfblxfvbnoxgmmpvimowoccdedkaqiexrcvxpuaiypkycxofewrprmowynq","qyuthohyzttitkgfrfybiuuvzzcqbddrbby","uinnxgnuuptrxhbhplcdklstqbamsxwaeubppeutqlgngtgzycxpcqjabucoi","ilpxatcgvfblxfvrpbuumlvymcimuothjdhlsgenxxcllwfibvvcavuiuesgdjhsgoxjzechjhwnprmut","btrvkwzfdovyyycfxwmxhutvldkblvrsmeyktlwnmdmdepjnkrzkmnerxesvycbrpghnf","ilpxatcgvfblxfvbnoxgmmpvimowoccdedkaqiexrcvxpuaiypkycltcndobbsywuhxohkzostnacjyhj","dijc","ilpxatcgvfblxfvbnoxgmmpvimowoccdednjkkgwtekjv","ilpxatcgvfblxfvbnoxgmmpvimowoccdedkaqiexrcvxpuaiypkycxoeirqztcwqxooniui","ilpxatcgvfblxfvbnoxgmmpvimowoccdedkaqiexrcvxpuaiyuhbnoibkpmygmul","potullxiksrpnhngusckddvrtylgwztzxmmgvtrlbgwkgbgohsgbljroghmpwrwupszmkv","ilpxatcgvfblxfvbnoxgmmpvimowoccdedkaqiexrcvxpuaiypkycxoqpodgpsgmlj","ryfoisftsawa","ilpxatcgvfblxfvbnoxgmmpvimowoccdedkaqiexrcvxpuaiypkycxoeirqztcaiamkdetaiukcnwdnxdqexqy","fzvfyiqrwbserbdnnymmcaokpasfpijvbwdxnbctzekkgcudayqlnsclgcxkaeslihczwxphyqbdyxthpuprxjb","ilpxatcgvfblxfvbnoxgmmpvimowoccdedkaqiexrcvxpvoltdohcmslq","ilpxatcgvfblxfvbnoxgmmpvimowoccdedkaqiexrcvxpuaiypkycxoeirqztcaiamkdeyozknywbktmyzkm","ilpxatcgvfblxfvbnoxgmmpvimowoccdedkaqiexrcvxpuaiypkycxoeirqlc","ilpxatcgvfblxfvbnoxgmmpvimowoccdedkaqiexrcvxpuaiypsan","ilpxatcgvfblxfvbnoxgmmpvimowoccdedkaqiexrcvxpuaiypkycxoeirqztcaiarrqyflykjdwvnvicearpou","yfilpbgdkfmphopolcvjbemkpqcqvcxdkkolpwgco","ilpxatcgvfblxfvbnoxgmmpvimowoccdedkaqiexrcvxpuaiypkycxoeirqztckwzvayxyzaqpm","ilpxatcgvfblxfvbnoxgmmpvimowoccdedkaqiexrcvxpuaiypkycxoeirqztcaiamkdetaiukcnwdnxdqkbrlywb","ilpxatcgvfblxfvbnoxgmmpvimowoccdedkaqiexrcvxpuaiypkycxoeirqztcaiamkdetaisnakhppbhnofpnuns","khdyqeswcmriitjysdj","ilpxatcgvfblxfvbnoxgmmpvimowoccdedkaqiexrcvxpnupiwbdseowsrurzjiscnitdqncuyzxvkuiskezqy","ilpxatcgvfovxvhriwhgpadztzfcdfgnhktkhqhjiueszhzjpmxrzfgccxovsqxo","ilpxatcgvfblxfvbnoxgmmpvimowoccdedkaqiexrcvxpuaiypkycxoeirqztcaiamkdetaiukcnvncgupn","ilpxatcgvfblxfvbnoxgmmpvimowoccdedkaqiexrcvxpuaiypkycxoeirqztcaiamkdetabyivtjxivaraiwg","zvtcdlbjbnlnadytwqwrnbjomdlmmujvwpmazwbjwippucsujioeevltrrdfivkamxgjtrqckuflvpnbsbrhda","apkqpncsbbiqstfxplizqbpriqywwjiwquzpfyhwyxfcucwcs","ilpxatcgvfblxfvbnoxgmmpvimowoccdedkaqiexrcvxpuaiypkycxoeirqztcaiamkdetjoolenqclkstqazwb","bjvozgbmtqdxyfirqwhebtijcwrebvdkxtxoahqsjtofybvh","ilpxatcgvfblxfvbnoxgmmpvimowoccdedkaqiexrcvxpuaiypkycxoeirqztcaiamkdetaiukfuxkg","ilpxatcgvfblxfvbnoxgmmpvimowoccdedkaqiexrcvxpuaiypkycqgxdkpnisqsfzlkmnpstbd","ilpxatcgvfblxfvbnoxgmmpvimowoccdedkaqiexrcvxpuaiypkycxoeirqztcaiamkdetaiukcnwdnxdqkbrwh","ilpxatcgvfblxfvbnoxgmmpvimowoccdedkaqiexrcvxpuaiypkycxoeirqztcaiamkdetaiukcnwdnxdqkygsrgv"};
        String word = "ilpxatcgvfblxfvbnoxgmmpvimowoccdedkaqiexrcvxpuaiypkycxoeirqztcaiamkdetaiukcnwdnxdqkbrlywrywcjmtvaleckwduejlayguredkubbcqhtiafhcsmlgdmpcdouxdyxykmujehtpkjqfbrbyehjcnymgumqwbfouubonhzhvssjmpudpvjtdlurkbwyahtclafjaztcdxstgpsvhbbyndqhfkyycxnrvitcasubhnaeolgmmmedgzqtpjjhtlkwqcnjgwehbriwiklwniobbzhegbisgwccvstmdqyneolakaakrnzhmczxdoxhelbezsggbrzlzrrecyvzvrteofqopamraasigegtovcspphlpmsxsfkouohlwdvgrttzltyojunyvmlmhjjihubmkkbrvsbbdiejimobknxcwntoaqltofqopslhzobiuqhlxivctogflejhdlulda";
        List<List<String>> suggestions = suggestedProducts(products, word);
        for(List<String> list: suggestions) {
            System.out.printf("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
            System.out.printf("[%s]\n", list.stream().collect(Collectors.joining(", ")));
        }
    }

    private BitSet getPrimes(int limit) {
        BitSet sieve = new BitSet(limit+    1);
        sieve.set( 2, limit);
        for(int i = 2; i*i <= limit; i++) {
            if(!sieve.get(i)) {
                continue;
            }
            for(int p = i << 1; p < limit; p += i) {
                sieve.clear(p);
            }
        }
        return sieve;
    }
    private void testUniquCharStr() {
        //String s = "ABCDQEFGHIQJKLMNOPQ";
        String s = "ABACADA";
        int total = uniqueLetterString(s);
        System.out.printf("Count: %d\n", total);
    }

    private void testMergeIntervaals() {
        //int[][] interval1 = {{1,2}, {3, 9}, {11, 14}};
        int[][] interval1 = {{1,2}, {5, 7}, {11, 14}, {16, 18}, {19, 20}, {22, 25}};
        int[][] interval2 = {{4,6}, {8, 10}, {12, 17}};
        int[][] merged = mergeSorted(interval1, interval2);
        for(int[] m : merged) {
            System.out.printf("{%d - %d}\n", m[0],  m[1]);
        }
    }

    private void testLongestRepeating() {
        String s = "babacc";
        String query = "bcb";
        int[] arr = {1,3,3};
        int[] res = longestRepeating(s, query, arr);
        System.out.printf("%s\n", IntStream.of(res).mapToObj(String::valueOf).collect(Collectors.joining(",", "[", "]")));
    }

    private void testAsteroids() {
        int[] astr = {10,  2, -15};
        int[] res = asteroidCollision(astr);
        System.out.printf("%s\n", IntStream.of(res).mapToObj(String::valueOf).collect(Collectors.joining(",", "[", "]")));
    }

    private void testLongest() {
        int longest = longestWithSingleChar("bbbccc".toCharArray());
        System.out.printf("Longest: %d\n", longest);
    }

    public int[] longestRepeating(String s, String queryCharacters, int[] queryIndices) {
        char[] arr = s.toCharArray();
        int m = arr.length, n = queryIndices.length;
        int[] output = new int[n];
        TreeMap<Integer, Integer> lengths = new TreeMap<>(), spans = new TreeMap<>();
        // Stores spans of each letter in the TreeMap
        for (int i = 0, j = 1; j <= m; j++) {
            if (j == m || arr[i] != arr[j]) {
                lengths.put(j - i, lengths.getOrDefault(j - i, 0) + 1);
                spans.put(i, j - 1);
                i = j;
            }
        }
        // Update spans on each query and find the max length
        for (int i = 0; i < queryIndices.length; i++) {
            int j = queryIndices[i];
            if (arr[j] != queryCharacters.charAt(i)) {
                // Remove the spans that has the character to be updated
                int l = spans.floorKey(j), r = spans.remove(l), length = r - l + 1;
                if (lengths.get(length) == 1) lengths.remove(length);
                else lengths.put(length, lengths.get(length) - 1);
                // if the character is going to be different from its neighbors, break the span
                if (l < j) {
                    spans.put(l, j - 1);
                    lengths.put(j - l, lengths.getOrDefault(j - l, 0) + 1);
                }
                if (r > j) {
                    spans.put(j + 1, r);
                    lengths.put(r - j, lengths.getOrDefault(r - j, 0) + 1);
                }
                arr[j] = queryCharacters.charAt(i);
                l = j;
                r = j;
                // if the character is going to be same as its neighbors, merge the spans
                if (j > 0 && arr[j] == arr[j - 1]) {
                    l = spans.floorKey(j);
                    length = spans.remove(l) - l + 1;
                    if (lengths.get(length) == 1) lengths.remove(length);
                    else lengths.put(length, lengths.get(length) - 1);
                }
                if (j < m - 1 && arr[j] == arr[j + 1]) {
                    int key = spans.ceilingKey(j);
                    r = spans.remove(key);
                    length = r - key + 1;
                    if (lengths.get(length) == 1) lengths.remove(length);
                    else lengths.put(length, lengths.get(length) - 1);
                }
                spans.put(l, r);
                lengths.put(r - l + 1, lengths.getOrDefault(r - l + 1, 0) + 1);
            }
            output[i] = lengths.lastKey();
        }
        return output;
    }

    public int[] asteroidCollision(int[] asteroids) {
        Deque<Integer> stack = new ArrayDeque<>();
        for(int astr: asteroids) {
            if(astr > 0) {
                stack.push(astr);
                continue;
            }
            while(!stack.isEmpty() && stack.peek() > 0 && Math.abs(astr) >= stack.peek()) {
                int top = stack.pop();
                if(top == Math.abs(astr)) {
                    astr = 0;
                }
            }
            if(astr == 0) {
                continue;
            }
            if(astr > 0 || stack.isEmpty())  {
                stack.push(astr);
            }
        }
        int[] result = new int[stack.size()];
        int i = 0;
        while(!stack.isEmpty()) {
            result[i++] = stack.pollLast();
        }
        return result;
    }
    private int longestWithSingleChar(char[] arr) {
        System.out.printf("Curr Str: %s\n", String.valueOf(arr));
        int longest = 1;
        int left = 0, right = 0;
        int[] counter = new int[26];
        while(right < arr.length) {
            counter[arr[right]-'a'] += 1;
            //System.out.printf("left: %d, right: %d, longest: %d\n", left, right, longest);
            while(counter[arr[right]-'a'] > 1) {
                counter[arr[left]-'a'] -= 1;
                left += 1;
            }
            longest = Math.max(longest, right-left+1);
            right += 1;
        }
        return longest;
    }
    public int[][] mergeSorted(int[][] intervals1, int[][] intervals2) {
        Deque<int[]> stack = new ArrayDeque<>();
        int i =  0, j =  0;
        while(i < intervals1.length && j < intervals2.length) {
            int[] res = null;
            if(overlap(intervals1[i], intervals2[j])) {
                res = merge(intervals1[i], intervals2[j]);
                i += 1;
                j += 1;
            }else if(intervals1[i][1] < intervals2[j][1]) {
                res = intervals1[i];
                i+= 1;
            } else {
                res = intervals2[j];
                j+= 1;
            }
            if(!stack.isEmpty() && overlap(res, stack.peek())) {
                stack.push(merge(stack.pop(), res));
            } else {
                stack.push(res);
            }
        }
        if(j < intervals2.length) {
            i = j;
            intervals1 = intervals2;
        }
        if(i < intervals1.length) {
            if(overlap(intervals1[i], stack.peek())) {
                stack.push(merge(intervals1[i], stack.pop()));
            } else {
                stack.push(intervals1[i]);
            }
            i += 1;
        }
        while(i < intervals1.length) {
            stack.push(intervals1[i]);
            i += 1;
        }
        int[][] result = new int[stack.size()][2];
        for(int  ind = stack.size()-1; !stack.isEmpty(); ind--) {
            result[ind] =stack.pop();
        }
        return result;
    }

    private int[] merge(int[] int1, int[] int2) {
        return new int[] {Math.min(int1[0], int2[0]), Math.max(int1[1], int2[1])};
    }

    private boolean overlap(int[] int1, int[] int2) {
        return Math.max(int1[0], int2[0]) <= Math.min(int1[1], int2[1]);
    }
    public List<List<String>> suggestedProducts(String[] products, String searchWord) {
        TreeSet<String> prodSet = new TreeSet<>();
        List<List<String>> result = new ArrayList<>();
        char[] searched = searchWord.toCharArray();
        for(String product: products) {
            if(product.charAt(0) == searched[0]) {
                prodSet.add(product);
            }
        }
        for(int i = 0; i < searched.length; i++) {
            List<String> list = new ArrayList<>();
            Iterator<String> itr =  prodSet.iterator();
            while(itr.hasNext()) {
                String product = itr.next();
                if(i >= product.length()
                        || product.charAt(i) != searched[i]) {
                    itr.remove();
                } else if(list.size() < 3){
                    list.add(product);
                }
                /*if(list.size() == 3) {
                    break;
                }*/
            }
            if(list.size() == 2) {
                System.out.printf("Forr index %d size is 2\n", i);
            }
            result.add(list);
        }
        return result;
    }
    public int uniqueLetterString(String s) {
        int[] freq = new int[26];
        int[] lastOccur = new int[26];
        int[] secondLastOccur = new int[26];
        int currentSum = 0;
        int res = 0;
        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            freq[c - 'A'] ++;
            if (freq[c - 'A'] == 1) {
                currentSum = currentSum + i + 1;
            } else if (freq[c - 'A'] == 2) {
                currentSum = currentSum + (i - lastOccur[c - 'A']) - (lastOccur[c - 'A'] + 1);
            } else {
                currentSum = currentSum + (i - lastOccur[c - 'A']) - (lastOccur[c - 'A'] - secondLastOccur[c - 'A']);
            }
            if (freq[c - 'A'] > 1) {
                secondLastOccur[c - 'A'] = lastOccur[c - 'A'];
            }
            lastOccur[c - 'A'] = i;
            res += currentSum;
        }
        return res;
    }

    class State {
        int node;
        double prob;
        State(int _node, double _prob) {
            node = _node;
            prob = _prob;
        }
    }
    public double maxProbability(int n, int[][] edges,
                                 double[] succProb, int start, int end) {
        Map<Integer, List<double[]>> GRAPH = new HashMap<>();
        for(int i = 0; i < edges.length; i++) {
            int[] edge = edges[i];
            GRAPH.getOrDefault(edge[0], new ArrayList<>()).add(new double[]{edge[1], succProb[i]});
            GRAPH.getOrDefault(edge[1], new ArrayList<>()).add(new double[]{edge[0], succProb[i]});
        }
        double[] maxProbs = new double[n];
        maxProbs[start] = 1.0d;
        ToDoubleFunction<Double> funct = d -> Double.valueOf(d);
        PriorityQueue<State> pq = new PriorityQueue<State>(
                (a, b) -> (((Double) b.prob).compareTo((Double) a.prob))
        );
        State startState = new State(start, 1.0d);
        pq.offer(startState);
        while(!pq.isEmpty()) {
            State current = pq.poll();
            if(current.node == end) {
                return current.prob;
            }
            for(double[] child : GRAPH.getOrDefault(current.node, Collections.emptyList())) {
                if(maxProbs[Double.valueOf(child[0]).intValue()]
                        >= current.prob*child[1]) {
                    continue;
                }
                double currProb = current.prob*child[1];
                pq.offer(new State((int) child[0], currProb));
                maxProbs[Double.valueOf(child[0]).intValue()] = currProb;
            }

        }
        return 0.0d;
    }
    private int getCurrencyExchangeRate(int source, int target, int[][] exchangeRates){

        Map<Integer, Map<Integer, Integer>> GRAPH = new HashMap<>();
        for(int[] exchangeRate: exchangeRates) {
            GRAPH.getOrDefault(exchangeRate[0], new HashMap<>()).put(exchangeRate[1], exchangeRate[2]);
        }
        Set<Integer> visited = new HashSet<>();
        return getCurrencyExchangeRate(source, target, visited, GRAPH);
    }

    private int getCurrencyExchangeRate(int current, int target, Set<Integer> visited, Map<Integer, Map<Integer, Integer>> graph) {

        if(current == target) {
            return 1;
        }
        int product = 0;
        for(Map.Entry<Integer, Integer> rates: graph.getOrDefault(current, Collections.emptyMap()).entrySet()) {
            if(visited.contains(rates.getKey())) {
                continue;
            }
            visited.add(current);
            product = Math.max(product, rates.getValue()*getCurrencyExchangeRate(rates.getKey(), target, visited, graph));
            visited.remove(rates.getKey());
        }
        return product;
    }
}
