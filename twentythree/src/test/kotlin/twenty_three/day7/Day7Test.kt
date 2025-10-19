package twenty_three.day7

import loadData
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class Day7Test {
    @Test
    fun `part 1 long`() {
        expectThat(part1(data().map { parse1(it) })) isEqualTo 247823654
    }

    @Test
    fun `part 1 short`() {
        expectThat(part1(shortData().map { parse1(it) })) isEqualTo 6440
    }

    @Test
    fun `part 2 long`() {
        expectThat(part1(data().map { parse2(it) })) isEqualTo 245461700
    }

    @Test
    fun `part 2 short`() {
        expectThat(part1(shortData().map { parse2(it) })) isEqualTo 5905
    }

    private fun data(modifier: String? = null) = loadData(2023, 7, modifier)

    private fun shortData() = data("short")
}
