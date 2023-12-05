package day5

import loadData
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class Day5Test {
    @Test
    fun `part 1 long`() {
        val (seeds, mappings) = data()
        expectThat(part1(seeds, mappings)) isEqualTo 84470622
    }

    @Test
    fun `part 1 short`() {
        val (seeds, mappings) = shortData()
        expectThat(part1(seeds, mappings)) isEqualTo 35
    }

    private fun data(modifier: String? = null) = Parser.parse(loadData(5, modifier))

    private fun shortData() = data("short")
}
