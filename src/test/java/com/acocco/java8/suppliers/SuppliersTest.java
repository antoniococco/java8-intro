/**
 * Copyright (c) Energeya LLC.  All rights reserved. http://www.energeya.com
 */
package com.acocco.java8.suppliers;

import java.util.function.Supplier;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.acocco.java8.stream.model.Module;
import com.acocco.java8.stream.model.Person;


/**
 *
 * Suppliers produce a result of a given generic type.
 * Unlike Functions, Suppliers don't accept arguments.
 *
 * @author aocco
 * @version $Id$
 *
 */
public class SuppliersTest
{
    @Test
    public void testSuppliers()
    {
        Supplier<Person> personSupplier = Person::new;
        Person person = personSupplier.get();

        Assert.assertNotNull(person);   // new Person


        Supplier<Module> moduleSupplier = Module::new;
        Module module = moduleSupplier.get();

        Assert.assertNotNull(module);   // new Module

    }
}
