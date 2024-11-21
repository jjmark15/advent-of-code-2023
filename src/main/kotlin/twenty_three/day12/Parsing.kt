package twenty_three.day12

import com.github.h0tk3y.betterParse.combinators.*
import com.github.h0tk3y.betterParse.grammar.Grammar
import com.github.h0tk3y.betterParse.grammar.tryParseToEnd
import com.github.h0tk3y.betterParse.lexer.literalToken
import com.github.h0tk3y.betterParse.lexer.regexToken
import com.github.h0tk3y.betterParse.parser.toParsedOrThrow

fun parse(lines: List<String>): List<SpringRow> = lines.fold(
    mutableListOf()
) { rows, line ->
    rows.add(parseLine(line))
    rows
}

private fun parseLine(line: String): SpringRow =
    SpringLineGrammar.tryParseToEnd(line).toParsedOrThrow().value

object SpringLineGrammar : Grammar<SpringRow>() {
    private val ws by regexToken("\\s+", ignore = true)
    private val numberString by regexToken("\\d+")
    private val number by numberString map { s -> s.text.toInt() }
    private val springStateString by regexToken("[#.?]")
    private val springState by springStateString map { s -> parseSpringState(s.text) }
    private val comma by literalToken(",")

    override val rootParser by oneOrMore(springState) and skip(ws) and separatedTerms(
        number, comma, acceptZero = true
    ) map { (states, groups) ->
        SpringRow(states, groups)
    }
}

private fun parseSpringState(s: String): SpringState = when (s) {
    "." -> SpringState.Working
    "#" -> SpringState.Broken
    "?" -> SpringState.Unknown
    else -> throw Exception("Could not parse spring state from $s")
}