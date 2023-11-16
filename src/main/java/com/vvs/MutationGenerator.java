package com.vvs;

import com.github.javaparser.ast.CompilationUnit;


public class MutationGenerator {
    private CompilationUnit cu;
    private AridNodeDetector aridDetector;

    public MutationGenerator(CompilationUnit ast, AridNodeDetector detector) {
        cu = ast;
        aridDetector = detector;
    }

    public CompilationUnit generate() {
        MutationOperator op = new MutationOperator(aridDetector);
        op.visit(cu, null);
        return cu;
    }
}