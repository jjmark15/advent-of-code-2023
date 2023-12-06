package day6

import loadData
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import java.util.regex.Pattern

class Day6Test {
    @Test
    fun `part 1 long`() {
        expectThat(part1(data())) isEqualTo 2449062
    }

    @Test
    fun `part 1 short`() {
        expectThat(part1(shortData())) isEqualTo 288
    }

    private fun data(modifier: String? = null) = parse(loadData(6, modifier))

    private fun shortData() = data("short")

    private fun parse(lines: List<String>): List<BoatRecord> {
        val times = lines[0].removePrefix("Time:").trim().split(Pattern.compile("\\s+")).map { it.toInt() }
        val distances = lines[1].removePrefix("Distance:").trim().split(Pattern.compile("\\s+")).map { it.toInt() }
        return times.zip(distances).map { (time, distance) -> BoatRecord(distance, time) }
    }
}
