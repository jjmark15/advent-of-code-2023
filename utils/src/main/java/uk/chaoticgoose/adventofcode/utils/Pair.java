package uk.chaoticgoose.adventofcode.utils;

import org.jspecify.annotations.NullMarked;

import static java.util.Objects.requireNonNull;

@NullMarked
public record Pair<T>(T left, T right) {
    public Pair {
        requireNonNull(left);
        requireNonNull(right);
    }
}
