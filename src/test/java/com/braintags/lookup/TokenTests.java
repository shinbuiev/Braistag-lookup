package com.braintags.lookup;


import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TokenTests {
    @Test
    public void adjust() throws Exception {
    }

    @Test
    public void canBeJoinedWith() throws Exception {
        Token first = new Token(0, 10, '0', '0');
        Token second = new Token(11, 15, '0', '0');

        assertFalse(first.canBeJoinedWith(first));
        assertFalse(first.canBeJoinedWith(second));
    }

    @Test
    public void joinWithIntoSingleToken() throws Exception {
        Token first = new Token(0, 10, '0', '1');
        Token second = new Token(11, 15, '0', '0');

        Token result = first.joinWith(second);

        assertEquals("start value differences", 0, result.start);
        assertEquals("end value differences", 15, result.end);
        assertEquals("first value differences", '0', result.first);
        assertEquals("last value differences", '0', result.last);
    }

    @Test
    public void joinWithIntoTwoTokens() throws Exception {
        Token first = new Token(0, 10, '0', '0');
        Token second = new Token(11, 15, '0', '0');

        Token result = first.joinWith(second);

        assertEquals(new Token(0, 15, '0', '0'), result);
    }

    @Test
    public void isAdjacentToTest() throws Exception {
        Token first = new Token(0, 10, '0', '0');
        Token second = new Token(10, 15, '0', '0');

        assertTrue(first.isAdjacentTo(second));
    }

    @Test
    public void canBeJoinedWithTest() throws Exception {
        Token first = new Token(0, 10, '0', '1');
        Token second = new Token(10, 15, '0', '0');
        Token third = new Token(15, 20, '0', '0');

        assertTrue(first.canBeJoinedWith(second));
        assertFalse(second.canBeJoinedWith(third));
    }



}
