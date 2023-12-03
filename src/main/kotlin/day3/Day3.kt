package day3

fun part1(schematic: List<String>): Int {
    var partNumberSum = 0

    val symbolPositions: Set<SchematicPosition> = schematic.withIndex().flatMap { (rowIndex, row) ->
        row.split("").filter { it.isNotEmpty() }.withIndex().mapNotNull { (columnIndex, character) ->
            if (isASymbol(character)) {
                SchematicPosition(columnIndex, columnIndex, rowIndex)
            } else {
                null
            }
        }
    }.toSet()

    for ((i, row) in schematic.withIndex()) {
        partNumberSum += numbersFrom(row, i).filter { number ->
            symbolPositions.any { position -> position.isAdjacentTo(number.position) }
        }.sumOf { it.value }
    }

    return partNumberSum
}

fun part2(schematic: List<String>): Int {
    val numbers = schematic.withIndex().flatMap { (rowIndex, rowString) ->
        numbersFrom(rowString, rowIndex)
    }

    return schematic.withIndex().flatMap { (rowIndex, rowString) ->
        rowString.split("").filter { it.isNotEmpty() }.withIndex().filter { (_, character) -> character == "*" }
            .map { (columnIndex, character) ->
                val position = positionGivenEnd(character, columnIndex, rowIndex)
                val adjacentNumbers = numbers.filter { number -> number.position.isAdjacentTo(position) }
                if (adjacentNumbers.size == 2) {
                    return@map adjacentNumbers[0].value * adjacentNumbers[1].value
                }
                0
            }
    }.sum()
}

private fun isASymbol(s: String): Boolean {
    return !".1234567890".contains(s)
}

private fun numbersFrom(rowString: String, row: Int): List<SchemaPartNumber> {
    val result: MutableList<SchemaPartNumber> = mutableListOf()
    var currentNumberString: StringBuilder = StringBuilder()

    for ((i, character) in rowString.split("").filter { it.isNotEmpty() }.withIndex()) {
        if ("1234567890".contains(character)) {
            currentNumberString.append(character)
        } else {
            if (currentNumberString.isNotEmpty()) {
                val toString = currentNumberString.toString()
                result.add(
                    SchemaPartNumber(
                        toString.toInt(), positionGivenEnd(toString, i - 1, row)
                    )
                )
            }
            currentNumberString = StringBuilder()
        }
    }

    if (currentNumberString.isNotEmpty()) {
        val toString = currentNumberString.toString()
        result.add(
            SchemaPartNumber(
                toString.toInt(), positionGivenEnd(toString, rowString.length - 1, row)
            )
        )
    }

    return result
}

private fun positionGivenEnd(s: String, columnEnd: Int, row: Int): SchematicPosition {
    val length = s.length
    return SchematicPosition(columnEnd + 1 - length, columnEnd, row)
}

private data class SchemaPartNumber(val value: Int, val position: SchematicPosition)

private data class SchematicPosition(val columnStart: Int, val columnEnd: Int, val row: Int) {
    fun isAdjacentTo(other: SchematicPosition): Boolean {
        return adjacentPoints().any { point -> other.points().contains(point) }
    }

    private fun adjacentPoints() = ((columnStart - 1)..(columnEnd + 1)).flatMap { column ->
        listOf(Point(column, row - 1), Point(column, row), Point(column, row + 1))
    }

    fun contains(point: Point): Boolean {
        return row == point.row && columnStart <= point.column && columnEnd >= point.column
    }

    fun points(): List<Point> {
        return (columnStart..columnEnd).map { Point(it, row) }
    }
}

data class Point(val column: Int, val row: Int)
