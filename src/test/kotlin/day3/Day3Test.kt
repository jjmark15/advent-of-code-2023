package day3

import loadData
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class Day3Test {
    @Test
    fun `part 1 long`() {
        expectThat(part1(data())) isEqualTo 539590
    }

    @Test
    fun `part 1 short`() {
        expectThat(part1(shortData())) isEqualTo 4361
    }

    @Test
    fun `part 2 long`() {
        expectThat(part2(data())) isEqualTo 80703636
    }

    @Test
    fun `part 2 short`() {
        expectThat(part2(shortData())) isEqualTo 467835
    }

    private fun data(modifier: String? = null): List<String> = loadData(3, modifier)

    private fun shortData() = data("short")
}
