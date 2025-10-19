package utils.grids.twodee

data class Position2D(val point: Point2D, val direction: Direction2D) {
    fun ahead(): Position2D = Position2D(point.toThe(direction), direction)

    fun turnedRight90(): Position2D = Position2D(point, direction.right90())

    fun turnedLeft90(): Position2D = Position2D(point, direction.left90())
}
