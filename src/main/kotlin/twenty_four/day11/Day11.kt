package twenty_four.day11

fun part1(input: List<Long>): Int {
    var stones = input.toList()
    repeat(25) { stones = blink(stones) }
    return stones.count()
}

private fun blink(stones: List<Long>): List<Long> = stones.flatMap { stone -> blink(stone) }

private fun blink(stoneValue: Long): List<Long> {
    if (stoneValue == 0L) return listOf(1)
    val toString = stoneValue.toString()
    if (toString.length % 2 == 0) {
        return listOf(toString.take(toString.length / 2).toLong(), toString.drop(toString.length / 2).toLong())
    }
    return listOf(stoneValue * 2024)
}
