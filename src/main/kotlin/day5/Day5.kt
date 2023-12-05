package day5

fun part1(
    seeds: List<Long>, mappings: Mappings
): Long = seeds.minOf { seed -> mappings.locationOfSeed(seed) }

fun part2(
    seedRanges: List<SeedRange>, mappings: Mappings
): Long = seedRanges.minOf { seedRange ->
    seedRange.range().minOf { seed ->
        mappings.locationOfSeed(seed)
    }
}

data class MappingRange(val sourceRangeStart: Long, val destinationRangeStart: Long, val rangeLength: Long) {
    fun get(id: Long): Long? {
        val highestOverriddenValue = sourceRangeStart + rangeLength - 1
        if (highestOverriddenValue < id || id < sourceRangeStart) {
            return null
        }

        val offset = id - sourceRangeStart
        return destinationRangeStart + offset
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
) {
    fun locationOfSeed(seed: Long): Long {
        return seedToSoilMap.get(seed).let { soilToFertiliserMap.get(it) }.let { fertiliserToWaterMap.get(it) }
            .let { waterToLightMap.get(it) }.let { lightToTemperatureMap.get(it) }
            .let { temperatureToHumidityMap.get(it) }.let { humidityToLocationMap.get(it) }
    }
}

data class SeedRange(val start: Long, val length: Long) {
    fun range(): LongRange = start..<start + length
}