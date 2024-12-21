package twenty_four.day9

import utils.indexOfOrNull

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

    fun compactFileBlocks() {
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

    fun compactFiles() {
        val fileIds = (0..fileBlocks.maxBy { it ?: 0 }!!).reversed()

        for (fileId in fileIds) {
            val startingIndex = fileBlocks.indexOf(fileId)
            val endIndex = fileBlocks.lastIndexOf(fileId)
            val fileSize = endIndex - startingIndex + 1

            val availableSpaceStartIndex = nextEmptyWindowStartIndexOfSizeUpTo(fileSize, startingIndex)

            if (availableSpaceStartIndex != null) {
                (startingIndex..endIndex).forEach { index -> fileBlocks[index] = null }
                val availableSpaceEndIndex = availableSpaceStartIndex + fileSize - 1
                (availableSpaceStartIndex..availableSpaceEndIndex).forEach { index -> fileBlocks[index] = fileId }
            }
        }
    }

    private fun nextEmptyWindowStartIndexOfSizeUpTo(size: Int, upToIndex: Int): Int? {
        var startIndex = 0
        while (startIndex + size - 1 < upToIndex) {
            val emptySpaceIndex = fileBlocks.subList(startIndex, upToIndex).indexOfOrNull(null)?.let { it + startIndex }
            if (emptySpaceIndex == null) return null
            val potentialSpace = fileBlocks.subList(emptySpaceIndex, upToIndex).take(size).takeWhile { it == null }
            if (potentialSpace.size >= size) return emptySpaceIndex
            startIndex = emptySpaceIndex + potentialSpace.indexOfOrNull(null)!! + 1
        }
        return null
    }

    fun checksum(): Long = fileBlocks.mapIndexed { index, id ->
        id?.let { it * index.toLong() } ?: 0
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

fun part1(diskMap: DiskMap): Long = diskMap.expand().also { it.compactFileBlocks() }.checksum()

fun part2(diskMap: DiskMap): Long = diskMap.expand().also { it.compactFiles() }.checksum()
