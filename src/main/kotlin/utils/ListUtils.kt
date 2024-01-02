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