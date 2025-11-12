package uk.chaoticgoose.adventofcode.twentyfive.utils;

import org.jspecify.annotations.NullMarked;

@NullMarked
public record Pair<T>(T left, T right) {
}
