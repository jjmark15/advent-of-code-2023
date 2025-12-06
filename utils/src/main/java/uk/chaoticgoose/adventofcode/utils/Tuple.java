package uk.chaoticgoose.adventofcode.utils;

import static java.util.Objects.requireNonNull;

public record Tuple<T, U>(T left, U right) {
    public Tuple {
        requireNonNull(left);
        requireNonNull(right);
    }
}
