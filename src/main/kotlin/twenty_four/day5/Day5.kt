package twenty_four.day5

data class PageOrderingRule(val before: Int, val after: Int)

data class UpdatePages(val pages: List<Int>) {
    fun middlePage(): Int = pages[(pages.size - 1) / 2]
}

fun UpdatePages.satisfiesAllOf(rules: List<PageOrderingRule>): Boolean = rules.all { rule ->
    val beforeIndex = pages.indexOf(rule.before)
    val afterIndex = pages.indexOf(rule.after)
    if (beforeIndex == -1 || afterIndex == -1) return@all true
    beforeIndex < afterIndex
}

data class PuzzleInput(val rules: List<PageOrderingRule>, val updates: List<UpdatePages>)

fun part1(input: PuzzleInput): Int =
    input.updates.filter { pages -> pages.satisfiesAllOf(input.rules) }.sumOf { it.middlePage() }
