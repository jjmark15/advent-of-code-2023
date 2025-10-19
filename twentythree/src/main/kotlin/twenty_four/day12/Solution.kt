package twenty_four.day12

import utils.grids.twodee.Direction2D
import utils.grids.twodee.Grid2D
import utils.grids.twodee.Point2D

fun part1(input: Grid2D<Char>): Int = RegionFinder(input).allRegions().sumOf { it.area * it.perimeter }

fun part2(input: Grid2D<Char>): Int = RegionFinder(input).allRegions().sumOf { it.area * it.sideCount }

private data class Region(val id: Char, val area: Int, val perimeter: Int, val sideCount: Int)

private class RegionFinder(private val grid: Grid2D<Char>) {
    private fun regionFromPoint(from: Point2D): Pair<Region, List<Point2D>> {
        val regionValue = grid.get(from)
        val queue = ArrayDeque<Point2D>().also { it.add(from) }
        val regionPoints = mutableSetOf<Point2D>()
        var sideCount = 0
        var perimeter = 0

        while (queue.isNotEmpty()) {
            val point = queue.removeFirst()
            regionPoints.add(point)
            perimeter += point.cardinalNeighbours().count { regionValue != grid.getOrNull(it) }
            sideCount += regionChangesOverCorner(point)
            queue.addAll(
                grid.cardinalNeighbours(point)
                    .filter { !regionPoints.contains(it) && !queue.contains(it) && grid.get(it) == regionValue })
        }

        return Pair(Region(regionValue, regionPoints.size, perimeter, sideCount), regionPoints.toList())
    }

    private fun regionChangesOverCorner(point: Point2D): Int {
        val value = grid.get(point)
        return listOf<Triple<Point2D, Point2D, Point2D>>(
            Triple(point.toThe(Direction2D.North), point.toThe(Direction2D.NorthWest), point.toThe(Direction2D.West)),
            Triple(point.toThe(Direction2D.West), point.toThe(Direction2D.SouthWest), point.toThe(Direction2D.South)),
            Triple(point.toThe(Direction2D.South), point.toThe(Direction2D.SouthEast), point.toThe(Direction2D.East)),
            Triple(point.toThe(Direction2D.East), point.toThe(Direction2D.NorthEast), point.toThe(Direction2D.North))
        ).map { (first, between, second) ->
            Triple(grid.getOrNull(first), grid.getOrNull(between), grid.getOrNull(second))
        }.count { (first, between, second) ->
            if (first == value && second == value && between != value) {
                return@count true
            }
            if (first != value && second != value) {
                return@count true
            }
            false
        }
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
