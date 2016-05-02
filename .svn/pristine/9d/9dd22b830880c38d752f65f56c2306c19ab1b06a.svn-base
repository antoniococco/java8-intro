/**
 * Copyright (c) Energeya LLC.  All rights reserved. http://www.energeya.com
 */
package com.acocco.java8.lambda;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;


/**
 * @author acocco
 * @version $Id$
 *
 */
public class LambdaTest
{

    @Test
    public void testLambda_One()
    {
        List<String> names = Arrays.asList("giovanni", "davide", "antonio", "mauro");

        // Version without lambda expression
        Collections.sort(names, new Comparator<String>()
        {
            @Override
            public int compare(String a, String b)
            {
                return a.compareTo(b);
            }
        });

        Assert.assertEquals(names.get(0), "antonio");
        Assert.assertEquals(names.get(3), "mauro");

        // First version with lambda expression
        Collections.sort(names, (String a, String b) ->
        {
            return a.compareTo(b);
        });

        Assert.assertEquals(names.get(0), "antonio");
        Assert.assertEquals(names.get(3), "mauro");

        // Second version with lambda expression
        Collections.sort(names, (String a, String b) -> a.compareTo(b));

        Assert.assertEquals(names.get(0), "antonio");
        Assert.assertEquals(names.get(3), "mauro");

        // Third version with lambda expression
        Collections.sort(names, (a, b) -> a.compareTo(b));

        Assert.assertEquals(names.get(0), "antonio");
        Assert.assertEquals(names.get(3), "mauro");
    }

    /**
     * How does lambda expressions fit into Javas type system? Each lambda corresponds to a given type, specified by an
     * interface. A so called functional interface must contain exactly one abstract method declaration. Each lambda
     * expression of that type will be matched to this abstract method.
     *
     * Since default methods are not abstract you're free to add default methods to your functional interface.
     */
    @Test
    public void testFunctionalInterface()
    {
        IConverter<String, Long> converterFromStringToLong = (from) -> Long.valueOf(from);
        Long convertedStringToLong = converterFromStringToLong.convert("145");

        Assert.assertEquals(convertedStringToLong, new Long(145));

        IConverter<Long, String> converterFromLongToString = (from) -> String.valueOf(from);
        String convertedLongToString = converterFromLongToString.convert(145L);

        Assert.assertEquals(convertedLongToString, "145");
    }

    /**
     * The above example code can be further simplified by utilizing static method references:
     */
    @Test
    public void testMethodAndConstructorReferences()
    {
        // First version using static method references
        IConverter<String, Long> converterToLong = Long::valueOf;
        Long convertedToLong = converterToLong.convert("145");

        Assert.assertEquals(convertedToLong, new Long(145));

        // Second version using static method references
        WordAnalyzer something = new WordAnalyzer();
        IConverter<String, String> converterStartWith = something::startsWith;
        String convertedToString = converterStartWith.convert("Java8");

        Assert.assertEquals(convertedToString, "J");

        // Version using static method references
        /*
         * We create a reference to the Student constructor via Student::new. The Java compiler automatically chooses
         * the right constructor by matching the signature of IStudentFactory.create.
         */
        IStudentFactory<Student> studentFactory = Student::new;
        Student student = studentFactory.create("Antonio", "Cocco");

        Assert.assertNotNull(student);
        Assert.assertEquals(student.getFirstName(), "Antonio");
        Assert.assertEquals(student.getLastName(), "Cocco");
    }

}
