package twenty_four.day2

class InputParser {
    fun parse(lines: List<String>): List<List<Long>> =
        lines.map { line -> line.split(" ").map { it.toLong() } }
}