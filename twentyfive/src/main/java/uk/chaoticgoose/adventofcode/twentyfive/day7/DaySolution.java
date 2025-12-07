package uk.chaoticgoose.adventofcode.twentyfive.day7;

import uk.chaoticgoose.adventofcode.utils.grid2d.Direction2D;
import uk.chaoticgoose.adventofcode.utils.grid2d.Grid2D;
import uk.chaoticgoose.adventofcode.utils.grid2d.Point2D;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Stream;

import static uk.chaoticgoose.adventofcode.twentyfive.day7.DiagramElement.*;

class DaySolution {
    long part1(List<List<DiagramElement>> input) {
        Grid2D<DiagramElement> grid = new Grid2D<>(input);

        Point2D start = grid.pointsMatching((_, element) -> element == DiagramElement.Start).getFirst();

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

            Point2D above = point.toThe(Direction2D.NORTH);
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
            case Start, Beam -> Stream.of(point.toThe(Direction2D.SOUTH)).filter(grid::contains).toList();
            case Splitter -> Stream.of(Direction2D.WEST, Direction2D.EAST).map(point::toThe).filter(grid::contains).toList();
            default -> throw new IllegalStateException();
        };
    }
}
