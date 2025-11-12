package uk.chaoticgoose.adventofcode.twentyfive.day0;

import org.jspecify.annotations.NullMarked;

import java.util.List;

import static com.ginsberg.gatherers4j.Gatherers4j.zipWith;
import static java.util.Objects.requireNonNull;

@NullMarked
class Day0Solution {
    long part1(LocationIds input) {
        var first = input.left.stream().sorted().toList();
        var second = input.right.stream().sorted().toList();
        return first.stream()
            .gather(zipWith(second))
            .mapToLong(e -> Math.abs(requireNonNull(e.first()) - requireNonNull(e.second())))
            .sum();
    }

    @NullMarked
    record LocationIds(List<Long> left, List<Long> right) {}
}
