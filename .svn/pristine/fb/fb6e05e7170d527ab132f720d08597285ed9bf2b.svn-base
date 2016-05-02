/**
 * Copyright (c) Energeya LLC.  All rights reserved. http://www.energeya.com
 */
package com.acocco.java8.defaultmethods;

import org.testng.Assert;
import org.testng.annotations.Test;


/**
 * Java 8 enables us to add non-abstract method implementations to interfaces by utilizing the default keyword. This
 * feature is also known as Extension Methods.
 *
 * This does not work with lambda expressions.
 *
 *
 * @author acocco
 * @version $Id$
 *
 */
public class DefaultMethodTest
{
    @Test
    public void testDefaultMethodTest_One()
    {
        ICalculatorDefaultMethod formula = new ICalculatorDefaultMethod()
        {
            @Override
            public double calculate(int a)
            {
                return sqrt(a * 100);
            }
        };

        double result = formula.calculate(100);     // 100.0
        double sqrt = formula.sqrt(16);           // 4.0

        Assert.assertEquals(result, 100d);
        Assert.assertEquals(sqrt, 4d);

        System.out.println("Result : " + result);
        System.out.println("Result sqrt : " + sqrt);
    }

}
