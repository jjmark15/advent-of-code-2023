package twenty_four.day6

import twenty_four.day6.MapElement.Empty
import twenty_four.day6.MapElement.GuardPath
import twenty_four.day6.MapElement.IntentionalObstacle
import twenty_four.day6.MapElement.Obstacle
import twenty_four.day6.MapElement.StartingPosition
import utils.grids.twodee.Grid2D
import utils.grids.twodee.Direction2D
import utils.grids.twodee.Point2D

sealed interface MapElement {
    data object Empty : MapElement
    data object Obstacle : MapElement
    data object IntentionalObstacle : MapElement
    data class StartingPosition(val direction: Direction2D.CardinalDirection2D) : MapElement
    data class GuardPath(val direction: PathDirection) : MapElement

    enum class PathDirection {
        Vertical, Horizontal, Change
    }

    fun isAnObstacle(): Boolean = this == Obstacle || this == IntentionalObstacle
}

fun debugMap(map: Grid2D<MapElement>) {
    println("====")
    println(map.debugDisplay { element ->
        when (element) {
            is StartingPosition -> when (element.direction) {
                Direction2D.East -> ">"
                Direction2D.North -> "^"
                Direction2D.South -> "v"
                Direction2D.West -> "<"
            }

            Empty -> "."
            Obstacle -> "#"
            IntentionalObstacle -> "O"
            is GuardPath -> when (element.direction) {
                MapElement.PathDirection.Vertical -> "|"
                MapElement.PathDirection.Horizontal -> "-"
                MapElement.PathDirection.Change -> "+"
            }
        }
    })
}

data class GuardPosition(val position: Point2D, val direction: Direction2D) {
    fun ahead(): Point2D = position.toThe(direction)

    fun turned90(): GuardPosition = GuardPosition(position, direction.right90())
}

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

fun part2(map: Grid2D<MapElement>): Int {
    var currentPosition = getStartingPosition(map)
    val visitedPositions = mutableSetOf(currentPosition)
    val obstaclePoints = mutableSetOf<Point2D>()

    while (!map.isOnEdge(currentPosition.position)) {
        val nextPosition = nextPosition(map, currentPosition)
        updateMapMarkings(map, currentPosition, nextPosition)
        if (!obstaclePoints.contains(nextPosition.position) && resultsInCycle(
                map.copyWith(nextPosition.position) { IntentionalObstacle }, visitedPositions, currentPosition
            )
        ) {
            obstaclePoints += nextPosition.position
        }
        visitedPositions.add(nextPosition)
        currentPosition = nextPosition
    }

    return obstaclePoints.size
}

fun updateMapMarkings(map: Grid2D<MapElement>, current: GuardPosition, next: GuardPosition) {
    var direction: MapElement.PathDirection = MapElement.PathDirection.Horizontal
    if (map.get(current.position) is StartingPosition) {
        return
    }
    if (current.direction != next.direction) {
        direction = MapElement.PathDirection.Change
    } else if (current.direction == Direction2D.North || current.direction == Direction2D.South) {
        direction = MapElement.PathDirection.Vertical
    }
    map.setPoint(current.position, GuardPath(direction))
}

fun resultsInCycle(
    map: Grid2D<MapElement>, visitedPositions: Set<GuardPosition>, startingPosition: GuardPosition
): Boolean {
    var currentPosition = startingPosition
    val newVisitedPositions = visitedPositions.toMutableSet()

    while (!map.isOnEdge(currentPosition.position)) {
        val nextPosition = nextPosition(map, currentPosition)
        updateMapMarkings(map, currentPosition, nextPosition)
        if (newVisitedPositions.contains(nextPosition)) {
            debugMap(map)
            return true
        }
        newVisitedPositions.add(nextPosition)
        currentPosition = nextPosition
    }

    return false
}

tailrec fun nextPosition(input: Grid2D<MapElement>, currentPosition: GuardPosition): GuardPosition {
    val proposed = currentPosition.ahead()
    if (!input.get(proposed).isAnObstacle()) {
        return GuardPosition(proposed, currentPosition.direction)
    }
    return nextPosition(input, currentPosition.turned90())
}

fun getStartingPosition(grid: Grid2D<MapElement>): GuardPosition = grid.elementsMatching { element ->
    element is StartingPosition
}.first().let { position -> GuardPosition(position, (grid.get(position) as StartingPosition).direction) }
