package utils

class Grid2D<T>(val inner: List<List<T>>) {
    fun get(point: Grid2DPoint): T = inner[point.row][point.column]

    fun getOrNull(point: Grid2DPoint): T? = if (!contains(point)) {
        null
    } else {
        get(point)
    }

    fun contains(point: Grid2DPoint): Boolean {
        val height = inner.size
        val width = inner.firstOrNull()?.size ?: 0

        return point.row in 0..<height && point.column in 0..<width
    }

    fun orthogonalNeighbours(point: Grid2DPoint): List<Grid2DPoint> = listOf(
        point.toThe(Grid2DDirection.North),
        point.toThe(Grid2DDirection.East),
        point.toThe(Grid2DDirection.South),
        point.toThe(Grid2DDirection.West),
    ).filter { contains(it) }
}

enum class Grid2DDirection {
    North, South, East, West;

    fun opposite(): Grid2DDirection {
        return when (this) {
            North -> South
            South -> North
            East -> West
            West -> East
        }
    }
}

data class Grid2DPoint(val row: Int, val column: Int) {
    fun toThe(direction: Grid2DDirection): Grid2DPoint {
        return when (direction) {
            Grid2DDirection.North -> this.copy(row = row - 1)
            Grid2DDirection.East -> this.copy(column = column + 1)
            Grid2DDirection.South -> this.copy(row = row + 1)
            Grid2DDirection.West -> this.copy(column = column - 1)
        }
    }
}