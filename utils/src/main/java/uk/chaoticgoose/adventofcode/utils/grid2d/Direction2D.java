package uk.chaoticgoose.adventofcode.utils.grid2d;

import org.jspecify.annotations.NullMarked;

@NullMarked
public sealed interface Direction2D {

    @NullMarked
    enum North implements Cardinal {
        Instance
    }
    @NullMarked
    enum South implements Cardinal {
        Instance
    }
    @NullMarked
    enum East implements Cardinal {
        Instance
    }
    @NullMarked
    enum West implements Cardinal {
        Instance
    }

    @NullMarked
    enum NorthEast implements Intercardinal {
        Instance
    }
    @NullMarked
    enum SouthEast implements Intercardinal {
        Instance
    }
    @NullMarked
    enum SouthWest implements Intercardinal {
        Instance
    }
    @NullMarked
    enum NorthWest implements Intercardinal {
        Instance
    }

    North NORTH = North.Instance;
    NorthEast NORTH_EAST = NorthEast.Instance;
    East EAST = East.Instance;
    SouthEast SOUTH_EAST = SouthEast.Instance;
    South SOUTH = South.Instance;
    SouthWest SOUTH_WEST = SouthWest.Instance;
    West WEST = West.Instance;
    NorthWest NORTH_WEST = NorthWest.Instance;

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
        return rotated90().rotated90();
    }

    Direction2D rotated90();

    default Direction2D rotated270() {
        return rotated90().rotated90().rotated90();
    }
}
