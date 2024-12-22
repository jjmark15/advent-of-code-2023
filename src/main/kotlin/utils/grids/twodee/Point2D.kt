package utils.grids.twodee

data class Point2D(val row: Int, val column: Int) {
    fun toThe(direction: Direction2D): Point2D {
        return when (direction) {
            Direction2D.North -> this.copy(row = row - 1)
            Direction2D.East -> this.copy(column = column + 1)
            Direction2D.South -> this.copy(row = row + 1)
            Direction2D.West -> this.copy(column = column - 1)
            Direction2D.NorthEast -> Point2D(row = row - 1, column = column + 1)
            Direction2D.SouthEast -> Point2D(row = row + 1, column = column + 1)
            Direction2D.SouthWest -> Point2D(row = row + 1, column = column - 1)
            Direction2D.NorthWest -> Point2D(row = row - 1, column = column - 1)
        }
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