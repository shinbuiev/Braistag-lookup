package com.braintags.lookup;

import java.lang.ref.SoftReference;
import java.util.Comparator;
import java.util.Objects;

/**
 * Token is a immutable class that keeps the indexes of the sequence and the first and last chars
 * Additionally it keeps the initial token, but it might be collected by GC if needed.
 */
public class Token {
    final long start;
    final long end;
    final char first;
    final char last;
    final private SoftReference<String> reference;

    public Token(long start, long end) {
        this(start, end, '\0', '\0', "");
    }

    public Token(long start, long end, char first, char last) {
        this(start, end, first, last, "");
    }

    public Token(long start, long end, char first, char last, String token) {
        this.start = start;
        this.end = end;
        this.first = first;
        this.last = last;
        this.reference = new SoftReference<>(token);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Token token = (Token) o;

        if (start != token.start) return false;
        return end == token.end;
    }

    @Override
    public int hashCode() {
        int result = (int) (start ^ (start >>> 32));
        result = 31 * result + (int) (end ^ (end >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return String.format("[%s:%s] %d %c..%c %s",
                String.valueOf(start),
                String.valueOf(end),
                length(),
                first, last, Objects.toString(reference.get()));
    }

    public long length() {
        return end - start;
    }

    public boolean isAdjacentTo(Token other) {
        return this.end == other.start;
    }

    public boolean canBeJoinedWith(Token other) {
        return isAdjacentTo(other) && !(this.last == '0' && other.first == '0');
    }

    public String getTokenString() {
        return Objects.toString(reference.get(), "");
    }

    public Token joinWith(Token other) {
        return new Token(start, other.end, first, other.last);
    }

    public Token trimEnd() {
        return new Token(start, end - 1, first, '1', "");
    }

    public Token trimStart() {
        return new Token(start - 1, end, '1', last, "");
    }

    public static Comparator<Token> byLengthComparator = Comparator.comparing(Token::length);
}
