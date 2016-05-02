/**
 * Copyright (c) Energeya LLC.  All rights reserved. http://www.energeya.com
 */
package com.acocco.java8.predicate;

import java.util.function.Predicate;

import org.testng.Assert;
import org.testng.annotations.Test;


/**
 *
 * Predicates are boolean-valued functions based on one argument.
 *
 * The interface contains various default methods for composing predicates to complex logical terms (and, or, negate)
 *
 * @author acocco
 * @version $Id$
 *
 */
public class PredicateTest
{
    @Test
    public void testPredicate()
    {
        Predicate<String> predicate = (s) -> s.length() > 0;

        Assert.assertTrue(predicate.test("rino gaetano"));
        Assert.assertFalse(predicate.negate().test("rino gaetano"));
    }
}
