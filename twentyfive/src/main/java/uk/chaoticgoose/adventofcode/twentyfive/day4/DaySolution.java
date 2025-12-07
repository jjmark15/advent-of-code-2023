package uk.chaoticgoose.adventofcode.twentyfive.day4;

import uk.chaoticgoose.adventofcode.utils.grid2d.Grid2D;
import uk.chaoticgoose.adventofcode.utils.grid2d.Point2D;

import java.util.List;

class DaySolution {
    long part1(List<List<MapItem>> input) {
        Grid2D<MapItem> grid = new Grid2D<>(input);

        return itemsThatCanBeRemoved(grid).size();
    }

    long part2(List<List<MapItem>> input) {
        Grid2D<MapItem> grid = new Grid2D<>(input);
        boolean removedAny = true;
        long removed = 0;

        while (removedAny) {
            List<Point2D> points = itemsThatCanBeRemoved(grid);
            if (points.isEmpty()) {
                removedAny = false;
            }
            for (var point : points) {
                if (grid.get(point).orElse(MapItem.Empty) == MapItem.Paper) {
                    grid.set(point, MapItem.Empty);
                    removed++;
                }
            }
        }

        return removed;
    }

    private List<Point2D> itemsThatCanBeRemoved(Grid2D<MapItem> grid) {
        return grid.pointsMatching((point, item) -> {
            if (item == MapItem.Empty) return false;
            return canBeRemoved(grid, point);
        });
    }

    private boolean canBeRemoved(Grid2D<MapItem> grid, Point2D point) {
        long neighbours = point.neighbours().stream()
            .filter(neighbour -> MapItem.Paper == grid.get(neighbour).orElse(null))
            .count();
        return neighbours < 4;
    }
}
