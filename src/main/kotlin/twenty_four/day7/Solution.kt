package twenty_four.day7

import java.util.function.Function

data class Equation(val testValue: Long, val numbers: List<Long>)

private enum class Operator {
    Plus, Times, Concat
}

fun part1(input: List<Equation>): Long = input.filter {
    canBeCombined(it) { previous ->
        when (previous) {
            Operator.Plus -> Operator.Times
            Operator.Times -> null
            Operator.Concat -> TODO()
        }
    }
}.sumOf { it.testValue }

fun part2(input: List<Equation>): Long = input.filter {
    canBeCombined(it) { previous ->
        when (previous) {
            Operator.Plus -> Operator.Times
            Operator.Times -> Operator.Concat
            Operator.Concat -> null
        }
    }
}.sumOf { it.testValue }

private fun canBeCombined(equation: Equation, nextOperator: Function<Operator, Operator?>): Boolean {
    var combo = OperatorCombo((0..<equation.numbers.size).map { Operator.Plus })

    while (true) {
        if (equation.satisfiedBy(combo)) {
            return true
        }

        combo = combo.nextComboUsing(nextOperator) ?: return false
    }
}

private class OperatorCombo(val operators: List<Operator>) {

    fun nextComboUsing(nextOperator: Function<Operator, Operator?>): OperatorCombo? {
        val newOperators = operators.toMutableList()
        for (index in operators.indices.reversed()) {
            val operator = operators[index]
            val newOperator = nextOperator.apply(operator)

            if (newOperator != null) {
                newOperators[index] = newOperator
                return OperatorCombo(newOperators.mapIndexed { i, o -> if (i > index) Operator.Plus else o })
            }
        }
        return null
    }
}

private fun Equation.satisfiedBy(operatorCombo: OperatorCombo): Boolean {
    var total = numbers.first()
    val remainingOperators = operatorCombo.operators.toMutableList()

    for (number in numbers.drop(1)) {
        when (remainingOperators.removeFirst()) {
            Operator.Plus -> total += number
            Operator.Times -> total *= number
            Operator.Concat -> total = "${total}${number}".toLong()
            else -> TODO()
        }
    }

    return total == testValue
}
