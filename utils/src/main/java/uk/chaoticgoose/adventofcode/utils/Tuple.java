package uk.chaoticgoose.adventofcode.utils;

import org.jspecify.annotations.NullMarked;

import static java.util.Objects.requireNonNull;

@NullMarked
public record Tuple<T, U>(T left, U right) {
    public Tuple {
        requireNonNull(left);
        requireNonNull(right);
    }
}
