package uk.chaoticgoose.adventofcode.twentyfive.day1;

record Rotation(Direction direction, long value) {
    enum Direction {
        Right,
        Left
    }
}
