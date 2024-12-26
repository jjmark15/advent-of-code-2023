package twenty_four.day12

import utils.grids.twodee.Grid2D
import utils.grids.twodee.Point2D

fun part1(input: Grid2D<Char>): Int = RegionFinder(input).allRegions().sumOf { it.price }

private data class Region(val id: Char, val area: Int, val perimeter: Int) {
    val price get() = perimeter * area
}

private class RegionFinder(private val grid: Grid2D<Char>) {
    private fun regionFromPoint(from: Point2D): Pair<Region, List<Point2D>> {
        val regionValue = grid.get(from)
        val queue = ArrayDeque<Point2D>().also { it.add(from) }
        val regionPoints = mutableSetOf<Point2D>()
        var perimeter = 0

        while (queue.isNotEmpty()) {
            val point = queue.removeFirst()
            regionPoints.add(point)
            perimeter += point.cardinalNeighbours().count { regionValue != grid.getOrNull(it) }
            queue.addAll(
                grid.cardinalNeighbours(point)
                    .filter { !regionPoints.contains(it) && !queue.contains(it) && grid.get(it) == regionValue })
        }

        return Pair(Region(regionValue, regionPoints.size, perimeter), regionPoints.toList())
    }

    fun allRegions(): List<Region> {
        val inspectedPoints = mutableListOf<Point2D>()
        val regions: MutableList<Region> = mutableListOf()

        grid.forEachIndexed { point, _ ->
            if (inspectedPoints.contains(point)) return@forEachIndexed
            val (region, points) = regionFromPoint(point)
            regions.add(region)
            inspectedPoints.addAll(points)
        }

        return regions.toList()
    }
}
