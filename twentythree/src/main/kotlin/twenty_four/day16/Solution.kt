package twenty_four.day16

import utils.grids.twodee.Direction2D
import utils.grids.twodee.Grid2D
import utils.grids.twodee.MazeRouteFinder
import utils.grids.twodee.Position2D
import utils.grids.twodee.PositionAndCost

enum class MazeElement {
    Empty, Wall, Start, End
}

fun part1(input: Grid2D<MazeElement>): Long {
    val startPosition = Position2D(input.elementsMatching { e -> e == MazeElement.Start }.first(), Direction2D.East)
    val endPoint = input.elementsMatching { e -> e == MazeElement.End }.first()
    return MazeRouteFinder(input, ::nextPositions).findCheapestRouteCost(startPosition, endPoint)
}

fun part2(input: Grid2D<MazeElement>): Int {
    val startPosition = Position2D(input.elementsMatching { e -> e == MazeElement.Start }.first(), Direction2D.East)
    val endPoint = input.elementsMatching { e -> e == MazeElement.End }.first()
    return MazeRouteFinder(input, ::nextPositions).cheapestRoutes(startPosition, endPoint).flatMap { it }
        .map { it.point }.toSet().size
}

private fun nextPositions(map: Grid2D<MazeElement>, positionAndCost: PositionAndCost): List<PositionAndCost> =
    listOf<PositionAndCost>(
        positionAndCost.moved(Position2D::ahead, 1),
        positionAndCost.moved(Position2D::turnedRight90, 1000),
        positionAndCost.moved(Position2D::turnedLeft90, 1000),
    ).filter { positionAndCost -> map.get(positionAndCost.position.point) != MazeElement.Wall }
