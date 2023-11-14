package com.vvs;

import com.github.javaparser.ast.CompilationUnit;
import com.vvs.CodeParser;

public class MutationTestingTool {

    public MutationTestingTool() {

    }

    public void run(String javaFilePath) {
        CodeParser parser = new CodeParser(javaFilePath);
        CompilationUnit ast = parser.getAST();
        System.out.println(ast);
        // aridCodeDetector.detect(cu);
        // mutationGenerator.generate(cu);
        // testRunner.run(cu);
        // analysisEngine.analyze(cu);
    }
}