package twenty_four.day4

import utils.toCharList

class InputParser {

    fun parse(lines: List<String>): List<List<Letter>> = lines.map { line -> line.toCharList().map { n -> fromInt(n) } }

    private fun fromInt(int: Char): Letter = when (int) {
        'X' -> Letter.X
        'M' -> Letter.M
        'A' -> Letter.A
        'S' -> Letter.S
        else -> throw IllegalArgumentException()
    }
}