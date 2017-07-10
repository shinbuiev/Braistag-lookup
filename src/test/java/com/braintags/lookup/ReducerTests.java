package com.braintags.lookup;


import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class ReducerTests {

    @Test
    public void test(){
        LinkedList<Token> tokens = new LinkedList<>();
        tokens.add(new Token(0, 5, '1', '1'));
        tokens.add(new Token(5, 8, '1', '1'));
        tokens.add(new Token(8, 10, '1', '1'));

        Reducer reducer = new Reducer();
        tokens.forEach(reducer::reduce);
        Queue<Token> reduced = reducer.tokens;

        assertEquals(reduced.size(), 1);
        assertThat(reduced, is(Arrays.asList(new Token(0, 10, '1', '1'))));
    }
}
