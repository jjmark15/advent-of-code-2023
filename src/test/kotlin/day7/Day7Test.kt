package day7

import loadData
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class Day7Test {
    @Test
    fun `part 1 long`() {
        expectThat(part1(data())) isEqualTo 247823654
    }

    @Test
    fun `part 1 short`() {
        expectThat(part1(shortData())) isEqualTo 6440
    }

    private fun data(modifier: String? = null) = loadData(7, modifier).map { parse(it) }

    private fun shortData() = data("short")
}
