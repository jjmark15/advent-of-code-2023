package twenty_four.day5

data class PageOrderingRule(val before: Int, val after: Int)

data class UpdatePages(val pages: List<Int>) {
    fun middlePage(): Int = pages[(pages.size - 1) / 2]

    fun sortedWith(comparator: Comparator<Int>): UpdatePages = UpdatePages(pages.sortedWith(comparator))
}

data class PuzzleInput(val rules: List<PageOrderingRule>, val updates: List<UpdatePages>)

private class PageComparator(private val rules: List<PageOrderingRule>) : Comparator<Int> {
    override fun compare(before: Int, after: Int): Int =
        rules.find { rule -> rule == PageOrderingRule(before, after) || rule == PageOrderingRule(before, after) }
            ?.let { rule ->
                if (rule.after == after) return@let -1
                1
            } ?: 0
}

fun part1(input: PuzzleInput): Int {
    val pageComparator = PageComparator(input.rules)
    return input.updates.filter { pages -> pages.sortedWith(pageComparator) == pages }.sumOf { it.middlePage() }
}

fun part2(input: PuzzleInput): Int {
    val pageComparator = PageComparator(input.rules)
    return input.updates.sumOf { pages ->
        val sorted = pages.sortedWith(pageComparator)
        if (sorted == pages) return@sumOf 0
        sorted.middlePage()
    }
}
