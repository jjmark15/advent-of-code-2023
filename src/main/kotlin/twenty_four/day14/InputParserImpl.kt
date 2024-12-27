package twenty_four.day14

import InputParser
import parser4k.Parser
import parser4k.commonparsers.Tokens
import parser4k.inOrder
import parser4k.map
import parser4k.oneOf
import parser4k.oneOrMore
import parser4k.optional
import parser4k.parseWith
import parser4k.str
import utils.grids.twodee.Point2D

class InputParserImpl : InputParser<List<PointAndVelocity>> {
    val number: Parser<Int> = inOrder(optional(str("-")), oneOrMore(Tokens.number)).map { (negative, digits) ->
        (negative?.let { -1 } ?: 1) * digits.joinToString("").toInt()
    }
    val coordinate: Parser<Pair<Int, Int>> =
        inOrder(oneOf('v', 'p'), str("="), number, str(","), number).map { (_, _, first, _, second) -> first to second }
    val pointAndVelocity: Parser<PointAndVelocity> =
        inOrder(coordinate, str(" "), coordinate).map { (point, _, velocity) ->
            PointAndVelocity(
                Point2D(point.second, point.first), RobotVelocity(velocity.first, velocity.second)
            )
        }

    override fun parse(lines: List<String>) = lines.map { line -> line.parseWith(pointAndVelocity) }
}
