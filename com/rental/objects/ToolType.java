package com.rental.objects;

import com.rental.util.Constants;

import java.math.BigDecimal;

/**
 * Enum class to hold properties associated with the defined types of tools
 */
public enum ToolType {
    LADDER(Constants.LADDER, true, true, false, BigDecimal.valueOf(1.99)),
    CHAINSAW(Constants.CHAINSAW, true, false, true, BigDecimal.valueOf(1.49)),
    JACKHAMMER(Constants.JACKHAMMER, true, false, false, BigDecimal.valueOf(2.99));

    private final String name; // String of the name of the tool type
    private final boolean chargeWeekday; // Whether or not the tool has a weekday charge
    private final boolean chargeWeekend; // Whether or not the tool has a weekend charge
    private final boolean chargeHoliday; // Whether or not the tool has a holiday charge
    private final BigDecimal dailyCharge; // The daily charge amount of the tool

    ToolType(String name, boolean chargeWeekday, boolean chargeWeekend,
                     boolean chargeHoliday, BigDecimal dailyCharge) {
        this.name = name;
        this.chargeWeekday = chargeWeekday;
        this.chargeWeekend = chargeWeekend;
        this.chargeHoliday = chargeHoliday;
        this.dailyCharge = dailyCharge;
    }

    public String getName() {
        return name;
    }

    public boolean isChargeWeekday() {
        return chargeWeekday;
    }

    public boolean isChargeWeekend() {
        return chargeWeekend;
    }

    public boolean isChargeHoliday() {
        return chargeHoliday;
    }

    public BigDecimal getDailyCharge() {
        return dailyCharge;
    }
}
