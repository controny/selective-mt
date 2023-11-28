package com.vvs;

public class App 
{
    public static void main( String[] args )
    {
        boolean isSelective = true;
        if (args.length > 0 && args[0].equals("0")) {
            isSelective = false;
        }
        MutationTestingTool tool = new MutationTestingTool();
        tool.run("src/main/java/com/vvs/sample/Sample.java", 100, isSelective);
    }
}
