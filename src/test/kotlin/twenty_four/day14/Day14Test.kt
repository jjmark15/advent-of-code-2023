package twenty_four.day14

import AbstractSolutionTest
import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class Day14Test : AbstractSolutionTest<List<PointAndVelocity>>(2024, 14) {
    @Test
    fun `part 1 short`() {
        assertThat(part1(parser.parse(shortData()), 7, 11)).isEqualTo(12)
    }

    @Test
    fun `part 1 long`() {
        assertThat(part1(parser.parse(data()), 103, 101)).isEqualTo(231852216)
    }

    override fun parser() = InputParserImpl()
}
