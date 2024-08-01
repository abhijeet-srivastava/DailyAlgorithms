package com.cloudbee;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class App {

    public static void main(String[] args) {
        App app = new App();
        //app.testProd();
        //app.testFreeIntervals();
        //app.testWordTyping();
        app.testInterlavingScheduler();

    }

    private void testInterlavingScheduler() {
        List<EventScheduler> list = Arrays.asList(
                new EventSchedulerImpl(Arrays.asList("a1", "a2", "a3")),
                new EventSchedulerImpl(Arrays.asList("b1", "b2", "b3", "b4", "b5", "b6", "b7", "b8", "b9")),
                new EventSchedulerImpl(Arrays.asList("c1", "c2", "c3"))

        );
        InterleavedEventScheduler ils = new InterleavedEventSchedulerImpl(list);
        while(ils.hasRemainingEvent()) {
            System.out.printf("next event: %s\n", ils.scheduleEvent());
        }
        System.out.printf("next event: %s\n", ils.scheduleEvent());

    }

    public int wordsTyping2(String[] sentence, int rows, int cols) {
        int numWord = sentence.length;
        int[] nextWord = new int[numWord];
        int[] times = new int[numWord];
        for (int i = 0; i < numWord; i++) {
            int curr = i;
            int currLen = 0;
            int currTime = 0;
            while (currLen + sentence[curr].length() <= cols) { // we can put curr word to the current line.
                currLen += sentence[curr].length() + 1; // update current length with appending space
                curr++;
                if (curr == numWord) { // increase time and reset curr index
                    currTime++;
                    curr = 0;
                }
            }
            // when loop exits, we stop at the first word of next line and times of the previous line.
            nextWord[i] = curr;
            times[i] = currTime;
        }

        int totalTimes = 0;
        int start = 0; // it is obviously that we start from the first word in the sentence.
        for (int i = 0; i < rows; i++) {
            totalTimes += times[start];
            start = nextWord[start]; // update start of next row.
        }
        return totalTimes;
    }
    public int wordsTyping1(String[] sentence, int rows, int cols) {
        String s = String.join(" ", sentence) + " ";
        int start = 0, l = s.length();
        for (int i = 0; i < rows; i++) {
            start += cols;
            if (s.charAt(start % l) == ' ') {
                start++;
            } else {
                while (start > 0 && s.charAt((start-1) % l) != ' ') {
                    start--;
                }
            }
        }

        return start / s.length();
    }

    private void testWordTyping() {
        /*String[] sentence = {"hello","world"};
        int rows = 2, cols = 8;*/
        /*String[] sentence = {"a", "bcd", "e"};
        int rows = 3, cols = 6;*/
        String[] sentence = {"i","had","apple","pie"};
        int rows = 4, cols = 5;
        /*String[] sentence = {"a", "b", "e"};
        int rows = 20000, cols = 20000;*/
        int count = wordsTyping2(sentence, rows, cols);
        System.out.printf("count: %d\n", count);
    }

    public int wordsTyping(String[] sentence, int rows, int cols) {
        Sentence words = new Sentence(sentence);
        int maxLen = words.getMaxWordLen();
        if(maxLen > cols) {
            return 0;
        }
        int row = 0;
        while(row < rows) {
            int colIdx =  0;
            while (colIdx < cols) {
                String word = words.nextWord();
                if(colIdx + word.length() -1 < cols) {
                    words.consume();
                    colIdx += word.length();
                    if(colIdx < cols) {
                        colIdx += 1;//Add space
                    }
                } else {
                    colIdx = cols;
                }
            }
            row += 1;
        }
        return words.getFreq();
    }
    public class Sentence {
        String[] words;

        Integer idx;
        int freq;

        public Sentence(String[] words) {
            this.words = words;
            this.idx = 0;
            this.freq = 0;
        }

        public String  nextWord() {
            return this.words[this.idx];
        }

        public void consume() {
            this.idx += 1;
            if(this.idx == words.length) {
                this.freq += 1;
                this.idx = 0;
            }
        }

        public  int getMaxWordLen() {
            return Arrays.stream(words).map(x -> x.length()).max(Comparator.comparingInt(a -> Integer.valueOf(a))).get();
        }

        public int getFreq() {
            return freq;
        }
    }

    private void testFreeIntervals() {
        List<List<Interval>> schedule = Arrays.asList(
                Arrays.asList(new Interval(1, 2), new Interval(5,6)),
                Arrays.asList(new Interval(1, 3)),
                Arrays.asList(new Interval(4, 10))
        );
        List<Interval> res = employeeFreeTime(schedule);
    }

    public List<Interval> employeeFreeTime(List<List<Interval>> schedule) {
        List<Interval> intervals
                = schedule.stream().flatMap(s -> s.stream())
                .sorted((i1, i2) -> i1.start == i2.start
                        ? Integer.compare(i1.end, i2.end): Integer.compare(i1.start, i2.start))
                .toList();
        Deque<Interval> stack = new ArrayDeque<>();
        for(Interval curr: intervals) {
            while(!stack.isEmpty() && overlaps(stack.peek(), curr)) {
                curr = merge(stack.pop(), curr);
            }
            stack.push(curr);
        }
        List<Interval> result = new ArrayList<>();
        int prev = Integer.MIN_VALUE;
        while(!stack.isEmpty()) {
            Interval lowest = stack.removeLast();
            result.add(new Interval(prev, lowest.start));
            prev = lowest.end;
        }
        return result;

    }
    private boolean overlaps(Interval i1, Interval i2) {
        return Math.max(i1.start, i2.start)  <= Math.min(i1.end, i2.end);
    }

    private Interval merge(Interval i1, Interval i2) {
        return new Interval(
                Math.min(i1.start, i2.start),
                Math.max(i1.end, i2.end)
        );
    }
    class Interval {
        public int start;
        public int end;

        public Interval() {}

        public Interval(int _start, int _end) {
            start = _start;
            end = _end;
        }
    }

    private void testProd() {
       String[] products = {"oqjrtcokaagakchrwrdbrlpnqivwcpzwuxbdkpkhndevouwyrtmbokxolhbvrencthmyplqixnhnokbhtbstmslfbinsypubqjckiqujvmknxuomdwqkfudgiqmpzkvnshrmnoeonzyfaipdcfdwhekrazfkdlfluyvoevahsyhvqjfcizxjhucbpqdjgmqqalqnvfyrtfkyrxlavfbagxkdloetaiedqbrmtzxwwrpyewrcbntsnrdzurzlfokcxolkltouozdobqvtoldjuincumspqpujynxxetbflfnkrscxgrvennmczurjvnoalxcfcrnddfckavedjrvewmawxazviumzpudgolofsxllhgsdrnvjbipcsqsdoosfdkhavhicsfbyavwyxziefmycknhzqujqkbqhacuaebwqpkfnkovxchczohhirczixikuoktkaamchcghynclonujiyzkcghjjjtzckjxmpssfqjirnfvrddvoqvdeteegupzevydcywjsoybsflpgpzkcoztcayffscwxkofwibguysdjtmddgevjhbxrfiqliqyiczdunhosdctrvlagcugpsskpvlsdhcbpdtdwjbhchymlptjrmzmezwljxemgzecptluxbwtgoyvqwrvxkartgrebdegyqdibemnficwpgsywlzczvwujmykkyhkkxhvrtmjxcyidawfxjcagsgutndplixpepddiosflfoujxndtgxsbzarlwacjtydorlrtukbyibaktiphojmrcbwjppnuwwihuerqswjpmyblrdslnwpdnzovpslsrfbzhjywbimwdiqhxmdssjovufgbqqykozfjbylihfwanzjdtgeoimwowocykeskvivwuybdrowolstbrbenioagkgwednhymqisdvjuycjvtrurzqssxrisozysizxeoyelhglnppsjzggjunmyabzptlfktzphlmhvpoqlnrwgxauepumssyecjpwfozhxhvodsasomhtgbasijrpphbijnxcyrajypogighdxacjxqyxagfuenwrdajqsuzrjtrqjebjnbmdzbqdgmbuqofbyegnxvgsegbaqxsdjjffdxiqgqtlgclqtmjqfmbrsccnoidkugbwscamrqfqmpbfuvvoxrodoccusixfehzetsbiflonazsmacvwilarpgpwjkexykqkmklqcuhdrhhgrzofbsjvzwdaghqxajgwqmuhyyervscyiyggbqyhpvotnmaubapigegatgtqeazgcytubsuhjzipbyxnkyqveeetgecxwinwdjuawpzdieizlzqplzajafeernecfkpiilnuqdjnipjybqishinomemdxfwabhemxecuyailbogehxwwtzvaueonxkjmdvojpjjxaftvnbbsfcjjyzwartrbmxgktbhrqeynwoziddoh",
                    "kfnyprlqjosvbkvfvanhqsxpfezfafuzxchbwsdagvgtmtwdjsgnodkahyfkpyfmrbrcineichemlomrgaydmjnzbqxhzpciwkwbcsrpeafwkitjibviipnhvcqntjuwnccylnqdwxmirvnggcdwfbldnhxsjjhfqcxlggcpldamlyttzibadxzcpwecjjxekeoucanprljnsgvhpxjydazzlxofypaxcnwhhkhrtwbecouqvoxnzsitdzyrxokhdtynjgygqarnhyaqsdrgdqdmpfwdhlayltvrjalfxxctwgqtmuwgdwzxyuiictaul",
                    "kfnwqytfhroyewirkaofdwdjtzyaeamrqkbdzganjfqfykfwizgtxjhrbvnmbwyfivwrbbjralxuptonwhlvztgefnggltqekduxdhznowjsayyaieacjvewhfookivjuwmwmhlobrgummkvjwclbipmkwrpbwqlbthbokenmcaupqbtuglgqtpuognhfachuscdsztjbuffgdzajlzhmnmksdnyrhahnnhilwnqohxyjsiatzshinquevymllusyqzkjwovmgtwwfztpbcpvyjdkqcdkscpimchxdjdrxvropgbgrrccfqnatlqjlojfczeggnanpuqtebjcyezdqpxsfkegxqfeplcmfxyqmlruapeywuftbbosmpegfpcepukcsvpgmfundbvbzazwqnefmbnnxntixtormwfbnuwheahzddwksodogedymeujvxacdbguthxhlcmmblfwjrnurpfuawoghyakqkccxcgxzdraasdivjgvtjrulhgktdxjuzpdyewckrpmjrttusqubhwntpchscprcdddsbyxrqrnplgazwjvojrtxogqdvqarcspvkfljlswpktqxlndidtnhzryohyyujfncunynxkblsdpjlaifdzsjxpuapwjzwzeahrnfkgtaehwjqsligfjnmsivinygzoeaejagsvzkyicbsyfxhqaotexdsvtxuexnmqsjhmojexevbgozlkbmuuexgpklnsetscrceuvtfhrxwvloiucqbzpxwtodopesljmisfxqmxhjlehpyaodfwqbnfvjtplauiyiyrddbjdpkvznmvpbzxgemfhgpwligjwznbztyaenziwooceebfwppfpgmzczoenqtmqcjdgvdlhjpwwlqcyjmaqnjicyrocrvxxrnrhpsqvrndurviilufvwbsswtjlmefydjechqrgeqjucsjyaaudocxgpvtbfekgynmnywltxvqddtjdaunvfqjclrjsohfenjxqjpvcklfdjoaavgoukzqjijycwqipkzpcvaqzqwgwnolrqudrcqqymlhelskpvrlobsjxlgsunkdlvqdgpnnrhcgdxasbdmrcrauswiticdiwkhdvpkduqgqpujcyjkrksmhjswvqccpxfacszfdsdvoxjnlphqawjlxryprgoexjqxryuxadviwdebxjyzolpqerfkxhklgpeffahclnfyfnwudwnjjqbcazsuzekwfsprbiwztqsxtxpiemfucwgcamdowvoymuwkstyqggmtknzezacoklmiaanvsttqknedeoayakomzzzntfmyfaunsnvimvkboqcfprizsofhauflvmetrwfoigjcxjavspnaqwpagfwslxolbjgglknrcacqntfcizfkmcrdbdidvojdritvnvgnsvrhjcutojfvjaspzdodnisyuirklwcxjitakdpxaclthbxgooxmqslftctxopfencxtzktckpkpkhlkboueylshbztlvkbtkpjdcxakldxmnjnqjyscgvydlmnpfnaxaicylivtesvtoqimovynmntiijxnwinlirbmiubpwlmwdscynsywgswsklxaxjwoculminicuphgtepjxmlhaolzzxuqqawjmvlniknniwexrelrvxagbtcqnzhdmiqfkyvwnsfuzskzlcvcyluzjtesuvzdknipreueyqgzvgbeqtmcnzwtfdgmihuzwtsdxahawfiwnpzzwpnzawfzyobyriuxbnlojvkfycwprgngluhgyirhutknuvdyebwrmaarbelkhzoqpilrneitzzuysmlpczdepdngeuwcpylcysafnmzulcrzlkaskbiexjikebbrwlbcpgvmiumsafuzqcuxnpwtjgqmlmnbeauljmyrxykugovjfazsrkzsyonecfejokeiwtxvdjawbkgvlqegxcoxwhldzoblzvhydopqpatwxllccdnlmxjypjiimtczqhubuloozyiwjmcsooqvufuamaopwxsexaaohazzctezrqahkdsnkqspkjqcvocnqirwzxocbxibrwrlrhzcuxlfqsgwoqyagkmcrdedasvrzjgugbrvvbsjtmjfbdxhchbkfomefrfrwdiyhhskrqstnnwrclsphqfrbwjvlitjmwtlmx",
                    "kfnyprlqjosvbkvfvanhqsxpfezfafuzxchbwsdagvgtmtwdjsgnodkahyfkpyfmrbrcineichemlomrgaydmjnzbqxhzpciwkwbcsrpeafwkitjibviipnhvcqntjuwnccylnqdwxmirvnggcdwfbldnhxsjjhfqcxlggcpldamlyttzibadxzcpwecjjxekeoucanprljnsgvhpxjydazzlxofypaxcnwhhkhrtwbecouqvoxnzsitdzyuoyzfkyseiohccpdtnjhqlrkgpcifvatradjfurxmwfssmbpbvxeoialjeyxujpgqdunhrthidhizzqddvuqzmoenmjzunulkrjyxfugrpvkwoiwyxwgrweakhbswllbyziranhxkleggegegdailjgyteaghdqnjqdjfhyrapqmckvxgxmasnweej"};
        String sw = "kfnyprlqjosvbkvfvanhqsxpfezfafuzxchbwsdagvgtmtwdjsgnodkahyfkpyfmrbrcineichemlomrgaydmjnzbqxhzpciwkwbcsrpeafwkitjibviipnhvcqntjuwnccylnqdwxmirvnggcdwfbldnhxsjjhfqcxlggcpldamlyttzibadxzcpwecjjxekeoucanprljnsgvhpxjydazzlxofypaxcnwhhkhrtwbecouqvoxnzsitdzyuoyzfkyseiohccpdtnjhqlrkgpcifvatradjfurxmwfssmbpbvxeoialjeyxujpgqdunhrthidhizzqddvuqzmoyrnqunojmtporeofgldjntqvlngobvtpbhmmdrkosxlkvmivonldjr";
        //String[] products = {"mobile","mouse","moneypot","monitor","mousepad"};
        //String sw = "mouse";
        List<List<String>> res1 = suggestedProducts(products, sw);
        for(int i = 0; i < res1.size(); i++) {
            System.out.printf("Up to prefix %d, len: %d\n", i, res1.get(i).size());
        }
    }

    private List<List<String>> suggestedProducts(String[] products, String sw) {
        Trie trie = new Trie();
        for(int i = 0; i < products.length; i++) {
            trie.appendWord(products[i]);
        }
        List<List<String>> res = new ArrayList<>();
        TrieNode curr = trie.root;
        for(int i = 0; i < sw.length(); i++) {
            char ch = sw.charAt(i);
            TrieNode child = curr == null ? null : curr.children[ch-'a'];
            List<String> top3 = new ArrayList<>();
            if(child != null) {
                top3 = child.getWords();
            }
            res.add(top3);
            curr = child;
        }
        return res;
    }

    public class Trie {
        TrieNode root;

        public Trie() {
            this.root = new TrieNode('/');
        }
        public void appendWord(String word) {
            TrieNode curr = this.root;
            for(int i = 0; i < word.length(); i++) {
                char ch = word.charAt(i);
                TrieNode child = curr.children[ch-'a'];
                if(child == null) {
                    child = new TrieNode(ch);
                    curr.children[ch-'a'] = child;
                }
                child.addWord(word);
                curr = child;
            }
        }
    }

    public class TrieNode {
        char ch;
        TrieNode[] children;
         List<String> words;

        public TrieNode(char ch) {
            this.ch = ch;
            this.children = new TrieNode[26];
            this.words = new ArrayList<>();
        }

        public void addWord(String word) {
            this.words.add(word);
        }

        public List<String> getWords() {
            Collections.sort(this.words);
            List<String> res = new ArrayList<>();
            for(int i = 0; i < Math.min(3, this.words.size()); i++) {
                res.add(this.words.get(i));
            }
            return res;
        }
    }
}
