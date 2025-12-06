package uk.chaoticgoose.adventofcode.utils;

import static java.util.Objects.requireNonNull;

public record Pair<T>(T left, T right) {
    public Pair {
        requireNonNull(left);
        requireNonNull(right);
    }
}
