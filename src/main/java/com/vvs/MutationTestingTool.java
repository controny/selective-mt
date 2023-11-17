package com.vvs;

import com.github.javaparser.ast.CompilationUnit;

public class MutationTestingTool {

    public MutationTestingTool() {

    }

    public void run(String javaFilePath, int maxMutations) {
        CodeParser parser = new CodeParser(javaFilePath);
        CompilationUnit ast = parser.getAST();
        System.out.println("Original:\n" + ast);
        AridNodeDetector aridNodeDetector = new AridNodeDetector(ast);
        MutationGenerator generator = new MutationGenerator(ast, aridNodeDetector, maxMutations);
        int index = 1;
        for (CompilationUnit mutatedAst : generator) {
            System.out.println("================");
            System.out.println("Mutant " + index + ":");
            if (mutatedAst.toString().equals(ast.toString())) {
                System.out.println("No mutation");
            } else {
                System.out.println(mutatedAst);
            }
            index++;
        }
        // TODO: run tests
        // TODO: generate report
    }
}