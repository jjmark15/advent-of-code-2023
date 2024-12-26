package twenty_four.day12

import InputParser
import utils.grids.twodee.Grid2D
import utils.toCharList

class InputParserImpl : InputParser<Grid2D<Char>> {
    override fun parse(lines: List<String>): Grid2D<Char> = Grid2D(lines.map { line -> line.toCharList() })
}
