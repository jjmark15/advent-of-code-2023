package twenty_four.day14

import utils.grids.twodee.Direction2D
import utils.grids.twodee.Point2D

fun part1(input: List<PointAndVelocity>, spaceHeight: Int, spaceWidth: Int): Long {
    val robotSecurityPredictor = RobotSecurityPredictor(input, spaceHeight, spaceWidth)
    robotSecurityPredictor.advancePredictionsFor(100)
    return robotSecurityPredictor.countRobotsPerQuadrant().reduce { acc, curr -> acc * curr }
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
}

data class RobotVelocity(val x: Int, val y: Int)

data class PointAndVelocity(val point: Point2D, val velocity: RobotVelocity)