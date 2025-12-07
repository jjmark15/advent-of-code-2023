package uk.chaoticgoose.adventofcode.twentyfive.day7;

import uk.chaoticgoose.adventofcode.utils.grid2d.Grid2D;
import uk.chaoticgoose.adventofcode.utils.grid2d.Point2D;

import java.util.*;
import java.util.stream.Stream;

import static uk.chaoticgoose.adventofcode.twentyfive.day7.DiagramElement.*;
import static uk.chaoticgoose.adventofcode.utils.grid2d.Direction2D.*;

class DaySolution {
    long part1(List<List<DiagramElement>> input) {
        Grid2D<DiagramElement> grid = new Grid2D<>(input);

        Point2D start = grid.pointsMatching((_, element) -> element == Start).getFirst();

        HashSet<Point2D> visited = new HashSet<>();
        ArrayDeque<Point2D> toVisit = new ArrayDeque<>();
        toVisit.add(start);

        while (!toVisit.isEmpty()) {
            Point2D next = toVisit.removeFirst();
            visited.add(next);
            toVisit.addAll(handleVisit(grid, next).stream().filter(point -> !visited.contains(point)).toList());
        }

        return grid.pointsMatching((point, element) -> {
            if (element != Splitter) {
                return false;
            }

            Point2D above = point.toThe(NORTH);
            return grid.contains(above) && Beam == grid.get(above).orElse(null);
        }).size();
    }

    private List<Point2D> handleVisit(Grid2D<DiagramElement> grid, Point2D point) {
        DiagramElement element = grid.get(point).orElseThrow();

        if (element == Empty) {
            grid.set(point, Beam);
        }

        DiagramElement newElement = grid.get(point).orElseThrow();

        return switch (newElement) {
            case Start, Beam -> Stream.of(point.toThe(SOUTH)).filter(grid::contains).toList();
            case Splitter -> Stream.of(WEST, EAST).map(point::toThe).filter(grid::contains).toList();
            default -> throw new IllegalStateException();
        };
    }

    long part2(List<List<DiagramElement>> input) {
        Grid2D<DiagramElement> grid = new Grid2D<>(input);

        Point2D start = grid.pointsMatching((_, element) -> element == Start).getFirst();

        HashMap<Point2D, Long> pathCounts = new HashMap<>();

        return countPathsFrom(start, grid, pathCounts);
    }

    private long countPathsFrom(Point2D point, Grid2D<DiagramElement> grid, HashMap<Point2D, Long> pathCounts) {
        if (pathCounts.containsKey(point)) {
            return pathCounts.get(point);
        }

        if (!grid.contains(point.toThe(SOUTH))) {
            return 1;
        }

        List<Point2D> children = handleVisit(grid, point);

        var count = children.stream().filter(grid::contains).mapToLong(it -> countPathsFrom(it, grid, pathCounts)).sum();
        pathCounts.put(point, count);
        return count;
    }
}
