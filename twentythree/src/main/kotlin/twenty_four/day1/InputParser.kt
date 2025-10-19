package twenty_four.day1

import parser4k.Parser
import parser4k.commonparsers.Tokens
import parser4k.inOrder
import parser4k.map
import parser4k.oneOrMore
import parser4k.parseWith
import parser4k.str

class InputParser {
    private val spaces: Parser<String> = oneOrMore(str(" ")).map { it.joinToString("") }
    private val number: Parser<Long> = oneOrMore(Tokens.digit).map { it.joinToString("").toLong() }
    private val line: Parser<Pair<Long, Long>> = inOrder(number, spaces, number).map { Pair(it.first, it.third) }

    fun parse(lines: List<String>): LocationIds =
        lines.map { it.parseWith(line) }.fold(Pair(mutableListOf<Long>(), mutableListOf<Long>())) { acc, line ->
            acc.first.add(line.first)
            acc.second.add(line.second)

            acc
        }.let { LocationIds(it.first, it.second) }
}