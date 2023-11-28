package com.vvs.sample;

import com.github.javaparser.utils.CodeGenerationUtils;
import com.github.javaparser.utils.SourceRoot;

import java.util.logging.Logger;

public class Sample {

    private static final Logger LOGGER = Logger.getLogger(Sample.class.getName());

    public int add(int a, int b) {
        LOGGER.info("Adding " + a + " and " + b);
        return a + b;
    }

    public int subtract(int a, int b) {
        LOGGER.info("Subtracting " + b + " from " + a);
        return a - b;
    }

    public int multiply(int a, int b) {
        LOGGER.info("Multiplying " + a + " by " + b);
        return a * b;
    }

    public double divide(int a, int b) {
        LOGGER.info("Dividing " + a + " by " + b);
        assert (b != 0) : "Division by zero!";
        return (double) a / b;
    }

    public void processNumbers(int[] numbers) {
        LOGGER.info("Processing an array of numbers");
        assert (numbers != null && numbers.length > 0) : "Input array is null or empty";

        for (int num : numbers) {
            if (num % 2 == 0) {
                LOGGER.info(num + " is even");
            } else {
                LOGGER.info(num + " is odd");
            }
        }
    }

    public static void main(String[] args) {
        Sample calculator = new Sample();

        // Test addition
        int sum = calculator.add(5, 7);
        System.out.println("Sum: " + sum);

        // Test subtraction
        int difference = calculator.subtract(10, 3);
        System.out.println("Difference: " + difference);

        // Test multiplication
        int product = calculator.multiply(4, 6);
        System.out.println("Product: " + product);

        // Test division
        double quotient = calculator.divide(8, 2);
        System.out.println("Quotient: " + quotient);

        // Test array processing
        int[] numbers = {1, 2, 3, 4, 5};
        calculator.processNumbers(numbers);
    }
}
