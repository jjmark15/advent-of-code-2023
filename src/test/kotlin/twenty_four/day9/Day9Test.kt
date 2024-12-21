package twenty_four.day9

import assertk.assertThat
import assertk.assertions.isEqualTo
import loadData
import org.junit.jupiter.api.Test

class Day9Test {
    private val parser = InputParser()

    @Test
    fun `part 1 long`() {
        assertThat(part1(parser.parse(data()))).isEqualTo(6242766523059)
    }

    @Test
    fun `part 1 short`() {
        assertThat(part1(parser.parse(shortData()))).isEqualTo(1928)
    }

    private fun data(modifier: String? = null) = loadData(2024, 9, modifier)

    private fun shortData() = data("short")
}
