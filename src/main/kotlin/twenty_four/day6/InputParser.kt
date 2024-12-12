package twenty_four.day6

import parser4k.Parser
import parser4k.map
import parser4k.oneOf
import parser4k.oneOrMore
import parser4k.parseWith
import parser4k.str
import utils.Grid2D
import utils.Grid2DDirection

class InputParser {

    private val obstruction: Parser<MapElement.Obstacle> = str("#").map { MapElement.Obstacle }
    private val empty: Parser<MapElement.Empty> = str(".").map { MapElement.Empty }
    private val startingPosition: Parser<MapElement.StartingPosition> = oneOf("^", ">", "<", "v").map { toStartingPosition(it) }
    private val row: Parser<List<MapElement>> = oneOrMore(oneOf(obstruction, empty, startingPosition))

    private fun toStartingPosition(s: String): MapElement.StartingPosition {
        return when (s) {
            "^" -> MapElement.StartingPosition(Grid2DDirection.North)
            ">" -> MapElement.StartingPosition(Grid2DDirection.East)
            "v" -> MapElement.StartingPosition(Grid2DDirection.South)
            "<" -> MapElement.StartingPosition(Grid2DDirection.West)
            else -> TODO()
        }
    }

    fun parse(lines: List<String>): Grid2D<MapElement> = lines.map { line -> line.parseWith(row) }.let(::Grid2D)
}
