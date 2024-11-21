package twenty_three.day10

import loadData
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class Day10Test {
    @Test
    fun `part 1 long`() {
        expectThat(part1(data())) isEqualTo 6599
    }

    @Test
    fun `part 1 short`() {
        expectThat(part1(data("short"))) isEqualTo 8
    }

    private fun data(modifier: String? = null) = parse(loadData(2023, 10, modifier))
}
