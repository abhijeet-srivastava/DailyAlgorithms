package com.amazon.multithreading;

import org.apache.xpath.operations.Bool;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DemoThreadPool {

    private void demoThreadPool() {
        ExecutorService execService = Executors.newFixedThreadPool(20);
        execService.submit(() -> System.out.printf("Printing in thread\n"));
        ExecutorService scheduledExecutor = Executors.newScheduledThreadPool(10);
    }
    private void demoStream() {
        try {
            List<String> fileLines = Files.readAllLines(Paths.get("data.txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Map<Boolean, List<Integer>> groupedByValue
                = IntStream.rangeClosed(10, 1000)
                .boxed()
                .collect(Collectors.partitioningBy(i -> i%2 == 0));
        /*Map<Boolean, List<Integer>> passedFailedMap = Stream.of(49, 58, 76, 82, 88, 90)
                .collect(Collectors.partitioningBy(i -> i > 60));*/
    }
    private void parseDocuments() {
        // 7.1 Fetch and Parse an XML web service
        try {
            Document parse = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                    .parse(new URL("http://www.omdbapi.com/?i=tt0121765&plot=short&r=xml").openStream());
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }

        // 7.2 Fetch and Parse JSON
        try {
            String json = new String(Files.readAllBytes(Paths.get("http://www.omdbapi.com/?i=tt0121765&plot=short&r=json")));
            System.out.println(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void sieveOfEratosthenes() {
        LinkedList<Integer> nums = new LinkedList<>(
                IntStream.rangeClosed(2, 1000).boxed().collect(Collectors.toList())
        );
        IntStream.rangeClosed(2, Double.valueOf(Math.sqrt(nums.getLast())).intValue())
                .forEach(n -> nums.removeIf(i -> i % n == 0 && n != i));
        nums.forEach(System.out::println);
    }
    private void demoFunctions() {
        BiFunction<Integer, Integer, Integer> sum = Math::addExact;

        Function<Integer, Boolean> isEven = (a) -> (a % 2 == 0);

        System.out.println(sum.andThen(isEven).apply(5, 4));

        System.out.println(sum.apply(5, 4));

        //System.out.println(sum(5, 4));

        System.out.println(isEven.apply(4));

        //System.out.println(isEven(4));
        //Consumer
        Consumer<Integer> evenOrOdd = a -> System.out.println(a % 2 == 0 ? "even" : "old");
        BiConsumer<Integer, Integer> displaySum = (a, b) -> System.out.println(a + b);
        BiConsumer<Integer, Integer> displayEachValue = (a, b) -> {
            System.out.println("First value: " + a);
            System.out.println("Second value: " + b);
        };
        evenOrOdd.accept(4);
        displaySum.andThen(displayEachValue).accept(4, 5);

        //Predicate
        Predicate<String> isCharacter = s -> s.length() == 1;
        Predicate<String> isEmpty = String::isEmpty;

        System.out.println(isCharacter.test("a"));
        System.out.println(isEmpty.negate().and(isCharacter).test("a"));
        BiPredicate<String, String> isEqual = (s1, s2) -> s1.equals(s2);
        System.out.println(isEqual.test("techteam", "techteam"));
        //Supplier
        Supplier<Calendar> calendarS = Calendar::getInstance;
        System.out.println(calendarS.get().getTime());
    }
    public void testHttpClient() {
        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofSeconds(20))
                .build();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://dummy.restapiexample.com/api/v1/employees")) // Modify to your endpoint
                .build();
        try {
            HttpResponse httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            System.out.println(httpResponse.body());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
