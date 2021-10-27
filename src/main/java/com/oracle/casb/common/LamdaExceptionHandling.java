package com.oracle.casb.common;

import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Created By : abhijsri
 * Date  : 2019-01-04
 **/
public class LamdaExceptionHandling {


    public void doSomethingOnStream(Stream<Integer> mystream) {
        mystream
                .map(wrap(item -> doSomething(item)))
                .forEach(System.out::println);
        mystream
                .map(liftWithValue(item -> doSomething(item)))
                .forEach(System.out::println);
        mystream
                .map(lift(item -> doSomething(item)))
                .forEach(System.out::println);
    }

    private String doSomething(Integer item) {
        return String.valueOf(item);
    }



    public static <T,R> Function<T,R> wrap(CheckedFunction<T,R> checkedFunction) {
        return t -> {
            try {
                return checkedFunction.apply(t);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    @FunctionalInterface
    public interface CheckedFunction<T,R> {
        R apply(T t) throws Exception;
    }

    public static <T,R> Function<T, Either> liftWithValue(CheckedFunction<T,R> function) {
        return t -> {
            try {
                return Either.Right(function.apply(t));
            } catch (Exception ex) {
                return Either.Left(Pair.of(ex,t));
            }
        };
    }

    public static <T,R> Function<T, Either> lift(CheckedFunction<T,R> function) {
        return t -> {
            try {
                return Either.Right(function.apply(t));
            } catch (Exception ex) {
                return Either.Left(ex);
            }
        };
    }
}
