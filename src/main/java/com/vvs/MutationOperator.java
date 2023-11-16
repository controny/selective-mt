package com.vvs;

import java.nio.file.Files;
import java.nio.file.Paths;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.StaticJavaParser;


public class MutationOperator extends ModifierVisitor<Void> {
    @Override
    public Visitable visit(IfStmt n, Void arg) {
        // Figure out what to get and what to cast simply by looking at the AST in a debugger! 
        n.getCondition().ifBinaryExpr(binaryExpr -> {
            if (binaryExpr.getOperator() == BinaryExpr.Operator.EQUALS && n.getElseStmt().isPresent()) {
                /* It's a good idea to clone nodes that you move around.
                    JavaParser (or you) might get confused about who their parent is!
                */
                Statement thenStmt = n.getThenStmt().clone();
                Statement elseStmt = n.getElseStmt().get().clone();
                n.setThenStmt(elseStmt);
                n.setElseStmt(thenStmt);
                binaryExpr.setOperator(BinaryExpr.Operator.NOT_EQUALS);
            }
        });
        return super.visit(n, arg);
    }
}