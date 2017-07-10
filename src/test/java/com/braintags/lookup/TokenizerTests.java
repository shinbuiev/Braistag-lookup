package com.braintags.lookup;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static com.braintags.lookup.Tokenizer.tokenize;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class TokenizerTests {

    @Test
    public void tokenizerCases() {
        assertEquals(tokenize("").size(), 0);
        assertThat(tokenize("0"), is(asList("0")));
        assertThat(tokenize("1"), is(asList("1")));
        assertThat(tokenize("01"), is(asList("01")));
        assertThat(tokenize("10"), is(asList("10")));

        assertThat(tokenize("00000000").size(), is(0));
        assertThat(tokenize("00001000"), is(asList("1")));
        assertThat(tokenize("00100100"), is(asList("1", "1")));
        assertThat(tokenize("1001"), is(asList("1", "1")));
        assertThat(tokenize("10001001"), is(asList("1", "1", "1")));

        assertThat(tokenize("001010100"), is(asList("10101")));
        assertThat(tokenize("010101010"), is(asList("010101010")));
    }

    @Test
    public void tokenizerRandomStringCases() {
        for (int i = 0; i < 1000; i++) {
            int length = ThreadLocalRandom.current().nextInt(10, 10000);
            String source = RandomStringUtils.random(length, '0', '1');
            List<String> tokens = tokenize(source);
            tokens.stream().forEach(token -> {
                assertTrue(source.contains(token));
                assertFalse(token, token.contains("00"));
            });
        }
    }
}
