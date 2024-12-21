package twenty_four.day7

data class Equation(val testValue: Long, val numbers: List<Long>)

private enum class Operator {
    Plus, Times
}

fun part1(input: List<Equation>): Long = input.filter { canBeCombined(it) }.sumOf { it.testValue }

private fun canBeCombined(equation: Equation): Boolean {
    var combo = OperatorCombo((0..<equation.numbers.size).map { Operator.Plus })

    while (true) {
        if (equation.satisfiedBy(combo)) {
            return true
        }

        combo = combo.nextCombo() ?: return false
    }
}

private class OperatorCombo(val operators: List<Operator>) {
    fun nextCombo(): OperatorCombo? {
        val newOperators = operators.toMutableList()
        for (index in operators.indices.reversed()) {
            val operator = operators[index]
            if (operator == Operator.Plus) {
                newOperators[index] = Operator.Times
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
            else -> TODO()
        }
    }

    return total == testValue
}
