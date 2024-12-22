package twenty_four.day10

import assertk.assertThat
import assertk.assertions.isEqualTo
import loadData
import org.junit.jupiter.api.Test

class Day10Test {
    private val parser = InputParser()

    @Test
    fun `part 1 long`() {
        assertThat(part1(parser.parse(data()))).isEqualTo(482)
    }

    @Test
    fun `part 1 short`() {
        assertThat(part1(parser.parse(shortData()))).isEqualTo(36)
    }

    @Test
    fun `part 2 long`() {
        assertThat(part2(parser.parse(data()))).isEqualTo(1094)
    }

    @Test
    fun `part 2 short`() {
        assertThat(part2(parser.parse(shortData()))).isEqualTo(81)
    }

    private fun data(modifier: String? = null) = loadData(2024, 10, modifier)

    private fun shortData() = data("short")
}
