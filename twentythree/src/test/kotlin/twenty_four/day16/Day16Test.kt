package twenty_four.day16

import AbstractSolutionTest
import InputParser
import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import utils.grids.twodee.Grid2D

class Day16Test : AbstractSolutionTest<Grid2D<MazeElement>>(2024, 16) {
    @Test
    fun `part 1 short`() {
        assertThat(part1(parser.parse(shortData()))).isEqualTo(7036)
    }

    @Test
    fun `part 1 long`() {
        assertThat(part1(parser.parse(data()))).isEqualTo(65436)
    }

    @Test
    fun `part 2 short`() {
        assertThat(part2(parser.parse(shortData()))).isEqualTo(45)
    }

    @Test
    fun `part 2 long`() {
        assertThat(part2(parser.parse(data()))).isEqualTo(489)
    }

    override fun parser(): InputParser<Grid2D<MazeElement>> = InputParserImpl()
}
