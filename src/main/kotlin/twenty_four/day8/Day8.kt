package twenty_four.day8

import utils.Grid2D
import utils.Grid2DPoint

data class Frequency(val value: Char)

fun part1(map: Grid2D<Frequency?>): Int = map.mapIndexedNotNull { point, value ->
    if (value == null) return@mapIndexedNotNull null

    map.mapIndexedNotNull { p, v ->
        if (v != value || p == point) {
            null
        } else {
            antinodePositions(map, point, p)
        }
    }.flatten().flatten()
}.flatten().flatten().toSet().count()

private fun antinodePositions(map: Grid2D<Frequency?>, first: Grid2DPoint, second: Grid2DPoint): List<Grid2DPoint> {
    val rowDiff = (first.row - second.row)
    val colDiff = (first.column - second.column)

    return listOf(
        Grid2DPoint(first.row + rowDiff, first.column + colDiff),
        Grid2DPoint(second.row - rowDiff, second.column - colDiff)
    ).filter { map.contains(it) }
}
