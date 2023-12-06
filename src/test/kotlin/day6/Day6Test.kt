package day6

import loadData
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import java.util.regex.Pattern

class Day6Test {
    @Test
    fun `part 1 long`() {
        expectThat(part1(parse1(data()))) isEqualTo 2449062
    }

    @Test
    fun `part 1 short`() {
        expectThat(part1(parse1(shortData()))) isEqualTo 288
    }

    @Test
    fun `part 2 long`() {
        expectThat(part1(parse2(data()))) isEqualTo 33149631
    }

    @Test
    fun `part 2 short`() {
        expectThat(part1(parse2(shortData()))) isEqualTo 71503
    }

    private fun data(modifier: String? = null) = loadData(6, modifier)

    private fun shortData() = data("short")

    private fun parse1(lines: List<String>): List<BoatRecord> {
        val times = lines[0].removePrefix("Time:").trim().split(Pattern.compile("\\s+")).map { it.toLong() }
        val distances = lines[1].removePrefix("Distance:").trim().split(Pattern.compile("\\s+")).map { it.toLong() }
        return times.zip(distances).map { (time, distance) -> BoatRecord(distance, time) }
    }

    private fun parse2(lines: List<String>): List<BoatRecord> {
        val time = lines[0].removePrefix("Time:").replace(" ", "").toLong()
        val distance = lines[1].removePrefix("Distance:").replace(" ", "").toLong()
        return listOf(BoatRecord(distance, time))
    }
}
