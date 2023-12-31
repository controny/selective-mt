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

    public void run(String javaFilePath, int maxMutations, boolean isSelective) {
        CodeParser parser = new CodeParser(javaFilePath);
        CompilationUnit ast = parser.getAST();
        System.out.println("Original:\n" + ast);
        AridNodeDetector aridNodeDetector = null;
        if (isSelective) {
            System.out.println("[Selective Mutation Testing]");
            aridNodeDetector = new AridNodeDetector(ast);
        } else {
            System.out.println("[Normal Mutation Testing]");
            aridNodeDetector = new DummyAridNodeDetector(ast);
        }
        MutationGenerator generator = new MutationGenerator(ast, aridNodeDetector, maxMutations);
        int index = 1;
        File testFile = new File(javaFilePath.replace("Sample", "SampleTest"));
        // get the test file content
        try {
            String testCode = new String(Files.readAllBytes(testFile.toPath()));
            // get the output directory
            String outDir = testFile.getParent();
            for (CompilationUnit mutatedAst : generator) {
                if (!mutatedAst.toString().equals(ast.toString())) {
                    System.out.println("Mutant " + index);
                    System.out.println("================\n");
                    // System.out.println(mutatedAst);
                    writeMutants(mutatedAst, testCode, outDir, index);
                    index++;
                }
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