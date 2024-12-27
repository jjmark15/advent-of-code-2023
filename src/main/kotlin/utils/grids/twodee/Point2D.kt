package utils.grids.twodee

data class Point2D(val row: Int, val column: Int) {
    fun toThe(direction: Direction2D, increment: Int = 1): Point2D = when (direction) {
        Direction2D.North -> this.copy(row = row - increment)
        Direction2D.East -> this.copy(column = column + increment)
        Direction2D.South -> this.copy(row = row + increment)
        Direction2D.West -> this.copy(column = column - increment)
        Direction2D.NorthEast -> Point2D(row = row - increment, column = column + increment)
        Direction2D.SouthEast -> Point2D(row = row + increment, column = column + increment)
        Direction2D.SouthWest -> Point2D(row = row + increment, column = column - increment)
        Direction2D.NorthWest -> Point2D(row = row - increment, column = column - increment)
    }

    fun neighbours(): List<Point2D> = listOf(
        this.toThe(Direction2D.North),
        this.toThe(Direction2D.NorthEast),
        this.toThe(Direction2D.East),
        this.toThe(Direction2D.SouthEast),
        this.toThe(Direction2D.South),
        this.toThe(Direction2D.SouthWest),
        this.toThe(Direction2D.West),
        this.toThe(Direction2D.NorthWest)
    )

    fun cardinalNeighbours(): List<Point2D> = listOf(
        this.toThe(Direction2D.North),
        this.toThe(Direction2D.East),
        this.toThe(Direction2D.South),
        this.toThe(Direction2D.West)
    )
}