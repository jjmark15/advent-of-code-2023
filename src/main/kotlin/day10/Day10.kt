package day10

import utils.Grid2D
import utils.Grid2DDirection
import utils.Grid2DPoint

fun part1(input: Grid2D<GridElement>): Int = findFarthestDistanceFromStart(input)

fun findFarthestDistanceFromStart(grid: Grid2D<GridElement>): Int {
    return getPipePositions(grid).size / 2
}

fun getPipePositions(grid: Grid2D<GridElement>): List<PipeSegment> {
    val startingPosition = grid.startingPosition()
    val pipe: MutableList<PipeSegment> = mutableListOf(PipeSegment(startingPosition))

    while (true) {
        val lastSegment = pipe.last()
        val next =
            grid.connectedPositions(lastSegment.position).filterNot { it.position == startingPosition }.firstOrNull {
                it.joinedFrom?.opposite() != lastSegment.joinedFrom
            }
        if (next != null) {
            pipe.add(next)
            continue
        }
        break
    }

    return pipe
}

sealed interface GridElement {
    data object Ground : GridElement
    data object StartingPosition : GridElement
}

sealed class PipePiece(val first: Grid2DDirection, val second: Grid2DDirection) : GridElement {
    data object Vertical : PipePiece(Grid2DDirection.North, Grid2DDirection.South)
    data object Horizontal : PipePiece(Grid2DDirection.East, Grid2DDirection.West)
    data object NorthEast : PipePiece(Grid2DDirection.North, Grid2DDirection.East)
    data object NorthWest : PipePiece(Grid2DDirection.North, Grid2DDirection.West)
    data object SouthWest : PipePiece(Grid2DDirection.South, Grid2DDirection.West)
    data object SouthEast : PipePiece(Grid2DDirection.South, Grid2DDirection.East)

    fun connectsBy(direction: Grid2DDirection): Boolean = direction == first || direction == second
}

fun Grid2D<GridElement>.startingPosition(): Grid2DPoint {
    for ((rowIndex, row) in inner.withIndex()) {
        for ((columnIndex, element) in row.withIndex()) {
            if (element is GridElement.StartingPosition) {
                return Grid2DPoint(rowIndex, columnIndex)
            }
        }
    }
    throw Exception("did not find starting position")
}

fun Grid2D<GridElement>.connectedPositions(
    position: Grid2DPoint
): List<PipeSegment> = when (val element = get(position)) {
    is GridElement.StartingPosition -> adjacentPipePositions(position)
    is PipePiece -> listOf(
        PipeSegment(position.toThe(element.first), element.first.opposite()),
        PipeSegment(position.toThe(element.second), element.second.opposite())
    )

    else -> throw Exception("Grid element cannot be connected")
}

private fun Grid2D<GridElement>.adjacentPipePositions(position: Grid2DPoint): List<PipeSegment> =
    listOf(Grid2DDirection.North, Grid2DDirection.East, Grid2DDirection.South, Grid2DDirection.West).map {
        PipeSegment(
            position.toThe(it), it.opposite()
        )
    }.filter { (position, direction) ->
        if (!contains(position)) return@filter false

        val element = get(position)
        if (element !is PipePiece) return@filter false

        element.connectsBy(direction!!)
    }

data class PipeSegment(val position: Grid2DPoint, val joinedFrom: Grid2DDirection? = null)
