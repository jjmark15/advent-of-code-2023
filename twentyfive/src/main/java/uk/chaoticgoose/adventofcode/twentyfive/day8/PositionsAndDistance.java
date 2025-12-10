package uk.chaoticgoose.adventofcode.twentyfive.day8;

import static java.util.Objects.requireNonNull;

record PositionsAndDistance(Position3D start, Position3D end, long distance) {
    public PositionsAndDistance {
        requireNonNull(start);
        requireNonNull(end);
    }
}
