package twenty_four.day1

import loadData
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class Day1Test {
    private val parser = InputParser()

    @Test
    fun `part 1 long`() {
        expectThat(part1(parser.parsePart1(data()))) isEqualTo 2430334
    }

    @Test
    fun `part 1 short`() {
        expectThat(part1(parser.parsePart1(shortData()))) isEqualTo 11
    }

    @Test
    fun `part 2 long`() {
        expectThat(part2(parser.parsePart1(data()))) isEqualTo 28786472
    }

    @Test
    fun `part 2 short`() {
        expectThat(part2(parser.parsePart1(shortData()))) isEqualTo 31
    }

    private fun data(modifier: String? = null) = loadData(2024, 1, modifier)

    private fun shortData() = data("short")
}
