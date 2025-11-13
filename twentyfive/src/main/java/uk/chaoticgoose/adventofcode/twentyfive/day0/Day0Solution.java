package uk.chaoticgoose.adventofcode.twentyfive.day0;

import org.jspecify.annotations.NullMarked;
import uk.chaoticgoose.adventofcode.twentyfive.utils.Pair;

import java.util.List;

import static com.ginsberg.gatherers4j.Gatherers4j.zipWith;
import static java.util.Objects.requireNonNull;

@NullMarked
class Day0Solution {
    long part1(Pair<List<Long>> input) {
        return input.left().stream().sorted()
            .gather(zipWith(input.right().stream().sorted()))
            .mapToLong(e -> Math.abs(requireNonNull(e.first()) - requireNonNull(e.second())))
            .sum();
    }
}
