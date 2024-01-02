package utils

import kotlin.math.max
import kotlin.math.min

open class Grid2D<T>(inner: List<List<T>>) {

    val inner: MutableList<MutableList<T>> = inner.map { row -> row.toMutableList() }.toMutableList()
    private val height: Int get() = inner.size
    private val width: Int get() = inner.firstOrNull()?.size ?: 0

    fun get(point: Grid2DPoint): T = inner[point.row][point.column]

    fun getOrNull(point: Grid2DPoint): T? = if (!contains(point)) {
        null
    } else {
        get(point)
    }

    fun contains(point: Grid2DPoint): Boolean {
        return point.row in 0..<height && point.column in 0..<width
    }

    fun orthogonalNeighbours(point: Grid2DPoint): List<Grid2DPoint> =
        point.orthogonallySurroundingPoints().filter { contains(it) }

    fun neighbours(point: Grid2DPoint): List<Grid2DPoint> = point.surroundingPoints().filter { contains(it) }

    fun rowsAllMatching(matcher: (T) -> Boolean): List<Int> =
        inner.withIndex().filter { (_, row) -> row.all { element -> matcher.invoke(element) } }
            .map { (index, _) -> index }

    fun columnsAllMatching(matcher: (T) -> Boolean): List<Int> = inner.fold(List(width) { true }) { columns, row ->
        columns.zip(row).map { (matches, element) ->
            matches && matcher.invoke(element)
        }
    }.withIndex().filter { (_, matches) -> matches }.map { (index, _) -> index }

    fun elementsMatching(matcher: (T) -> Boolean): List<Grid2DPoint> = inner.mapIndexed { rowIndex, row ->
        row.mapIndexedNotNull { columnIndex, element ->
            if (matcher.invoke(element)) {
                Grid2DPoint(rowIndex, columnIndex)
            } else {
                null
            }
        }
    }.flatten()

    fun duplicateRow(rowIndex: Int) {
        val newRow = inner[rowIndex]
        inner.add(rowIndex, newRow)
    }

    fun duplicateColumn(columnIndex: Int) {
        inner.forEach { row ->
            val newElement = row[columnIndex]
            row.add(columnIndex, newElement)
        }
    }
}

enum class Grid2DDirection {
    North, NorthEast, East, SouthEast, South, SouthWest, West, NorthWest;

    fun opposite(): Grid2DDirection {
        return when (this) {
            North -> South
            NorthEast -> SouthWest
            East -> West
            SouthEast -> NorthWest
            South -> North
            SouthWest -> NorthEast
            West -> East
            NorthWest -> SouthEast
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
            Grid2DDirection.NorthEast -> Grid2DPoint(row = row - 1, column = column + 1)
            Grid2DDirection.SouthEast -> Grid2DPoint(row = row + 1, column = column + 1)
            Grid2DDirection.SouthWest -> Grid2DPoint(row = row + 1, column = column - 1)
            Grid2DDirection.NorthWest -> Grid2DPoint(row = row - 1, column = column - 1)
        }
    }

    fun orthogonalDistanceTo(other: Grid2DPoint): Int {
        val verticalDistance = max(row, other.row) - min(row, other.row)
        val horizontalDistance = max(column, other.column) - min(column, other.column)
        return verticalDistance + horizontalDistance
    }

    fun surroundingPoints(): List<Grid2DPoint> = listOf(
        this.toThe(Grid2DDirection.North),
        this.toThe(Grid2DDirection.NorthEast),
        this.toThe(Grid2DDirection.East),
        this.toThe(Grid2DDirection.SouthEast),
        this.toThe(Grid2DDirection.South),
        this.toThe(Grid2DDirection.SouthWest),
        this.toThe(Grid2DDirection.West),
        this.toThe(Grid2DDirection.NorthWest)
    )

    fun orthogonallySurroundingPoints(): List<Grid2DPoint> = listOf(
        this.toThe(Grid2DDirection.North),
        this.toThe(Grid2DDirection.East),
        this.toThe(Grid2DDirection.South),
        this.toThe(Grid2DDirection.West)
    )
}