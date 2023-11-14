package com.vvs;

public class App 
{
    public static void main( String[] args )
    {
        MutationTestingTool tool = new MutationTestingTool();
        tool.run("src/main/resources/Sample.java");
    }
}
