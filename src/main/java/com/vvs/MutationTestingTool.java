package com.vvs;

import com.github.javaparser.ast.CompilationUnit;
import com.vvs.CodeParser;
import com.vvs.MutationGenerator;

public class MutationTestingTool {

    public MutationTestingTool() {

    }

    public void run(String javaFilePath) {
        CodeParser parser = new CodeParser(javaFilePath);
        CompilationUnit ast = parser.getAST();
        // pass a deep copy of the AST to the mutation generator
        MutationGenerator generator = new MutationGenerator(ast.clone());
        System.out.println("Original:\n" + ast);
        System.out.println("================");
        System.out.println("Mutated:\n");
        CompilationUnit mutatedAst = generator.generate();
        System.out.println(mutatedAst);
        // aridCodeDetector.detect(cu);
        // mutationGenerator.generate(cu);
        // testRunner.run(cu);
        // analysisEngine.analyze(cu);
    }
}