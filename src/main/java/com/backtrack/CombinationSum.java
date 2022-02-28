package com.backtrack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CombinationSum {

    public static void main(String[] args) {
        CombinationSum cs = new CombinationSum();
        cs.testCombSum();
    }

    private void testCombSum() {
        int[] candidates = {2,3,6,7};
        List<List<Integer>> result = combinationSum(candidates, 7);
        for(List<Integer> list: result) {
            System.out.printf("[%s]\n", list.stream().map(String::valueOf).collect(Collectors.joining(", ")));
        }
    }

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(candidates);
        dfs( 0, target,candidates, new ArrayList<>(), result);
        return result;
    }
    private void dfs(int index, int  target, int[] candidates, List<Integer> current,  List<List<Integer>> result) {

        if(target <  0) {
            return;
        } else if(target == 0) {
            result.add(new ArrayList(current));
            return;
        }
        for(int i = index; i < candidates.length; i++) {
            current.add(candidates[i]);
            dfs(i, target - candidates[i], candidates, current, result);
            current.remove(current.size() - 1);
        }
    }
}
