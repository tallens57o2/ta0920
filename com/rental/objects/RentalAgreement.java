package com.rental.objects;

import com.rental.util.ChargeDayCalculator;
import com.rental.util.DateConverter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Class representing a Rental Agreement. Calculates all desired fields
 * upon construction, and has a toString to allow easy output to command line
 */
public class RentalAgreement {
    private final String toolCode;
    private final Tool tool;
    private final String checkoutDate;
    private final String returnDate;
    private final Integer rentalDays;
    private final Integer chargeDays;
    private final BigDecimal preDiscountCharge;
    private final Integer percentDiscount;
    private final BigDecimal discountAmount;
    private final BigDecimal finalCharge;

    public RentalAgreement(String toolCode, Tool tool, String checkoutDate,
                           Integer rentalDays, Integer percentDiscount) {
        this.toolCode = toolCode;
        this.tool = tool;
        this.checkoutDate = checkoutDate;
        this.rentalDays = rentalDays;
        this.percentDiscount = percentDiscount;

        /* Some of these calculations are done in the constructor to allow all fields
         * while also avoiding recalculating if multiple years (hence multiple
         * calender objects) are required
         */

        // Running total of each type of day in rental period
        ChargeDayCalculator.Days days = new ChargeDayCalculator.Days();
        // Remaining number of days needing to be counted
        int remainingRentalDays = rentalDays;
        // Calender object for the current year being counted
        Calendar calendar = DateConverter.createCalenderFromString(checkoutDate);

        int daysLeftInYear = (calendar.get(Calendar.YEAR) % 4 == 0 ? 366 : 365) -
                calendar.get(Calendar.DAY_OF_YEAR);
        // Need to count first day in calender if a year has wrapped
        boolean yearWrap = false;

        // If the rental period extends beyond current year, will handle each year in turn
        while (remainingRentalDays > daysLeftInYear) {
            if (!yearWrap) {
                yearWrap = true;
            }
            // Counts number of each type of day left in the year
            days.add(ChargeDayCalculator.calculateChargeDays(calendar, daysLeftInYear));
            // Updates number of days required to count
            remainingRentalDays -= daysLeftInYear;
            // Constructs a new calender object for the next year, starting on January 1st
            calendar = new GregorianCalendar(calendar.get(Calendar.YEAR) + 1, 0, 1);
            // Number of days in year, depending on if it contains Leap Day
            daysLeftInYear = calendar.get(Calendar.YEAR) % 4 == 0 ? 366 : 365;
        }
        // Last year to count, rental period expires in year represented by the calender
        days.add(ChargeDayCalculator.calculateChargeDays(calendar, remainingRentalDays));

        // Based on number of each type of day in period, how many to charge
        chargeDays = ChargeDayCalculator.calculateDaysToCharge(days, tool.getType());
        // Finds and formats return date for the tool
        returnDate = DateConverter.getReturnDate(
                calendar, remainingRentalDays - (yearWrap ? 1 : 0));

        // Daily charge x number of chargeable days
        preDiscountCharge = getDailyCharge().multiply(BigDecimal.valueOf(chargeDays));
        // Pre-discount charge x Percent discount x .01 (half-rounded up to cents)
        discountAmount = preDiscountCharge.multiply(BigDecimal.valueOf(
                percentDiscount, 2)).setScale(2, RoundingMode.HALF_UP);
        // Pre-discount charge - discount amount
        finalCharge = preDiscountCharge.subtract(discountAmount);
    }

    public String getToolCode() {
        return toolCode;
    }

    public String getToolTypeName() {
        return tool.getType().getName();
    }

    public String getToolBrand() {
        return tool.getBrand();
    }

    public String getCheckoutDate() {
        return checkoutDate;
    }

    public BigDecimal getDailyCharge() {
        return tool.getType().getDailyCharge();
    }

    public Integer getRentalDays() {
        return rentalDays;
    }

    public Integer getPercentDiscount() {
        return percentDiscount;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public Integer getChargeDays() {
        return chargeDays;
    }

    public BigDecimal getPreDiscountCharge() {
        return preDiscountCharge;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public BigDecimal getFinalCharge() {
        return finalCharge;
    }

    @Override
    public String toString() {
        return new StringBuilder("========================================\n")
                .append("Tool code: ").append(toolCode).append("\n")
                .append("Tool type: ").append(getToolTypeName()).append("\n")
                .append("Tool brand: ").append(getToolBrand()).append("\n")
                .append("Rental days: ").append(rentalDays).append("\n")
                .append("Check out date: ").append(checkoutDate).append("\n")
                .append("Due date: ").append(returnDate).append("\n")
                .append("Daily rental charge: $").append(getDailyCharge()).append("\n")
                .append("Charge days: ").append(chargeDays).append("\n")
                .append("Pre-discount charge: $").append(preDiscountCharge).append("\n")
                .append("Discount percent: ").append(percentDiscount).append("%\n")
                .append("Discount amount: $").append(discountAmount).append("\n")
                .append("Final charge: $").append(finalCharge).append("\n")
                .toString();
    }
}
