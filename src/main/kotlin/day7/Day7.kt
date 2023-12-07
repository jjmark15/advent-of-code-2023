package day7

fun part1(input: List<HandAndBid>): Long =
    input.sortedBy { it.hand }.withIndex().sumOf { (i, handAndBid) -> handAndBid.bid * (i + 1) }

sealed class HandType : Comparable<HandType> {
    override fun compareTo(other: HandType): Int {
        val handTypes = listOf(FiveOfAKind, FourOfAKind, FullHouse, ThreeOfAKind, TwoPair, OnePair, HighCard).reversed()

        return handTypes.indexOf(this) - handTypes.indexOf(other)
    }

    data object FiveOfAKind : HandType()
    data object FourOfAKind : HandType()
    data object FullHouse : HandType()
    data object ThreeOfAKind : HandType()
    data object TwoPair : HandType()
    data object OnePair : HandType()
    data object HighCard : HandType()
}

data class Hand(val cards: List<CamelCard>) : Comparable<Hand> {
    private fun type(): HandType {
        val jokers = cards.count { it.value == 'X' }
        var counts = cards.filter { it.value != 'X' }.fold(mutableMapOf<CamelCard, Int>()) { acc, camelCard ->
            acc.compute(camelCard) { _, current -> (current ?: 0) + 1 }
            acc
        }.values.sorted().reversed().toMutableList()

        repeat(jokers) {
            var i = 0
            while (true) {
                val atIndex = counts.getOrElse(i) { 0 } + 1
                val newList = counts.toMutableList()
                if (newList.size == i) {
                    newList.add(atIndex)
                }
                newList[i] = atIndex
                val newListTotal = newList.sum()
                if (newListTotal <= 5) {
                    counts = newList
                    break
                }
                i++
            }
        }

        return when (counts) {
            listOf(5) -> HandType.FiveOfAKind
            listOf(4, 1) -> HandType.FourOfAKind
            listOf(3, 2) -> HandType.FullHouse
            listOf(3, 1, 1) -> HandType.ThreeOfAKind
            listOf(2, 2, 1) -> HandType.TwoPair
            listOf(2, 1, 1, 1) -> HandType.OnePair
            else -> HandType.HighCard
        }
    }

    override fun compareTo(other: Hand): Int {
        if (type() != other.type()) {
            return type().compareTo(other.type())
        }

        for ((index, card) in this.cards.withIndex()) {
            val otherCard = other.cards[index]

            if (card != otherCard) {
                return card.compareTo(otherCard)
            }
        }

        return 0
    }
}

data class CamelCard(val value: Char) : Comparable<CamelCard> {
    override fun compareTo(other: CamelCard): Int {
        val order = "AKQJT987654321X".reversed()

        return order.indexOf(this.value) - order.indexOf(other.value)
    }
}

data class HandAndBid(val hand: Hand, val bid: Long)