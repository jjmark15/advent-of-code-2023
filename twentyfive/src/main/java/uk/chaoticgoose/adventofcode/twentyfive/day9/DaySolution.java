package uk.chaoticgoose.adventofcode.twentyfive.day9;

import uk.chaoticgoose.adventofcode.utils.grid2d.Point2D;

import java.util.List;

import static java.util.function.Predicate.not;

class DaySolution {
    long part1(List<Point2D> input) {
        return input.stream().flatMapToLong(point -> input.stream()
                .filter(not(point::equals))
                .mapToLong(other -> area(point, other)))
            .max()
            .orElseThrow();
    }

    private long area(Point2D point, Point2D other) {
        long height = Math.abs(point.y() - other.y()) + 1;
        long width = Math.abs(point.x() - other.x()) + 1;
        return height * width;
    }
}
