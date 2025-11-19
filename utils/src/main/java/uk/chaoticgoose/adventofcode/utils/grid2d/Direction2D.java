package uk.chaoticgoose.adventofcode.utils.grid2d;

import org.jspecify.annotations.NullMarked;

@NullMarked
public sealed interface Direction2D {

    @NullMarked
    final class North implements Cardinal {
        private North() {}
    }
    @NullMarked
    final class South implements Cardinal {
        private South() {}
    }
    @NullMarked
    final class East implements Cardinal {
        private East() {}
    }
    @NullMarked
    final class West implements Cardinal {
        private West() {}
    }

    @NullMarked
    final class NorthEast implements Intercardinal {
        private NorthEast() {}
    }
    @NullMarked
    final class SouthEast implements Intercardinal {
        private SouthEast() {}
    }
    @NullMarked
    final class SouthWest implements Intercardinal {
        private SouthWest() {}
    }
    @NullMarked
    final class NorthWest implements Intercardinal {
        private NorthWest() {}
    }

    North NORTH = new North();
    NorthEast NORTH_EAST = new NorthEast();
    East EAST = new East();
    SouthEast SOUTH_EAST = new SouthEast();
    South SOUTH = new South();
    SouthWest SOUTH_WEST = new SouthWest();
    West WEST = new West();
    NorthWest NORTH_WEST = new NorthWest();

    @NullMarked
    sealed interface Cardinal extends Direction2D {

        default Cardinal rotated90() {
            return switch (this) {
                case North _ -> EAST;
                case South _ -> WEST;
                case East _ -> SOUTH;
                case West _ -> NORTH;
            };
        }
    }

    @NullMarked
    sealed interface Intercardinal extends Direction2D {

        default Intercardinal rotated90() {
            return switch (this) {
                case NorthEast _ -> SOUTH_EAST;
                case SouthEast _ -> SOUTH_WEST;
                case SouthWest _ -> NORTH_WEST;
                case NorthWest _ -> NORTH_EAST;
            };
        }
    }

    default Direction2D opposite() {
        return rotated180();
    }

    default Direction2D rotated180() {
        return rotated90().rotated90();
    }

    Direction2D rotated90();

    default Direction2D rotated270() {
        return rotated90().rotated90().rotated90();
    }
}
