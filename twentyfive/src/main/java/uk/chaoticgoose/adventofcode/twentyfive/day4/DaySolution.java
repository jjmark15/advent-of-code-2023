package uk.chaoticgoose.adventofcode.twentyfive.day4;

import uk.chaoticgoose.adventofcode.utils.grid2d.Grid2D;

import java.util.List;

class DaySolution {
    long part1(List<List<MapItem>> input) {
        Grid2D<MapItem> grid = new Grid2D<>(input);

        return grid.valuesMatching((point, item) -> {
            if (item == MapItem.Empty) return false;
            long neighbours = point.neighbours().stream()
                .filter(neighbour -> MapItem.Paper == grid.get(neighbour).orElse(null))
                .count();
            return neighbours < 4;
        }).size();
    }
}
