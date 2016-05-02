/**
 * Copyright (c) Energeya LLC.  All rights reserved. http://www.energeya.com
 */
package com.acocco.java8.date;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.zone.ZoneRules;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.testng.Assert;
import org.testng.annotations.Test;


/**
 *
 * The following examples cover the most important parts of this new API
 *
 * @author acocco
 * @version $Id$
 *
 */
public class DateTest
{
    /**
     * Clock provides access to the current date and time.
     * Clocks are aware of a timezone and may be used instead
     * of System.currentTimeMillis() to retrieve the current milliseconds
     */
    @Test
    public void testClock()
    {
        Clock clock = Clock.systemDefaultZone();
        long millis = clock.millis();

        Assert.assertNotNull(millis);

        Instant instant = clock.instant();
        Date legacyDate = Date.from(instant);   // legacy java.util.Date

        Assert.assertNotNull(legacyDate);

        Calendar now = Calendar.getInstance();
        now.setTime(legacyDate);

        Assert.assertEquals(now.get(Calendar.YEAR), 2015);
    }

    @Test
    public void testTimezones()
    {
        System.out.println(ZoneId.getAvailableZoneIds());
        // prints all available timezone ids

        ZoneId zone1 = ZoneId.of("Europe/Berlin");
        ZoneId zone2 = ZoneId.of("Brazil/East");

        ZoneRules rules = zone1.getRules();
        ZoneRules rules2 = zone2.getRules();
        System.out.println(rules);
        System.out.println(rules2);

        // ZoneRules[currentStandardOffset=+01:00]
        // ZoneRules[currentStandardOffset=-03:00]
    }

    /**
     * LocalTime represents a time without a timezone
     */
    @Test
    public void testLocalTime()
    {
        ZoneId zone1 = ZoneId.of("Europe/Berlin");
        ZoneId zone2 = ZoneId.of("Brazil/East");

        LocalTime now1 = LocalTime.now(zone1);
        LocalTime now2 = LocalTime.now(zone2);

        System.out.println(now1.isBefore(now2));  // false
        Assert.assertFalse(now1.isBefore(now2));

        long hoursBetween = ChronoUnit.HOURS.between(now1, now2);
        long minutesBetween = ChronoUnit.MINUTES.between(now1, now2);

        System.out.println(hoursBetween);       // -5
        System.out.println(minutesBetween);     // -239

        // Another example
        LocalTime late = LocalTime.of(23, 59, 59);
        System.out.println(late);       // 23:59:59

        DateTimeFormatter italianFormatter = DateTimeFormatter
            .ofLocalizedTime(FormatStyle.SHORT)
            .withLocale(Locale.ITALIAN);

        LocalTime leetTime = LocalTime.parse("13.37", italianFormatter);
        System.out.println(leetTime);   // 13:37
        Assert.assertEquals(leetTime.getHour(), 13);
        Assert.assertEquals(leetTime.getMinute(), 37);
    }

    /**
     * LocalDate represents a distinct date, e.g. 2014-03-25.
     * It's immutable and works exactly analog to LocalTime.
     *
     * The sample demonstrates how to calculate new dates by
     * adding or substracting days, months or years.
     *
     * Keep in mind that each manipulation returns a new instance, LocalDate is immutable
     */
    @Test
    public void testLocalDate()
    {
        LocalDate independenceDay = LocalDate.of(2014, Month.JULY, 4);
        LocalDate tomorrow = independenceDay.plus(1, ChronoUnit.DAYS);
        LocalDate yesterday = tomorrow.minusDays(2);

        Assert.assertNotNull(yesterday);

        DayOfWeek dayOfWeek = independenceDay.getDayOfWeek();
        String dayName = dayOfWeek.name();
        System.out.println(dayName);    // FRIDAY
        Assert.assertEquals(dayName, "FRIDAY");

        // Parsing a LocalDate from a string is just as simple as parsing a LocalTime:
        DateTimeFormatter germanFormatter = DateTimeFormatter
            .ofLocalizedDate(FormatStyle.MEDIUM)
            .withLocale(Locale.GERMAN);

        LocalDate xmas = LocalDate.parse("24.12.2014", germanFormatter);
        System.out.println(xmas);   // 2014-12-24
    }

    /**
     * LocalDateTime represents a date-time.
     * It combines date and time as seen in the above sections into one instance.
     * LocalDateTime is immutable and works similar to LocalTime and LocalDate.
     */
    @Test
    public void testLocalDateTime()
    {
        LocalDateTime sylvester = LocalDateTime.of(2014, Month.DECEMBER, 31, 23, 59, 59);

        DayOfWeek dayOfWeek = sylvester.getDayOfWeek();
        System.out.println(dayOfWeek.name());      // WEDNESDAY
        Assert.assertEquals(dayOfWeek.name(), "WEDNESDAY");

        Month month = sylvester.getMonth();
        System.out.println(month.name());          // DECEMBER
        Assert.assertEquals(month.name(), "DECEMBER");

        long minuteOfDay = sylvester.getLong(ChronoField.MINUTE_OF_DAY);
        System.out.println(minuteOfDay);    // 1439

        // With the additional information of a timezone it can be converted to an instant.
        // Instants can easily be converted to legacy dates of type java.util.Date.

        Instant instant = sylvester
            .atZone(ZoneId.systemDefault())
            .toInstant();

        Date legacyDate = Date.from(instant);
        System.out.println(legacyDate);     // Wed Dec 31 23:59:59 CET 2014

    }

}
