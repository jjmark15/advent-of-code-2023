package uk.chaoticgoose.adventofcode.twentyfive.day0;

import org.jspecify.annotations.NullMarked;

import java.util.List;

import static com.ginsberg.gatherers4j.Gatherers4j.zipWith;
import static java.util.Objects.requireNonNull;

@NullMarked
class Day0Solution {
    long part1(LocationIds input) {
        return input.left.stream().sorted()
            .gather(zipWith(input.right.stream().sorted()))
            .mapToLong(e -> Math.abs(requireNonNull(e.first()) - requireNonNull(e.second())))
            .sum();
    }

    @NullMarked
    record LocationIds(List<Long> left, List<Long> right) {}
}
