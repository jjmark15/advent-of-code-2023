package twenty_four.day15

import utils.grids.twodee.Direction2D
import utils.grids.twodee.Grid2D
import utils.grids.twodee.Point2D
import java.util.*

data class ProblemInput(
    val warehouseMap: Grid2D<MapElement>, val intendedMovements: List<Direction2D.CardinalDirection2D>
)

enum class MapElement {
    Box, Wall, Robot, Empty, LeftBoxHalf, RightBoxHalf
}

fun part1(input: ProblemInput): Long {
    val (warehouseMap, intendedMovements) = input
    val robotLocation: Point2D = warehouseMap.elementsMatching { it == MapElement.Robot }.single()
    val warehouseManager = WarehouseManager(warehouseMap)
    warehouseManager.moveRobot(robotLocation, intendedMovements)
    return warehouseManager.boxCoordinateSum()
}

fun part2(input: ProblemInput): Long {
    val (warehouseMap, intendedMovements) = input.let { (warehouseMap, intendedMovements) -> expandMap(warehouseMap) to intendedMovements }
    val robotLocation: Point2D = warehouseMap.elementsMatching { it == MapElement.Robot }.single()
    val warehouseManager = WarehouseManager(warehouseMap)
    warehouseManager.moveRobot(robotLocation, intendedMovements)
    return warehouseManager.boxCoordinateSum()
}

private fun expandMap(map: Grid2D<MapElement>): Grid2D<MapElement> = Grid2D(map.flatMap { element ->
    when (element) {
        MapElement.Box -> listOf(MapElement.LeftBoxHalf, MapElement.RightBoxHalf)
        MapElement.Wall -> listOf(MapElement.Wall, MapElement.Wall)
        MapElement.Robot -> listOf(MapElement.Robot, MapElement.Empty)
        MapElement.Empty -> listOf(MapElement.Empty, MapElement.Empty)
        MapElement.LeftBoxHalf, MapElement.RightBoxHalf -> TODO()
    }
})

private class WarehouseManager(private val map: Grid2D<MapElement>) {
    fun moveRobot(robotLocation: Point2D, instructions: List<Direction2D.CardinalDirection2D>) {
        instructions.fold(robotLocation) { robotLocation, instruction ->
            tryToMoveRobot(robotLocation, instruction) ?: robotLocation
        }
    }

    private fun tryToMoveRobot(robotLocation: Point2D, instruction: Direction2D.CardinalDirection2D): Point2D? {
        val toVisit = PriorityQueue<Point2D>(compareBy { coordinatePriorityInDirection(it, instruction) }).apply {
            add(robotLocation)
        }
        val moves = mutableListOf<Point2D>()

        while (toVisit.isNotEmpty()) {
            val point = toVisit.poll()
            moves.add(point)
            val nextPoint = point.toThe(instruction)
            val nextValue = map.get(nextPoint)

            when (nextValue) {
                MapElement.Wall -> return null
                MapElement.Empty -> continue
                MapElement.Box, MapElement.LeftBoxHalf, MapElement.RightBoxHalf -> toVisit.addAll(
                    pointsLinkedTo(
                        nextPoint
                    ).filter { !toVisit.contains(it) && !moves.contains(it) })

                MapElement.Robot -> TODO()
            }
        }

        moves.reversed().forEach { point -> movePointInDirection(point, instruction) }

        return robotLocation.toThe(instruction)
    }

    private fun coordinatePriorityInDirection(point: Point2D, direction: Direction2D.CardinalDirection2D): Int =
        when (direction) {
            Direction2D.East -> point.column
            Direction2D.North -> -1 * point.row
            Direction2D.South -> point.row
            Direction2D.West -> -1 * point.column
        }

    private fun movePointInDirection(point: Point2D, direction: Direction2D) {
        map.setPoint(point.toThe(direction), map.get(point))
        map.setPoint(point, MapElement.Empty)
    }

    private fun pointsLinkedTo(point: Point2D): List<Point2D> = when (map.get(point)) {
        MapElement.Box, MapElement.Wall, MapElement.Robot, MapElement.Empty -> listOf(point)
        MapElement.LeftBoxHalf -> listOf(point, point.toThe(Direction2D.East))
        MapElement.RightBoxHalf -> listOf(point.toThe(Direction2D.West), point)
    }

    fun boxCoordinateSum(): Long = map.mapIndexed { point, element ->
        when (element) {
            MapElement.Box, MapElement.LeftBoxHalf -> (point.row * 100 + point.column).toLong()
            else -> 0L
        }
    }.flatten().sum()
}