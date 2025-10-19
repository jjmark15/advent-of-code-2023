package twenty_four.day5

import parser4k.Parser
import parser4k.commonparsers.Tokens
import parser4k.commonparsers.joinedWith
import parser4k.inOrder
import parser4k.map
import parser4k.parseWith
import parser4k.str
import utils.lineGroups

class InputParser {

    private val number: Parser<Int> = parser4k.repeat(Tokens.digit, 2, 2).map { it.joinToString("").toInt() }
    private val orderingRule: Parser<PageOrderingRule> =
        inOrder(number, str("|"), number).map { (before, _, after) -> PageOrderingRule(before, after) }
    private val updatePages: Parser<UpdatePages> = number.joinedWith(str(",")).map { pages -> UpdatePages(pages) }

    fun parse(lines: List<String>): PuzzleInput = lines.lineGroups().let { lineGroups ->
        PuzzleInput(lineGroups[0].map { it.parseWith(orderingRule) }, lineGroups[1].map { it.parseWith(updatePages) })
    }
}
