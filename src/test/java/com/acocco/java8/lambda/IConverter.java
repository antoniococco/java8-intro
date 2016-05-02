/**
 * Copyright (c) Energeya LLC.  All rights reserved. http://www.energeya.com
 */
package com.acocco.java8.lambda;

/**
 * @author acocco
 * @version $Id$
 *
 */
@FunctionalInterface
interface IConverter<F, T>
{
    T convert(F from);
}
