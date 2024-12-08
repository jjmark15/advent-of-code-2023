package twenty_four.day5

data class PageOrderingRule(val before: Int, val after: Int)

data class UpdatePages(val pages: List<Int>) {
    fun middlePage(): Int = pages[(pages.size - 1) / 2]

    fun sortedWith(comparator: Comparator<Int>): UpdatePages = UpdatePages(pages.sortedWith(comparator))
}

data class PuzzleInput(val rules: List<PageOrderingRule>, val updates: List<UpdatePages>)

private class PageComparator(private val rules: List<PageOrderingRule>) : Comparator<Int> {
    override fun compare(before: Int, after: Int): Int {
        val rule =
            rules.find { rule -> rule == PageOrderingRule(before, after) || rule == PageOrderingRule(before, after) }

        if (rule == null) return 0
        if (rule.after == after) return -1
        return 1
    }
}

fun part1(input: PuzzleInput): Int {
    val pageComparator = PageComparator(input.rules)
    return input.updates.filter { pages -> pages.sortedWith(pageComparator) == pages }
        .sumOf { it.middlePage() }
}

fun part2(input: PuzzleInput): Int {
    val pageComparator = PageComparator(input.rules)
    val middlePages: List<Int> = input.updates.mapNotNull { pages ->
        val sorted = pages.sortedWith(pageComparator)
        if (sorted == pages) return@mapNotNull null
        sorted.middlePage()
    }
    return middlePages.sum()
}
