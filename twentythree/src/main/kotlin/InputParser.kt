interface InputParser<T> {
    fun parse(lines: List<String>): T
}