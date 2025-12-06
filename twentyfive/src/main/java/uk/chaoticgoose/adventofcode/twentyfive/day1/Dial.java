package uk.chaoticgoose.adventofcode.twentyfive.day1;

class Dial {
    private long current = 0L;
    private final long max;

    public Dial(long start, long max) {
        this.current = start;
        this.max = max;
    }

    public long rotate(Rotation rotation) {
        long multiplier = switch (rotation.direction()) {
            case Right -> 1L;
            case Left -> -1L;
        };

        this.current = (this.current + rotation.value() * multiplier) % (max + 1);

        if (this.current < 0) {
            this.current += this.max + 1;
        }

        return this.current;
    }
}
