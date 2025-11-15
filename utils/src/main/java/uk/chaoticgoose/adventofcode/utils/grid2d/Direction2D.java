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

    enum NorthEast implements Intercardinal {
        Instance
    }
    enum SouthEast implements Intercardinal {
        Instance
    }
    enum SouthWest implements Intercardinal {
        Instance
    }
    enum NorthWest implements Intercardinal {
        Instance
    }

    North NORTH = North.Instance;
    NorthEast NORTH_EAST = NorthEast.Instance;
    South SOUTH = South.Instance;
    SouthEast SOUTH_EAST = SouthEast.Instance;
    East EAST = East.Instance;
    SouthWest SOUTH_WEST = SouthWest.Instance;
    West WEST = West.Instance;
    NorthWest NORTH_WEST = NorthWest.Instance;

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
        return rotated90().rotated90();
    }

    Direction2D rotated90();

    default Direction2D rotated270() {
        return rotated90().rotated90().rotated90();
    }
}
