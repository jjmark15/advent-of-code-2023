package day11

import utils.Grid2D
import utils.cartesianProduct

fun part1(input: Grid2D<SpaceElement>): Int {
    val emptyRows = input.rowsAllMatching { spaceElement -> spaceElement == SpaceElement.Empty }
    val emptyColumns = input.columnsAllMatching { spaceElement -> spaceElement == SpaceElement.Empty }

    emptyRows.forEach { input.setRowExpansionFactor(it, 2) }
    emptyColumns.forEach { input.setColumnExpansionFactor(it, 2) }

    val galaxyPositions = input.elementsMatching { spaceElement -> spaceElement == SpaceElement.Galaxy }

    return galaxyPositions.indices.cartesianProduct().filter { (firstIndex, secondIndex) -> secondIndex > firstIndex }
        .sumOf { (firstIndex, secondIndex) ->
            input.orthogonalDistanceTo(
                galaxyPositions[firstIndex], galaxyPositions[secondIndex]
            )
        }
}

enum class SpaceElement {
    Empty, Galaxy
}
