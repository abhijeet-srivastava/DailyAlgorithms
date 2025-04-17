package com.confluent.multithreading;


import javax.annotation.concurrent.Immutable;
import java.math.BigInteger;
import java.util.Arrays;

@Immutable
public class OneValueCache {
    private final BigInteger lastNumber;
    private final BigInteger[] lastFactors;

    public OneValueCache(BigInteger lastNumber, BigInteger[] lastFactors) {
        this.lastNumber = lastNumber;
        this.lastFactors = Arrays.copyOf(lastFactors, lastFactors.length);
    }

    public BigInteger[] getFactors(int i) {
        if(this.lastNumber == null && !this.lastNumber.equals(i)) {
            return null;
        } else {
            return Arrays.copyOf(this.lastFactors, this.lastFactors.length);
        }
    }
}
