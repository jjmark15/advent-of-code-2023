package twenty_three.day2

import com.github.h0tk3y.betterParse.combinators.and
import com.github.h0tk3y.betterParse.combinators.map
import com.github.h0tk3y.betterParse.combinators.separatedTerms
import com.github.h0tk3y.betterParse.combinators.skip
import com.github.h0tk3y.betterParse.grammar.Grammar
import com.github.h0tk3y.betterParse.grammar.tryParseToEnd
import com.github.h0tk3y.betterParse.lexer.literalToken
import com.github.h0tk3y.betterParse.lexer.regexToken
import com.github.h0tk3y.betterParse.parser.Parser
import com.github.h0tk3y.betterParse.parser.toParsedOrThrow
import loadData
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class Day2Test {
    @Test
    fun `part 1 long`() {
        expectThat(part1(data(), 12, 13, 14)) isEqualTo 2439
    }

    @Test
    fun `part 1 short`() {
        expectThat(part1(shortData(), 12, 13, 14)) isEqualTo 8
    }

    @Test
    fun `part 2 long`() {
        expectThat(part2(data())) isEqualTo 63711
    }

    @Test
    fun `part 2 short`() {
        expectThat(part2(shortData())) isEqualTo 2286
    }

    private fun data(modifier: String? = null): List<Game> =
        loadData(2023, 2, modifier).map { line -> GameGrammar.tryParseToEnd(line).toParsedOrThrow().value }

    private fun shortData() = data("short")
}

object GameGrammar : Grammar<Game>() {
    private val gameKeyword by literalToken("Game")
    private val ws by regexToken("\\s+", ignore = true)
    private val digit by regexToken("\\d+")
    private val colon by literalToken(":", ignore = true)
    private val semicolon by literalToken(";")
    private val comma by literalToken(",")
    private val integer by digit map { id -> id.text.toInt() }
    private val colour by regexToken("blue|green|red")
    private val gameId by skip(gameKeyword) and integer and skip(colon)
    private val cubeColourCount by integer and colour map { (count, colour) ->
        CubeColourCount(
            parseColour(colour.text), count
        )
    }
    private val cubeDraw by separatedTerms(cubeColourCount, comma) map { counts ->
        CubeDraw(
            counts.filter { it.colour == CubeColour.Red }.sumOf { it.quantity },
            counts.filter { it.colour == CubeColour.Green }.sumOf { it.quantity },
            counts.filter { it.colour == CubeColour.Blue }.sumOf { it.quantity }
        )
    }

    override val rootParser: Parser<Game> by gameId and separatedTerms(cubeDraw, semicolon) map { (id, cubeDraws) ->
        Game(id, cubeDraws)
    }

    private fun parseColour(s: String): CubeColour {
        return when (s) {
            "red" -> CubeColour.Red
            "green" -> CubeColour.Green
            "blue" -> CubeColour.Blue
            else -> throw Exception("could not parse colour from $s")
        }
    }
}
