package day8

import loadData
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class Day8Test {
    @Test
    fun `part 1 long`() {
        val (instructions, mappings) = data()
        expectThat(part1(instructions, mappings)) isEqualTo 12361
    }

    @Test
    fun `part 1 short`() {
        val (instructions, mappings) = shortData()
        expectThat(part1(instructions, mappings)) isEqualTo 6
    }

    @Test
    fun `part 2 long`() {
        val (instructions, mappings) = data()
        expectThat(part2(instructions, mappings)) isEqualTo 12362
    }

    @Test
    fun `part 2 short`() {
        val (instructions, mappings) = shortData2()
        expectThat(part2(instructions, mappings)) isEqualTo 6
    }

    private fun data(modifier: String? = null) = parse(loadData(8, modifier))

    private fun shortData() = data("short")

    private fun shortData2() = data("short_2")
}
