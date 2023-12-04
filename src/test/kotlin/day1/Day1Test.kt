package day1

import loadData
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class Day1Test {
    @Test
    fun `part 1 long`() {
        expectThat(part1(data())) isEqualTo 54877
    }

    @Test
    fun `part 1 short`() {
        expectThat(part1(shortData())) isEqualTo 142
    }

    @Test
    fun `part 2 long`() {
        expectThat(part2(data())) isEqualTo 54100
    }

    @Test
    fun `part 2 short`() {
        expectThat(
            part2(
                listOf(
                    "two1nine",
                    "eightwothree",
                    "abcone2threexyz",
                    "xtwone3four",
                    "4nineeightseven2",
                    "zoneight234",
                    "7pqrstsixteen",
                )
            )
        ) isEqualTo 281
    }

    @Test
    fun `part 2 cares about first and last words`() {
        expectThat(
            part2(
                listOf(
                    "twone",
                )
            )
        ) isEqualTo 21
    }

    private fun data(modifier: String? = null) = loadData(1, modifier)

    private fun shortData() = data("short")
}
