package com.rental.util;

/**
 * Utility class just to hold string constants to avoid typos and re-definig them
 */
public class Constants {
    // Tool Types
    public static final String LADDER = "Ladder";
    public static final String CHAINSAW = "Chainsaw";
    public static final String JACKHAMMER = "Jackhammer";

    // Tool Codes
    public static final String LADW = "LADW";
    public static final String CHNS = "CHNS";
    public static final String JAKR = "JAKR";
    public static final String JAKD = "JAKD";

    // Tool Brands
    public static final String WERNER = "Werner";
    public static final String SITHL = "Sithl";
    public static final String RIDGID = "Ridgid";
    public static final String DEWALT = "DeWalt";

    // Error Messages
    public static final String BAD_TOOL_CODE = "Unknown tool code: ";
    public static final String BAD_RENTAL_DAYS =
            "Invalid amount entered for number of rental days";
    public static final String BAD_PERCENT_DISCOUNT = "Malformed percent discount input";
    public static final String INVALID_DATE_FORMAT = "Please enter date in mm/dd/yy format";
    public static final String INVALID_MONTH = "Please enter a month in the range 1-12";
    public static final String INVALID_DAY = "Given day does not exist is specified month";
    public static final String INVALID_YEAR = "Please enter a value between 0-99 for year";
    public static final String INVALID_LEAP_DAY = "2/29 is not a valid day for given year";
    public static final String REPEAT_TOOL_CODE = "Repeat Tool codes are not allowed";

    private Constants() {
        // Utility class, should not be instantiated
    }
}
