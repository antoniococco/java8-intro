/**
 * Copyright (c) Energeya LLC.  All rights reserved. http://www.energeya.com
 */
package com.acocco.java8.lambda;

/**
 * @author acocco
 * @version $Id$
 *
 */
interface IStudentFactory<S extends Student>
{
    S create(String firstName, String lastName);
}
