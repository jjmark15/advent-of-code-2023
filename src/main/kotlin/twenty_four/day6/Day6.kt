package twenty_four.day6

import utils.Grid2D
import utils.Grid2DDirection
import utils.Grid2DPoint

sealed interface MapElement {
    data object Empty : MapElement
    data object Obstacle : MapElement
    data class StartingPosition(val direction: Grid2DDirection) : MapElement
}

data class GuardPosition(val position: Grid2DPoint, val direction: Grid2DDirection)

fun part1(input: Grid2D<MapElement>): Int {
    var currentPosition = getStartingPosition(input)
    val visitedPositions = mutableSetOf(currentPosition.position)

    while (!input.isOnEdge(currentPosition.position)) {
        val nextPosition = nextPosition(input, currentPosition)
        visitedPositions.add(nextPosition.position)
        currentPosition = nextPosition
    }

    return visitedPositions.size
}

tailrec fun nextPosition(input: Grid2D<MapElement>, currentPosition: GuardPosition): GuardPosition {
    val proposed = currentPosition.position.toThe(currentPosition.direction)
    if (input.get(proposed) != MapElement.Obstacle) {
        return GuardPosition(proposed, currentPosition.direction)
    }
    return nextPosition(input, GuardPosition(currentPosition.position, currentPosition.direction.right90()))
}

fun getStartingPosition(grid: Grid2D<MapElement>): GuardPosition = grid.elementsMatching { element ->
    element is MapElement.StartingPosition
}.first().let { position -> GuardPosition(position, (grid.get(position) as MapElement.StartingPosition).direction) }
