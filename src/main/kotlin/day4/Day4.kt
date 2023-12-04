package day4

import kotlin.math.pow

fun part1(scratchCards: List<ScratchCard>): Int {
    return scratchCards.sumOf { scratchCard ->
        scratchCard.score()
    }
}

data class ScratchCard(val id: Int, val winningNumbers: List<Int>, val revealedNumbers: List<Int>) {
    fun score(): Int {
        return revealedNumbers.count { winningNumbers.contains(it) }.let { count ->
            if (count > 0) {
                2.0.pow(count - 1).toInt()
            } else {
                0
            }
        }
    }
}