package twenty_four.day8

import utils.Grid2D
import utils.toCharList

class InputParser {
    fun parse(lines: List<String>): Grid2D<Frequency?> =
        Grid2D(lines.map { line -> line.toCharList().map { c -> if (c == '.') null else Frequency(c) } })
}
