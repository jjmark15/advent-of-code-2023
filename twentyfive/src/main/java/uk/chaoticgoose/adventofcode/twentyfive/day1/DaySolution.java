package uk.chaoticgoose.adventofcode.twentyfive.day1;

import java.util.List;

class DaySolution {
    long part1(List<Rotation> input) {
        Dial dial = new Dial(50, 99);
        long zeroCount = 0;

        for (Rotation rotation : input) {
            long current = dial.rotate(rotation);
            if (current == 0) {
                zeroCount++;
            }
        }

        return zeroCount;
    }
}
