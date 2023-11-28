package com.vvs.sample;

import com.github.javaparser.utils.CodeGenerationUtils;
import com.github.javaparser.utils.SourceRoot;

import java.util.logging.Logger;

public class Sample {

    public int add(int a, int b) {
        System.out.println("Adding " + a + " and " + b);
        return a + b;
    }

    public int subtract(int a, int b) {
        System.out.println("Subtracting " + b + " from " + a);
        return a - b;
    }

    public int multiply(int a, int b) {
        System.out.println("Multiplying " + a + " by " + b);
        return a * b;
    }

    public double divide(int a, int b) {
        System.out.println("Dividing " + a + " by " + b);
        if (b == 0) {
            throw new ArithmeticException("Division by zero!");
        }
        return (double) a / b;
    }

    public void processNumbers(int[] numbers) {
        System.out.println("Processing an array of numbers");
        assert (numbers != null && numbers.length > 0) : "Input array is null or empty";

        for (int num : numbers) {
            if (num % 2 == 0) {
                System.out.println(num + " is even");
            } else {
                System.out.println(num + " is odd");
            }
        }
    }
}
