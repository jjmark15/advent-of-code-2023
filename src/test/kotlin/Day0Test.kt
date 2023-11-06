import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class Day0Test {
    @Test
    fun `part 1 long`() {
        expectThat(maxCaloriesCarried(longData())) isEqualTo 69528
    }

    @Test
    fun `part 1 short`() {
        expectThat(maxCaloriesCarried(shortData())) isEqualTo 24000
    }

    private fun longData() = loadData(0)
        .lineGroups()
        .map { elf ->
            elf.map { line -> line.toInt() }
        }

    private fun shortData() = listOf(
        listOf(1000, 2000, 3000),
        listOf(4000),
        listOf(5000, 6000),
        listOf(7000, 8000, 9000),
        listOf(10000)
    )
}
