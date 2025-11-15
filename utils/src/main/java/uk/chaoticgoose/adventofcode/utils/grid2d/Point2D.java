package uk.chaoticgoose.adventofcode.utils.grid2d;

import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
public record Point2D(int x, int y) {

    public Point2D toThe(Direction2D direction) {
        return switch (direction) {
            case North -> new Point2D(x, y - 1);
            case South -> new Point2D(x, y + 1);
            case East -> new Point2D(x + 1, y);
            case West -> new Point2D(x - 1, y);
        };
    }

    public List<Point2D> cardinalNeighbours() {
        return List.of(
            this.toThe(Direction2D.North),
            this.toThe(Direction2D.East),
            this.toThe(Direction2D.South),
            this.toThe(Direction2D.West)
        );
    }
}
