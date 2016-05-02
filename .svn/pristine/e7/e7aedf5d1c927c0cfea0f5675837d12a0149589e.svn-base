/**
 * Copyright (c) Energeya LLC.  All rights reserved. http://www.energeya.com
 */
package com.acocco.java8.optional;

import java.util.Optional;

import org.testng.Assert;
import org.testng.annotations.Test;


/**
 *
 * Optionals are not functional interfaces,
 * instead it's a nifty utility to prevent NullPointerException.
 *
 * Instead of returning null you return an Optional in Java 8.
 *
 * @author acocco
 * @version $Id$
 *
 */
public class OptionalTest
{
    @Test
    public void testOptional()
    {
        Optional<String> optional = Optional.of("antonio");

        Assert.assertTrue(optional.isPresent());           // true
        String name = optional.get();
        Assert.assertNotNull(name);
        Assert.assertEquals(name, "antonio");

        Assert.assertEquals(optional.orElse("fallback"), "antonio");

        optional.ifPresent((s) -> System.out.println("antonio vai a lavorare.."));
    }
}
