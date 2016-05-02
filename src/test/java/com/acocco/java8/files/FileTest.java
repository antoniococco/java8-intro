/**
 * Copyright (c) Energeya LLC.  All rights reserved. http://www.energeya.com
 */
package com.acocco.java8.files;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.testng.annotations.Test;


/**
 * Reading text files into memory and writing strings into a text file in Java 8 is finally a simple task.
 *
 * The method Files.readAllLines reads all lines of a given file into a list of strings
 *
 * @author acccco
 * @version $Id$
 *
 */
public class FileTest
{
    @Test
    public void testFile() throws IOException
    {
        List<String> lines;
        try
        {
            lines = Files.readAllLines(Paths.get("src/test.js"));
            lines.add("print('end reading files');");
            Files.write(Paths.get("src/test-modified.js"), lines);
        }
        catch (IOException e)
        {
            throw new IOException(e.getMessage());
        }


        // If you need more fine-grained control you can instead construct a new buffered reader:
        Path path = Paths.get("src/test.js");
        try (BufferedReader reader = Files.newBufferedReader(path))
        {
            System.out.println(reader.readLine());
        }

        // Or in case you want to write to a file simply construct a buffered writer instead:
        path = Paths.get("src/test-modified.js");
        try (BufferedWriter writer = Files.newBufferedWriter(path))
        {
            writer.write("print('EHHH CERTO');");
        }
    }
}
