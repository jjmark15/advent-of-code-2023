package twenty_three.day8

import com.github.h0tk3y.betterParse.combinators.and
import com.github.h0tk3y.betterParse.combinators.map
import com.github.h0tk3y.betterParse.combinators.skip
import com.github.h0tk3y.betterParse.grammar.Grammar
import com.github.h0tk3y.betterParse.grammar.tryParseToEnd
import com.github.h0tk3y.betterParse.lexer.literalToken
import com.github.h0tk3y.betterParse.lexer.regexToken
import com.github.h0tk3y.betterParse.parser.toParsedOrThrow
import utils.lineGroups

fun parse(lines: List<String>): Pair<Instructions, NetworkMap> {
    val lineGroups = lines.lineGroups()

    val instructions = Instructions(lines[0].map { s ->
        when (s) {
            'L' -> Direction.Left
            else -> Direction.Right
        }
    })

    val mappings = lineGroups[1].map { line ->
        NetworkMapGrammar.tryParseToEnd(line).toParsedOrThrow().value
    }.toMap()

    return Pair(instructions, NetworkMap(mappings))
}

object NetworkMapGrammar : Grammar<Pair<Node, DirectionAlternatives>>() {
    private val ws by regexToken("\\s+", ignore = true)
    private val nodeName by regexToken("\\w{3}")
    private val node by nodeName map { s -> Node(s.text) }
    private val bracket by regexToken("[()]")
    private val comma by literalToken(",")
    private val equals by literalToken("=")
    private val directionAlternatives by skip(bracket) and node and skip(
        comma
    ) and node and skip(bracket) map { (leftNode, rightNode) -> DirectionAlternatives(leftNode, rightNode) }

    override val rootParser by node and skip(equals) and directionAlternatives map { (node, directions) ->
        Pair(node, directions)
    }
}