package twenty_four.day9

class InputParser {
    fun parse(lines: List<String>): DiskMap = lines.first().split("").filter { it.isNotEmpty() }
        .fold(Pair(mutableListOf<DiskMapIndicator>(), false)) { (indicators, isFreeSpace), c ->
            val number = c.toInt()
            val indicator =
                if (isFreeSpace) DiskMapIndicator.FreeSpaceLength(number) else DiskMapIndicator.FileLength(number)
            indicators.add(indicator)
            Pair(indicators, !isFreeSpace)
        }.let { DiskMap(it.first.toList()) }
}
