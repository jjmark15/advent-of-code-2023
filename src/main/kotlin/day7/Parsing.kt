package day7


import java.util.regex.Pattern

fun parse1(s: String): HandAndBid {
    val strings = s.split(Pattern.compile("\\s+"))
    return HandAndBid(parseHand(strings[0]), strings[1].toLong())
}

fun parse2(s: String): HandAndBid {
    val strings = s.replace("J", "X").split(Pattern.compile("\\s+"))
    return HandAndBid(parseHand(strings[0]), strings[1].toLong())
}

fun parseHand(s: String): Hand = Hand(s.map { c -> CamelCard(c) })
