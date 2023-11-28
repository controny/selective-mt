package com.vvs.sample;

import org.junit.Test;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class SampleTest {

    @Test
    public void testAddition() {
        Sample calculator = new Sample();
        int sum = calculator.add(3, 5);
        assertEquals(8, sum);
    }

    @Test
    public void testSubtraction() {
        Sample calculator = new Sample();
        int difference = calculator.subtract(10, 4);
        assertEquals(6, difference);
    }

    @Test
    public void testMultiplication() {
        Sample calculator = new Sample();
        int product = calculator.multiply(2, 7);
        assertEquals(14, product);
    }

    @Test
    public void testDivision() {
        Sample calculator = new Sample();
        double quotient = calculator.divide(9, 3);
        assertEquals(3.0, quotient, 0.001);
    }

    @Test
    public void testDivisionByZero() {
        Sample calculator = new Sample();
        try {
            calculator.divide(8, 0);
            fail("Expected ArithmeticException");
        } catch (Exception e) {
            System.out.println("e message: " + e.getMessage());
            assertTrue(e.getMessage().contains("Division by zero"));
        }
    }

    @Test
    public void testArrayProcessing() {
        Sample calculator = new Sample();
        int[] numbers = {1, 2, 3, 4, 5};
        calculator.processNumbers(numbers);
        // Additional assertions for array processing can be added here
    }
}
