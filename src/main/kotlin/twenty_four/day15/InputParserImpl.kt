package twenty_four.day15

import InputParser
import parser4k.Parser
import parser4k.map
import parser4k.oneOf
import parser4k.oneOrMore
import parser4k.parseWith
import utils.grids.twodee.Direction2D
import utils.grids.twodee.Grid2D
import utils.lineGroups

class InputParserImpl : InputParser<ProblemInput> {
    private val mapElement: Parser<MapElement> = oneOf('#', '.', 'O', '@').map {
        when (it) {
            '#' -> MapElement.Wall
            '.' -> MapElement.Empty
            '@' -> MapElement.Robot
            'O' -> MapElement.Box
            else -> TODO()
        }
    }
    private val mapLine: Parser<List<MapElement>> = oneOrMore(mapElement)
    private val movement: Parser<Direction2D> = oneOf('^', '>', 'v', '<').map {
        when (it) {
            '^' -> Direction2D.North
            '>' -> Direction2D.East
            'v' -> Direction2D.South
            '<' -> Direction2D.West
            else -> TODO()
        }
    }
    private val movements: Parser<List<Direction2D>> = oneOrMore(movement)

    override fun parse(lines: List<String>): ProblemInput {
        val lineGroups = lines.lineGroups()

        val grid: Grid2D<MapElement> = Grid2D(lineGroups[0].map { line -> line.parseWith(mapLine) })
        val intendedMovements: List<Direction2D> = lineGroups[1].flatMap { line -> line.parseWith(movements) }
        return ProblemInput(grid, intendedMovements)
    }
}
