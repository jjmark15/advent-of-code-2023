package uk.chaoticgoose.adventofcode.utils.grid2d;

import org.jspecify.annotations.NullMarked;

@NullMarked
public sealed interface Direction2D {

    enum North implements Cardinal {
        Instance
    }
    enum South implements Cardinal {
        Instance
    }
    enum East implements Cardinal {
        Instance
    }
    enum West implements Cardinal {
        Instance
    }

    North NORTH = North.Instance;
    South SOUTH = South.Instance;
    East EAST = East.Instance;
    West WEST = West.Instance;

    sealed interface Cardinal extends Direction2D {

        default Cardinal opposite() {
            return switch (this) {
                case North _ -> SOUTH;
                case South _ -> NORTH;
                case East _ -> WEST;
                case West _ -> EAST;
            };
        }

        default Cardinal rotated90() {
            return switch (this) {
                case North _ -> EAST;
                case South _ -> WEST;
                case East _ -> SOUTH;
                case West _ -> NORTH;
            };
        }

        default Cardinal rotated270() {
            return switch (this) {
                case North _ -> WEST;
                case South _ -> EAST;
                case East _ -> NORTH;
                case West _ -> SOUTH;
            };
        }
    }

    Direction2D opposite();

    Direction2D rotated90();

    Direction2D rotated270();
}
