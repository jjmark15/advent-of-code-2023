package twenty_four.day7

import parser4k.Parser
import parser4k.commonparsers.Tokens
import parser4k.commonparsers.joinedWith
import parser4k.inOrder
import parser4k.map
import parser4k.oneOrMore
import parser4k.parseWith
import parser4k.str

class InputParser {

    private val numberParser: Parser<Long> = oneOrMore(Tokens.digit).map { it.joinToString("").toLong() }
    private val equationParser: Parser<Equation> =
        inOrder(numberParser, str(": "), numberParser.joinedWith(str(" "))).map { (testValue, _, numbers) ->
            Equation(
                testValue, numbers
            )
        }

    fun parse(lines: List<String>): List<Equation> = lines.map { line -> line.parseWith(equationParser) }
}
