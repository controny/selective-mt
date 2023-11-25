package com.vvs;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;


public class MutantTest {
    Mutant_1 sample;

    @Before
    public void setUp(){
        sample = new Mutant_1();
    }

    @Test
    public void testZero(){
        assertTrue(sample.isEven(0));
    }

    @Test
    public void testOne(){
        assertTrue(sample.isEven(1));
    }

    @Test
    public void testTwo(){
        assertTrue(sample.isEven(2));
    }

    @Test
    public void testNeg(){
        assertTrue(sample.isEven(-1));
    }
}