/**
 * Copyright (c) Energeya LLC.  All rights reserved. http://www.energeya.com
 */
package com.acocco.java8.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.acocco.java8.stream.model.Module;
import com.acocco.java8.stream.model.SubModule;


/**
 * A stream represents a sequence of elements. It supports different kind of operations. It cn be used with list and
 * set, but not with map.
 *
 * Stream operations are either terminal or intermediate.
 *
 * Intermediate operations return a stream so we can chain multiple intermediate operations without using semicolons
 * (examples : filter, map and sorted).
 *
 * Terminal operations are either void or return a non-stream result (examples : forEach).
 *
 * For a full list of all available stream operations see the Stream Javadoc
 *
 * (http://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html).
 *
 * @author acocco
 * @version $Id$
 */

public class StreamTest
{

    /**
     * Count is a terminal operation and it is equivalent to :
     *
     * mapToLong(e -> 1L).sum()
     *
     * see API [http://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html#count--]
     */
    @Test
    public void testSequentialStream_CounterElements()
    {
        List<String> myList = Arrays.asList("XDM-trading", "XDM-commons", "xdm-settlement", "EnergeyaConnectorService");

        Stream<String> sortedStream = myList
            .stream()
            .filter(s -> s.startsWith("XDM"))
            .map(String::toLowerCase)
            .sorted();

        long counterElements = sortedStream.count();

        // Expected result :

        // xdm-commons
        // xdm-trading

        // Other Equivalent Example

        Assert.assertEquals(counterElements, 2);
        Stream<String> sortedStreamEquivalent = myList
            .stream()
            .filter(s -> s.startsWith("XDM"))
            .map(String::toLowerCase)
            .sorted();

        long equivalentCounterElements = sortedStreamEquivalent.mapToLong(e -> 1L).sum();

        Assert.assertEquals(equivalentCounterElements, 2);
    }

    /**
     * Select the first element of the stream
     */
    @Test
    public void testSequentialStream_SelectFirstElementOfStream()
    {
        Arrays
            .asList("xdm-trading", "xdm-commons", "xdm-settlement")
            .stream()
            .findFirst()
            .ifPresent(System.out::println);

        // Expected result : xdm-trading

        Stream
            .of("xdm-trading", "xdm-commons", "xdm-settlement")
            .findFirst()
            .ifPresent(System.out::println);

        // Expected result : xdm-trading
    }

    /**
     * Java streams cannot be reused.
     * As soon as you call any terminal operation the stream is closed.
     */
    @Test(expectedExceptions = IllegalStateException.class)
    public void testSequentialStream_ReusingStreamsException()
    {
        Stream<String> stream = Stream
            .of("xdm-trading", "xdm-commons", "xdm-settlement")
            .filter(s -> s.startsWith("xdm"));

        stream.anyMatch(s -> s.startsWith("xdm"));    // ok
        stream.noneMatch(s -> s.startsWith("xdm-booommmmm"));   // exception
    }

    /**
     * Solution to reuse stream
     */
    @Test
    public void testSequentialStream_ReusingStreams()
    {
        Supplier<Stream<String>> streamSupplier = () -> Stream
            .of("xdm-trading", "xdm-commons", "xdm-settlement")
            .filter(s -> s.startsWith("xdm"));

        streamSupplier.get().anyMatch(s -> s.startsWith("xdm"));   // ok
        streamSupplier.get().noneMatch(s -> s.startsWith("xdm-booo"));  // ok
    }

    @Test
    public void testSequentialStream_Collect()
    {
        List<Person> persons = getPersons();

        List<Person> filteredPersons = persons
            .stream()
            .filter(p -> p.name.startsWith("M"))
            .collect(Collectors.toList());

        Assert.assertEquals(filteredPersons.size(), 2); // Mauro, Mirko
    }

    @Test
    public void testSequentialStream_Collect_Average()
    {
        Map<Integer, List<Person>> personsByAge = getPersons()
            .stream()
            .collect(Collectors.groupingBy(p -> p.age));

        personsByAge.forEach((age, p) -> System.out.format("age %s: %s\n", age, p));

        Assert.assertEquals(personsByAge.size(), 3);

        // age 18: [Davide, Giovanni]
        // age 23: [Antonio, Fabio]
        // age 12: [Mauro, Mirko]

    }

    @Test
    public void testSequentialStream_Collect_Statistics()
    {
        IntSummaryStatistics ageSummary = getPersons()
            .stream()
            .collect(Collectors.summarizingInt(p -> p.age));

        System.out.println(ageSummary);
        // IntSummaryStatistics{count=6, sum=106, min=12, average=17,666667, max=23}

        Assert.assertEquals(ageSummary.getCount(), 6);
        Assert.assertEquals(ageSummary.getSum(), 106);
        Assert.assertEquals(ageSummary.getMin(), 12);
        Assert.assertEquals(ageSummary.getMax(), 23);
    }

    /**
     * Flat Map is used when we want to transform one object into multiple others objects
     */
    @Test
    public void testSequentialStream_FlatMap()
    {
        getSubModules()
            .stream()
            .flatMap(f -> f.modules.stream())
            .forEach(b -> System.out.println(b.name));

        // Xdm1 <- Xdmsub1
        // Xdm2 <- Xdmsub1
        // Xdm3 <- Xdmsub1
        // Xdm1 <- Xdmsub2
        // Xdm2 <- Xdmsub2
        // Xdm3 <- Xdmsub2
        // Xdm1 <- Xdmsub3
        // Xdm2 <- Xdmsub3
        // Xdm3 <- Xdmsub3

        // the above code example can be simplified into :

        IntStream
            .range(1, 4)
            .mapToObj(i -> new SubModule("Xdmsub" + i))
            .peek(f -> IntStream
                // Peek Returns a stream consisting of the elements of this stream, additionally
                // performing the provided action on each element as elements are consumed from the
                // resulting stream.
                .range(1, 4)
                .mapToObj(i -> new Module("Xdm" + i + " <- " + f.name))
                .forEach(f.modules::add))
            .flatMap(f -> f.modules.stream())
            .forEach(b -> System.out.println(b.name));
    }


    /**
     * The operator Reduce, reduces a stream of elements to exactly one element of the stream.
     */
    @Test
    public void testSequentialStream_Reduce()
    {
        getPersons()
            .stream()
            .reduce((p1, p2) -> p1.age > p2.age ? p1 : p2)
            .ifPresent(System.out::println);

        // expected --> Fabio
    }


    /********************** START PARALLELAL STREAM ************************************/

    /**
     * Streams can be executed in parallel to increase runtime performance on large amount of input elements. Parallel
     * streams use a ForkJoinPool available via the static ForkJoinPool.commonPool() method.
     *
     * The size of the underlying thread-pool uses up to five threads - depending on the amount of available physical
     * CPU cores (Runtime.getRuntime().availableProcessors())
     *
     * On my machine the common pool is initialized with a parallelism of 3 per default. This value can be decreased or
     * increased by setting the following JVM parameter:
     *
     * -Djava.util.concurrent.ForkJoinPool.common.parallelism=3
     */
    @Test
    public void testParallelalStream_One()
    {
        ForkJoinPool commonPool = ForkJoinPool.commonPool();
        int parallelism = commonPool.getParallelism();
        Assert.assertEquals(parallelism, 3); // 3
    }

    /**
     * Collections support the method parallelStream() to create a parallel stream of elements.
     * Alternatively you can call the intermediate method parallel() on a given stream to convert a sequential stream to
     * a parallel counterpart.
     */
    @Test
    public void testParallelalStream_Two()
    {
        Arrays
            .asList("a1", "a2", "b1", "b2", "c1")
            .parallelStream()
            .filter(s ->
            {
                System.out.format(
                    "filter: %s [%s]\n",
                    s,
                    Thread.currentThread().getName());
                return true;
            })
            .map(s ->
            {
                System.out.format(
                    "map: %s [%s]\n",
                    s,
                    Thread.currentThread().getName());
                return s.toUpperCase();
            })
            .forEach(s -> System.out.format(
                "forEach: %s [%s]\n",
                s,
                Thread.currentThread().getName()));


        /*
         * The output is :
         *
         filter: b1 [main]
		 filter: c1 [ForkJoinPool.commonPool-worker-2]
		 map: c1 [ForkJoinPool.commonPool-worker-2]
		 filter: a1 [ForkJoinPool.commonPool-worker-3]
		 map: a1 [ForkJoinPool.commonPool-worker-3]
		 forEach: A1 [ForkJoinPool.commonPool-worker-3]
		 filter: a2 [ForkJoinPool.commonPool-worker-1]
		 map: a2 [ForkJoinPool.commonPool-worker-1]
		 forEach: A2 [ForkJoinPool.commonPool-worker-1]
		 filter: b2 [ForkJoinPool.commonPool-worker-3]
		 map: b2 [ForkJoinPool.commonPool-worker-3]
		 forEach: B2 [ForkJoinPool.commonPool-worker-3]
		 forEach: C1 [ForkJoinPool.commonPool-worker-2]
		 map: b1 [main]
		 forEach: B1 [main]
         */
    }

    /********************** END PARALLEL STREAM ************************************/


    /***** START - DIFFERENCES BETWEEN SEQUENTIAL AND PARALLELAL STREAM ********************/
    @Test(enabled = true)
    public void testDifferencesBetweenSequentialAndParallelalStream()
    {
        int max = 1000000;
        List<String> values = new ArrayList<>(max);
        for (int i = 0; i < max; i++)
        {
            UUID uuid = UUID.randomUUID();
            values.add(uuid.toString());
        }

        // Sequential Stream
        long t0 = System.nanoTime();

        long count = values.stream().sorted().count();
        System.out.println(count);

        long t1 = System.nanoTime();

        long millisSequentialStream = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
        String sequentialStreamTime = String.format("sequential sort took: %d ms", millisSequentialStream);
        System.out.println(sequentialStreamTime);

        // Parallelal Stream

        t0 = System.nanoTime();

        count = values.parallelStream().sorted().count();
        System.out.println(count);

        t1 = System.nanoTime();

        long millisParallelalStream = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
        String parallelalStreamTime = String.format("parallel sort took: %d ms", millisParallelalStream);
        System.out.println(parallelalStreamTime);


        // ASSERT
        Assert.assertTrue(millisParallelalStream < millisSequentialStream);
    }

    /***** END - DIFFERENCES BETWEEN SEQUENTIAL AND PARALLELAL STREAM ********************/


    private List<SubModule> getSubModules()
    {
        List<SubModule> subModules = new ArrayList<>();

        // create sub modules
        IntStream
            .range(1, 4)
            .forEach(i -> subModules.add(new SubModule("Xdmsub" + i)));

        // create modules
        subModules.forEach(f -> IntStream
            .range(1, 4)
            .forEach(i -> f.modules.add(new Module("Xdm" + i + " <- " + f.name))));

        return subModules;
    }

    private List<Person> getPersons()
    {
        List<Person> persons = Arrays.asList(
            new Person("Davide", 18),
            new Person("Giovanni", 18),
            new Person("Antonio", 23),
            new Person("Fabio", 23),
            new Person("Mauro", 12),
            new Person("Mirko", 12));

        return persons;
    }


    private class Person
    {
        private String name;

        private int age;

        Person(String name, int age)
        {
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString()
        {
            return name;
        }
    }

}
