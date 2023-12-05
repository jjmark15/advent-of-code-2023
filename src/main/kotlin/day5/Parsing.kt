package day5

import lineGroups

class Parser {

    private fun parseSeeds(s: String): List<Long> {
        return s.removePrefix("seeds:").trim().split(" ").map { it.toLong() }
    }

    private fun parseMappingLine(s: String): MappingRange {
        val numbers = s.trim().split(" ").map { it.toLong() }
        return MappingRange(numbers[1], numbers[0], numbers[2])
    }

    companion object {
        fun parse(lines: List<String>): Pair<List<Long>, Mappings> {
            val parser = Parser()
            val lineGroups = lines.lineGroups().toMutableList()

            val seeds = parser.parseSeeds(lineGroups.removeFirst()[0])

            val mappings = lineGroups.map { lineGroup ->
                Mapping(lineGroup.subList(1, lineGroup.size).map { line ->
                    parser.parseMappingLine(line)
                }.fold(mutableListOf()) { acc, curr ->
                    acc.add(curr)
                    acc
                })
            }

            return Pair(
                seeds,
                Mappings(mappings[0], mappings[1], mappings[2], mappings[3], mappings[4], mappings[5], mappings[6])
            )
        }
    }
}