package utils

import java.util.function.Function
import kotlin.math.max
import kotlin.math.min

open class Grid2D<T>(val inner: List<List<T>>) {
    private val height: Int get() = inner.size
    private val width: Int get() = inner.firstOrNull()?.size ?: 0
    private val rowExpansionFactors: MutableMap<Int, Int> = mutableMapOf()
    private val columnExpansionFactors: MutableMap<Int, Int> = mutableMapOf()

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

    fun setRowExpansionFactor(rowIndex: Int, expansionFactor: Int) {
        rowExpansionFactors[rowIndex] = expansionFactor
    }

    fun setColumnExpansionFactor(columnIndex: Int, expansionFactor: Int) {
        columnExpansionFactors[columnIndex] = expansionFactor
    }

    private fun rowExpansionFactor(rowIndex: Int): Int = rowExpansionFactors.getOrDefault(rowIndex, 1)

    private fun columnExpansionFactor(columnIndex: Int): Int = columnExpansionFactors.getOrDefault(columnIndex, 1)

    fun orthogonalDistanceTo(point: Grid2DPoint, other: Grid2DPoint): Long {
        val verticalDistance = ascendingNumberRange(point.row, other.row).sumOf { rowExpansionFactor(it).toLong() } - 1
        val horizontalDistance =
            ascendingNumberRange(point.column, other.column).sumOf { columnExpansionFactor(it).toLong() } - 1
        return verticalDistance + horizontalDistance
    }

    fun <R> mapPoints(mapper: Function<Grid2DPoint, R>): List<R> =
        inner.flatMapIndexed { rowIndex: Int, cells: List<T> ->
            List(cells.size) { columnIndex ->
                Grid2DPoint(
                    rowIndex, columnIndex
                )
            }
        }.map { point -> mapper.apply(point) }

    fun cellsInDirection(from: Grid2DPoint, count: Int, direction: Grid2DDirection): List<Grid2DPoint> {
        val cells = mutableListOf(from)

        for (i in 1 until count) {
            cells.add(cells.last().toThe(direction))
        }

        return cells
    }

    fun isOnEdge(point: Grid2DPoint): Boolean {
        return point.row == 0 || point.row == height - 1 || point.column == 0 || point.column == width - 1
    }
}

fun <T: DebugDisplay> Grid2D<T>.debugDisplay(): String {
    return inner.joinToString("\n") { it.joinToString("") { element -> element.display() } }
}

private fun ascendingNumberRange(a: Int, b: Int): IntRange = IntRange(
    min(a, b), max(a, b)
)

sealed interface Grid2DDirection {
    data object NorthEast : Grid2DDirection
    data object SouthEast : Grid2DDirection
    data object SouthWest : Grid2DDirection
    data object NorthWest : Grid2DDirection
    data object North : OrthogonalGrid2DDirection
    data object East : OrthogonalGrid2DDirection
    data object South : OrthogonalGrid2DDirection
    data object West : OrthogonalGrid2DDirection

    fun opposite(): Grid2DDirection = when (this) {
        North -> South
        NorthEast -> SouthWest
        East -> West
        SouthEast -> NorthWest
        South -> North
        SouthWest -> NorthEast
        West -> East
        NorthWest -> SouthEast
    }

    fun right90(): Grid2DDirection = when (this) {
        North -> East
        East -> South
        South -> West
        West -> North
        else -> TODO()
    }

    sealed interface OrthogonalGrid2DDirection : Grid2DDirection
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