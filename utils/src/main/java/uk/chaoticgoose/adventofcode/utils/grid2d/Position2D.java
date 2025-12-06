package uk.chaoticgoose.adventofcode.utils.grid2d;

public record Position2D(Point2D point, Direction2D direction) {

    public Position2D ahead() {
        return new Position2D(point.toThe(direction), direction);
    }

    public Position2D before() {
        return new Position2D(point.toThe(direction.opposite()), direction);
    }

    public Position2D turned90() {
        return new Position2D(point, direction.rotated90());
    }

    public Position2D turned270() {
        return new Position2D(point, direction.rotated270());
    }

    public Position2D turnTo(Direction2D direction) {
        return new Position2D(point, direction);
    }
}
