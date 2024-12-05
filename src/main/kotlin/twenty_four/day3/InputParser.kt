package twenty_four.day3

import parser4k.Parser
import parser4k.anyCharExcept
import parser4k.commonparsers.Tokens
import parser4k.inOrder
import parser4k.map
import parser4k.oneOfWithPrecedence
import parser4k.optional
import parser4k.parseWith
import parser4k.skip
import parser4k.skipWrapper
import parser4k.str
import parser4k.zeroOrMore

class InputParser {
    private val filler: Parser<Char> = anyCharExcept('\\')
    private val number: Parser<Long> = inOrder(
        Tokens.digit,
        optional(Tokens.digit),
        optional(Tokens.digit)
    ).map { (first, second, third) -> "${first}${second ?: ""}${third ?: ""}".toLong() }
    private val multiply: Parser<NumberPair> =
        inOrder(str("mul("), number, str(","), number, str(")")).skipWrapper().map { (first, _, second) ->
            NumberPair(
                first, second
            )
        }
    private val operations: Parser<List<NumberPair?>> = zeroOrMore(oneOfWithPrecedence(multiply, filler.skip()))

    fun parse(lines: List<String>): List<NumberPair> = lines.flatMap { it.parseWith(operations) }.filterNotNull()
}