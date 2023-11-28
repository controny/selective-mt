package com.vvs;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;


public class DummyAridNodeDetector extends AridNodeDetector {

    public DummyAridNodeDetector(CompilationUnit ast) {
        super(ast);
    }
    
    @Override
    public boolean isArid(Node node) {
        return false;
    }
}