package utils

fun List<String>.lineGroups(): List<List<String>> =
    this.fold(mutableListOf<MutableList<String>>(mutableListOf())) { lineGroups, line ->
        if (line.isEmpty()) {
            lineGroups.add(mutableListOf())
        } else {
            lineGroups.last().add(line)
        }
        lineGroups
    }.filter { it.isNotEmpty() }

fun <T, U> List<List<T>>.map2D(convert: (T) -> U): List<List<U>> = this.map { row -> row.map(convert) }

fun List<String>.to2DCharacterMatrix(): List<List<String>> {
    return this.map { row -> row.split("").filter { it.isNotEmpty() } }
}