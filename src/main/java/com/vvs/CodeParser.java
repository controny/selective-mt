package com.vvs;

import java.nio.file.Files;
import java.nio.file.Paths;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.StaticJavaParser;


public class CodeParser {
    private CompilationUnit cu;

    public CodeParser(String filePath) {
        try {
            cu = StaticJavaParser.parse(Files.newInputStream(Paths.get(filePath)));
        } catch (Exception e) {
            System.out.println("Parsing Error: " + e);
        }
    }

    public CompilationUnit getAST() {
        // `cu` is essentially the root node of the AST
        return cu;
    }
}