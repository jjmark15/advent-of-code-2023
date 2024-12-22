package twenty_four.day8

import utils.grids.twodee.Grid2D
import utils.grids.twodee.Point2D

data class Frequency(val value: Char)

fun part1(map: Grid2D<Frequency?>): Int = map.mapIndexedNotNull { point, value ->
    if (value == null) return@mapIndexedNotNull null

    map.mapIndexedNotNull { p, v ->
        if (v != value || p == point) {
            null
        } else {
            antinodePositionsWithResonanceMultiplier(map, point, p, 1)
        }
    }.flatten().flatten()
}.flatten().flatten().toSet().count()

fun part2(map: Grid2D<Frequency?>): Int = map.mapIndexedNotNull { point, value ->
    if (value == null) return@mapIndexedNotNull null

    map.mapIndexedNotNull { p, v ->
        if (v != value || p == point) {
            null
        } else {
            allResonantAntinodePositions(map, point, p)
        }
    }.flatten().flatten()
}.flatten().flatten().toSet().count()

private fun allResonantAntinodePositions(
    map: Grid2D<Frequency?>,
    first: Point2D,
    second: Point2D
): List<Point2D> {
    val positions: MutableSet<Point2D> = mutableSetOf(first, second)
    var positionOffsetMultiplier = 1

    while (true) {
        val newPositions = antinodePositionsWithResonanceMultiplier(map, first, second, positionOffsetMultiplier)
        if (newPositions.isEmpty()) break
        positions.addAll(newPositions)
        positionOffsetMultiplier += 1
    }

    return positions.toList()
}

private fun antinodePositionsWithResonanceMultiplier(
    map: Grid2D<Frequency?>,
    first: Point2D,
    second: Point2D,
    resonanceOffsetMultiplier: Int
): List<Point2D> =
    listOf(
        nextResonantLocation(first, second, resonanceOffsetMultiplier),
        nextResonantLocation(second, first, resonanceOffsetMultiplier),
    ).filter { map.contains(it) }

private fun nextResonantLocation(first: Point2D, second: Point2D, offsetMultiplier: Int): Point2D {
    val rowDiff = (first.row - second.row)
    val colDiff = (first.column - second.column)

    return Point2D(first.row + rowDiff * offsetMultiplier, first.column + colDiff * offsetMultiplier)
}
