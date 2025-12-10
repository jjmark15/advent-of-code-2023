package uk.chaoticgoose.adventofcode.twentyfive.day8;

import java.util.List;
import java.util.Set;

import static java.util.Comparator.reverseOrder;
import static uk.chaoticgoose.adventofcode.utils.ActionUtils.repeat;

class DaySolution {
    long part1(List<Position3D> input, int connectionCount) {
        Grid3D grid = new Grid3D(input);

        repeat(connectionCount, grid::connectNearestUnconnectedPair);

        return grid.circuits().stream()
            .map(Set::size)
            .sorted(reverseOrder())
            .limit(3)
            .mapToLong(Integer::longValue)
            .reduce(1, (a, b) -> a * b);
    }

    long part2(List<Position3D> input) {
        Grid3D grid = new Grid3D(input);

        while (!grid.fullyConnected()) {
            grid.connectNearestUnconnectedPair();
        }

        PositionsAndDistance lastConnected = grid.lastConnected();
        return lastConnected.start().x() * lastConnected.end().x();
    }
}
