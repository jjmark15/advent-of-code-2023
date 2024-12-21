package twenty_four.day9

class DiskMap(indicators: List<DiskMapIndicator>) {
    val indicators: List<DiskMapIndicator> = indicators.fold(
        Pair<MutableList<DiskMapIndicator>, Int>(
            mutableListOf(), 0
        )
    ) { (indicators, index), indicator ->
        val (newIndicator, newId) = when (indicator) {
            is DiskMapIndicator.FileLength -> Pair(
                DiskMapIndicator.FileLengthWithId(
                    indicator.length, index
                ), index + 1
            )

            is DiskMapIndicator.FreeSpaceLength -> Pair(indicator, index)
            else -> TODO()
        }

        indicators.add(newIndicator)

        Pair(indicators, newId)
    }.first.toList()
}

private class ExpandedDiskMap(fileBlocks: List<Int?>) {
    private val fileBlocks = fileBlocks.toMutableList()

    fun compactFiles() {
        var endCursor = fileBlocks.size - 1

        while (endCursor > fileBlocks.indexOf(null)) {
            val cursorValue = fileBlocks[endCursor]
            if (cursorValue != null) {
                val nullIndex = fileBlocks.indexOf(null)
                fileBlocks[nullIndex] = cursorValue
                fileBlocks[endCursor] = null
            }
            endCursor--
        }
    }

    fun checksum(): Long = fileBlocks.mapIndexed { index, id ->
        if (id == null) {
            0
        } else {
            index.toLong() * id.toLong()
        }
    }.sum()
}

private fun DiskMap.expand(): ExpandedDiskMap = indicators.flatMap { indicator ->
    when (indicator) {
        is DiskMapIndicator.FileLengthWithId -> (0..<indicator.length).map { indicator.id }
        is DiskMapIndicator.FreeSpaceLength -> (0..<indicator.length).map { null }
        else -> TODO()
    }
}.let { ExpandedDiskMap(it) }

sealed interface DiskMapIndicator {
    data class FileLength(val length: Int) : DiskMapIndicator
    data class FileLengthWithId(val length: Int, val id: Int) : DiskMapIndicator
    data class FreeSpaceLength(val length: Int) : DiskMapIndicator
}

fun part1(diskMap: DiskMap): Long = diskMap.expand().also { it.compactFiles() }.checksum()
