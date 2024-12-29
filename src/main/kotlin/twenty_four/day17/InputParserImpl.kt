package twenty_four.day17

import InputParser
import parser4k.Parser
import parser4k.commonparsers.Tokens
import parser4k.commonparsers.joinedWith
import parser4k.inOrder
import parser4k.map
import parser4k.oneOf
import parser4k.oneOrMore
import parser4k.parseWith
import parser4k.str
import utils.lineGroups

class InputParserImpl : InputParser<ComputerSetup> {
    private val number: Parser<Int> = oneOrMore(Tokens.digit).map { it.joinToString("").toInt() }
    private val register: Parser<Pair<Char, Long>> =
        inOrder(str("Register "), oneOf('A', 'B', 'C'), str(": "), number).map { (_, name, _, n) -> name to n.toLong() }
    private val program: Parser<List<Int>> =
        inOrder(str("Program: "), number.joinedWith(",")).map { (_, numbers) -> numbers }

    override fun parse(input: List<String>): ComputerSetup {
        val lineGroups = input.lineGroups()
        return ComputerSetup(lineGroups[0].map { it.parseWith(register) }, lineGroups[1].single().parseWith(program))
    }
}
