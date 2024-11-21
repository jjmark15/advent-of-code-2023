package twenty_three.day1

import com.github.kittinunf.result.Result
import com.github.kittinunf.result.isSuccess

val DIGIT_WORDS: Map<String, Int> = mapOf(
    "one" to 1,
    "two" to 2,
    "three" to 3,
    "four" to 4,
    "five" to 5,
    "six" to 6,
    "seven" to 7,
    "eight" to 8,
    "nine" to 9,
)

fun part1(corruptedCalibrationValues: List<String>): Int =
    corruptedCalibrationValues.map { value ->
        value.filter { c ->
            Result.of<Int, NumberFormatException> { c.toString().toInt() }.isSuccess()
        }
    }.map { "${it.first()}${it.last()}" }.sumOf { it.toInt() }

fun part2(corruptedCalibrationValues: List<String>): Int =
    part1(corruptedCalibrationValues.map { value ->
        val newChars: MutableList<String> = mutableListOf()

        for (i in value.indices) {
            val slice = value.slice(i..<value.length)
            var wordFound = false

            for ((word, number) in DIGIT_WORDS) {
                if (slice.startsWith(word)) {
                    newChars.add(number.toString())
                    wordFound = true
                    break
                }
            }

            if (!wordFound) newChars.add(slice.first().toString())
        }

        newChars.joinToString("")
    })
