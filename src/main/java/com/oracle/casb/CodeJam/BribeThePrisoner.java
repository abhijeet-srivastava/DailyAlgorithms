package com.oracle.casb.CodeJam;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created By : abhijsri
 * Date  : 28/08/18
 **/
public class BribeThePrisoner {

    public static void main(String[] args) {
        BribeThePrisoner bs = new BribeThePrisoner();
        bs.solve();
    }

    Map<String, Integer> memory;

    public BribeThePrisoner() {
        this.memory = new HashMap<>();
    }

    private void solve() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("src/main/resources/C-small-practice.in"))))) {
            int testCount = Integer.valueOf(reader.readLine());
            for (int testNum = 0; testNum < testCount; testNum++) {
                String[] pqArr = reader.readLine().split("\\s");
                int size = Integer.parseInt(pqArr[0]);
                int toRelease = Integer.parseInt(pqArr[1]);
                int[] releases = new int[toRelease];
                String[] array = reader.readLine().split("\\s");
                for (int i = 0; i < array.length; i++) {
                    releases[i] = Integer.valueOf(array[i]);
                }
                System.out.printf("Case #%d: %d%c", testNum+1, getMinBribe(size, releases), '\n');
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int getMinBribe(int size, int[] releases) {
        return getMinBribe(0, size-1, releases);
    }

    private int getMinBribe(int leftBorder, int rightBorder, int[] releases) {
        String memoryKey = String.valueOf(leftBorder) +
                "-" + String.valueOf(rightBorder)+":" +
                Arrays.toString(releases);
        if(memory.containsKey(memoryKey)) {
            return memory.get(memoryKey);
        }
        int size = rightBorder - leftBorder +1;
        int result = 0;
        if (releases.length == 0) {
            result = 0;
        } else if (releases.length == 1) {
            result = size - 1;
        } else {
            int cost = Integer.MAX_VALUE;
            for (int i = 0; i < releases.length; i++) {
                int[] leftArr = Arrays.copyOfRange(releases, leftBorder, i);
                int[] rightArray = Arrays.copyOfRange(releases, i+1, rightBorder);
                int currCost = (size - 1)
                        + getMinBribe(leftBorder, releases[i] - 1, leftArr)
                        + getMinBribe(releases[i], rightBorder, rightArray);
                if (currCost < cost) {
                    cost = currCost;
                }
            }
            result = cost;
        }
        memory.put(memoryKey, result);
        return result;
    }

    private int[] getFairDistribution(int[] oils, int N, int expected, int start) {
        int[] distribution = new int[N];
        int leastDiff = 0;
        int index = start;
        int M = oils.length;
        for (int i = 0; i < N; i++) {
            index %= M;
            int currPartision = 0;
            while (true) {
                if (currPartision == 0) {
                    currPartision += oils[index];
                } else {
                    if ((expected - currPartision) > 4) {

                    }
                }
                index += 1;
                index %= M;

            }
        }
        return distribution;
    }


}
