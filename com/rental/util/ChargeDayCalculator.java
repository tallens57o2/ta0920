package com.rental.util;

import com.rental.objects.ToolType;

import java.util.Calendar;

/**
 * Utility class responsible for the logic for calculating if a specific day
 * should be charged or not. Everything is done based on the day of the current
 * year (if the range spans multiple years, each year is handled separately
 * and summed). This is used to identify which day of the year each holiday
 * is observed, as well as based on which day of the week the year starts on,
 * each day can be identified as a weekday/weekend.
 */
public class ChargeDayCalculator {
    /**
     * Calculates the number of each type of day for a given year
     *
     * @param calendar contains the start day to begin counting
     * @param chargeDays number of days in the given year to check
     * @return a Days helper class the contains the number of each type of day
     */
    public static Days calculateChargeDays(Calendar calendar, int chargeDays) {
        // day of the week the year starts on (1-Sunday ... 7-Saturday)
        int yearStartDayOfWeek = calcYearStartDayOfWeek(calendar);
        // day of the year which the 4th of July is observed
        int fourthOfJulyDayOfYear = calculateFourthOfJulyOfYear(calendar, yearStartDayOfWeek);
        // day of the year that Labor Day falls on
        int laborDayDayDayOfYear = calculateLaborDayOfYear(calendar, yearStartDayOfWeek);

        /**
         * Every week besides border weeks contains 5 weekdays and 2 weekends.
         * It is always possible to consider one side a full week
         * (if range is at least 7), so only have to individually check
         * a single end of the range.
         */
        int weeks = chargeDays / 7; // counts number of full 7 day weeks
        // Handles the "edge days" that do not complete a full week
        Days days = calculatePartialWeek(calendar.get(Calendar.DAY_OF_WEEK),
                                    chargeDays % 7);
        days.addWeeks(weeks); // add 5 weekdays and 2 weekends for each full week

        /**
         * Based on our rules, each holiday will always replace a weekday,
         * so if either of the holidays for the current year fall in our date range,
         * replace a weekday with a holiday
         */
        if (fourthOfJulyDayOfYear > calendar.get(Calendar.DAY_OF_YEAR) &&
            fourthOfJulyDayOfYear < calendar.get(Calendar.DAY_OF_YEAR) + chargeDays) {
            days.addHoliday();
        }

        if (laborDayDayDayOfYear > calendar.get(Calendar.DAY_OF_YEAR) &&
            laborDayDayDayOfYear < calendar.get(Calendar.DAY_OF_YEAR) + chargeDays) {
            days.addHoliday();
        }

        return days;
    }

    /**
     * Calculates which day of the year the 4th of July falls on
     *
     * @param calendar contains the year in question
     * @param yearStartDay the day of the week the year starts on
     * @return the day of the year to observe July 4th on
     */
    private static int calculateFourthOfJulyOfYear(Calendar calendar, int yearStartDay) {
        /* July 4th will either be the 185th or 186th day of the year depending
           Leap Day
        */
        int dayOfYear = calendar.get(Calendar.YEAR) % 4 == 0 ? 186 : 185;
        /* Find the day of the week for the holiday, but if it falls on a weekend
         change the day it is observed to the neighboring weekday
         */
        int dayOfWeek = calculateDayOfWeek(dayOfYear, yearStartDay);

        if (dayOfWeek == 1) {
            return dayOfYear + 1;
        }
        else if (dayOfWeek == 7) {
            return dayOfYear - 1;
        }
        else {
            return dayOfYear;
        }
    }

    /**
     * Calculates which day of the year Labor Day falls on
     *
     * @param calendar contains the year in question
     * @param yearStartDay the day of the week the year starts on
     * @return the day of the year to observe Labor Day
     */
    private static int calculateLaborDayOfYear(Calendar calendar, int yearStartDay) {
        /* Determines which day of the year 9/1 will be on to start considering
           when Labor Day will be observed
         */
        int dayOfYear = calendar.get(Calendar.YEAR) % 4 == 0 ? 245 : 244;
        // Finds the day of week that 9/1 falls on
        int dayOfWeek = calculateDayOfWeek(dayOfYear, yearStartDay);

        // If 9/1 is a Sunday, Labor Day will fall on the following day
        if (dayOfWeek == 1) {
            return dayOfYear + 1;
        }
        // If 9/1 is a Monday, it is Labor Day
        else if (dayOfWeek == 2) {
            return dayOfYear;
        }
        /* Otherwise, Labor Day is the following Monday, so add a week (7),
           then 2 for the number representing Monday, then subtract the amount
           corresponding to the day of the week 9/1 falls on
         */
        else {
            return dayOfYear + 9 - dayOfWeek;
        }
    }

    /**
     * Calculates the day of the week the corresponding year starts on
     *
     * @param calendar containing the desired year
     * @return int representing the day of the week January 1st is on
     */
    private static int calcYearStartDayOfWeek(Calendar calendar) {
        /* The notion here is that based on the current day of the year,
           calculate the mod 7 to represent the offset from the first
           of January, and use this to find 1/1 day of the week. Need
           to add 1 since the count starts at 1 and not 0
         */
        int startDay = calendar.get(Calendar.DAY_OF_WEEK) + 1 -
                       calendar.get(Calendar.DAY_OF_YEAR) % 7;
        // Since % in Java returns remainder need to ensure result is positive
        return startDay > 0 ? startDay : startDay + 7;
    }

    /**
     * Effectively does the reverse operation of the above:
     * Calculates the day of the week a specific day of the year falls on
     *
     * @param dayOfYear day of year for date in question
     * @param yearStartDay day of the week the year starts on
     * @return the number corresponding to the day of the week for desired date
     */
    private static int calculateDayOfWeek(int dayOfYear, int yearStartDay) {
        int dayInWeek = (dayOfYear - 1) % 7 + yearStartDay;
        return dayInWeek <= 7 ? dayInWeek : dayInWeek - 7;
    }

    /**
     * Calculates the number of each type of day for a partial week
     *
     * @param startDay number represnting the day in the week to start on
     * @param days number of days to count
     * @return Days object containing the number of each type of day
     */
    private static Days calculatePartialWeek(int startDay, int days) {
        int weekdays = 0;
        int weekends = 0;

        for (int i = 0; i < days; i++) {
            // add i + 1 since we count last day, but not first
            // if it is a Sunday or Saturday, increase weekends, otherwise weekdays
            if ((startDay + i + 1) % 7 == 1 || (startDay + i + 1) % 7 == 0) {
                weekends++;
            }
            else {
                weekdays++;
            }
        }

        return new Days(weekdays, weekends, 0);
    }

    /**
     * Calculates the number of days to charge based on number of each type
     * of day and the type of tool being rented
     *
     * @param days object containing counts of each type of day
     * @param toolType type of tool being rented
     * @return count of the number of days that are to be charged
     */
    public static int calculateDaysToCharge(Days days, ToolType toolType) {
        int chargeDays = 0;
        if (toolType.isChargeWeekday()) {
            chargeDays += days.weekdays;
        }
        if (toolType.isChargeWeekend()) {
            chargeDays += days.weekends;
        }
        if (toolType.isChargeHoliday()) {
            chargeDays += days.holidays;
        }
        return chargeDays;
    }

    private ChargeDayCalculator() {
        // Utility class, should not be instantiated
    }

    /**
     * Helper class to keep track of the current count of each type of day
     */
    public static class Days {
        // Left package-protected so parent class can access values directly
        int weekdays;
        int weekends;
        int holidays;

        /**
         * Default constructor to instantiate empty count
         */
        public Days() {
            weekdays = 0;
            weekends = 0;
            holidays = 0;
        }

        /**
         * Constructor allowing to set counts to specific amounts
         * @param weekdays current number of weekdays
         * @param weekends current number of weekends
         * @param holidays current number of holidays
         */
        public Days(int weekdays, int weekends, int holidays) {
            this.weekdays = weekdays;
            this.weekends = weekends;
            this.holidays = holidays;
        }

        /**
         * Allows for summing of two different objects
         *
         * @param days addend value to be added to current count
         */
        public void add(Days days) {
            this.weekdays += days.weekdays;
            this.weekends += days.weekends;
            this.holidays += days.holidays;
        }

        /**
         * Allows for adding of entire weeks to current counts
         *
         * @param weeks number of weeks to add
         */
        public void addWeeks(int weeks) {
            this.weekdays += 5 * weeks;
            this.weekends += 2 * weeks;
        }

        /**
         * Allows for simple addition of a holiday to be counted
         * (always replaces a weekday since both holidays are always observed
         * on a weekday as opposed to the weekend)
         */
        public void addHoliday() {
            weekdays--;
            holidays++;
        }
    }
}
