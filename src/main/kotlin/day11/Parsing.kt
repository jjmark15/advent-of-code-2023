package day11

import utils.Grid2D
import utils.map2D
import utils.toCharList

fun parse(input: List<String>): Grid2D<SpaceElement> {
    return input.map { it.toCharList() }.map2D { c ->
        when (c) {
            '.' -> SpaceElement.Empty
            else -> SpaceElement.Galaxy
        }
    }.let { Grid2D(it) }
}