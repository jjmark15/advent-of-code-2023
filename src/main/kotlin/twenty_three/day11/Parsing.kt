package twenty_three.day11

import utils.Grid2D
import utils.map2D
import utils.toCharList

fun parse(input: List<String>): Grid2D<twenty_three.day11.SpaceElement> {
    return input.map { it.toCharList() }.map2D { c ->
        when (c) {
            '.' -> twenty_three.day11.SpaceElement.Empty
            else -> twenty_three.day11.SpaceElement.Galaxy
        }
    }.let { Grid2D(it) }
}