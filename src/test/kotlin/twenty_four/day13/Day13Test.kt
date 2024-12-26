package twenty_four.day13

import AbstractSolutionTest
import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class Day13Test : AbstractSolutionTest<List<ClawMachineSettings>>(2024, 13) {
    @Test
    fun `part 1 short`() {
        assertThat(part1(parser.parse(shortData()))).isEqualTo(480)
    }

    @Test
    fun `part 1 long`() {
        assertThat(part1(parser.parse(data()))).isEqualTo(40069)
    }

    override fun parser() = InputParserImpl()
}
