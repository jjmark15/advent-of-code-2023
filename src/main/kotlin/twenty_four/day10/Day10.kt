package twenty_four.day10

import utils.Grid2D
import utils.Grid2DPoint
import java.util.function.BiFunction
import java.util.function.Predicate

fun part1(input: Grid2D<Int>): Int = solve(input, true)

fun part2(input: Grid2D<Int>): Int = solve(input, false)

private fun solve(input: Grid2D<Int>, uniquePaths: Boolean): Int {
    val mapTraverser = MapTraverser(input) { map, from ->
        input.orthogonalNeighbours(from).filter { map.get(it) == map.get(from) + 1 }
    }
    val trailheads = input.elementsMatching { height -> height == 0 }
    return trailheads.sumOf { trailhead ->
            mapTraverser.breadthFirstSearch(
                trailhead,
                { it: Int -> it == 9 },
                uniquePaths
            ).count()
        }
}

private class MapTraverser<T>(
    private val map: Grid2D<T>, private val nextPointFactory: BiFunction<Grid2D<T>, Grid2DPoint, List<Grid2DPoint>>
) {
    fun breadthFirstSearch(
        from: Grid2DPoint, destinationMatcher: Predicate<T>, uniquePaths: Boolean = true
    ): List<Grid2DPoint> {
        val visitedPlaces = mutableSetOf<Grid2DPoint>()
        val placesToVisit = mutableListOf<Grid2DPoint>(from)
        val reachableDestinations = mutableListOf<Grid2DPoint>()

        while (placesToVisit.isNotEmpty()) {
            val place = placesToVisit.removeLast()
            val newPlaces = nextPointFactory.apply(map, place)
            if (uniquePaths) visitedPlaces.add(place)
            placesToVisit.addAll(newPlaces.filterNot { visitedPlaces.contains(it) })
            if (destinationMatcher.test(map.get(place))) reachableDestinations.add(place)
        }

        return reachableDestinations.toList()
    }
}
