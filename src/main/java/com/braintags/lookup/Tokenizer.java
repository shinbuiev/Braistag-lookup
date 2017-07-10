package com.braintags.lookup;

import java.io.BufferedReader;
import java.io.CharArrayReader;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Tokenizer {

    public static final Pattern pattern = Pattern.compile("00+");

    /**
     * method overloads is for testing purpose
     * @param source string to tokenize
     * @return list of String
     */
    public static List<String> tokenize(final String source) {
        return tokenize(new StringReader(source)).stream()
                .map(token -> token.getTokenString())
                .collect(Collectors.toList());
    }

    /**
     * method overloads is for testing purpose
     * @param source string to tokenize
     * @return list of Token
     */
    public static List<Token> tokenize(Readable readable){
        LinkedList<Token> results = new LinkedList<>();
        tokenize(readable, 0, results::add);
        return results;
    }

    /**
     * this method is used for finding the sequences of chars without  00
     * @param readable something readable
     * @param offset the offset of the readable if any
     * @param sink the queue to put the results
     * @return void
     */
    public static void tokenize(Readable readable, long offset, Consumer<Token> consumer) {
        Scanner scanner = new Scanner(readable).useDelimiter(Tokenizer.pattern);

        while (scanner.hasNext()) {
            String sequence = scanner.next();
            if (sequence.isEmpty())
                continue;

            int start = scanner.match().start();
            int end = scanner.match().end();
            char first = sequence.charAt(0);
            char last = sequence.charAt(sequence.length() - 1);

            Token token = new Token(start + offset, end + offset, first, last, sequence);
            printIfLongest(token);
            consumer.accept(token);
        }
    }

    private static void printIfLongest(Token token) {
        if (longestLength.get() < token.length())
        {
            longestLength.set(token.length());
            System.out.println(token);
        }
    }

    static AtomicLong longestLength = new AtomicLong();

}
