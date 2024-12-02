package twenty_four.day2

import assertk.assertThat
import assertk.assertions.isEqualTo
import loadData
import org.junit.jupiter.api.Test

class Day2Test {
    private val parser = InputParser()

    @Test
    fun `part 1 long`() {
        assertThat(part1(parser.parse(data()))).isEqualTo(332)
    }

    @Test
    fun `part 1 short`() {
        assertThat(part1(parser.parse(shortData()))).isEqualTo(2)
    }

    @Test
    fun `part 2 long`() {
        assertThat(part2(parser.parse(data()))).isEqualTo(398)
    }

    @Test
    fun `part 2 short`() {
        assertThat(part2(parser.parse(shortData()))).isEqualTo(4)
    }

    @Test
    fun `part 2 short breakdown`() {
        val shortData = parser.parse(shortData())

        assertThat(part2(listOf(shortData[0]))).isEqualTo(1)
        assertThat(part2(listOf(shortData[1]))).isEqualTo(0)
        assertThat(part2(listOf(shortData[2]))).isEqualTo(0)
        assertThat(part2(listOf(shortData[3]))).isEqualTo(1)
        assertThat(part2(listOf(shortData[4]))).isEqualTo(1)
        assertThat(part2(listOf(shortData[5]))).isEqualTo(1)
    }

    private fun data(modifier: String? = null) = loadData(2024, 2, modifier)

    private fun shortData() = data("short")
}
