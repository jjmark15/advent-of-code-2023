package twenty_three.day9

import loadData
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import java.util.regex.Pattern

class Day9Test {
    @Test
    fun `part 1 long`() {
        expectThat(part1(data())) isEqualTo 1955513104
    }

    @Test
    fun `part 1 short`() {
        expectThat(part1(data("short"))) isEqualTo 114
    }
    @Test
    fun `part 2 long`() {
        expectThat(part2(data())) isEqualTo 1131
    }

    @Test
    fun `part 2 short`() {
        expectThat(part2(data("short"))) isEqualTo 2
    }

    private fun data(modifier: String? = null) = loadData(2023, 9, modifier).map {
        it.split(Pattern.compile("\\s+")).filterNot { it.isBlank() }.map { it.toInt() }
    }
}
