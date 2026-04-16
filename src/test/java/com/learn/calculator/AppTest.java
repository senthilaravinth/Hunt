package com.learn.calculator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {

   App myCalc = new App();

    @Test
    public void testAddition() {
        // Syntax: assertEquals(ExpectedValue, ActualValue)
        assertEquals(7, myCalc.add(2, 5));
    }

    @Test
    public void testSubtraction() {
        assertEquals(2, myCalc.subtract(6, 4));
    }
    
    @Test
    public void testNegativeSubtraction() {
        assertEquals(-2, myCalc.subtract(4, 6));
    }
}
