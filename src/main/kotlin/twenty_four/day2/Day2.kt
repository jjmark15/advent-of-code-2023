package twenty_four.day2

import kotlin.math.absoluteValue

fun part1(input: List<List<Long>>): Long {
    return input.count { isSafe(it) }.toLong()
}

private fun isSafe(report: List<Long>): Boolean = report.windowed(2, 1, false).fold(State(null)) { state, window ->
    if (!state.safe) return@fold state

    val previous = window[0]
    val current = window[1]
    val newDirection: Direction = toDirection(previous, current)
    val difference = (current - previous).absoluteValue

    if (newDirection == Direction.Unknown) return@fold state.copy(safe = false)
    if (difference < 1 || difference > 3) return@fold state.copy(safe = false)
    if (state.direction != null && newDirection != state.direction) {
        return@fold state.copy(safe = false)
    }

    return@fold state.copy(direction = newDirection)
}.safe

private fun toDirection(previous: Long, current: Long): Direction {
    if (previous == current) return Direction.Unknown
    if (previous < current) return Direction.Ascending
    return Direction.Descending
}

private data class State(val direction: Direction?, val safe: Boolean = true) {}

private enum class Direction {
    Ascending, Descending, Unknown
}
