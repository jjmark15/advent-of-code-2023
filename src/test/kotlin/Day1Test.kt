import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class Day1Test {
    @Test
    fun `part 1 long`() {
        expectThat(sumOfCalibrationValues(longData())) isEqualTo 54877
    }

    @Test
    fun `part 1 short`() {
        expectThat(sumOfCalibrationValues(shortData())) isEqualTo 142
    }

//    @Test
//    fun `part 2 long`() {
//        expectThat(totalCaloriesCarriedByTopThreeElves(longData())) isEqualTo 206152
//    }
//
//    @Test
//    fun `part 2 short`() {
//        expectThat(totalCaloriesCarriedByTopThreeElves(shortData())) isEqualTo 45000
//    }

    private fun longData(modifier: String? = null) = loadData(1, modifier)

    private fun shortData() = longData("short")
}
