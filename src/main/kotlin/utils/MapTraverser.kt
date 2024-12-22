package utils

import java.util.function.BiFunction
import java.util.function.Predicate

class MapTraverser<T>(
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