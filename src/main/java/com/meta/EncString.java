package com.meta;

import java.util.HashSet;
import java.util.Set;

public class EncString {


    public boolean possibleEquals(String str1, String str2) {
        Set<String> failedStatesMemo = new HashSet<>();
        return isEquals(str1, 0, 0, str2,  0, 0, failedStatesMemo);
    }

    private boolean isEquals(String str1, int i1, int wc1, String str2,  int i2, int wc2, Set<String> failedStatesMemo) {
        int commonReduce = Math.min(wc1, wc2);
        wc1 -= commonReduce;
        wc2 -= commonReduce;
        while (true) {
            if(wc1 > 0) {
                if(isEnd(i2, str2)) {
                    return false;
                } else if(isLetter(i2, str2)) {
                    wc1 -= 1;
                    i2 += 1;
                } else {
                    break;
                }
            }else if(wc2 > 0) {
                if(isEnd(i1, str1)) {
                    return false;
                } else if(isLetter(i1, str1)) {
                    wc2 -= 1;
                    i1 += 1;
                } else {
                    break;
                }
            } else {
                if(isEnd(i1, str1) && isEnd(i2, str2)) {
                    return true;
                } else if(isEnd(i1, str1) || isEnd(i2, str2)) {
                    return false;
                } else if(isLetter(i1, str1) && isLetter(i2, str2)){
                    if(str1.charAt(i1) != str2.charAt(i2)) {
                        return false;
                    }
                    i1 += 1;
                    i2 += 1;
                } else {
                    break;
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append(i1).append(wc1).append(i2).append(wc2);
        if(failedStatesMemo.contains(sb.toString())) {
            return false;
        }
        Set<int[]> skips1 = extractSkips(i1, str1);
        Set<int[]> skips2 = extractSkips(i2, str2);
        for(int[] skip1 : skips1) {
            for (int[] skip2 : skips2) {
                boolean result = isEquals(
                        str1,
                        i1 + skip1[0],
                        wc1 + skip1[1],
                        str2,
                        i2 + skip2[0],
                        wc2 +skip2[1],
                        failedStatesMemo
                );
                if(result) {
                    return true;
                }
            }
        }
        failedStatesMemo.add(sb.toString());
        return false;
    }

    private Set<int[]> extractSkips(int index, String str) {
        Set<int[]> skipSet = new HashSet<>();
        generateSkips(index, str, 0, 0, skipSet);
        return skipSet;
    }

    private void generateSkips(int digitStart, String str, int lenSoFar, int valSoFar,  Set<int[]> skipSet) {
        int end = digitStart;
        if(isEnd(end, str) || isLetter(end, str)) {
            skipSet.add(new int[]{lenSoFar, valSoFar});
            return;
        }
        int currNum = 0;
        while (!isEnd(end, str) && !isLetter(end, str)) {
            currNum = currNum*10 + Character.getNumericValue(str.charAt(end));
            int segLen = end - digitStart + 1;
            generateSkips(end+1, str,  lenSoFar+segLen, valSoFar + currNum,  skipSet);
            end += 1;
        }
    }

    private boolean isLetter(int index, String str) {
        return !isEnd(index, str) && Character.isLetter(str.charAt(index));
    }

    private boolean isEnd(int index, String str) {
        return index >= str.length();
    }
}
