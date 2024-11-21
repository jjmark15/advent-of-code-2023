package twenty_three.day11

import utils.Grid2D
import utils.cartesianProduct

fun part1(input: Grid2D<twenty_three.day11.SpaceElement>): Long = twenty_three.day11.part2(input, 2)

fun part2(input: Grid2D<twenty_three.day11.SpaceElement>, expansionFactor: Int): Long {
    val emptyRows = input.rowsAllMatching { spaceElement -> spaceElement == twenty_three.day11.SpaceElement.Empty }
    val emptyColumns = input.columnsAllMatching { spaceElement -> spaceElement == twenty_three.day11.SpaceElement.Empty }

    emptyRows.forEach { input.setRowExpansionFactor(it, expansionFactor) }
    emptyColumns.forEach { input.setColumnExpansionFactor(it, expansionFactor) }

    val galaxyPositions = input.elementsMatching { spaceElement -> spaceElement == twenty_three.day11.SpaceElement.Galaxy }

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
