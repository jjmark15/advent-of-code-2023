package twenty_four.day13

import utils.grids.twodee.Point2D

fun part1(input: List<ClawMachineSettings>): Long = input.sumOf { fewestTokensToWinPrize(it) }

data class ClawMachineSettings(
    val buttonASettings: ButtonSettings, val buttonBSettings: ButtonSettings, val prizeLocation: Point2D
)

enum class ButtonName {
    A, B
}

data class ButtonSettings(val name: ButtonName, val xDisplacement: Int, val yDisplacement: Int) {
    val cost
        get() = when (name) {
            ButtonName.A -> 3
            ButtonName.B -> 1
        }
}

private fun fewestTokensToWinPrize(clawMachineSettings: ClawMachineSettings): Long {
    val aX = clawMachineSettings.buttonASettings.xDisplacement
    val aY = clawMachineSettings.buttonASettings.yDisplacement
    val bX = clawMachineSettings.buttonBSettings.xDisplacement
    val bY = clawMachineSettings.buttonBSettings.yDisplacement
    val pX = clawMachineSettings.prizeLocation.column
    val pY = clawMachineSettings.prizeLocation.row
    val denominator = aX * bY - aY * bX
    val countA = (bY * pX - bX * pY) / denominator
    val countB = (aX * pY - aY * pX) / denominator
    val reachedX = countA * aX + countB * bX
    val reachedY = countA * aY + countB * bY
    return if (reachedX == pX && reachedY == pY) {
        (countA * clawMachineSettings.buttonASettings.cost + countB * clawMachineSettings.buttonBSettings.cost).toLong()
    } else 0
}
