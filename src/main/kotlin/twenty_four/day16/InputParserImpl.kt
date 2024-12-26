package twenty_four.day16

import InputParser
import parser4k.Parser
import parser4k.map
import parser4k.oneOf
import parser4k.oneOrMore
import parser4k.parseWith
import parser4k.str
import utils.grids.twodee.Grid2D

class InputParserImpl : InputParser<Grid2D<MazeElement>> {
    private val emptyElementParser: Parser<MazeElement> = str(".").map { MazeElement.Empty }
    private val wallElementParser: Parser<MazeElement> = str("#").map { MazeElement.Wall }
    private val startElementParser: Parser<MazeElement> = str("S").map { MazeElement.Start }
    private val endElementParser: Parser<MazeElement> = str("E").map { MazeElement.End }
    private val mazeElementParser: Parser<MazeElement> =
        oneOf(emptyElementParser, wallElementParser, startElementParser, endElementParser)

    override fun parse(lines: List<String>): Grid2D<MazeElement> =
        Grid2D(lines.map { line -> line.parseWith(oneOrMore(mazeElementParser)) })
}
