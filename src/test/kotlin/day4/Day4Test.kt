package day4

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

class Day4Test {
    @Test
    fun `part 1 long`() {
        expectThat(part1(longData())) isEqualTo 22897
    }

    @Test
    fun `part 1 short`() {
        expectThat(part1(shortData())) isEqualTo 13
    }

    @Test
    fun `part 2 long`() {
        expectThat(part2(longData())) isEqualTo 5095824
    }

    @Test
    fun `part 2 short`() {
        expectThat(part2(shortData())) isEqualTo 30
    }

    private fun longData(modifier: String? = null): List<ScratchCard> =
        loadData(4, modifier).map { line -> ScratchCardGrammar.tryParseToEnd(line).toParsedOrThrow().value }

    private fun shortData() = longData("short")
}

object ScratchCardGrammar : Grammar<ScratchCard>() {
    private val cardKeyword by literalToken("Card")
    private val ws by regexToken("\\s+", ignore = true)
    private val digit by regexToken("\\d+")
    private val integer by digit map { id -> id.text.toInt() }
    private val colon by literalToken(":", ignore = true)
    private val bar by literalToken("|")
    private val cardId by skip(cardKeyword) and integer and skip(colon)

    override val rootParser: Parser<ScratchCard> by cardId and separatedTerms(
        integer,
        ws
    ) and skip(bar) and separatedTerms(integer, ws) map { (id, winningNumbers, revealedNumbers) ->
        ScratchCard(id, winningNumbers, revealedNumbers)
    }
}