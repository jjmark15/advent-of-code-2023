import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

fun loadData(day: Int, modifier: String? = null): List<String> {
    val fileNameSuffix = modifier?.let { "_$it" }.orEmpty()
    val fileName = "day_${day}_data${fileNameSuffix}.txt"
    val inputStream = object {}.javaClass.getResourceAsStream(fileName)
        ?: throw Exception("Resource '$fileName' not found on classpath.")

    return inputStream.bufferedReader().readLines()
}

class ResourceLoadingTest {

    @Test
    fun `groups consecutive non-empty lines`() {
        expectThat(
            listOf(
                "one", "", "two", "three", "", "", "four", ""
            ).lineGroups()
        ) isEqualTo listOf(
            listOf("one"), listOf("two", "three"), listOf("four")
        )
    }
}