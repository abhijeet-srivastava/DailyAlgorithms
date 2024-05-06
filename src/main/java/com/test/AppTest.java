package com.test;


import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

public class AppTest {
    public static void main(String[] args) {
        AppTest at = new AppTest();
        //at.testString();
        //at.testTeamRatings();
        //at.testStringTransformation();
        //at.testAllOne();
        //at.testBrowserHistory();
        //at.testStringIterator();
        //at.testAutoComplete();
        //at.testBitSet();
        //at.testLogStream();
        //System.out.printf("%s\n", "2016:01:01:01:01:01".substring(0, 16));
        System.out.printf("res: %d\n", strangeCounter(1000000000000l));
    }

    public static long strangeCounter(long t) {
        // Write your code here
        long cycleStartTs  = (t+2)/3;
        long i = 0, val = 1;
        while(val <= cycleStartTs) {
            i += 1;
            val = (long)Math.pow(2, i);
        }
        i -= 1;
        long rem = t - (3 * (int)Math.pow(2, i) - 2);
        System.out.printf("cycleStartTs: %d, i: %d, rem: %d\n",cycleStartTs, i, rem);
        return 3l* (long)Math.pow(2, i) - rem;

    }
    private void testLogStream() {
        LogSystem ls = new LogSystem();
        ls.put(1, "2017:01:01:23:59:59");
        ls.put(2, "2017:01:01:22:59:59");
        ls.put(3, "2016:01:01:00:00:00");
        List<Integer> res = null;
        res = ls.retrieve("2016:01:01:01:01:01", "2017:01:01:23:00:00", "Year");
        res.stream().forEach(System.out::println);
        res = ls.retrieve("2016:01:01:01:01:01", "2017:01:01:23:00:00", "Hour");
        res.stream().forEach(System.out::println);

    }

    private void testBitSet() {
        Bitset bs = new Bitset(6119);
        bs.fix(4453);
        bs.flip();
        System.out.printf("All: %b\n", bs.all());
        System.out.printf("One: %b\n", bs.one());
    }

    private void testAutoComplete() {
        String[] sentences = {"uqpewwnxyqxxlhiptuzevjxbwedbaozz","ewftoujyxdgjtazppyztom","pvyqceqrdrxottnukgbdfcr","qtdkgdbcyozhllfycfjhdsdnuhycqcofaojknuqqnozltrjcabyxrdqwrxvqrztkcxpenbbtnnnkfhmebj","jwfbusbwahyugiaiazysqbxkwgcawpniptbtmhqyrlxdwxxwhtumglihrgizrczv","cfptjitfzdcrhw","aitqgitjgrcbacgnaasvbouqsqcwbyskkpsnigtfeecmlkcjbgduban","utsqkmiqqgglufourfdpgdmrkbippffacwvtkpflzrvdlkdxykfpkoqcb","ethtbdopotpamvrwuomlpahtveyw","jiaqkaxovsqtkpdjfbkajpvpyetuoqwnrnpjdhoojbsdvneecsdvgqpyurmsvcy","j","btbnuplyeuccjbernsfbnveillrwdbqledwvpmvdbcugkurrkabtpykhlcogeszclyfuquafouv","hndjzblegevtfkgbjttektox","gtvnlninpvenapyfgmsjdisfnmiktitrutctawosjflvzfkbegnprixzqwzcyhoovsivuwmofsveqkyosowuyamuvy","sawrirvrfrbfagreahrioaombukmdwztbpggnxd","mgdcwptvbvhzyvvumvbjjn","otjvvkegwleyyxtghwgfmlsqlhrlibdvqfinyyebotjpwoaejhtornfgikmifdmwswbqgwhcbzuhrpajxuqicegcptszct","zlondsttyvnnnnxjtoqnlktitwzurissczzbyfsbgpoawodwjpsmavaugnhqtsbeixwl","yehvdehbtmwqkmcjmvpivfzqvevkotwzvjoyfvp","bjximtpayjdcxbrnksbtfnpynzaygygdflowewprqngdadzdhxcpgapjejojrkzrutgcsfpfvpluagniqimfqddldxqiw","bysyrxfykivyauysytgxfhqcrxliulahuizjvozpywrokxujhzpauxwufcxiitukljiiclatfrspqcljjoxpxziumstnhqr","uxtvutlgqapyfltiulwrplesmtowzoyhhjhzihatpuvmutxqgxfawpwypedbz","jzgsdjdawrqfladolduldhpdpagmvllvzamypuqlrpbmhxxadqaqrqavtxeghcyysjynovkiyjtvdluttodtmtocajgttmv","mbijfkmepalhdiubposdksdmmttxblkodcdrxbnxaqebnwliatnxpwaohbwkidia","ljggggbyxwrwanhjonoramexdmgjigrtpz","cqfvkutpipxjepfgsufonvjtotwfxyn","kvseesjazssavispavchdpzvdhibptowhyrrshyntpwkez","nveuzbaosuayteiozmnelxlwkrrrjlwvhejxhupvchfwmvnqukphgoacnazuoimcliubvhv","uwrpwhfdrxfnarxqpkhrylkwiuhzubjfk","bniyggdcloefwy","ihranmhbsahqjxesbtmdkjfsupzdzjvdfovgbtwhqfjdddwhdvrnlyscvqlnqpzegnvvzyymrajvso","lscreasfuxpdxsiinymuzybjexkpfjiplevqcjxlm","uwgnfozopsygnmptdtmmuumahoungpkodwxrcvfymqpeymaqruayvqqgoddgbnhemtsjifhxwiehncswxzrghf","nyfbxgcpfrzyqwfjzgmhuohjhrjizsyjqgmertmooeiaadcmiuyyylpcosnweoyydeauazhzbeaqn","tpylrxbwnkrfxckfdlvrbytaezuzmyscpvruthuvbxjenkeolvqsrjqzizyclzmqtjvnamdansmzyspcfghfprorqprua","nhldlmxpuckxeekipkrzugatjiivtazjbjyxokksyueyjbgmrovbckbxqcqefaiavzsarbbypgmpxe","sylraqsd","xr","xkzpxkhrucyatpatkigvntohihibyisyqtkjdhatdvyvxbjttz","nvnz","blzddwxphkgqfsfzfclwytstpvpzgcdeggdwzukzirscfzcteeuqbmmrfxcnokbbyxkqrxtjfarcefiynwfmy","inuxmuhtdwpuvyludwtokhtalxbuccepsayrjycbcwbtnfholjvkmypodv","awwillrm","xznodxngrstjrwqzmlmigpw","khlxjdtictufdfbkgfusdtaaeuspbbfmtjodflgqofzlqnulkdztflm","nlngmckslyqzjiyiexbropbxnynjcstziluewypboqhqndqsxhtnosrgrameajovsclrgwqjgnztvxrkhwnxkfrf","yroadxhxyacaexrwju","ujxlbpcbxdqrvubifnpzjmmkolyljzjhdegaiowaal","tnfnjgtxbckbpyplucprxcqzhrfjimylmlhdglntfydepltjvklyxesndzuubienhvuaqc","ouedhtkpkg","ygchsrrubucqffewifsxaefwocfaiiupqbomktvrcddggqfgnaycstpccwtbheyaqwhosxajqeqqxzyjsfng","jqqgpjvfkgjh","csowoazaiyejgyixszqmtctpzlkccccqregyhtvxccvrpkupwcyhqatxscevzdfbdqnuyadiyfnhysddfyxpgqtjiogmxsmzbbkr","dlzxdpchkdaztkqtrjmuujgoiae","plcjkwukkyqluxjbhxsyeaqvviinfuujsafwsquidvmutsrukxwrv","yopqbtpoqhpcktjangauzcvvpephhprpaaclbbkgchlqkrwdsaupeizlwxzcpkchoagmrrkwdkthosmrjefgbumnrjsb"};
        int[] degrees = {12,9,4,4,1,5,3,4,7,9,2,4,2,3,11,13,1,3,4,10,7,1,9,5,10,14,5,3,2,11,5,14,4,13,11,5,15,8,1,12,2,11,4,2,11,14,9,12,1,7,13,11,7,2,6,10};
        AutocompleteSystem ac = new AutocompleteSystem(sentences, degrees);
        System.out.printf("~~~~~~~~~~~~~~~\n");
        List<String> res = ac.input('w');
        res.stream().forEach(System.out::println);
        System.out.printf("~~~~~~~~~~~~~~~\n");
        res = ac.input('o');
        res.stream().forEach(System.out::println);
        System.out.printf("~~~~~~~~~~~~~~~\n");
        res = ac.input('f');
        res.stream().forEach(System.out::println);
        System.out.printf("~~~~~~~~~~~~~~~\n");
        res = ac.input('q');
        res.stream().forEach(System.out::println);
        System.out.printf("~~~~~~~~~~~~~~~\n");
        res = ac.input('i');
        res.stream().forEach(System.out::println);
        System.out.printf("~~~~~~~~~~~~~~~\n");
        res = ac.input(' ');
        res.stream().forEach(System.out::println);
        System.out.printf("~~~~~~~~~~~~~~~\n");
        res = ac.input('a');
        res.stream().forEach(System.out::println);
        System.out.printf("~~~~~~~~~~~~~~~\n");
        res = ac.input('#');
        res.stream().forEach(System.out::println);
        System.out.printf("~~~~~~~~~~~~~~~\n");
        res = ac.input('i');
        res.stream().forEach(System.out::println);
        System.out.printf("~~~~~~~~~~~~~~~\n");
        res = ac.input(' ');
        res.stream().forEach(System.out::println);
        System.out.printf("~~~~~~~~~~~~~~~\n");
        res = ac.input('a');
        res.stream().forEach(System.out::println);
        System.out.printf("~~~~~~~~~~~~~~~\n");
        res = ac.input('#');
        res.stream().forEach(System.out::println);
        System.out.printf("~~~~~~~~~~~~~~~\n");

    }

    private void testStringIterator() {
        StringIterator si = new StringIterator("L1e2t1C1o1d1e1");
        int i = 1;
        while(si.hasNext()) {
            System.out.printf("%d next = %c\n", i++, si.next());
        }
    }

    private void testBrowserHistory() {
        String curr = "";
        BrowserHistory bh = new BrowserHistory("leetcode.com");
        bh.visit("google.com");
        bh.visit("facebook.com");
        bh.visit("youtube.com");
        System.out.printf("%s \n", bh.back(1));
        System.out.printf("%s \n", bh.back(1));
        System.out.printf("%s \n", bh.forward(1));
        bh.visit("linkedin.com");
        System.out.printf("%s \n", bh.forward(2));
        System.out.printf("%s \n", bh.back(2));
        System.out.printf("%s \n", bh.back(7));

    }

    private void testAllOne() {
        AllOne ao = new AllOne();
        ao.inc("hello");
        ao.inc("goodbye");
        ao.inc("hello");
        ao.inc("hello");
        System.out.printf("Max key: %s\n", ao.getMaxKey());
        ao.inc("leet");
        ao.inc("code");
        ao.inc("leet");
        ao.dec("hello");
        ao.inc("leet");
        ao.inc("code");
        ao.inc("code");
        System.out.printf("Max key: %s\n", ao.getMaxKey());
        PriorityQueue<Map.Entry<Integer, Long>> pq
                = new PriorityQueue<>(Comparator.comparingLong(e -> e.getValue()));
    }

    private void testStringTransformation() {
        String s = "abcd", t = "cdab";
        StringBuilder sb = new StringBuilder(t);
        sb.append('#');
        sb.append(s);
        sb.append(s);
        int[] zArr = zFunction(sb.toString());
        System.out.printf("Concat String: %s\n", sb.toString());
    }

    private int[] zFunction(String str) {
        int n = str.length();
        int[] z = new int[n];
        int l = 0;
        for(int i = 1; i < n; i++) {
            z[i] = Math.min(z[l] + l -i, z[i-l]);
            z[i] = Math.max(0, z[i]);
            while(i + z[i] < n && str.charAt(z[i]) == str.charAt(i+z[i])) {
                z[i]++;
            }
            if(i + z[i] > l + z[i]) {
                l = i;
            }
        }
        return z;
    }

    private void testTeamRatings() {
        String[] votes = {"WXYZ","XYZW"};
        String ratings = rankTeams(votes);
        System.out.printf("Ratings: %s\n", ratings);
    }

    public String rankTeams(String[] votes) {
        if(votes.length == 1) {
            return votes[0];
        }
        int count = votes[0].length();
        Map<Character, TeamRanks> teams = new HashMap<>();
        for(char ch: votes[0].toCharArray()) {
            teams.put(ch, new TeamRanks(ch, count));
        }
        for(String vote: votes) {
            for(int i = 0; i < vote.length(); i++) {
                teams.get(vote.charAt(i)).updateRatings(i);
            }
        }
        return teams.values().stream().sorted()
                .map(a -> a.teamName).map(String::valueOf)
                .collect(Collectors.joining(""));
    }
    
    private class TeamRanks implements Comparable<TeamRanks> {
        Character teamName;
        int[] rankingCounts;
        
        private TeamRanks(char name, int n) {
            this.teamName = name;
            this.rankingCounts = new int[n];
        }
        private void updateRatings(int pos) {
            rankingCounts[pos] += 1;
        }

        @Override
        public int compareTo(TeamRanks other) {
            for(int i = 0; i < this.rankingCounts.length; i++) {
                if(this.rankingCounts[i] < other.rankingCounts[i]) {
                    return 1;
                } else if(this.rankingCounts[i] > other.rankingCounts[i]) {
                    return -1;
                }
            }
            return this.teamName.compareTo(other.teamName);
        }
    }

    private void testString() {
        char[] keys = new char[26];
        keys[3] = 'a';
        System.out.printf("State: %s\n", new String(keys));
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
