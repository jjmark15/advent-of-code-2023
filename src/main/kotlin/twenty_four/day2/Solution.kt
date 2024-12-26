package twenty_four.day2

import kotlin.math.absoluteValue

fun part1(input: List<List<Long>>): Long = input.count { isSafe(it) }.toLong()

private fun isSafe(report: List<Long>): Boolean {
    report.windowed(2, 1, false).fold(State(null)) { state, window ->
        val previous = window[0]
        val current = window[1]
        val newDirection: Direction = toDirection(previous, current)
        val difference = (current - previous).absoluteValue

        if (newDirection == Direction.Unknown || (difference < 1 || difference > 3) || (state.direction != null && newDirection != state.direction)) return false

        return@fold state.copy(direction = newDirection)
    }
    return true
}

fun part2(input: List<List<Long>>): Long = input.count { isSafeWithDampener(it) }.toLong()

private fun isSafeWithDampener(report: List<Long>): Boolean =
    isSafe(report) || report.withIndex().any { (index, _) -> isSafe(reportExcludingElement(report, index)) }

private fun reportExcludingElement(report: List<Long>, excluding: Int): List<Long> =
    report.filterIndexed { index, _ -> index != excluding }

private fun toDirection(previous: Long, current: Long): Direction {
    if (previous == current) return Direction.Unknown
    if (previous < current) return Direction.Ascending
    return Direction.Descending
}

private data class State(val direction: Direction?)

private enum class Direction {
    Ascending, Descending, Unknown
}
