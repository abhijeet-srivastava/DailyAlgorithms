package com.oracle.casb;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Response;

import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.time.LocalDate.of;
import static org.asynchttpclient.Dsl.asyncHttpClient;

/**
 * Created By : abhijsri
 * Date  : 13/06/18
 **/
public class CompletebleFutureTest {

    private static final String PATH = "http://api.worldweatheronline.com/premium/v1/past-weather.ashx?q=%s,%s&date=%s&enddate=%s&tp=24&format=csv&key=%s";
    private static final String KEY = "54a4f43fc39c435fa2c143536183004";

    private static final Pattern NEWLINE =Pattern.compile("\\n");
    private static final Pattern COMMA = Pattern.compile(",");;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletebleFutureTest cft = new CompletebleFutureTest();
        //cft.testTemp();
        cft.testBoolean();

        //cft.testCombine1();
        //cft.testCombine2();
        //cft.testCombine3();
        //cft.testCombine4();
        //cft.test1();
        //cft.test2();
    }

    private void testBoolean() {
        int x = 3;
        boolean isDefect = false;
        do
        {
            x++;
            if(isDefect = true)
                System.out.print("A");
            else
                System.out.print("B");
        }
        while(x <= 5);
    }

    private void testTemp() {
        CompletableFuture<IntStream>  lisbonTempsInMarch =
                getTemperaturesAsync(38.717, -9.133, of(2018,4,1), of(2018,4,30));

        System.out.println(lisbonTempsInMarch.join().max());
    }

    public  CompletableFuture<IntStream> getTemperaturesAsync(double lat, double log, LocalDate from, LocalDate to) {
        AsyncHttpClient asyncHttpClient = asyncHttpClient();
        CompletableFuture<Stream<String>> csv = asyncHttpClient
                .prepareGet(String.format(PATH, lat, log, from, to, KEY))
                .execute()
                .toCompletableFuture()
                .thenApply(Response::getResponseBody)
                .thenApply(NEWLINE::splitAsStream);
        boolean[] isEven = {true};
        return csv.thenApply(str -> str
                .filter(w ->!w.startsWith("#"))     // Filter comments
                .skip(1)                            // Skip line: Not Available
                .filter(l -> isEven[0] =!isEven[0]) // Filter Even line
                .map(line ->COMMA.splitAsStream(line).skip(2).findFirst().get()) // Extract temperature in Celsius
                .mapToInt(Integer::parseInt));// Convert to Integer
    }

    private void testCombine4() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future1
                = CompletableFuture.supplyAsync(() -> "Hello");
        CompletableFuture<String> future2
                = CompletableFuture.supplyAsync(() -> "Beautiful");
        CompletableFuture<String> future3
                = CompletableFuture.supplyAsync(() -> "World");

        CompletableFuture<Void> combinedFuture
                = CompletableFuture.allOf(future1, future2, future3);

        String combined = Stream.of(future1, future2, future3)
                .map(CompletableFuture::join)
                .collect(Collectors.joining(" "));


        // ...

        System.out.println(combined);

    }

    private void testCombine3() {
        CompletableFuture future = CompletableFuture.supplyAsync(() -> "Hello")
                .thenAcceptBoth(CompletableFuture.supplyAsync(() -> " World"),
                        (s1, s2) -> System.out.println(s1 + s2));

    }

    private void testCombine2() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture
                = CompletableFuture.supplyAsync(() -> "Hello")
                .thenCombine(CompletableFuture.supplyAsync(
                        () -> " World"), (s1, s2) -> s1 + s2);
        System.out.println(completableFuture.get());
    }

    private void testCombine1() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture
                = CompletableFuture.supplyAsync(() -> "Hello")
                .thenCompose(s -> CompletableFuture.supplyAsync(() -> s + " World"));
        System.out.println(completableFuture.get());

        //assertEquals("Hello World", completableFuture.get());
    }

    private void test2() {
        CompletableFuture<String> completableFuture
                = CompletableFuture.supplyAsync(() -> "Hello");

        CompletableFuture<Void> future = completableFuture
                .thenAccept(s -> System.out.println("Computation returned: " + s));

        try {
            future.get();
            //System.out.println(future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void test1() {
        CompletableFuture<String> completableFuture
                = CompletableFuture.supplyAsync(() -> "Hello");

        CompletableFuture<String> future = completableFuture
                .thenApply(s -> s + " World");
        System.out.println(future.join());
    }

    
}
