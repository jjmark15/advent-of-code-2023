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
            symbolPositions.any { position -> number.position.isAdjacentTo(position) }
        }.sumOf { it.value }
    }

    return partNumberSum
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
                        toString.toInt(), positionGivenEnd(toString, i, row)
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
                toString.toInt(), positionGivenEnd(toString, rowString.length, row)
            )
        )
    }

    return result
}

private fun positionGivenEnd(s: String, columnEnd: Int, row: Int): SchematicPosition {
    val length = s.length
    return SchematicPosition(columnEnd - length, columnEnd - 1, row)
}

private data class SchemaPartNumber(val value: Int, val position: SchematicPosition)

private data class SchematicPosition(val columnStart: Int, val columnEnd: Int, val row: Int) {
    fun isAdjacentTo(other: SchematicPosition): Boolean {
        return other.columnStart >= columnStart - 1 && other.columnEnd <= columnEnd + 1 && other.row >= row - 1 && other.row <= row + 1
    }
}
