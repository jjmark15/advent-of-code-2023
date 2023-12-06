package day6

fun part1(records: List<BoatRecord>): Int = records.map { (recordDistance, recordTime) ->
    (0..recordTime).count { chargingTime ->
        val distance = chargingTime * (recordTime - chargingTime)
        distance > recordDistance
    }
}.reduce { acc, i -> acc * i }


data class BoatRecord(val distance: Int, val time: Int)