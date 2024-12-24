package twenty_four.day16

import utils.grids.twodee.Direction2D
import utils.grids.twodee.Grid2D
import utils.grids.twodee.Point2D
import utils.grids.twodee.Position2D
import java.util.*
import java.util.function.BiFunction
import java.util.function.Function

enum class MazeElement {
    Empty, Wall, Start, End
}

fun part1(input: Grid2D<MazeElement>): Long {
    val startPosition = Position2D(input.elementsMatching { e -> e == MazeElement.Start }.first(), Direction2D.East)
    val endPoint = input.elementsMatching { e -> e == MazeElement.End }.first()
    return MazeRouteFinder(input, ::nextPositions).findCheapestRouteCost(startPosition, endPoint)
}

private fun nextPositions(map: Grid2D<MazeElement>, positionAndCost: PositionAndCost): List<PositionAndCost> =
    listOf<PositionAndCost>(
        positionAndCost.moved(Position2D::ahead, 1),
        positionAndCost.moved(Position2D::turnedRight90, 1000),
        positionAndCost.moved(Position2D::turnedLeft90, 1000),
    ).filter { positionAndCost -> map.get(positionAndCost.position.point) != MazeElement.Wall }

private class MazeRouteFinder(
    private val grid: Grid2D<MazeElement>,
    private val nextPositionsFactory: BiFunction<Grid2D<MazeElement>, PositionAndCost, List<PositionAndCost>>
) {
    fun findCheapestRouteCost(startPosition: Position2D, endPoint: Point2D): Long {
        val visitedAndCosts: MutableMap<Position2D, Long> = mutableMapOf()
        val toVisit: PriorityQueue<PositionAndCost> = PriorityQueue(compareBy { it.cost })
        toVisit.add(PositionAndCost(startPosition, 0))

        while (toVisit.isNotEmpty()) {
            val positionAndCost = toVisit.poll()
            if (positionAndCost.position.point == endPoint) return positionAndCost.cost
            if (positionAndCost.cost < (visitedAndCosts.getOrDefault(positionAndCost.position, Long.MAX_VALUE))) {
                visitedAndCosts[positionAndCost.position] = positionAndCost.cost
                toVisit.addAll(
                    nextPositionsFactory.apply(grid, positionAndCost)
                        .filter { positionAndCost -> grid.contains(positionAndCost.position.point) })
            }
        }

        throw Exception("No paths found from start to end")
    }
}

private data class PositionAndCost(val position: Position2D, val cost: Long) {
    fun moved(move: Function<Position2D, Position2D>, additionalCost: Long): PositionAndCost =
        PositionAndCost(move.apply(position), cost + additionalCost)
}