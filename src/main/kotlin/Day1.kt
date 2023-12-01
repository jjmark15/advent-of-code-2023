import com.github.kittinunf.result.Result
import com.github.kittinunf.result.isSuccess

fun sumOfCalibrationValues(corruptedCalibrationValues: List<String>): Int = corruptedCalibrationValues.map { value ->
    value.filter { c ->
        Result.of<Int, NumberFormatException> { c.toString().toInt() }.isSuccess()
    }
}.map { "${it.first()}${it.last()}" }.sumOf { it.toInt() }
