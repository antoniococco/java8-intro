/**
 * Copyright (c) Energeya LLC.  All rights reserved. http://www.energeya.com
 */
package com.acocco.java8.consumers;

import java.util.function.Consumer;

import org.testng.annotations.Test;

import com.acocco.java8.stream.model.Person;


/**
 *
 * Consumers represents operations to be performed on a single input argument.
 *
 * @author acocco
 * @version $Id$
 *
 */
public class ConsumerTest
{
    @Test
    public void testConsumers()
    {
        Consumer<Person> greeter = (p) -> System.out.println("Ciao, " + p.name + " eta' : " + p.age);
        greeter.accept(new Person("Antonio", 34)); // Expected result "Ciao, Antonio eta' : 34"

        // Other example

        Consumer<Long> longConsumer = (p) ->
        {
            Long result = p + 2;
            System.out.println("result : " + result);
        };
        longConsumer.accept(32L); // Expected result "result : 34"
    }
}
