package day4

import kotlin.math.pow

fun part1(scratchCards: List<ScratchCard>): Int {
    return scratchCards.sumOf { scratchCard ->
        scratchCard.score()
    }
}

fun part2(scratchCards: List<ScratchCard>): Int {
    val cardCopies: MutableMap<Int, Int> = mutableMapOf()

    var cardCount = 0

    for (card in scratchCards) {
        val multiplier = 1 + (cardCopies.remove(card.id) ?: 0)

        cardCount += multiplier

        val matchCount = card.matchCount()
        if (matchCount > 0) {
            val cardCopyIds = (card.id + 1)..(matchCount + card.id)
            for (id in cardCopyIds) {
                cardCopies.compute(id) { _, copies -> (copies ?: 0) + multiplier }
            }
        }
    }

    return cardCount
}

data class ScratchCard(val id: Int, val winningNumbers: List<Int>, val revealedNumbers: List<Int>) {
    fun score(): Int = matchCount().let { count ->
        if (count > 0) {
            2.0.pow(count - 1).toInt()
        } else {
            0
        }
    }


    fun matchCount(): Int = revealedNumbers.count { winningNumbers.contains(it) }
}