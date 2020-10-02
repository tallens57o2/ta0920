package com.rental.util;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Utility class responsible for converting dates in string format to Java
 * Calender class objects to do actual logic on
 */
public class DateConverter {
    // Store the number of days in each month of the standard year
    private static int[] daysInMonth = new int[]{31,28,31,30,31,30,31,31,30,31,30,31};

    /**
     * Creates a Calender object from the specified mm/dd/yy formatting
     *
     * @param dateString date as a String of the mm/dd/yy form
     * @return Calender with the specified date
     */
    public static Calendar createCalenderFromString(String dateString) {
        try {
            String[] tokens = dateString.split("/");

            if (tokens.length != 3) {
                throw new RuntimeException(Constants.INVALID_DATE_FORMAT);
            }

            int month = Integer.parseInt(tokens[0]) - 1; // Calender uses 0-11 for months
            if (month < 0 || month > 11) {
                throw new RuntimeException(Constants.INVALID_MONTH);
            }

            int day = Integer.parseInt(tokens[1]);
            /* assuming we only care about the years 2000-2099, since only supporting
               2 digit years, so appending "20" to start of year */
            int year = Integer.parseInt("20" + tokens[2]);

            if (month == 1 && day == 29) { //special if leap day is start date
                if (year % 4 != 0) {
                    throw new RuntimeException(Constants.INVALID_LEAP_DAY);
                }
            }
            // Otherwise, ensure day exists in given month
            else if (day > daysInMonth[month]) {
                throw new RuntimeException(Constants.INVALID_DAY);
            }
            else if (year < 2000 || year > 2100) {
                throw new RuntimeException(Constants.INVALID_YEAR);
            }

            return new GregorianCalendar(year, month, day);
        }
        catch (RuntimeException e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    /**
     *
     *
     * @param calender Calender corresponding to current year and start date
     * @param daysToAdd number of days that still need to be incremented
     * @return Specified end of rental date in mm/dd/yy string form
     */
    public static String getReturnDate(Calendar calender, int daysToAdd) {
        int month = calender.get(Calendar.MONTH);
        int day = calender.get(Calendar.DAY_OF_MONTH);
        int year = calender.get(Calendar.YEAR);

        // Loop checks if date falls after current month
        while (daysToAdd >= getDaysLeftInMonth(day, month, year)) {
            daysToAdd -= getDaysLeftInMonth(day, month, year);
            day = 0; // if we are incrementing the month, start day is the first
            month++;
        }

        return new StringBuilder()
                .append(month + 1)
                .append("/")
                .append(day + daysToAdd)
                .append("/")
                .append(year - 2000)
                .toString();
    }

    /**
     * Determines how many days are left in the month
     *
     * @param day day of the month currently on
     * @param month the month in question
     * @param year the year in question (only matters for Leap Day)
     * @return number of days left in the month
     */
    private static int getDaysLeftInMonth(int day, int month, int year) {
        int daysInMonth = month != 2 ? DateConverter.daysInMonth[month] :
                year % 4 == 0 ? 29 : 28;
        return daysInMonth - day;
        /*return month != 2 ? daysInMonth[month] :
            year % 4 == 0 ? 29 : 28;*/
    }

    private DateConverter() {
        // Utility class, should not be instantiated
    }
}
