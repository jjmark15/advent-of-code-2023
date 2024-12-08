package twenty_four.day4

import assertk.assertThat
import assertk.assertions.isEqualTo
import loadData
import org.junit.jupiter.api.Test

class Day4Test {
    private val parser = InputParser()

    @Test
    fun `part 1 long`() {
        assertThat(part1(parser.parse(data()))).isEqualTo(2573)
    }

    @Test
    fun `part 1 short`() {
        assertThat(part1(parser.parse(shortData()))).isEqualTo(18)
    }

    @Test
    fun `part 2 long`() {
        assertThat(part2(parser.parse(data()))).isEqualTo(1850)
    }

    @Test
    fun `part 2 short`() {
        assertThat(part2(parser.parse(shortData()))).isEqualTo(9)
    }

    private fun data(modifier: String? = null) = loadData(2024, 4, modifier)

    private fun shortData() = data("short")
}
