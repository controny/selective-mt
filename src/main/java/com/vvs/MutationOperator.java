package com.vvs;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;


public class MutationOperator extends ModifierVisitor<Void> {
    private AridNodeDetector aridDetector;
    private int currentLine;

    public MutationOperator(AridNodeDetector detector) {
        super();
        aridDetector = detector;
        currentLine = 1; // start from the first line
    }

    @Override
    public Visitable visit(BinaryExpr n, Void arg) {
        if (!shouldSkip(n)) {
            if (n.getOperator() == BinaryExpr.Operator.EQUALS) {
                n.setOperator(BinaryExpr.Operator.NOT_EQUALS);
            }
        }
        currentLine++;
        return super.visit(n, arg);
    }

    public boolean shouldSkip(Node n) {
        if (n.getBegin().isPresent() && n.getBegin().get().line != currentLine) {
            // This node is not on the current line, skip it
            return true;
        }

        if (aridDetector.isArid(n)) {
            // skip this node if it's arid
            // return the statement without modifying it
            return true;
        }

        return false;
    }
}