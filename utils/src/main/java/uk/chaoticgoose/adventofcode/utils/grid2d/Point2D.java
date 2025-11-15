package uk.chaoticgoose.adventofcode.utils.grid2d;

import org.jspecify.annotations.NullMarked;

import java.util.List;
import java.util.stream.Stream;

import static uk.chaoticgoose.adventofcode.utils.grid2d.Direction2D.*;
import static uk.chaoticgoose.adventofcode.utils.grid2d.Direction2D.WEST;

@NullMarked
public record Point2D(int x, int y) {

    public Point2D toThe(Direction2D direction) {
        return switch (direction) {
            case North _ -> new Point2D(x, y - 1);
            case South _ -> new Point2D(x, y + 1);
            case East _ -> new Point2D(x + 1, y);
            case West _ -> new Point2D(x - 1, y);
            case NorthEast _ -> this.toThe(NORTH).toThe(EAST);
            case SouthEast _ -> this.toThe(SOUTH).toThe(EAST);
            case SouthWest _ -> this.toThe(SOUTH).toThe(WEST);
            case NorthWest _ -> this.toThe(NORTH).toThe(WEST);
        };
    }

    public List<Point2D> cardinalNeighbours() {
        return List.of(
            this.toThe(NORTH),
            this.toThe(EAST),
            this.toThe(SOUTH),
            this.toThe(WEST)
        );
    }

    public List<Point2D> intercardinalNeighbours() {
        return List.of(
            this.toThe(NORTH_EAST),
            this.toThe(SOUTH_EAST),
            this.toThe(SOUTH_WEST),
            this.toThe(NORTH_WEST)
        );
    }

    public List<Point2D> neighbours() {
        return Stream.concat(cardinalNeighbours().stream(), intercardinalNeighbours().stream()).toList();
    }
}
