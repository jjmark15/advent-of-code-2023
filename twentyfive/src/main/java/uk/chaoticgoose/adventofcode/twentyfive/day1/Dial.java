package uk.chaoticgoose.adventofcode.twentyfive.day1;

import uk.chaoticgoose.adventofcode.utils.Tuple;

class Dial {
    private long current;
    private final long max;

    public Dial(long start, long max) {
        this.current = start;
        this.max = max;
    }

    public Tuple<Long, Long> rotate(Rotation rotation) {
        long multiplier = switch (rotation.direction()) {
            case Right -> 1L;
            case Left -> -1L;
        };

        var previous = current;
        var newCurrent = this.current + rotation.value() * multiplier;
        this.current = (newCurrent) % (max + 1);

        if (this.current < 0) {
            this.current += this.max + 1;
        }

        return new Tuple<>(this.current, zeroHits(previous, newCurrent));
    }

    private long zeroHits(long previous, long current) {
        if (current == 0) {
            return 1;
        }

        if (current > 0) {
            return current / (this.max + 1);
        }

        var additional = previous > 0 ? 1 : 0;

        return Math.abs(current) / (this.max + 1) + additional;
    }
}
