import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class Day1Test {
    @Test
    fun `part 1 long`() {
        expectThat(naiveSumOfCalibrationValues(longData())) isEqualTo 54877
    }

    @Test
    fun `part 1 short`() {
        expectThat(naiveSumOfCalibrationValues(shortData())) isEqualTo 142
    }

    @Test
    fun `part 2 long`() {
        expectThat(sumOfCalibrationValues(longData())) isEqualTo 54100
    }

    @Test
    fun `part 2 short`() {
        expectThat(
            sumOfCalibrationValues(
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
            sumOfCalibrationValues(
                listOf(
                    "twone",
                )
            )
        ) isEqualTo 21
    }

    private fun longData(modifier: String? = null) = loadData(1, modifier)

    private fun shortData() = longData("short")
}
