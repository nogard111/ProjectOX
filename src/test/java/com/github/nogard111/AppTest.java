package com.github.nogard111;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }
    @Test
    public void shouldAnswerWithTrue2()
    {
        assertTrue( false );
    }
    @Test
    public void shouldAnswerWithTrue3()
    {
        assertTrue( 2/0 == 0 );
    }
}
