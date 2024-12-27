package twenty_four.day13

fun part1(input: List<ClawMachineSettings>): Long = input.sumOf { fewestTokensToWinPrize(it) }

fun part2(input: List<ClawMachineSettings>): Long {
    val prizeLocationOffset = 10000000000000
    return input.map {
        it.copy(
            prizeLocation = it.prizeLocation.copy(
                x = it.prizeLocation.x + prizeLocationOffset,
                y = it.prizeLocation.y + prizeLocationOffset
            )
        )
    }.sumOf { fewestTokensToWinPrize(it) }
}

data class PrizeLocation(val x: Long, val y: Long)

data class ClawMachineSettings(
    val buttonASettings: ButtonSettings, val buttonBSettings: ButtonSettings, val prizeLocation: PrizeLocation
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
    val pX = clawMachineSettings.prizeLocation.x
    val pY = clawMachineSettings.prizeLocation.y
    val denominator = aX * bY - aY * bX
    val countA = (bY * pX - bX * pY) / denominator
    val countB = (aX * pY - aY * pX) / denominator
    val reachedX = countA * aX + countB * bX
    val reachedY = countA * aY + countB * bY
    return if (reachedX == pX && reachedY == pY) {
        (countA * clawMachineSettings.buttonASettings.cost + countB * clawMachineSettings.buttonBSettings.cost).toLong()
    } else 0
}
