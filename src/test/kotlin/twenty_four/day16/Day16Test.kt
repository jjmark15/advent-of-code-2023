package twenty_four.day16

import assertk.assertThat
import assertk.assertions.isEqualTo
import loadData
import org.junit.jupiter.api.Test

class Day16Test {
    private val parser = InputParser()

    @Test
    fun `part 1 long`() {
        assertThat(part1(parser.parse(data()))).isEqualTo(65436)
    }

    @Test
    fun `part 1 short`() {
        assertThat(part1(parser.parse(shortData()))).isEqualTo(7036)
    }

    private fun data(modifier: String? = null) = loadData(2024, 16, modifier)

    private fun shortData() = data("short")
}
