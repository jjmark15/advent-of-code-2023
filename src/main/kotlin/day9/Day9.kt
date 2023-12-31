package day9

fun part1(input: List<List<Int>>): Int = input.sumOf { predictedNextValue(it) }

fun predictedNextValue(sequence: List<Int>): Int {
    var differencesEqualZero = false
    var previousSequence = sequence
    val lastKnownValues = mutableListOf(sequence.last())

    while (!differencesEqualZero) {
        val differences = previousSequence.windowed(2).map { (a, b) -> b - a }

        if (differences.all { it == 0 }) differencesEqualZero = true
        previousSequence = differences
        lastKnownValues.add(differences.last())
    }

    return lastKnownValues.reversed().reduce { acc, i -> acc + i }
}
