package com.vvs;

import com.github.javaparser.ast.CompilationUnit;

import com.vvs.MutationOperator;


public class MutationGenerator {
    private CompilationUnit cu;

    public MutationGenerator(CompilationUnit ast) {
        cu = ast;
    }

    public CompilationUnit generate() {
        MutationOperator op = new MutationOperator();
        op.visit(cu, null);
        return cu;
    }
}