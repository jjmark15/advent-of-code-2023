package uk.chaoticgoose.adventofcode.utils.grid2d;

import org.jspecify.annotations.NullMarked;

@NullMarked
public enum Direction2D {
    North,
    South,
    East,
    West;

    public Direction2D opposite() {
        return switch (this) {
            case North -> South;
            case South -> North;
            case East -> West;
            case West -> East;
        };
    }

    public Direction2D rotated90() {
        return switch (this) {
            case North -> East;
            case South -> West;
            case East -> South;
            case West -> North;
        };
    }

    public Direction2D rotated270() {
        return switch (this) {
            case North -> West;
            case South -> East;
            case East -> North;
            case West -> South;
        };
    }
}
