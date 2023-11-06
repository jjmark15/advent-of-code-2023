import kotlin.collections.ArrayList

fun loadData(day: Int) : List<String> {
    val fileName = "day_${day}_data.txt"
    val inputStream = object {}.javaClass.getResourceAsStream(fileName)
        ?: throw Exception("Resource '$fileName' not found on classpath.")

    return inputStream.bufferedReader().readLines()
}

fun List<String>.lineGroups(): List<List<String>> {
    val lineGroups = this.fold(mutableListOf<MutableList<String>>(mutableListOf())) { acc, curr ->
        if (curr.isEmpty()) {
            acc.add(ArrayList())
        } else {
            acc.last().add(curr)
        }
        acc
    }.filter { it.isNotEmpty() }

    return lineGroups
}