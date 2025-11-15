package uk.chaoticgoose.adventofcode.utils;

import org.jspecify.annotations.Nullable;

@NotNullMarked
public record Tuple<T extends @Nullable Object, U extends @Nullable Object>(T left, U right) {
}
