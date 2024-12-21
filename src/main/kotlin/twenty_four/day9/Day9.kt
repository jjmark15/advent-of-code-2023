package twenty_four.day9

data class DiskMap(val indicators: List<DiskMapIndicator>)

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

private fun DiskMap.expand(): ExpandedDiskMap =
    indicators.fold(Pair<MutableList<Int?>, Int>(mutableListOf(), 0)) { (fileBlocks, id), indicator ->
        val p: Pair<List<Int?>, Int> = when (indicator) {
            is DiskMapIndicator.FileLength -> Pair((0..<indicator.length).map { id }, id + 1)
            is DiskMapIndicator.FreeSpaceLength -> Pair((0..<indicator.length).map { null }, id)
        }

        fileBlocks.addAll(p.first)

        Pair(fileBlocks, p.second)
    }.let { ExpandedDiskMap(it.first.toList()) }

sealed interface DiskMapIndicator {
    data class FileLength(val length: Int) : DiskMapIndicator
    data class FreeSpaceLength(val length: Int) : DiskMapIndicator
}

fun part1(diskMap: DiskMap): Long = diskMap.expand().also { it.compactFiles() }.checksum()
