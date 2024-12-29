package twenty_four.day17

import kotlin.math.pow

fun part1(input: ComputerSetup): String {
    val computer = Computer()
    computer.initialiseRegisters(input.registers)
    computer.runProgram(input.program)
    return computer.getOutput()
}

data class ComputerSetup(
    val registers: List<Pair<Char, Long>>,
    val program: List<Int>
)

private class Computer {
    private val registers: MutableMap<Char, Long> = mutableMapOf()
    private val output: MutableList<Int> = mutableListOf()

    fun initialiseRegisters(registers: List<Pair<Char, Long>>) {
        this.registers.clear()
        registers.forEach { register -> this.registers[register.first] = register.second }
    }

    fun runProgram(program: List<Int>) {
        var cursor = 0

        while (cursor < program.size) {
            val subList = program.subList(cursor, cursor + 2)
            val instruction = Instruction.fromOpCode(subList[0])
            val operand = subList[1]
            cursor = executeInstruction(instruction, operand, cursor)
        }
    }

    private fun executeInstruction(instruction: Instruction, literalOperand: Int, currentCursor: Int): Int {
        val comboOperand = comboOperand(literalOperand)
        var newCursor: Int? = null

        when (instruction) {
            Instruction.ADV -> saveToRegister('A', divide(getRegisterValue('A'), 2f.pow(comboOperand).toInt()))
            Instruction.BXL -> saveToRegister('B', bitwiseXOR(getRegisterValue('B'), literalOperand))
            Instruction.BST -> saveToRegister('B', comboOperand % 8)
            Instruction.JNZ ->
                if (getRegisterValue('A') != 0) {
                    newCursor = literalOperand
                }
            Instruction.BXC -> saveToRegister('B', bitwiseXOR(getRegisterValue('B'), getRegisterValue('C')))
            Instruction.OUT -> output.add(comboOperand % 8)
            Instruction.BDV -> saveToRegister('B', divide(getRegisterValue('A'), 2f.pow(comboOperand).toInt()))
            Instruction.CDV -> saveToRegister('C', divide(getRegisterValue('A'), 2f.pow(comboOperand).toInt()))
        }

        return newCursor ?: (currentCursor + 2)
    }

    private fun bitwiseXOR(left: Int, right: Int): Int = left xor right

    private fun comboOperand(operand: Int): Int = when (operand) {
        in 0..3 -> operand
        4 -> getRegisterValue('A')
        5 -> getRegisterValue('B')
        6 -> getRegisterValue('C')
        else -> TODO()
    }

    private fun saveToRegister(register: Char, value: Int) {
        registers[register] = value.toLong()
    }

    private fun getRegisterValue(register: Char): Int = registers[register]!!.toInt()

    private fun divide(numerator: Int, denominator: Int): Int = numerator / denominator

    fun getOutput(): String = output.joinToString(",")
}

private enum class Instruction {
    ADV, BXL, BST, JNZ, BXC, OUT, BDV, CDV;

    companion object {
        fun fromOpCode(opCode: Int): Instruction = when (opCode) {
            0 -> ADV
            1 -> BXL
            2 -> BST
            3 -> JNZ
            4 -> BXC
            5 -> OUT
            6 -> BDV
            7 -> CDV
            else -> throw IllegalArgumentException("Invalid op code $opCode")
        }
    }
}
