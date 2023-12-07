package day7


import java.util.regex.Pattern

fun parse(s: String): HandAndBid {
    val strings = s.split(Pattern.compile("\\s+"))
    return HandAndBid(parseHand(strings[0]), strings[1].toLong())
}

fun parseHand(s: String): Hand = Hand(s.map { c -> CamelCard(c) })
