package twenty_four.day1

import kotlin.math.absoluteValue

data class LocationIds(val left: List<Long>, val right: List<Long>)

fun part1(input: LocationIds): Long {
    val first = input.left.sorted()
    val second = input.right.sorted()
    return first.zip(second).sumOf { (l, r) -> (l - r).absoluteValue }
}