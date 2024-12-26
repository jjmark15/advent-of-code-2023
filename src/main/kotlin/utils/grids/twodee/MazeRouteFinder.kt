package utils.grids.twodee

import java.util.*
import java.util.function.BiFunction

class MazeRouteFinder<T>(
    private val grid: Grid2D<T>,
    private val nextPositionsFactory: BiFunction<Grid2D<T>, PositionAndCost, List<PositionAndCost>>
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

    fun cheapestRoutes(startPosition: Position2D, endPoint: Point2D): List<List<Position2D>> {
        val visitedAndCosts: MutableMap<Position2D, Long> = mutableMapOf()
        val toVisit: PriorityQueue<RouteHistory> = PriorityQueue(compareBy { it.cost })
        toVisit.add(RouteHistory(mutableListOf(PositionAndCost(startPosition, 0))))
        var cheapestCost: Long? = null
        val cheapestRoutes = mutableSetOf<List<Position2D>>()

        while (toVisit.isNotEmpty()) {
            val route = toVisit.poll()
            if (cheapestCost != null && route.cost > cheapestCost) return cheapestRoutes.toList()
            if (route.last.position.point == endPoint) {
                if (cheapestCost == null) cheapestCost = route.cost
                if (route.cost == cheapestCost) cheapestRoutes.add(route.positions.map { it.position })
            }
            if (route.cost <= (visitedAndCosts.getOrDefault(route.last.position, Long.MAX_VALUE))) {
                visitedAndCosts[route.last.position] = route.cost
                toVisit.addAll(
                    nextPositionsFactory.apply(grid, route.last)
                        .filter { positionAndCost -> grid.contains(positionAndCost.position.point) }
                        .map { positionAndCost -> route.appendedWith(positionAndCost) })
            }
        }

        return cheapestRoutes.toList()
    }

    private data class RouteHistory(val positions: List<PositionAndCost>) {
        val cost get(): Long = positions.last().cost

        val last get() = positions.last()

        fun appendedWith(positionAndCost: PositionAndCost): RouteHistory = RouteHistory(positions + positionAndCost)
    }
}