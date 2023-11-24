package com.vvs;

import java.util.Iterator;

import com.github.javaparser.ast.CompilationUnit;


public class MutationGenerator implements Iterable<CompilationUnit> {
    private CompilationUnit originalAST;
    private AridNodeDetector aridDetector;
    private MutationOperator mutationOperator;
    private int maxMutations;

    public MutationGenerator(CompilationUnit ast, AridNodeDetector detector, int limit) {
        originalAST = ast;
        aridDetector = detector;
        mutationOperator = new MutationOperator(aridDetector);
        maxMutations = limit;
        // the max number of mutations should no exceed the total number of lines
        if (originalAST.getRange().isPresent()) {
            int totalLines = originalAST.getRange().get().end.line;
            System.err.println("Total lines of code: " + totalLines);
            if (totalLines < maxMutations) {
                maxMutations = totalLines;
            }
        }
    }

    @Override
    public Iterator<CompilationUnit> iterator() {
        return new Iterator<CompilationUnit>() {
            private int count = 0;

            @Override
            public boolean hasNext() {
                return count < maxMutations;
            }

            @Override
            public CompilationUnit next() {
                CompilationUnit cu = originalAST.clone();
                mutationOperator.setLineToVisit(count + 1);
                mutationOperator.visit(cu, null);
                count++;
                return cu;
            }
        };
    }
}