package utils

fun List<String>.lineGroups(): List<List<String>> = this.contiguousGroupsMatching { it.isNotEmpty() }

fun <T, U> Iterable<Iterable<T>>.map2D(convert: (T) -> U): List<List<U>> = this.map { row -> row.map(convert) }

fun <T> Iterable<T>.cartesianProduct(): List<Pair<T, T>> =
    this.flatMap { element -> this.map { Pair(element, it) } }

fun <T> Iterable<T>.contiguousGroupsMatching(matcher: (T) -> Boolean): List<List<T>> =
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