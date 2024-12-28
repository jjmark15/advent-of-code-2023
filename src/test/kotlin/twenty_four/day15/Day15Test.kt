package twenty_four.day15

import AbstractSolutionTest
import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class Day15Test : AbstractSolutionTest<ProblemInput>(2024, 15) {
    @Test
    fun `part 1 short`() {
        assertThat(part1(parser.parse(shortData()))).isEqualTo(10092)
    }

    @Test
    fun `part 1 long`() {
        assertThat(part1(parser.parse(data()))).isEqualTo(1318523)
    }

    override fun parser() = InputParserImpl()
}
