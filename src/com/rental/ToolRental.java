package com.rental;

public class ToolRental {

    /**
     * Simple main just ensuring that the input arguments will not cause
     * NullPointerExceptions or IndexOutOfBoundsExceptions.
     *
     * Testing primarily done through unit tests
     *
     * Command line argument handling done in RentalAgreementFactory,
     * since this isn't really intended to be used
     */
    public static void main(String[] args) {
        if (args.length != 4 ||
            args[0] == null ||
            args[1] == null ||
            args[2] == null ||
            args[3] == null) {
            throw new RuntimeException("Invalid number of arguments given");
        }
        else {
            RentalAgreementFactory factory = new RentalAgreementFactory();

            System.out.println(factory.createRentalAgreement(args[0], args[1], args[2], args[3]));
        }
    }
}
