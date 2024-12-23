package utils.grids.twodee

import java.util.function.BiFunction
import java.util.function.Predicate

class Grid2DTraverser<T>(
    private val map: Grid2D<T>, private val nextPointFactory: BiFunction<Grid2D<T>, Point2D, List<Point2D>>
) {
    private fun search(
        from: Point2D, destinationMatcher: Predicate<T>, mode: TraversalMode, uniquePaths: Boolean
    ): List<Point2D> {
        val visitedPlaces = mutableSetOf<Point2D>()
        val placesToVisit = ArrayDeque<Point2D>()
        placesToVisit.add(from)
        val reachableDestinations = mutableListOf<Point2D>()

        while (placesToVisit.isNotEmpty()) {
            val place = when (mode) {
                TraversalMode.BFS -> placesToVisit.removeFirst()
                TraversalMode.DFS -> placesToVisit.removeLast()
            }
            val newPlaces = nextPointFactory.apply(map, place)
            if (uniquePaths) visitedPlaces.add(place)
            placesToVisit.addAll(newPlaces.filterNot {
                uniquePaths && (visitedPlaces.contains(it) || placesToVisit.contains(
                    it
                ))
            })
            if (destinationMatcher.test(map.get(place))) reachableDestinations.add(place)
        }

        return reachableDestinations.toList()
    }

    private fun search(
        from: Point2D, target: T, mode: TraversalMode, uniquePaths: Boolean
    ): List<Point2D> = search(from, { e -> e == target }, mode, uniquePaths)

    fun breadthFirstSearch(
        from: Point2D, destinationMatcher: Predicate<T>, uniquePaths: Boolean = true
    ): List<Point2D> = search(from, destinationMatcher, TraversalMode.BFS, uniquePaths)

    fun breadthFirstSearch(
        from: Point2D, target: T, uniquePaths: Boolean = true
    ): List<Point2D> = search(from, target, TraversalMode.BFS, uniquePaths)

    fun depthFirstSearch(
        from: Point2D, destinationMatcher: Predicate<T>, uniquePaths: Boolean = true
    ): List<Point2D> = search(from, destinationMatcher, TraversalMode.DFS, uniquePaths)

    fun depthFirstSearch(
        from: Point2D, target: T, uniquePaths: Boolean = true
    ): List<Point2D> = search(from, target, TraversalMode.DFS, uniquePaths)

    private enum class TraversalMode {
        BFS, DFS
    }
}