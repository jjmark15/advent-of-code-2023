package twenty_three.day5

import parser4k.Parser
import parser4k.commonparsers.Tokens
import parser4k.commonparsers.joinedWith
import parser4k.commonparsers.token
import parser4k.inOrder
import parser4k.map
import parser4k.oneOrMore
import parser4k.optional
import parser4k.parseWith
import parser4k.str
import parser4k.zeroOrMore

class ParserV2 {
    private val number: Parser<Long> = oneOrMore(Tokens.digit).map { it.joinToString("").toLong() }
    private val aSpace = str(" ")
    private val numberSequence: Parser<List<Long>> = oneOrMoreSeparated(number, aSpace)
    private val word: Parser<String> = oneOrMore(Tokens.letter).map { it.joinToString("") }
    private val linebreak = str("\n")
    private val emptyLine: Parser<String> = inOrder(linebreak, zeroOrMore(aSpace), linebreak).map { "" }

    private val mappingRange: Parser<MappingRange> = numberSequence.map { MappingRange(it[1], it[0], it[2]) }
    private val mappingName: Parser<String> = inOrder(
        word.joinedWith("-").map { it.joinToString("_") }, str(" map:")
    ).map { (name, _) -> name }
    private val mapping: Parser<Mapping> = inOrder(
        mappingName, linebreak, mappingRange.joinedWith(linebreak)
    ).map { (_, _, ranges) -> Mapping(ranges) }

    private val seedsExpr: Parser<List<Long>> =
        inOrder(token("seeds: "), numberSequence).map { (_, numbers) -> numbers }
    private val mappings: Parser<Mappings> =
        mapping.joinedWith(emptyLine).map { Mappings(it[0], it[1], it[2], it[3], it[4], it[5], it[6]) }

    private fun <T> oneOrMoreSeparated(parser: Parser<T>, separator: Parser<Any>): Parser<List<T>> =
        oneOrMore(inOrder(optional(separator), parser)).map { e -> e.map { it.second } }

    fun parsePart1(lines: List<String>): Pair<List<Long>, Mappings> {
        val seeds = lines[0].parseWith(seedsExpr)
        val mappings = lines.subList(2, lines.size).joinToString("\n").parseWith(mappings)

        return Pair(seeds, mappings)
    }
}
