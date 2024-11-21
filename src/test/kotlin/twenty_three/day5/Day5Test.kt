package twenty_three.day5

import loadData
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class Day5Test {
    @Test
    fun `part 1 long`() {
        val (seeds, mappings) = Parser.parsePart1(data())
        expectThat(part1(seeds, mappings)) isEqualTo 84470622
    }

    @Test
    fun `part 1 short`() {
        val (seeds, mappings) = Parser.parsePart1(shortData())
        expectThat(part1(seeds, mappings)) isEqualTo 35
    }

    @Test
    fun `part 2 long`() {
        val (seeds, mappings) = Parser.parsePart2(data())
        expectThat(part2(seeds, mappings)) isEqualTo 26714516
    }

    @Test
    fun `part 2 short`() {
        val (seeds, mappings) = Parser.parsePart2(shortData())
        expectThat(part2(seeds, mappings)) isEqualTo 46
    }

    private fun data(modifier: String? = null) = loadData(2023, 5, modifier)

    private fun shortData() = data("short")
}
