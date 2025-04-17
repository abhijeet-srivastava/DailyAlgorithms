package com.lambda;

import org.checkerframework.checker.units.qual.A;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

public class TestLambda {

    private void testExecuteAround() {
        try {
            String line = processFile((BufferedReader br) -> br.readLine() + br.readLine());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String processFile(BufferedReaderProcessor processor) throws IOException {
        try(BufferedReader br = new BufferedReader(new FileReader(""))) {
            return processor.process(br);
        }
    }

    @FunctionalInterface
    private interface BufferedReaderProcessor {
        String process(BufferedReader reader) throws IOException;
    }

    /*@FunctionalInterface
    public interface Predicate<T> {
        boolean test(T t);
    }*/

    public <T> List<T> filter(List<T> list, Predicate<T> p) {
        List<T> result = new ArrayList<>();
        for(T val: list) {
            if(p.test(val)) {
                result.add(val);
            }
        }
        return result;
    }

    public void testPredicate() {
        Predicate<String> lenGreaterThen5 = s -> s.length() >= 5;
        List<String> result = filter(List.of("a", "b", "c"), lenGreaterThen5);
    }

    public void testConsumer() {
        forEach (List.of("a", "b", "c"), a -> System.out.printf("%s", a));
    }
    public <T> void forEach(List<T> list, Consumer<T> consumer) {
        for(T t: list) {
            consumer.accept(t);
        }
    }

    public void testFunction() {
        List<Integer> strLen = map(List.of("a", "ab", "abc"), a -> a.length());
        IntPredicate evenNumbers  = (a) -> a%2 == 0;
    }

    public <T,R>  List<R> map(List<T> list, Function<T,R> function) {
        List<R> result = new ArrayList<>();
        for(T t: list) {
            result.add(function.apply(t));
        }
        return result;
    }

}
