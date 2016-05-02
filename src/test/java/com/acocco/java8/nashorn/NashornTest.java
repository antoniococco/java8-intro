/**
 * Copyright (c) Energeya LLC.  All rights reserved. http://www.energeya.com
 */
package com.acocco.java8.nashorn;

import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.testng.Assert;
import org.testng.annotations.Test;


/**
 * The Nashorn Javascript Engine is part of Java SE 8 and competes with other engines like Google V8 (the engine that
 * powers Google Chrome and Node.js).
 *
 * Nashorn extends Javas capabilities by running dynamic javascript code natively on the JVM.
 *
 * @author tony
 * @version $Id$
 *
 */
public class NashornTest
{
    @Test
    public void testNashorn() throws FileNotFoundException
    {

        // Invoking Javascript Functions from Java
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
        try
        {
            engine.eval(new FileReader("src/script.js"));

            Invocable invocable = (Invocable) engine;

            Object result = invocable.invokeFunction("fun1", "GINO GINA");
            System.out.println(result);
            Assert.assertEquals(result, "javascript end");
            System.out.println(result.getClass());

        }
        catch (FileNotFoundException | ScriptException | NoSuchMethodException e)
        {
            throw new FileNotFoundException(e.getMessage());
        }

        // Javascript Boo, GINO GINA
        // javascript end
        // class java.lang.String
    }
}
