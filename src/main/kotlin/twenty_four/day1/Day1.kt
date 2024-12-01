package twenty_four.day1

import kotlin.math.absoluteValue

data class LocationIds(val left: List<Long>, val right: List<Long>)

fun part1(input: LocationIds): Long {
    val first = input.left.sorted()
    val second = input.right.sorted()
    return first.zip(second).sumOf { (l, r) -> (l - r).absoluteValue }
}

fun part2(input: LocationIds): Long {
    val cache = mutableMapOf<Long, Long>()

    val numbers = input.left
    val reference = input.right.sorted()

    return numbers.sumOf { number -> number * cache.getOrPut(number) { reference.count { it == number }.toLong() } }
}