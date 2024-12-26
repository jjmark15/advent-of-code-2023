package utils.grids.twodee

import utils.map2D
import java.util.function.BiConsumer
import java.util.function.BiFunction
import java.util.function.Consumer
import java.util.function.Function
import java.util.function.Predicate
import kotlin.math.max
import kotlin.math.min

open class Grid2D<T>(cells: List<List<T>>) {
    val inner: MutableList<MutableList<T>> = cells.map { row -> row.toMutableList() }.toMutableList()
    private val height: Int get() = inner.size
    private val width: Int get() = inner.firstOrNull()?.size ?: 0
    private val rowExpansionFactors: MutableMap<Int, Int> = mutableMapOf()
    private val columnExpansionFactors: MutableMap<Int, Int> = mutableMapOf()

    fun get(point: Point2D): T = inner[point.row][point.column]

    fun getOrNull(point: Point2D): T? = if (contains(point)) {
        get(point)
    } else {
        null
    }

    fun copyWith(point: Point2D, modifier: Function<T, T>): Grid2D<T> {
        val newInner = inner.map { row -> row.toMutableList() }
        newInner[point.row][point.column] = modifier.apply(newInner[point.row][point.column])
        return Grid2D(newInner.map { row -> row.toList() })
    }

    fun contains(point: Point2D): Boolean = point.row in 0..<height && point.column in 0..<width

    fun cardinalNeighbours(point: Point2D): List<Point2D> = point.cardinalNeighbours().filter { contains(it) }

    fun neighbours(point: Point2D): List<Point2D> = point.neighbours().filter { contains(it) }

    fun rowsAllMatching(matcher: (T) -> Boolean): List<Int> =
        inner.withIndex().filter { (_, row) -> row.all { element -> matcher.invoke(element) } }
            .map { (index, _) -> index }

    fun columnsAllMatching(matcher: (T) -> Boolean): List<Int> = inner.fold(List(width) { true }) { columns, row ->
        columns.zip(row).map { (matches, element) ->
            matches && matcher.invoke(element)
        }
    }.withIndex().filter { (_, matches) -> matches }.map { (index, _) -> index }

    fun elementsMatching(matcher: (T) -> Boolean): List<Point2D> = inner.mapIndexed { rowIndex, row ->
        row.mapIndexedNotNull { columnIndex, element ->
            if (matcher.invoke(element)) {
                Point2D(rowIndex, columnIndex)
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

    fun orthogonalDistanceTo(point: Point2D, other: Point2D): Long {
        val verticalDistance = ascendingNumberRange(point.row, other.row).sumOf { rowExpansionFactor(it).toLong() } - 1
        val horizontalDistance =
            ascendingNumberRange(point.column, other.column).sumOf { columnExpansionFactor(it).toLong() } - 1
        return verticalDistance + horizontalDistance
    }

    fun <R> mapPoints(mapper: Function<Point2D, R>): List<R> = inner.flatMapIndexed { rowIndex: Int, cells: List<T> ->
        List(cells.size) { columnIndex ->
            Point2D(
                rowIndex, columnIndex
            )
        }
    }.map { point -> mapper.apply(point) }

    fun <R> map(mapper: Function<T, R>): List<List<R>> = inner.map2D { mapper.apply(it) }

    fun <R> mapIndexed(mapper: BiFunction<Point2D, T, R>): List<List<R>> = inner.mapIndexed { rowIndex, row ->
        row.mapIndexed { columnIndex, value -> mapper.apply(Point2D(rowIndex, columnIndex), value) }
    }

    fun forEach(consumer: Consumer<T>) {
        inner.flatten().forEach(consumer)
    }

    fun forEachIndexed(consumer: BiConsumer<Point2D, T>) {
        inner.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { columnIndex, value ->
                consumer.accept(
                    Point2D(
                        rowIndex, columnIndex
                    ), value
                )
            }
        }
    }

    fun <R> mapIndexedNotNull(mapper: BiFunction<Point2D, T, R?>): List<List<R>> = inner.mapIndexed { rowIndex, row ->
        row.mapIndexedNotNull() { columnIndex, value -> mapper.apply(Point2D(rowIndex, columnIndex), value) }
    }

    fun count(predicate: Predicate<T>): Int = inner.sumOf { row -> row.count { predicate.test(it) } }

    fun pointsInDirection(from: Point2D, count: Int, direction: Direction2D): List<Point2D> =
        (0..<count).fold(mutableListOf(from)) { points, _ ->
            points.add(points.last().toThe(direction))
            points
        }.take(count)

    fun isOnEdge(point: Point2D): Boolean =
        point.row == 0 || point.row == height - 1 || point.column == 0 || point.column == width - 1

    fun setPoint(position: Point2D, value: T) {
        inner[position.row][position.column] = value
    }

    fun debugDisplay(valueDisplay: Function<T, String>): String =
        inner.joinToString("\n") { it.joinToString("", transform = valueDisplay::apply) }

    fun debugDisplay(valueDisplay: BiFunction<Point2D, T, String>): String = inner.mapIndexed { rowIndex, row ->
        row.mapIndexed { columnIndex, value ->
            valueDisplay.apply(
                Point2D(
                    rowIndex, columnIndex
                ), value
            )
        }.joinToString("")
    }.joinToString("\n")

    private fun ascendingNumberRange(a: Int, b: Int): IntRange = IntRange(
        min(a, b), max(a, b)
    )
}