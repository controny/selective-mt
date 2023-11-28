package com.vvs;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.stmt.Statement;


public class AridNodeDetector {
    private CompilationUnit cu;

    public AridNodeDetector(CompilationUnit ast) {
        cu = ast;
    }

    public boolean isArid(Node node) {
        String str = null;
        if (node instanceof Statement) {
            str = node.toString();
        } else {
            str = node.getParentNode().toString();
        }
        // Block Statment => arid
        // if(str.contains("{") || str.contains("}")){
        //     return true;
        // }

        // 2: 只有log/io/print => arid
        if(str.contains("print") || str.contains("log") || str.contains("Stream")){
            return true;
        }

        if(str.contains("assert")){
            return true;
        }

        return false;
    }
}