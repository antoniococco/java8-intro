/**
 * Copyright (c) Energeya LLC.  All rights reserved. http://www.energeya.com
 */
package com.acocco.java8.map;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.testng.Assert;
import org.testng.annotations.Test;


/**
 *
 * Maps don't support streams.
 *
 * Instead maps now support various new and useful methods for doing common tasks.
 *
 * @author acocco
 * @version $Id$
 */
public class MapTest
{
    @Test
    public void testMap()
    {
        /*
         * putIfAbsent prevents us from writing additional if null checks;
         * forEach accepts a consumer to perform operations for each value of the map.
         */
        Map<Integer, String> map = new HashMap<>();

        for (int i = 0; i < 10; i++)
        {
            map.putIfAbsent(i, "value" + i);
        }

        map.forEach((id, val) -> System.out.println(val));

        // --------------------------

        map.computeIfPresent(3, (num, val) -> val + num);
        Assert.assertEquals(map.get(3), "value33");             // value33

        map.computeIfAbsent(23, num -> "value" + num);
        Assert.assertTrue(map.containsKey(23));    // true
        Assert.assertEquals(map.get(23), "value23");

        map.computeIfAbsent(3, num -> "antonio");
        Assert.assertEquals(map.get(3), "value33");            // value33


        // how to remove entries for a a given key,
        // only if it's currently mapped to a given value:
        map.remove(3, "val33");
        Assert.assertEquals(map.get(3), "value33");            // val33

        map.remove(3, "value33");
        Assert.assertNull(map.get(3));             // null


        // Return default
        String orDefault = map.getOrDefault(42, "not found");
        Assert.assertEquals(orDefault, "not found");  // not found

        // Example of search method by key - This method is used only for concurrent map
        ConcurrentHashMap<String, String> concurrentMap = new ConcurrentHashMap<>();
        concurrentMap.put("antonio", "Engineer, Professional Services");
        concurrentMap.put("davide", "Engineer, BA Liquid Corporate");
        concurrentMap.put("andrea", "Engineer, BA Liquid Corporate");
        String result = concurrentMap.search(3, (key, value) ->
        {
            System.out.println(Thread.currentThread().getName());
            if ("davide".equals(key))
            {
                return value;
            }
            return null;
        });
        Assert.assertNotNull(result);
        Assert.assertEquals(result, "Engineer, BA Liquid Corporate");


        // Example of search method by value - This method is used only for concurrent map
        result = concurrentMap.searchValues(3, value ->
        {
            System.out.println(Thread.currentThread().getName());
            if (value.contains("Professional"))
            {
                return value;
            }
            return null;
        });

        Assert.assertNotNull(result);
        Assert.assertEquals(result, "Engineer, Professional Services");
    }
}
