package utils.grids.twodee

import java.util.function.BiFunction
import java.util.function.Predicate

class Grid2DTraverser<T>(
    private val map: Grid2D<T>, private val nextPointFactory: BiFunction<Grid2D<T>, Point2D, List<Point2D>>
) {
    fun breadthFirstSearch(
        from: Point2D, destinationMatcher: Predicate<T>, uniquePaths: Boolean = true
    ): List<Point2D> {
        val visitedPlaces = mutableSetOf<Point2D>()
        val placesToVisit = mutableListOf<Point2D>(from)
        val reachableDestinations = mutableListOf<Point2D>()

        while (placesToVisit.isNotEmpty()) {
            val place = placesToVisit.removeLast()
            val newPlaces = nextPointFactory.apply(map, place)
            if (uniquePaths) visitedPlaces.add(place)
            placesToVisit.addAll(newPlaces.filterNot { visitedPlaces.contains(it) })
            if (destinationMatcher.test(map.get(place))) reachableDestinations.add(place)
        }

        return reachableDestinations.toList()
    }

    fun breadthFirstSearch(
        from: Point2D, target: T, uniquePaths: Boolean = true
    ): List<Point2D> = breadthFirstSearch(from, { e -> e == target }, uniquePaths)
}