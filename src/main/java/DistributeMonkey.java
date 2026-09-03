import java.util.ArrayList;
import java.util.List;

public class DistributeMonkey {
    public static void main(String[] args) {

    }

    // 15=>(3,5)
    // 17 => 2,3,4,5
    // Sieve of Erthasose=> All the prime numbers, if belongs to prime number then return false
    //n - Number of Banans,
    // k - Number of Monkeys
    public boolean canDistributeEqually(int n) {
        if(n == 1) {
            return false;
        }
        // Optimal approach
        // If n can not be divided upt o square root, then its a Prime number
        for(int k = 2; (k*k) <= n; k++) {
            if(n%k == 0) {
                return true;
            }
        }
        return false;
    }
    //array = [16,17,3,4,5,2]
    //result = [17,5,2]
    //highest = 17
    // i = 0
    //arr[1] > highest
    //res= {2, 5, 17}
    // result = [2,5,17]
    public int[] findHighestFromRight(int[] nums) {
        List<Integer> res = new ArrayList<>();
        int highest = Integer.MIN_VALUE;
        for(int i = nums.length-1; i >= 0; i--) {
            if(nums[i] > highest) {
                res.add(nums[i]);
                highest = nums[i];
            }
        }
        int n = res.size();
        int[] result = new int[n];
        for(int i = 0 ; i < n; i++) {
            result[i] = res.get(n-i-1);
        }
        return result;
    }

}
