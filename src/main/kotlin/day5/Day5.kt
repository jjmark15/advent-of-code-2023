package day5

fun part1(
    seeds: List<Long>, mappings: Mappings
): Long = seeds.minOf { seed ->
    mappings.seedToSoilMap.get(seed).let { mappings.soilToFertiliserMap.get(it) }
        .let { mappings.fertiliserToWaterMap.get(it) }.let { mappings.waterToLightMap.get(it) }
        .let { mappings.lightToTemperatureMap.get(it) }.let { mappings.temperatureToHumidityMap.get(it) }
        .let { mappings.humidityToLocationMap.get(it) }
}


data class MappingRange(val sourceRangeStart: Long, val destinationRangeStart: Long, val rangeLength: Long) {
    fun get(id: Long): Long? {
        for (index in (0..<rangeLength)) {
            val source = sourceRangeStart + index
            if (source > id) {
                return null
            }
            if (source == id) return destinationRangeStart + index
        }

        return null
    }
}

class Mapping(overrides: List<MappingRange>) {
    private val overrides = overrides.sortedBy { it.sourceRangeStart }

    private fun previousOverride(id: Long): MappingRange? {
        return overrides.lastOrNull { override -> override.sourceRangeStart <= id }
    }

    fun get(id: Long): Long {
        return previousOverride(id)?.get(id) ?: id
    }
}

data class Mappings(
    val seedToSoilMap: Mapping,
    val soilToFertiliserMap: Mapping,
    val fertiliserToWaterMap: Mapping,
    val waterToLightMap: Mapping,
    val lightToTemperatureMap: Mapping,
    val temperatureToHumidityMap: Mapping,
    val humidityToLocationMap: Mapping
)