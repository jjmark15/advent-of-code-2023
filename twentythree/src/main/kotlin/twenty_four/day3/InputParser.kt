package twenty_four.day3

import parser4k.Parser
import parser4k.anyCharExcept
import parser4k.commonparsers.Tokens
import parser4k.inOrder
import parser4k.map
import parser4k.oneOf
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
    private val multiply: Parser<Statement.Multiply> =
        inOrder(str("mul("), number, str(","), number, str(")")).skipWrapper().map { (first, _, second) ->
            Statement.Multiply(
                first, second
            )
        }
    private val doStatement: Parser<Statement.Do> = str("do()").map { Statement.Do }
    private val dontStatement: Parser<Statement.Dont> = str("don't()").map { Statement.Dont }
    private val statement: Parser<Statement> = oneOf(multiply, doStatement, dontStatement)

    private val statements: Parser<List<Statement?>> = zeroOrMore(oneOfWithPrecedence(statement, filler.skip()))

    fun parse(lines: List<String>): List<Statement> = lines.flatMap { it.parseWith(statements) }.filterNotNull()
}