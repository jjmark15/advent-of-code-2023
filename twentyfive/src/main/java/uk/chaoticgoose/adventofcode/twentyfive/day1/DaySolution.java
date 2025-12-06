package uk.chaoticgoose.adventofcode.twentyfive.day1;

import uk.chaoticgoose.adventofcode.utils.Tuple;

import java.util.List;

class DaySolution {
    long part1(List<Rotation> input) {
        Dial dial = new Dial(50, 99);
        long zeroCount = 0;

        for (Rotation rotation : input) {
            long current = dial.rotate(rotation).left();
            if (current == 0) {
                zeroCount++;
            }
        }

        return zeroCount;
    }

    long part2(List<Rotation> input) {
        Dial dial = new Dial(50, 99);
        long zeroCount = 0;

        for (Rotation rotation : input) {
            Tuple<Long, Long> result = dial.rotate(rotation);
            zeroCount += result.right();
        }

        return zeroCount;
    }
}
