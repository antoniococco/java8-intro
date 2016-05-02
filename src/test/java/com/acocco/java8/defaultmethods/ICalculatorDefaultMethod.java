/**
 * Copyright (c) Energeya LLC.  All rights reserved. http://www.energeya.com
 */
package com.acocco.java8.defaultmethods;

/**
 * @author acocco
 * @version $Id$
 *
 */
public interface ICalculatorDefaultMethod
{
    double calculate(int a);

    default double sqrt(int a)
    {
        return Math.sqrt(a);
    }
}
