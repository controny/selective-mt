package com.vvs;

import com.github.javaparser.ast.CompilationUnit;
import com.vvs.sample.*;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class ReportGenerator {
    public static void main(String[] args) {
        int total = 0, detected = 0;

        StringBuffer sb = new StringBuffer();
        JUnitCore jUnitCore = new JUnitCore();
        Result result = jUnitCore.run(SampleTest.class);
        for (Failure failure : result.getFailures()) {
            sb.append(failure.getTestHeader() + " ");
        }
        String ori = sb.toString();
        System.out.println("Original: " + ori);

        // Run tests on each mutant
        // iterate over all classes with a string pattern of "Mutant*Test"
        while (true) {
            sb = new StringBuffer();
            try {
                String className = "com.vvs.sample.Mutant" + (total + 1) + "Test";
                Class testClass = Class.forName(className);
                System.out.println("\n===================\n");
                System.out.println("Testing " + className + "\n");
                Result mutantsResult = jUnitCore.run(testClass);
                for (Failure failure : mutantsResult.getFailures()) {
                    sb.append(failure.getTestHeader() + " ");
                }
                System.out.println();
                if (!ori.equals(sb.toString())){
                    detected++;
                    System.out.println("Killed!");
                } else {
                    System.out.println("Survived!");
                }
                total++;
            } catch (ClassNotFoundException e) {
                break;
            }
        }


        System.out.println("\n\n*******************************");
        System.out.println("Total #Mutants: " + total);
        System.out.println("Total #Survival: " + (total - detected));
        System.out.println("Total #Killed: " + detected);
        System.out.println("Mutants score: " + String.format("%.2f", 1.0 * detected / total));
        System.out.println("*******************************\n\n");
    }

}
