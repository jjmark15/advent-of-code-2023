package twenty_four.day10

import utils.Grid2D

class InputParser {
    fun parse(lines: List<String>): Grid2D<Int> =
        Grid2D(lines.map { it.split("").filterNot { it.isEmpty() }.map { it.toInt() } })
}
