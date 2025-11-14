package uk.chaoticgoose.adventofcode.utils;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public record Tuple<T extends @Nullable Object, U extends @Nullable Object>(T left, U right) {
}
