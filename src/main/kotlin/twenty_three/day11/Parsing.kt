package twenty_three.day11

import utils.grids.twodee.Grid2D
import utils.map2D
import utils.toCharList

fun parse(input: List<String>): Grid2D<SpaceElement> {
    return input.map { it.toCharList() }.map2D { c ->
        when (c) {
            '.' -> twenty_three.day11.SpaceElement.Empty
            else -> twenty_three.day11.SpaceElement.Galaxy
        }
    }.let { Grid2D(it) }
}