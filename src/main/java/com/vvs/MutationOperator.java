package com.vvs;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.BooleanLiteralExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.UnaryExpr;
import com.github.javaparser.ast.stmt.AssertStmt;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;


public class MutationOperator extends ModifierVisitor<Void> {
    private long randomSeed = 123;
    private AridNodeDetector aridDetector;
    private int currentLine;
    private BinaryExpr.Operator[] arithmeticOperators = {
        BinaryExpr.Operator.PLUS,
        BinaryExpr.Operator.MINUS,
        BinaryExpr.Operator.MULTIPLY,
        BinaryExpr.Operator.DIVIDE,
        BinaryExpr.Operator.REMAINDER
    };
    private BinaryExpr.Operator[] logicalConnectors = {
        BinaryExpr.Operator.AND,
        BinaryExpr.Operator.OR
    };
    private BinaryExpr.Operator[] relationalOperators = {
        BinaryExpr.Operator.LESS,
        BinaryExpr.Operator.LESS_EQUALS,
        BinaryExpr.Operator.GREATER,
        BinaryExpr.Operator.GREATER_EQUALS,
        BinaryExpr.Operator.EQUALS,
        BinaryExpr.Operator.NOT_EQUALS
    };
    private UnaryExpr.Operator[] unaryOperators = {
        UnaryExpr.Operator.POSTFIX_INCREMENT,
        UnaryExpr.Operator.POSTFIX_DECREMENT,
        UnaryExpr.Operator.PREFIX_INCREMENT,
        UnaryExpr.Operator.PREFIX_DECREMENT,
        UnaryExpr.Operator.LOGICAL_COMPLEMENT,
        UnaryExpr.Operator.BITWISE_COMPLEMENT,
        UnaryExpr.Operator.MINUS
    };

    public MutationOperator(AridNodeDetector detector) {
        super();
        aridDetector = detector;
    }

    @Override
    public Visitable visit(BinaryExpr n, Void arg) {
        if (!shouldSkip(n)) {
            BinaryExpr ori = n.clone();

            // iterate through all the potential mutation functions
            List<Function<BinaryExpr, Node>> mutationFunctions = Arrays.asList(
                this::replaceArithmeticOperator,
                this::replaceLogicalConnector,
                this::replaceRelationalOperator
            );

            for (Function<BinaryExpr, Node> function : mutationFunctions) {
                Node replacement = function.apply(n);
                if (replacement != null) {
                    System.out.println("Line " + currentLine + " : " + ori + " -> " + replacement);
                    if (n != replacement) {
                        n.replace(replacement);
                        return replacement;
                    }
                    break;
                }
            } 
        } else {
            return n;
        }
        return super.visit(n, arg);
    }

    @Override
    public Visitable visit(NameExpr n, Void arg) {
        if (!shouldSkip(n)) {
            // randomly choose to insert or not
            int random = new Random(randomSeed).nextInt(3);
            if (random == 0) {
                NameExpr ori = n.clone();
                Node replacement = insertUnaryOperator(n);
                System.out.println("Line " + currentLine + " : " + ori + " -> " + replacement);
                n.replace(replacement);
                return replacement;
            }
        }
        return super.visit(n, arg);
    }

    // @Override
    // public Visitable visit(BlockStmt n, Void arg) {
    //     if (!shouldSkip(n)) {
    //         // Statement removal (SR)
    //         // randomly choose to remove or not
    //         int random = new Random(randomSeed).nextInt(2);
    //         if (random == 0) {
    //             n.remove();
    //             System.out.println("Line " + currentLine + " : REMOVE " + n);
    //             return null;
    //         }
    //     }
    //     return super.visit(n, arg);
    // }

    @Override
    public Visitable visit(AssertStmt n, Void arg) {
        if (!shouldSkip(n)) {
            // Statement removal (SR)
            // randomly choose to remove or not
            int random = new Random(randomSeed).nextInt(2);
            if (random == 0) {
                n.remove();
                System.out.println("Line " + currentLine + " : REMOVE " + n);
                return null;
            }
        } else {
            return n;
        }
        return super.visit(n, arg);
    }

    public void setLineToVisit(int line) {
        currentLine = line;
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

    private int pickReplacementIndex(int opSize, int oriIndex) {
        int randomIndex = oriIndex;
        Random randomGen = new Random(randomSeed);
        do {
            randomIndex = randomGen.nextInt(opSize);
        } while (randomIndex == oriIndex);
        return randomIndex;
    }

    private Node replaceArithmeticOperator(BinaryExpr n) {
        // Arithmetic operator replacement (AOR)
        // Randomly replace an arithmetic expression with {a, b, a − b, a * b, a / b, a % b}
        BinaryExpr.Operator op = n.getOperator();
        for (int i = 0; i < arithmeticOperators.length; i++) {
            if (arithmeticOperators[i] == op) {
                int opIndex = pickReplacementIndex(arithmeticOperators.length + 2, i);
                if (opIndex < arithmeticOperators.length) {
                    // replace the operator with another one
                    BinaryExpr.Operator newOp = arithmeticOperators[opIndex];
                    n.setOperator(newOp);
                    return n;
                } else {
                    // replace the binary expression with one of the operands
                    Node replacement = opIndex == arithmeticOperators.length ? n.getLeft() : n.getRight();
                    return replacement;
                }
            }
        }
        return null;
    }

    private Node replaceLogicalConnector(BinaryExpr n) {
        // Logical connector replacement (LCR)
        // Randomly replace a logical expression with {a, b, a || b, true, false}
        BinaryExpr.Operator op = n.getOperator();
        for (int i = 0; i < logicalConnectors.length; i++) {
            if (logicalConnectors[i] == op) {
                int opIndex = pickReplacementIndex(logicalConnectors.length + 4, i);
                if (opIndex < logicalConnectors.length) {
                    // replace the operator with another one
                    BinaryExpr.Operator newOp = logicalConnectors[opIndex];
                    n.setOperator(newOp);
                    return n;
                } else if (opIndex < logicalConnectors.length + 2) {
                    // replace the binary expression with one of the operands
                    Node replacement = opIndex == logicalConnectors.length ? n.getLeft() : n.getRight();
                    return replacement;
                } else {
                    // replace the binary expression with true/false
                    boolean boolValue = (opIndex == logicalConnectors.length + 2);
                    Node replacement = new BooleanLiteralExpr(boolValue);
                    return replacement;
                }
            }
        }
        return null;
    }

    private Node replaceRelationalOperator(BinaryExpr n) {
        // Relational operator replacement (ROR)
        // Randomly replace a relational expression with {a < b, a <= b, a >= b, true, false}
        BinaryExpr.Operator op = n.getOperator();
        for (int i = 0; i < relationalOperators.length; i++) {
            if (relationalOperators[i] == op) {
                int opIndex = pickReplacementIndex(relationalOperators.length + 2, i);
                if (opIndex < relationalOperators.length) {
                    // replace the operator with another one
                    BinaryExpr.Operator newOp = relationalOperators[opIndex];
                    n.setOperator(newOp);
                    return n;
                } else {
                    // replace the binary expression with true/false
                    boolean boolValue = (opIndex == relationalOperators.length);
                    Node replacement = new BooleanLiteralExpr(boolValue);
                    return replacement;
                }
            }
        }
        return null;
    }

    private Node insertUnaryOperator(NameExpr n) {
        // Unary operator insertion (UOI)
        // Randomly insert a unary operator {++, --, !, ~, -} to a variable
        int opIndex = pickReplacementIndex(unaryOperators.length, -1);
        NameExpr nameExpr = new NameExpr(n.getName());
        Node newNode = new UnaryExpr(nameExpr, unaryOperators[opIndex]);
        return newNode;
    }
}