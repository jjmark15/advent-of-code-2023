package uk.chaoticgoose.adventofcode.utils.grid2d;

import org.jspecify.annotations.NullMarked;

import java.util.List;

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
}
