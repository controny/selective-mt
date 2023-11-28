package com.vvs;

public class App 
{
    public static void main( String[] args )
    {
        MutationTestingTool tool = new MutationTestingTool();
        tool.run("src/main/java/com/vvs/sample/Sample.java", 100);
    }
}
