package twenty_four.day14

import utils.grids.twodee.Direction2D
import utils.grids.twodee.Grid2D
import utils.grids.twodee.Point2D

fun part1(input: List<PointAndVelocity>, spaceHeight: Int, spaceWidth: Int): Long {
    val robotSecurityPredictor = RobotSecurityPredictor(input, spaceHeight, spaceWidth)
    robotSecurityPredictor.advancePredictionsFor(100)
    return robotSecurityPredictor.countRobotsPerQuadrant().reduce { acc, curr -> acc * curr }
}

fun part2(input: List<PointAndVelocity>, spaceHeight: Int, spaceWidth: Int): Long {
    val robotSecurityPredictor = RobotSecurityPredictor(input, spaceHeight, spaceWidth)
    var seconds: Long = 0

    while (robotSecurityPredictor.largestRegionSize() < 20) {
        robotSecurityPredictor.advancePredictionsFor(1)
        seconds++
    }

    return seconds
}

private class RobotSecurityPredictor(
    private var robots: List<PointAndVelocity>,
    private val spaceHeight: Int,
    private val spaceWidth: Int,
) {
    fun advancePredictionsFor(seconds: Int) {
        robots = robots.map { robot -> PointAndVelocity(robotLocationAfter(seconds, robot), robot.velocity) }
    }

    private fun robotLocationAfter(seconds: Int, robot: PointAndVelocity): Point2D {
        val totalMovedX = robot.velocity.x * seconds
        val totalMovedY = robot.velocity.y * seconds
        return baselinePoint(robot.point.toThe(Direction2D.South, totalMovedY).toThe(Direction2D.East, totalMovedX))
    }

    private fun baselinePoint(point: Point2D): Point2D {
        val newRow = (point.row % spaceHeight).let { if (it < 0) spaceHeight + it else it }
        val newColumn = (point.column % spaceWidth).let { if (it < 0) spaceWidth + it else it }
        return Point2D(newRow, newColumn)
    }

    fun countRobotsPerQuadrant(): List<Long> {
        val midRow = spaceHeight / 2
        val midCol = spaceWidth / 2
        return robots.map { it.point }.filter { (row, col) -> row != midRow && col != midCol }.groupBy { (row, col) ->
            var key = 0
            if (col > midCol) key += 1
            if (row > midRow) key += 2
            key
        }.values.map { value -> value.size.toLong() }
    }

    fun largestRegionSize(): Int {
        val grid: Grid2D<Int> = Grid2D.ofSize(spaceHeight, spaceWidth) { _ -> 0 }
        robots.groupBy { it.point }.forEach { (point, robots) -> grid.setPoint(point, robots.size) }
        val toVisit: ArrayDeque<Point2D> = ArrayDeque<Point2D>().also { it.addAll(robots.map { it.point }) }
        val seen = mutableSetOf<Point2D>()
        var largestRegionSize = 0

        while (toVisit.isNotEmpty()) {
            val point = toVisit.removeFirst()
            if (point in seen) {
                continue
            }
            val region = regionFromPoint(grid, point)
            if (region.size > largestRegionSize) {
                largestRegionSize = region.size
            }
            seen.addAll(region)
        }

        return largestRegionSize
    }

    private fun regionFromPoint(grid: Grid2D<Int>, point: Point2D): List<Point2D> {
        val seen = mutableSetOf<Point2D>()
        val toVisit = ArrayDeque<Point2D>().also { it.add(point) }

        while (toVisit.isNotEmpty()) {
            val point = toVisit.removeFirst()
            if (point in seen || point in toVisit) continue
            seen.add(point)
            toVisit.addAll(
                grid.cardinalNeighbours(point)
                    .filter { !seen.contains(it) && !toVisit.contains(it) && grid.get(it) > 0 })
        }

        return seen.toList()
    }
}

data class RobotVelocity(val x: Int, val y: Int)

data class PointAndVelocity(val point: Point2D, val velocity: RobotVelocity)