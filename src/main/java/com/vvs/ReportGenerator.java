package com.vvs;

import com.github.javaparser.ast.CompilationUnit;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.io.PrintStream;

public class ReportGenerator {
    public static void main(String[] args) {
        int total = 1, detected = 0, sel_total = 1, sel_detected = 0;

        StringBuffer sb = new StringBuffer();
        JUnitCore jUnitCore = new JUnitCore();
        Result result = jUnitCore.run(SampleTest.class);
        for (Failure failure : result.getFailures()) {
            sb.append(failure.getTestHeader() + " ");
        }
        String ori = sb.toString();


        // Run tests on each mutant
        // TO DO: How to automated run Mutant_1, Mutants_2, Mutants_3, ...
        sb = new StringBuffer();
        Result mutantsResult = jUnitCore.run(MutantTest.class);
        for (Failure failure : mutantsResult.getFailures()) {
            sb.append(failure.getTestHeader() + " ");
        }
        if (!ori.equals(sb.toString())){
            detected++;
        }


        System.out.println(("Mutation testing"));
        System.out.println("Total Mutants:" + total);
        System.out.println("Total Undetected:" + (total - detected));
        System.out.println("Total Detected:" + detected);
        System.out.println("Mutants score:" + (detected / total) * 100);

        System.out.println(("Selective Mutation testing"));
        System.out.println("Total Mutants:" + sel_total);
        System.out.println("Total Undetected:" + (sel_total - sel_detected));
        System.out.println("Total Detected:" + sel_detected);
        System.out.println("Mutants score:" + (sel_detected / sel_total) * 100);
    }


    private static boolean testEqual(CompilationUnit ori, CompilationUnit aft){
        StringBuffer sb1 = new StringBuffer();
        StringBuffer sb2 = new StringBuffer();
        JUnitCore jUnitCore = new JUnitCore();

        Result result = jUnitCore.run(SampleTest.class);
        for (Failure failure : result.getFailures()) {
            sb1.append(failure.getTestHeader() + " ");
        }


        Result mutantsResult = jUnitCore.run(MutantTest.class);
        for (Failure failure : mutantsResult.getFailures()) {
            sb2.append(failure.getTestHeader() + " ");
        }

        return sb1.toString().equals(sb2.toString());
    }


}
