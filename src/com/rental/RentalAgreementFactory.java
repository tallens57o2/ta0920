package com.rental;

import com.rental.objects.RentalAgreement;
import com.rental.objects.Tool;
import com.rental.util.Constants;

import java.util.Objects;

/**
 * Factory class responsible for taking the input from the Point-of-Sales
 * System, sanitizing it, and creating RentalAgreements with desired values
 */
public class RentalAgreementFactory {
    private final ToolInventory toolInventory;

    /**
     * Default Constructor
     */
    public RentalAgreementFactory() {
        toolInventory = ToolInventory.defaultInventory();
    }

    /**
     * Driving function that processes input and constructs associated
     * RentalAgreements if the input is valid
     *
     * @param toolCode code for desired tool to rent
     * @param checkoutDate date to start rental as a String in mm/dd/yy format
     * @param rentalDays number of days desired to rent tool
     * @param percentDiscount percentage discount to be applied to the rental
     * @return RentalAgreement with all of its fields populated
     */
    public RentalAgreement createRentalAgreement(String toolCode, String checkoutDate,
                                                 String rentalDays, String percentDiscount) {
        return new RentalAgreement(toolCode,
                                   getToolFromCode(toolCode),
                                   checkoutDate, //sanitize this input in the class instead
                                   parseRentalDays(rentalDays),
                                   parsePercentDiscount(percentDiscount));
    }

    /**
     * Helper function that ensure the desired tool to rent exists
     *
     * @param input string input for the tool code
     * @return tool object associated with given tool code
     */
    private Tool getToolFromCode(String input) {
        // Ensures the tool exists in the system, otherwise set to null
        Tool tool = toolInventory.getTool(input);
        if (Objects.isNull(tool)) {
            System.out.println(Constants.BAD_TOOL_CODE + input);
            throw new RuntimeException(Constants.BAD_TOOL_CODE + input);
        }
        return tool;
    }

    /**
     * Helper function to ensure a valid rental day count is given
     *
     * @param input string input for number of rental days
     * @return number of rental days
     */
    private int parseRentalDays(String input) {
        try {
            int rentalDays = Integer.parseInt(input);
            if (rentalDays > 1) {
                return rentalDays;
            }
            else {
                throw new RuntimeException();
            }
        }
        catch (RuntimeException e) {
            System.out.println(Constants.BAD_RENTAL_DAYS);
            throw new RuntimeException(Constants.BAD_RENTAL_DAYS);
        }
    }

    /**
     * Ensures input contains %, and is in the valid range 0-100 (inclusive)
     *
     * @param input string input for percent discount
     * @return discount percent stored as a whole number
     */
    private int parsePercentDiscount(String input) {
        try {
            if (input.endsWith("%")) {
                // Removes the % sign
                int percentDiscount =
                        Integer.parseInt(input.substring(0, input.length() - 1));
                // Ensures value falls within valid discount range
                if (percentDiscount >= 0 && percentDiscount <= 100) {
                    return percentDiscount;
                } else {
                    throw new RuntimeException();
                }
            } else {
                throw new RuntimeException();
            }
        } catch (RuntimeException e) {
            System.out.println(Constants.BAD_PERCENT_DISCOUNT);
            throw new RuntimeException(Constants.BAD_PERCENT_DISCOUNT);
        }
    }
}
