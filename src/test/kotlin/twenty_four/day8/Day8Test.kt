package twenty_four.day8

import assertk.assertThat
import assertk.assertions.isEqualTo
import loadData
import org.junit.jupiter.api.Test

class Day8Test {
    private val parser = InputParser()

    @Test
    fun `part 1 long`() {
        assertThat(part1(parser.parse(data()))).isEqualTo(291)
    }

    @Test
    fun `part 1 short`() {
        assertThat(part1(parser.parse(shortData()))).isEqualTo(14)
    }

    private fun data(modifier: String? = null) = loadData(2024, 8, modifier)

    private fun shortData() = data("short")
}
