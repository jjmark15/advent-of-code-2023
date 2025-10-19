package twenty_four.day3

sealed interface Statement {
    data class Multiply(val first: Long, val second: Long) : Statement
    data object Do : Statement
    data object Dont : Statement
}

fun part1(input: List<Statement>): Long = input.sumOf {
    when (it) {
        Statement.Do -> 0
        Statement.Dont -> 0
        is Statement.Multiply -> it.first * it.second
    }
}

fun part2(input: List<Statement>): Long = input.fold(SumAndEnabled()) { acc, statement ->
    when (statement) {
        Statement.Do -> acc.enable()
        Statement.Dont -> acc.disable()
        is Statement.Multiply -> {
            if (acc.enabled) {
                acc.add(statement.first * statement.second)
            }
        }
    }

    return@fold acc
}.sum

private class SumAndEnabled(var enabled: Boolean = true, var sum: Long = 0) {
    fun enable() {
        enabled = true
    }

    fun disable() {
        enabled = false
    }

    fun add(value: Long) {
        sum += value
    }
}