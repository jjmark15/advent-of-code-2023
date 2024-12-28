package twenty_four.day15

import utils.grids.twodee.Direction2D
import utils.grids.twodee.Grid2D
import utils.grids.twodee.Point2D

data class ProblemInput(val warehouseMap: Grid2D<MapElement>, val intendedMovements: List<Direction2D>)

enum class MapElement {
    Box, Wall, Robot, Empty
}

fun part1(input: ProblemInput): Long {
    val (warehouseMap, intendedMovements) = input
    val robotLocation: Point2D = warehouseMap.elementsMatching { it == MapElement.Robot }.single()
    val warehouseManager = WarehouseManager(warehouseMap)
    warehouseManager.moveRobot(robotLocation, intendedMovements)
    return warehouseManager.boxCoordinateSum()
}

private class WarehouseManager(private val map: Grid2D<MapElement>) {
    fun moveRobot(robotLocation: Point2D, instructions: List<Direction2D>) {
        instructions.fold(robotLocation) { robotLocation, instruction ->
            tryToMoveRobot(robotLocation, instruction) ?: robotLocation
        }
    }

    private fun tryToMoveRobot(robotLocation: Point2D, instruction: Direction2D): Point2D? {
        val elementStack: MutableList<Point2D> = mutableListOf(robotLocation)

        while (elementStack.isNotEmpty()) {
            val point = elementStack.removeLast()
            val value = map.get(point)
            val nextPoint = point.toThe(instruction)
            when (map.get(nextPoint)) {
                MapElement.Wall -> return null
                MapElement.Box -> {
                    elementStack.add(point)
                    elementStack.add(nextPoint)
                }
                MapElement.Empty -> {
                    map.setPoint(point, MapElement.Empty)
                    map.setPoint(nextPoint, value)
                }
                MapElement.Robot -> TODO()
            }
        }

        return robotLocation.toThe(instruction)
    }

    fun boxCoordinateSum(): Long = map.mapIndexed { point, element ->
        if (element == MapElement.Box) {
            (point.row * 100 + point.column).toLong()
        } else {
            0L
        }
    }.flatten().sum()
}