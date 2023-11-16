package com.vvs;

import com.github.javaparser.ast.CompilationUnit;

public class MutationTestingTool {

    public MutationTestingTool() {

    }

    public void run(String javaFilePath) {
        CodeParser parser = new CodeParser(javaFilePath);
        CompilationUnit ast = parser.getAST();
        System.out.println("Original:\n" + ast);
        System.out.println("================");
        AridNodeDetector aridNodeDetector = new AridNodeDetector(ast);
        MutationGenerator generator = new MutationGenerator(ast, aridNodeDetector);
        CompilationUnit mutatedAst = generator.generate();
        System.out.println("Mutated:\n");
        System.out.println(mutatedAst);
        // TODO: run tests
        // TODO: generate report
    }
}