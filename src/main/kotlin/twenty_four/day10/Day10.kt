package twenty_four.day10

import utils.Grid2D
import utils.Grid2DPoint
import java.util.function.Function
import java.util.function.Predicate

fun part1(map: Grid2D<Int>): Int {
    val mapTraverser =
        MapTraverser(map) { from -> map.orthogonalNeighbours(from).filter { map.get(it) == map.get(from) + 1 } }
    return map.elementsMatching { height -> height == 0 }
        .sumOf { trailhead -> mapTraverser.findReachableDestinations(trailhead) { it == 9 }.count() }
}

fun part2(map: Grid2D<Int>): Int {
    val mapTraverser =
        MapTraverser(map) { from -> map.orthogonalNeighbours(from).filter { map.get(it) == map.get(from) + 1 } }
    return map.elementsMatching { height -> height == 0 }
        .sumOf { trailhead -> mapTraverser.countDistinctPathsToDestinations(trailhead) { it == 9 } }
}

private class MapTraverser<T>(
    private val map: Grid2D<T>, private val nextPointFactory: Function<Grid2DPoint, List<Grid2DPoint>>
) {
    fun findReachableDestinations(from: Grid2DPoint, destinationMatcher: Predicate<T>): List<Grid2DPoint> {
        val visitedPlaces = mutableSetOf<Grid2DPoint>()
        val placesToVisit = mutableListOf<Grid2DPoint>(from)
        val reachableDestinations = mutableSetOf<Grid2DPoint>()

        while (placesToVisit.isNotEmpty()) {
            val place = placesToVisit.removeLast()
            val newPlaces = nextPointFactory.apply(place)
            visitedPlaces.add(place)
            placesToVisit.addAll(newPlaces.filterNot { visitedPlaces.contains(it) })
            if (destinationMatcher.test(map.get(place))) {
                reachableDestinations.add(place)
            }
        }

        return reachableDestinations.toList()
    }

    fun countDistinctPathsToDestinations(from: Grid2DPoint, destinationMatcher: Predicate<T>): Int {
        val placesToVisit = mutableListOf<Grid2DPoint>(from)
        val reachableDestinations = mutableListOf<Grid2DPoint>()

        while (placesToVisit.isNotEmpty()) {
            val place = placesToVisit.removeLast()
            val newPlaces = nextPointFactory.apply(place)
            placesToVisit.addAll(newPlaces)
            if (destinationMatcher.test(map.get(place))) {
                reachableDestinations.add(place)
            }
        }

        return reachableDestinations.count()
    }
}
