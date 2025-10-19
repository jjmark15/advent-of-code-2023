package twenty_four.day11

class InputParser {
    fun parse(lines: List<String>): List<Long> = lines.first().split(" ").filterNot { it.isEmpty() }.map { it.toLong() }
}
