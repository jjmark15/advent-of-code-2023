package uk.chaoticgoose.adventofcode.utils.grid2d;

public sealed interface Direction2D {

    final class North implements Cardinal {
        private North() {}
    }
    final class South implements Cardinal {
        private South() {}
    }
    final class East implements Cardinal {
        private East() {}
    }
    final class West implements Cardinal {
        private West() {}
    }

    final class NorthEast implements Intercardinal {
        private NorthEast() {}
    }
    final class SouthEast implements Intercardinal {
        private SouthEast() {}
    }
    final class SouthWest implements Intercardinal {
        private SouthWest() {}
    }
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
