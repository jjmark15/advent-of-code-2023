package twenty_three.day10

import utils.grids.twodee.Grid2D
import utils.grids.twodee.Direction2D
import utils.grids.twodee.Point2D

fun part1(input: PipeGrid): Int = findFarthestDistanceFromStart(input)

fun findFarthestDistanceFromStart(grid: PipeGrid): Int {
    return getPipePositions(grid).size / 2
}

fun getPipePositions(grid: PipeGrid): List<PipeSegment> {
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

sealed class PipePiece(val first: Direction2D, val second: Direction2D) : GridElement {
    data object Vertical : PipePiece(Direction2D.North, Direction2D.South)
    data object Horizontal : PipePiece(Direction2D.East, Direction2D.West)
    data object NorthEast : PipePiece(Direction2D.North, Direction2D.East)
    data object NorthWest : PipePiece(Direction2D.North, Direction2D.West)
    data object SouthWest : PipePiece(Direction2D.South, Direction2D.West)
    data object SouthEast : PipePiece(Direction2D.South, Direction2D.East)

    fun connectsBy(direction: Direction2D): Boolean = direction == first || direction == second
}

class PipeGrid(inner: List<List<GridElement>>) : Grid2D<GridElement>(inner) {
    fun startingPosition(): Point2D {
        for ((rowIndex, row) in inner.withIndex()) {
            for ((columnIndex, element) in row.withIndex()) {
                if (element is GridElement.StartingPosition) {
                    return Point2D(rowIndex, columnIndex)
                }
            }
        }
        throw Exception("did not find starting position")
    }

    fun connectedPositions(
        position: Point2D
    ): List<PipeSegment> = when (val element = get(position)) {
        is GridElement.StartingPosition -> adjacentPipePositions(position)
        is PipePiece -> listOf(
            PipeSegment(position.toThe(element.first), element.first.opposite()),
            PipeSegment(position.toThe(element.second), element.second.opposite())
        )

        else -> throw Exception("Grid element cannot be connected")
    }

    private fun adjacentPipePositions(position: Point2D): List<PipeSegment> =
        listOf(Direction2D.North, Direction2D.East, Direction2D.South, Direction2D.West).map {
            PipeSegment(
                position.toThe(it), it.opposite()
            )
        }.filter { (position, direction) ->
            if (!contains(position)) return@filter false

            val element = get(position)
            if (element !is PipePiece) return@filter false

            element.connectsBy(direction!!)
        }
}

data class PipeSegment(val position: Point2D, val joinedFrom: Direction2D? = null)
