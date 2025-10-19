package twenty_three.day12

import loadData
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class Day12Test {
    @Test
    fun `part 1 long`() {
        expectThat(part1(data())) isEqualTo 7622
    }

    @Test
    fun `part 1 short`() {
        expectThat(part1(data("short"))) isEqualTo 21
    }

    @Test
    fun `part 2 long`() {
        expectThat(part2(data())) isEqualTo 7622
    }

    @Test
    fun `part 2 short`() {
        expectThat(part2(data("short"))) isEqualTo 525152
    }

    private fun data(modifier: String? = null) = parse(loadData(2023, 12, modifier))
}
