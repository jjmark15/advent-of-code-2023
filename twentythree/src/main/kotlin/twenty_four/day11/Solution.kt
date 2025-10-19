package twenty_four.day11

fun part1(input: List<Long>): Long = solve(input, 25)

fun part2(input: List<Long>): Long = solve(input, 75)

private fun solve(input: List<Long>, blinks: Int): Long {
    val blinkCache = mutableMapOf<CacheKey, Long>()
    return input.fold(0) { totalCount, stoneValue -> totalCount + countAfterNBlinks(stoneValue, blinks, blinkCache) }
}

private fun countAfterNBlinks(stoneValue: Long, count: Int, blinkCache: MutableMap<CacheKey, Long>): Long {
    val cacheKey = CacheKey(stoneValue, count)
    if (blinkCache.containsKey(cacheKey)) return blinkCache[cacheKey]!!

    val (first, second) = blink(stoneValue)
    if (count == 1) return 1 + if (second == null) 0L else 1
    val stoneCount = countAfterNBlinks(first, count - 1, blinkCache) + (second?.let {
        countAfterNBlinks(
            second,
            count - 1,
            blinkCache
        )
    } ?: 0L)

    blinkCache.put(cacheKey, stoneCount)

    return stoneCount
}

private fun blink(stoneValue: Long): Pair<Long, Long?> {
    if (stoneValue == 0L) return Pair(1, null)
    val toString = stoneValue.toString()
    if (toString.length % 2 == 0) {
        return Pair(toString.take(toString.length / 2).toLong(), toString.drop(toString.length / 2).toLong())
    }
    return Pair(stoneValue * 2024, null)
}

private data class CacheKey(val number: Long, val blinkCount: Int)
