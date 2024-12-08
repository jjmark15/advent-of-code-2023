package twenty_four.day4

import utils.Grid2D
import utils.Grid2DDirection

enum class Letter {
    X, M, A, S
}

private val XMAS = listOf(Letter.X, Letter.M, Letter.A, Letter.S)

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
            grid.cellsInDirection(point, 4, direction).mapNotNull { grid.getOrNull(it) } == XMAS
        }
    }.sum().toLong()
}
