package uk.chaoticgoose.adventofcode.utils;

import org.jspecify.annotations.Nullable;

@NotNullMarked
public record Pair<T extends @Nullable Object>(T left, T right) {
}
