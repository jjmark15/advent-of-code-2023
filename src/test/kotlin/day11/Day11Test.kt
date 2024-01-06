package day11

import loadData
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class Day11Test {
    @Test
    fun `part 1 long`() {
        expectThat(part1(data())) isEqualTo 10289334
    }

    @Test
    fun `part 1 short`() {
        expectThat(part1(data("short"))) isEqualTo 374
    }

    @Test
    fun `part 2 long`() {
        expectThat(part2(data(), 1000000)) isEqualTo 649862989626
    }

    @Test
    fun `part 2 short`() {
        expectThat(part2(data("short"), 10)) isEqualTo 1030
    }

    private fun data(modifier: String? = null) = parse(loadData(11, modifier))
}
