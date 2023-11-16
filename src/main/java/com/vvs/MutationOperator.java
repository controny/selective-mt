package com.vvs;

import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;


public class MutationOperator extends ModifierVisitor<Void> {
    private AridNodeDetector aridDetector;

    public MutationOperator(AridNodeDetector detector) {
        super();
        aridDetector = detector;
    }

    @Override
    public Visitable visit(IfStmt n, Void arg) {
        if (aridDetector.isArid(n)) {
            // skip this node if it's arid
            // return the statement without modifying it
            return n;
        }
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