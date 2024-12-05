package twenty_four.day3

data class NumberPair(val first: Long, val second: Long)

fun part1(input: List<NumberPair>): Long = input.sumOf { (first, second) -> first * second }
