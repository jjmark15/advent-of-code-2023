package utils

fun List<String>.lineGroups(): List<List<String>> = this.contiguousGroupsMatching { it.isNotEmpty() }

fun <T, U> List<List<T>>.map2D(convert: (T) -> U): List<List<U>> = this.map { row -> row.map(convert) }

fun List<String>.to2DCharacterMatrix(): List<List<String>> {
    return this.map { row -> row.split("").filter { it.isNotEmpty() } }
}

fun <T, L : Iterable<T>> L.cartesianProduct(): List<Pair<T, T>> =
    this.flatMap { element -> this.map { Pair(element, it) } }

fun <T, L : Iterable<T>> L.contiguousGroupsMatching(matcher: (T) -> Boolean): List<List<T>> =
    this.fold<T, MutableList<MutableList<T>>>(
        mutableListOf(
            mutableListOf()
        )
    ) { acc, t ->
        if (matcher.invoke(t)) {
            acc.last().add(t)
        } else {
            if (acc.last().isNotEmpty()) {
                acc.add(mutableListOf())
            }
        }
        acc
    }.filter { it.isNotEmpty() }