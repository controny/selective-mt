package com.vvs;

import com.github.javaparser.ast.CompilationUnit;
import com.vvs.sample.*;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Set;
import java.util.stream.Collectors;

public class ReportGenerator {
    public static void main(String[] args) {
        int total = 1, detected = 0;

        StringBuffer sb = new StringBuffer();
        JUnitCore jUnitCore = new JUnitCore();
        Result result = jUnitCore.run(SampleTest.class);
        for (Failure failure : result.getFailures()) {
            sb.append(failure.getTestHeader() + " ");
        }
        String ori = sb.toString();


        // Run tests on each mutant
        // iterate over all classes with a string pattern of "Mutant*Test"
        while (true) {
            sb = new StringBuffer();
            try {
                String className = "com.vvs.sample.Mutant" + total + "Test";
                Result mutantsResult = jUnitCore.run(Class.forName(className));
                System.out.println("Testing " + className);
                for (Failure failure : mutantsResult.getFailures()) {
                    sb.append(failure.getTestHeader() + " ");
                }
                if (!ori.equals(sb.toString())){
                    detected++;
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
