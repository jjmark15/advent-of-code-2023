package twenty_four.day4

import utils.Grid2D
import utils.Grid2DDirection

enum class Letter {
    X, M, A, S
}

private val XMAS = listOf(Letter.X, Letter.M, Letter.A, Letter.S)
private val MAS = listOf(Letter.M, Letter.A, Letter.S)

fun part1(input: List<List<Letter>>): Long {
    val directions = listOf(
        Grid2DDirection.North,
        Grid2DDirection.NorthEast,
        Grid2DDirection.East,
        Grid2DDirection.SouthEast,
        Grid2DDirection.South,
        Grid2DDirection.SouthWest,
        Grid2DDirection.West,
        Grid2DDirection.NorthWest
    )
    val grid = Grid2D(input)

    return grid.mapPoints { point ->
        directions.count { direction ->
            grid.pointsInDirection(point, 4, direction).mapNotNull { grid.getOrNull(it) } == XMAS
        }
    }.sum().toLong()
}

fun part2(input: List<List<Letter>>): Long {
    val grid = Grid2D(input)

    return grid.mapPoints { point ->
        val middle = grid.get(point)

        val first = listOfNotNull(grid.getOrNull(point.toThe(Grid2DDirection.NorthEast)), middle, grid.getOrNull(point.toThe(Grid2DDirection.SouthWest)))
        val second = listOfNotNull(grid.getOrNull(point.toThe(Grid2DDirection.NorthWest)), middle, grid.getOrNull(point.toThe(Grid2DDirection.SouthEast)))

        (first == MAS || first.asReversed() == MAS) && (second == MAS || second.asReversed() == MAS)
    }.count { it }.toLong()
}
