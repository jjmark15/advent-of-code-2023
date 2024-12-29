package twenty_four.day17

import AbstractSolutionTest
import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class Day17Test : AbstractSolutionTest<ComputerSetup>(2024, 17) {
    @Test
    fun `part 1 short`() {
        assertThat(part1(parser.parse(shortData()))).isEqualTo("4,6,3,5,6,3,5,2,1,0")
    }

    @Test
    fun `part 1 long`() {
        assertThat(part1(parser.parse(data()))).isEqualTo("1,2,3,1,3,2,5,3,1")
    }

    override fun parser() = InputParserImpl()
}
