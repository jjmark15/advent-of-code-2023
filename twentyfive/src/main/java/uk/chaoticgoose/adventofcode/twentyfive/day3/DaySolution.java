package uk.chaoticgoose.adventofcode.twentyfive.day3;

import uk.chaoticgoose.adventofcode.utils.ListUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Gatherers;

import static java.util.stream.Gatherers.fold;

class DaySolution {
    long part1(List<BatteryBank> input) {
        return input.stream().mapToLong(bank -> maximumJoltage(bank, 2)).sum();
    }

    long part2(List<BatteryBank> input) {
        return input.stream().mapToLong(bank -> maximumJoltage(bank, 12)).sum();
    }

    private long maximumJoltage(BatteryBank bank, int batteryCount) {
        return bank.batteries().stream()
            .gather(Gatherers.windowSliding(batteryCount))
            .gather(fold(
                () -> new ArrayList<>(Arrays.asList(new Integer[batteryCount])),
                (state, window) -> {

                    for (int i = 0; i < window.size(); i++) {
                        var old = ListUtils.get(state, i).orElse(0);
                        var option = window.get(i);
                        if (old < option) {
                            for (int j = i; j < window.size(); j++) {
                                state.set(j, window.get(j));
                            }
                            return state;
                        }
                    }

                    return state;
                }))
            .findFirst()
            .orElseThrow()
            .stream()
            .gather(fold(() -> 0L, (acc, n) -> acc * 10 + n))
            .findFirst().orElseThrow();
    }
}
