package twenty_four.day10

import utils.Grid2D
import utils.MapTraverser

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
