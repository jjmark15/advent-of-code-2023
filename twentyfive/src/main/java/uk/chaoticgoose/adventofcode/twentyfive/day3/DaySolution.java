package uk.chaoticgoose.adventofcode.twentyfive.day3;

import uk.chaoticgoose.adventofcode.utils.Pair;

import java.util.List;

import static java.util.stream.Gatherers.fold;
import static java.util.stream.Gatherers.windowSliding;

class DaySolution {
    long part1(List<BatteryBank> input) {
        return input.stream().mapToLong(this::maximumJoltage).sum();
    }

    private int maximumJoltage(BatteryBank bank) {
        Pair<Integer> batteryPair = bank.batteries().stream().gather(windowSliding(2)).gather(fold(
            () -> new Pair<>(0, 0),
            (state, window) -> {
                var curr = window.get(0);
                var next = window.get(1);

                if (curr > state.left()) {
                    return new Pair<>(curr, next);
                }

                if (next > state.right()) {
                    return new Pair<>(state.left(), next);
                }

                return state;
            })).findFirst().orElseThrow();
        return batteryPair.left() * 10 + batteryPair.right();
    }
}
