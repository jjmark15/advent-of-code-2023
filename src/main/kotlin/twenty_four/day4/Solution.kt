package twenty_four.day4

import utils.grids.twodee.Grid2D
import utils.grids.twodee.Direction2D

enum class Letter {
    X, M, A, S
}

private val XMAS = listOf(Letter.X, Letter.M, Letter.A, Letter.S)
private val MAS = listOf(Letter.M, Letter.A, Letter.S)

fun part1(input: List<List<Letter>>): Long {
    val directions = listOf(
        Direction2D.North,
        Direction2D.NorthEast,
        Direction2D.East,
        Direction2D.SouthEast,
        Direction2D.South,
        Direction2D.SouthWest,
        Direction2D.West,
        Direction2D.NorthWest
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

        val first = listOfNotNull(grid.getOrNull(point.toThe(Direction2D.NorthEast)), middle, grid.getOrNull(point.toThe(Direction2D.SouthWest)))
        val second = listOfNotNull(grid.getOrNull(point.toThe(Direction2D.NorthWest)), middle, grid.getOrNull(point.toThe(Direction2D.SouthEast)))

        (first == MAS || first.asReversed() == MAS) && (second == MAS || second.asReversed() == MAS)
    }.count { it }.toLong()
}
