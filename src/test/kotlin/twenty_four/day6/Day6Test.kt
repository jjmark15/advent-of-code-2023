package twenty_four.day6

import assertk.assertThat
import assertk.assertions.isEqualTo
import loadData
import org.junit.jupiter.api.Test

class Day6Test {
    private val parser = InputParser()

    @Test
    fun `part 1 long`() {
        assertThat(part1(parser.parse(data()))).isEqualTo(4722)
    }

    @Test
    fun `part 1 short`() {
        assertThat(part1(parser.parse(shortData()))).isEqualTo(41)
    }

    @Test
    fun `part 2 long`() {
        assertThat(part2(parser.parse(data()))).isEqualTo(1602)
    }

    @Test
    fun `part 2 short`() {
        assertThat(part2(parser.parse(shortData()))).isEqualTo(6)
    }

    private fun data(modifier: String? = null) = loadData(2024, 6, modifier)

    private fun shortData() = data("short")
}
