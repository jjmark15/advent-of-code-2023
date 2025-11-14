package uk.chaoticgoose.adventofcode.utils;

import org.jspecify.annotations.NullMarked;

@NullMarked
public record Pair<T>(T left, T right) {
}
