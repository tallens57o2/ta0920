package com.rental;

import com.rental.objects.RentalAgreement;
import com.rental.objects.Tool;
import com.rental.objects.ToolType;
import com.rental.util.Constants;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class Tests {
    private static RentalAgreementFactory rentalAgreementFactory;

    @BeforeClass
    public static void setup() {
        rentalAgreementFactory = new RentalAgreementFactory();
    }

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    // Given tests ================================================================================
    @Test
    public void test1() {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage(Constants.BAD_PERCENT_DISCOUNT);
        RentalAgreement agreement = rentalAgreementFactory.createRentalAgreement(
                Constants.JAKR, "9/3/15", "5", "101%");
    }

    @Test
    public void test2() {
        RentalAgreement agreement = rentalAgreementFactory.createRentalAgreement(
                Constants.LADW, "7/2/20", "3", "10%");
        assertEquals(Constants.LADW, agreement.getToolCode());
        assertEquals(Constants.LADDER, agreement.getToolTypeName());
        assertEquals(Constants.WERNER, agreement.getToolBrand());
        assertEquals(Integer.valueOf(3), agreement.getRentalDays());
        assertEquals("7/2/20", agreement.getCheckoutDate());
        assertEquals("7/5/20", agreement.getReturnDate());
        assertEquals(BigDecimal.valueOf(1.99), agreement.getDailyCharge());
        assertEquals(Integer.valueOf(2), agreement.getChargeDays());
        assertEquals(BigDecimal.valueOf(3.98), agreement.getPreDiscountCharge());
        assertEquals(Integer.valueOf(10), agreement.getPercentDiscount());
        assertEquals(BigDecimal.valueOf(.4d).setScale(2), agreement.getDiscountAmount());
        assertEquals(BigDecimal.valueOf(3.58), agreement.getFinalCharge());
        System.out.println(agreement.toString());
    }

    @Test
    public void test3() {
        RentalAgreement agreement = rentalAgreementFactory.createRentalAgreement(
                Constants.CHNS, "7/2/15", "5", "25%");
        assertEquals(Constants.CHNS, agreement.getToolCode());
        assertEquals(Constants.CHAINSAW, agreement.getToolTypeName());
        assertEquals(Constants.SITHL, agreement.getToolBrand());
        assertEquals(Integer.valueOf(5), agreement.getRentalDays());
        assertEquals("7/2/15", agreement.getCheckoutDate());
        assertEquals("7/7/15", agreement.getReturnDate());
        assertEquals(BigDecimal.valueOf(1.49), agreement.getDailyCharge());
        assertEquals(Integer.valueOf(3), agreement.getChargeDays());
        assertEquals(BigDecimal.valueOf(4.47), agreement.getPreDiscountCharge());
        assertEquals(Integer.valueOf(25), agreement.getPercentDiscount());
        assertEquals(BigDecimal.valueOf(1.12), agreement.getDiscountAmount());
        assertEquals(BigDecimal.valueOf(3.35), agreement.getFinalCharge());
        System.out.println(agreement.toString());
    }

    @Test
    public void test4() {
        RentalAgreement agreement = rentalAgreementFactory.createRentalAgreement(
                Constants.JAKD, "9/3/15", "6", "0%");
        assertEquals(Constants.JAKD, agreement.getToolCode());
        assertEquals(Constants.JACKHAMMER, agreement.getToolTypeName());
        assertEquals(Constants.DEWALT, agreement.getToolBrand());
        assertEquals(Integer.valueOf(6), agreement.getRentalDays());
        assertEquals("9/3/15", agreement.getCheckoutDate());
        assertEquals("9/9/15", agreement.getReturnDate());
        assertEquals(BigDecimal.valueOf(2.99), agreement.getDailyCharge());
        assertEquals(Integer.valueOf(3), agreement.getChargeDays());
        assertEquals(BigDecimal.valueOf(8.97), agreement.getPreDiscountCharge());
        assertEquals(Integer.valueOf(0), agreement.getPercentDiscount());
        assertEquals(BigDecimal.valueOf(0).setScale(2), agreement.getDiscountAmount());
        assertEquals(BigDecimal.valueOf(8.97), agreement.getFinalCharge());
        System.out.println(agreement.toString());
    }

    @Test
    public void test5() {
        RentalAgreement agreement = rentalAgreementFactory.createRentalAgreement(
                Constants.JAKR, "7/2/15", "9", "0%");
        assertEquals(Constants.JAKR, agreement.getToolCode());
        assertEquals(Constants.JACKHAMMER, agreement.getToolTypeName());
        assertEquals(Constants.RIDGID, agreement.getToolBrand());
        assertEquals(Integer.valueOf(9), agreement.getRentalDays());
        assertEquals("7/2/15", agreement.getCheckoutDate());
        assertEquals("7/11/15", agreement.getReturnDate());
        assertEquals(BigDecimal.valueOf(2.99), agreement.getDailyCharge());
        assertEquals(Integer.valueOf(5), agreement.getChargeDays());
        assertEquals(BigDecimal.valueOf(14.95), agreement.getPreDiscountCharge());
        assertEquals(Integer.valueOf(0), agreement.getPercentDiscount());
        assertEquals(BigDecimal.valueOf(0).setScale(2), agreement.getDiscountAmount());
        assertEquals(BigDecimal.valueOf(14.95), agreement.getFinalCharge());
        System.out.println(agreement.toString());
    }

    @Test
    public void test6() {
        RentalAgreement agreement = rentalAgreementFactory.createRentalAgreement(
                Constants.JAKR, "7/2/20", "4", "50%");
        assertEquals(Constants.JAKR, agreement.getToolCode());
        assertEquals(Constants.JACKHAMMER, agreement.getToolTypeName());
        assertEquals(Constants.RIDGID, agreement.getToolBrand());
        assertEquals(Integer.valueOf(4), agreement.getRentalDays());
        assertEquals("7/2/20", agreement.getCheckoutDate());
        assertEquals("7/6/20", agreement.getReturnDate());
        assertEquals(BigDecimal.valueOf(2.99), agreement.getDailyCharge());
        assertEquals(Integer.valueOf(1), agreement.getChargeDays());
        assertEquals(BigDecimal.valueOf(2.99), agreement.getPreDiscountCharge());
        assertEquals(Integer.valueOf(50), agreement.getPercentDiscount());
        assertEquals(BigDecimal.valueOf(1.5).setScale(2), agreement.getDiscountAmount());
        assertEquals(BigDecimal.valueOf(1.49), agreement.getFinalCharge());
        System.out.println(agreement.toString());
    }

    // Extra tests ================================================================================
    @Test
    public void testWrappingYear() {
        RentalAgreement agreement = rentalAgreementFactory.createRentalAgreement(
                Constants.LADW, "12/30/20", "33", "0%");
        assertEquals(Constants.LADW, agreement.getToolCode());
        assertEquals(Constants.LADDER, agreement.getToolTypeName());
        assertEquals(Constants.WERNER, agreement.getToolBrand());
        assertEquals(Integer.valueOf(33), agreement.getRentalDays());
        assertEquals("12/30/20", agreement.getCheckoutDate());
        assertEquals("2/1/21", agreement.getReturnDate());
        assertEquals(BigDecimal.valueOf(1.99), agreement.getDailyCharge());
        assertEquals(Integer.valueOf(33), agreement.getChargeDays());
        assertEquals(BigDecimal.valueOf(65.67), agreement.getPreDiscountCharge());
        assertEquals(Integer.valueOf(0), agreement.getPercentDiscount());
        assertEquals(BigDecimal.valueOf(0).setScale(2), agreement.getDiscountAmount());
        assertEquals(BigDecimal.valueOf(65.67), agreement.getFinalCharge());
        System.out.println(agreement.toString());
    }

    @Test
    public void testWrappingMultipleYears() {
        RentalAgreement agreement = rentalAgreementFactory.createRentalAgreement(
                Constants.LADW, "12/30/20", "375", "0%");
        assertEquals(Constants.LADW, agreement.getToolCode());
        assertEquals(Constants.LADDER, agreement.getToolTypeName());
        assertEquals(Constants.WERNER, agreement.getToolBrand());
        assertEquals(Integer.valueOf(375), agreement.getRentalDays());
        assertEquals("12/30/20", agreement.getCheckoutDate());
        assertEquals("1/9/22", agreement.getReturnDate());
        assertEquals(BigDecimal.valueOf(1.99), agreement.getDailyCharge());
        assertEquals(Integer.valueOf(373), agreement.getChargeDays());
        assertEquals(BigDecimal.valueOf(742.27), agreement.getPreDiscountCharge());
        assertEquals(Integer.valueOf(0), agreement.getPercentDiscount());
        assertEquals(BigDecimal.valueOf(0).setScale(2), agreement.getDiscountAmount());
        assertEquals(BigDecimal.valueOf(742.27), agreement.getFinalCharge());
        System.out.println(agreement.toString());
    }

    @Test
    public void testLaborDay91() {
        RentalAgreement agreement = rentalAgreementFactory.createRentalAgreement(
                Constants.LADW, "8/31/14", "5", "0%");
        assertEquals(Constants.LADW, agreement.getToolCode());
        assertEquals(Constants.LADDER, agreement.getToolTypeName());
        assertEquals(Constants.WERNER, agreement.getToolBrand());
        assertEquals(Integer.valueOf(5), agreement.getRentalDays());
        assertEquals("8/31/14", agreement.getCheckoutDate());
        assertEquals("9/5/14", agreement.getReturnDate());
        assertEquals(BigDecimal.valueOf(1.99), agreement.getDailyCharge());
        assertEquals(Integer.valueOf(4), agreement.getChargeDays());
        assertEquals(BigDecimal.valueOf(7.96), agreement.getPreDiscountCharge());
        assertEquals(Integer.valueOf(0), agreement.getPercentDiscount());
        assertEquals(BigDecimal.valueOf(0).setScale(2), agreement.getDiscountAmount());
        assertEquals(BigDecimal.valueOf(7.96), agreement.getFinalCharge());
        System.out.println(agreement.toString());
    }

    @Test
    public void testLaborDay92() {
        RentalAgreement agreement = rentalAgreementFactory.createRentalAgreement(
                Constants.LADW, "8/31/19", "5", "0%");
        assertEquals(Constants.LADW, agreement.getToolCode());
        assertEquals(Constants.LADDER, agreement.getToolTypeName());
        assertEquals(Constants.WERNER, agreement.getToolBrand());
        assertEquals(Integer.valueOf(5), agreement.getRentalDays());
        assertEquals("8/31/19", agreement.getCheckoutDate());
        assertEquals("9/5/19", agreement.getReturnDate());
        assertEquals(BigDecimal.valueOf(1.99), agreement.getDailyCharge());
        assertEquals(Integer.valueOf(4), agreement.getChargeDays());
        assertEquals(BigDecimal.valueOf(7.96), agreement.getPreDiscountCharge());
        assertEquals(Integer.valueOf(0), agreement.getPercentDiscount());
        assertEquals(BigDecimal.valueOf(0).setScale(2), agreement.getDiscountAmount());
        assertEquals(BigDecimal.valueOf(7.96), agreement.getFinalCharge());
        System.out.println(agreement.toString());
    }

    @Test
    public void testBadToolCode() {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage(Constants.BAD_TOOL_CODE + "TEST");
        RentalAgreement agreement = rentalAgreementFactory.createRentalAgreement(
                "TEST", "9/3/15", "0", "0%");
    }

    @Test
    public void testBadRentalDaysInput() {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage(Constants.BAD_RENTAL_DAYS);
        RentalAgreement agreement = rentalAgreementFactory.createRentalAgreement(
                Constants.JAKR, "9/3/15", "0", "0%");
    }

    @Test
    public void testMissingPercentSign() {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage(Constants.BAD_PERCENT_DISCOUNT);
        RentalAgreement agreement = rentalAgreementFactory.createRentalAgreement(
                Constants.JAKR, "9/3/15", "5", "5");
    }

    @Test
    public void testInvalidDateFormat() {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage(Constants.INVALID_DATE_FORMAT);
        RentalAgreement agreement = rentalAgreementFactory.createRentalAgreement(
                Constants.JAKR, "1/15", "5", "5%");
    }

    @Test
    public void testInvalidMonth() {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage(Constants.INVALID_MONTH);
        RentalAgreement agreement = rentalAgreementFactory.createRentalAgreement(
                Constants.JAKR, "13/1/15", "5", "5%");
    }

    @Test
    public void testInvalidDay() {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage(Constants.INVALID_DAY);
        RentalAgreement agreement = rentalAgreementFactory.createRentalAgreement(
                Constants.JAKR, "1/32/15", "5", "5%");
    }

    @Test
    public void testInvalidYear() {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage(Constants.INVALID_YEAR);
        RentalAgreement agreement = rentalAgreementFactory.createRentalAgreement(
                Constants.JAKR, "1/1/815", "5", "5%");
    }

    @Test
    public void testInvalidLeapDay() {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage(Constants.INVALID_LEAP_DAY);
        RentalAgreement agreement = rentalAgreementFactory.createRentalAgreement(
                Constants.JAKR, "2/29/15", "5", "5%");
    }

    @Test
    public void testToolInventory() {
        ToolInventory inventory = new ToolInventory();
        inventory.addTool(Constants.LADW, new Tool(ToolType.LADDER, Constants.WERNER));
        Tool tool = inventory.removeTool(Constants.LADW);
        assertEquals(ToolType.LADDER, tool.getType());
        assertEquals(Constants.WERNER, tool.getBrand());
        inventory.addTool(Constants.LADW, new Tool(ToolType.LADDER, Constants.WERNER));
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage(Constants.REPEAT_TOOL_CODE);
        inventory.addTool(Constants.LADW, new Tool(ToolType.LADDER, Constants.WERNER));
    }
}

