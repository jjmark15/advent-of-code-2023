package twenty_four.day10

import utils.grids.twodee.Grid2D
import utils.grids.twodee.Grid2DTraverser

fun part1(input: Grid2D<Int>): Int = solve(input, true)

fun part2(input: Grid2D<Int>): Int = solve(input, false)

private fun solve(input: Grid2D<Int>, uniquePaths: Boolean): Int {
    val grid2DTraverser = Grid2DTraverser(input) { map, from ->
        input.cardinalNeighbours(from).filter { map.get(it) == map.get(from) + 1 }
    }
    val trailheads = input.elementsMatching { height -> height == 0 }
    return trailheads.sumOf { trailhead ->
            grid2DTraverser.breadthFirstSearch(
                trailhead,
                9,
                uniquePaths
            ).count()
        }
}
