/**
 * Copyright (c) Energeya LLC.  All rights reserved. http://www.energeya.com
 */
package com.acocco.java8.function;

import java.util.function.Function;

import org.testng.Assert;
import org.testng.annotations.Test;


/**
 *
 * Functions accept one argument and produce a result.
 * Default methods can be used to chain multiple functions together (compose, andThen)
 *
 * @author acocco
 * @version $Id$
 *
 */
public class FunctionTest
{
    @Test
    public void testFunction()
    {
        Function<String, Integer> toInteger = Integer::valueOf;
        Function<String, String> backToString = toInteger.andThen(String::valueOf);

        String apply = backToString.apply("456");

        Assert.assertEquals(apply, "456");
        System.out.println("Result : " + apply);
    }
}
