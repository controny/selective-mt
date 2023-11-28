package com.vvs;

import com.github.javaparser.ast.CompilationUnit;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.nio.file.*;


public class MutationTestingTool {

    public MutationTestingTool() {

    }

    public void run(String javaFilePath, int maxMutations) {
        CodeParser parser = new CodeParser(javaFilePath);
        CompilationUnit ast = parser.getAST();
        System.out.println("Original:\n" + ast);
        AridNodeDetector aridNodeDetector = new AridNodeDetector(ast);
        MutationGenerator generator = new MutationGenerator(ast, aridNodeDetector, maxMutations);
        Set<CompilationUnit> mutants = new HashSet<>();
        Set<CompilationUnit> selectiveMutants = new HashSet<>();
        int index = 1, cnt = 1;
        File testFile = new File(javaFilePath.replace("Sample", "SampleTest"));
        // get the test file content
        try {
            String testCode = new String(Files.readAllBytes(testFile.toPath()));
            // get the output directory
            String outDir = testFile.getParent();
            for (CompilationUnit mutatedAst : generator) {
                System.out.println("================");
                System.out.println("Mutant " + index + ":");
                if (mutatedAst.toString().equals(ast.toString())) {
                    System.out.println("No mutation");
                } else {
                    System.out.println(mutatedAst);
                    writeMutants(mutatedAst, testCode, outDir, cnt);
                    mutants.add(mutatedAst);
                    cnt++;
                }
                index++;
            }
        } catch (IOException e){
            e.printStackTrace();
        }

    }
    private void writeMutants(CompilationUnit cu, String testCode, String outputDir, int idx) {
        String newClassName = "Mutant" + idx;
        File file = new File(outputDir, newClassName + ".java");
        File testFile = new File(outputDir, newClassName + "Test.java");
        try {
            file.createNewFile();
            FileWriter fw = new FileWriter(file);
            // replace class names of "Sample" with "Mutant1", "Mutant2", ...
            String newCode = cu.toString().replaceAll("Sample", newClassName);
            fw.write(newCode);
            fw.flush();
            fw.close();
            
            // generate the corresponding test file
            testFile.createNewFile();
            fw = new FileWriter(testFile);
            // replace class names of "Sample" with "Mutant1", "Mutant2", ...
            String newTestCode = testCode.replaceAll("Sample", newClassName);
            fw.write(newTestCode);
            fw.flush();
            fw.close();
        } catch (IOException e){
            e.printStackTrace();
        }

    }
}