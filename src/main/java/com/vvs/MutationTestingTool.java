package com.vvs;

import com.github.javaparser.ast.CompilationUnit;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;;import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class MutationTestingTool {

    public MutationTestingTool() {

    }

    public void run(String javaFilePath, int maxMutations) {
        CodeParser parser = new CodeParser(javaFilePath);
        CompilationUnit ast = parser.getAST();
        System.out.println("Original:\n" + ast);
        AridNodeDetector aridNodeDetector = new AridNodeDetector(ast);
        MutationGenerator generator = new MutationGenerator(ast, aridNodeDetector, maxMutations);
        Set<CompilationUnit> mutants = new HashSet<>();
        Set<CompilationUnit> selectiveMutants = new HashSet<>();
        int index = 1, cnt = 1;
        for (CompilationUnit mutatedAst : generator) {
            System.out.println("================");
            System.out.println("Mutant " + index + ":");
            if (mutatedAst.toString().equals(ast.toString())) {
                System.out.println("No mutation");
            } else {
                mutatedAst.setPackageDeclaration("com.vvs");
                System.out.println(mutatedAst);
                mutants.add(mutatedAst);
                writeMutants(mutatedAst,cnt);
                cnt++;
            }
            index++;
        }

    }
    private void writeMutants(CompilationUnit cu, int idx) {
        String curPath = System.getProperty("user.dir");
        System.out.println(curPath);
        File file = new File(curPath + "/src/main/java/com/vvs", "Mutant_"+ idx + ".java");
        try {
            file.createNewFile();
            FileWriter fw = new FileWriter(file);
            fw.write(cu.toString());
            fw.flush();
            fw.close();
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}