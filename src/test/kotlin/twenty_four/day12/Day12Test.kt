package twenty_four.day12

import AbstractSolutionTestBase
import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import utils.grids.twodee.Grid2D

class Day12Test : AbstractSolutionTestBase<Grid2D<Char>>(2024, 12) {
    @Test
    fun `part 1 short`() {
        assertThat(part1(parser.parse(shortData()))).isEqualTo(1930)
    }

    @Test
    fun `part 1 long`() {
        assertThat(part1(parser.parse(data()))).isEqualTo(1344578)
    }

    override fun parser() = InputParserImpl()
}