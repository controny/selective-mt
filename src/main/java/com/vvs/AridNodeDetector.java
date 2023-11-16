package com.vvs;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.stmt.Statement;


public class AridNodeDetector {
    private CompilationUnit cu;

    public AridNodeDetector(CompilationUnit ast) {
        cu = ast;
    }

    public boolean isArid(Statement node) {
        // TODO: implement the detection logic
        return false;
    }
}