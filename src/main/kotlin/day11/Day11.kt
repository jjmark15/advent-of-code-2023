package day11

import utils.Grid2D
import utils.Grid2DPoint

fun part1(input: Grid2D<SpaceElement>): Int {
    val emptyRows = input.rowsAllMatching { spaceElement -> spaceElement == SpaceElement.Empty }
    val emptyColumns = input.columnsAllMatching { spaceElement -> spaceElement == SpaceElement.Empty }

    emptyRows.reversed().forEach { input.duplicateRow(it) }
    emptyColumns.reversed().forEach { input.duplicateColumn(it) }

    val galaxyPositions = input.elementsMatching { spaceElement -> spaceElement == SpaceElement.Galaxy }

    return galaxyPositions.indices.cartesianProduct().filter { (firstIndex, secondIndex) -> secondIndex > firstIndex }
        .sumOf { (firstIndex, secondIndex) ->
            distanceBetween(
                galaxyPositions[firstIndex], galaxyPositions[secondIndex]
            )
        }
}

fun distanceBetween(first: Grid2DPoint, second: Grid2DPoint): Int {
    var steps = 0
    var interimPoint = first

    while (interimPoint.orthogonalDistanceTo(second) > 0) {
        steps++
        val orthogonalDistanceTarget = interimPoint.orthogonalDistanceTo(second) - 1
        interimPoint = interimPoint.orthogonallySurroundingPoints()
            .first { point -> point.orthogonalDistanceTo(second) == orthogonalDistanceTarget }
    }

    return steps
}

fun <T, L : Iterable<T>> L.cartesianProduct(): List<Pair<T, T>> =
    this.flatMap { element -> this.map { Pair(element, it) } }

enum class SpaceElement {
    Empty, Galaxy
}
