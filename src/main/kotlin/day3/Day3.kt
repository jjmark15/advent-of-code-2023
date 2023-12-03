package day3

fun part1(schematic: List<String>): Int {
    var partNumberSum = 0

    var previousRowNumbers: MutableSet<SchematicElement.PartNumber>
    var currentRowNumbers: MutableSet<SchematicElement.PartNumber> = mutableSetOf()
    var previousRowSymbols: MutableSet<SchematicPosition>
    var currentRowSymbols: MutableSet<SchematicPosition> = mutableSetOf()


    for ((i, row) in schematic.withIndex()) {
        previousRowNumbers = currentRowNumbers
        currentRowNumbers = mutableSetOf()
        previousRowSymbols = currentRowSymbols
        currentRowSymbols = mutableSetOf()

        elementsFrom(row, i).forEach { element ->
            when (element) {
                is SchematicElement.PartNumber -> currentRowNumbers.add(element)
                is SchematicElement.Symbol -> currentRowSymbols.add(element.position)
            }
        }

        val allSymbols = mutableSetOf<SchematicPosition>()
        allSymbols.addAll(previousRowSymbols)
        allSymbols.addAll(currentRowSymbols)

        previousRowNumbers.removeIf { element ->
            val adjacentToSymbol = allSymbols.any { schematicPosition ->
                element.position.isAdjacentTo(schematicPosition)
            }

            if (adjacentToSymbol) partNumberSum += element.value

            adjacentToSymbol
        }

        currentRowNumbers.removeIf { element ->
            val adjacentToSymbol = allSymbols.any { schematicPosition ->
                element.position.isAdjacentTo(schematicPosition)
            }

            if (adjacentToSymbol) partNumberSum += element.value

            adjacentToSymbol
        }
    }

    return partNumberSum
}

private fun elementsFrom(rowString: String, row: Int): List<SchematicElement> {
    val result: MutableList<SchematicElement> = mutableListOf()
    var currentNumberString: StringBuilder = StringBuilder()

    for ((i, character) in rowString.split("").filter { it.isNotEmpty() }.withIndex()) {
        if ("1234567890".contains(character)) {
            currentNumberString.append(character)
        } else if ("." == character) {
            if (currentNumberString.isNotEmpty()) {
                val toString = currentNumberString.toString()
                result.add(SchematicElement.PartNumber(toString.toInt(), positionGivenEnd(toString, i, row)))
            }
            currentNumberString = StringBuilder()
        } else {
            if (currentNumberString.isNotEmpty()) {
                val toString = currentNumberString.toString()
                result.add(SchematicElement.PartNumber(toString.toInt(), positionGivenEnd(toString, i, row)))
            }
            currentNumberString = StringBuilder()
            result.add(SchematicElement.Symbol(SchematicPosition(i, i, row)))
        }
    }

    if (currentNumberString.isNotEmpty()) {
        val toString = currentNumberString.toString()
        result.add(
            SchematicElement.PartNumber(
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

private sealed class SchematicElement() {
    data class PartNumber(val value: Int, val position: SchematicPosition) : SchematicElement()
    data class Symbol(val position: SchematicPosition) : SchematicElement()
}

private data class SchematicPosition(val columnStart: Int, val columnEnd: Int, val row: Int) {
    fun isAdjacentTo(other: SchematicPosition): Boolean {
        return other.columnStart >= columnStart - 1 && other.columnEnd <= columnEnd + 1 && other.row >= row - 1 && other.row <= row + 1
    }
}
