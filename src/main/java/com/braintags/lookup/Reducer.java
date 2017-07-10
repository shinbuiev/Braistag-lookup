package com.braintags.lookup;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class Reducer {

    public static AtomicLong maxTokenLength = new AtomicLong(0);

    public static Queue<Token> reduce(LinkedList<Token> input){
        Reducer reducer = new Reducer();
        input.forEach(reducer::reduce);
        return reducer.tokens;
    }

    public final LinkedList<Token> tokens;
    private int lastCompactSize = 0;

    public Reducer() {
        this(new LinkedList<>());
    }

    public Reducer(LinkedList<Token> sink) {
        this.tokens = sink;
    }

    public void reduce(Token next) {
        if (tokens.isEmpty()) {
            tokens.addLast(next);
        } else {
            Token current = tokens.getLast();

            if (current.isAdjacentTo(next)) {
                if (current.canBeJoinedWith(next)) {
                    tokens.addLast(tokens.removeLast().joinWith(next));
                } else {
                    tokens.addLast(tokens.removeLast().trimEnd());
                    tokens.addLast(next.trimStart());
                }
            } else {
                tokens.addLast(next);
            }
        }

        if (tokens.size() > lastCompactSize + 8096)
            compact();
    }

    /**
     * This method remove the small tokens from the middle of the queue
     */
    public void compact() {
        if (tokens.size() <= 3)
            return;

        Token first = tokens.getFirst();
        Token last = tokens.getLast();
        Token max = tokens.stream().max(Token.byLengthComparator).get();
        List<Token> onlyLongestTokens = tokens.stream()
                .filter(t -> t.length() > max.length() - 3)
                .collect(Collectors.toList());
        tokens.clear();
        tokens.add(first);
        tokens.addAll(onlyLongestTokens);
        tokens.add(last);

        lastCompactSize = tokens.size();
    }

}
