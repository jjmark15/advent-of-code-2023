package day6

fun part1(records: List<BoatRecord>): Int = records.map { (recordDistance, recordTime) ->
    (0L..recordTime).count { chargingTime ->
        val distance = chargingTime * (recordTime - chargingTime)
        distance > recordDistance
    }
}.reduce { acc, i -> acc * i }


data class BoatRecord(val distance: Long, val time: Long)