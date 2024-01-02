package day11

import utils.Grid2D
import utils.map2D
import utils.to2DCharacterMatrix

fun parse(input: List<String>): Grid2D<SpaceElement> {
    return input.to2DCharacterMatrix().map2D { c ->
        if (c == ".") {
            SpaceElement.Empty
        } else {
            SpaceElement.Galaxy
        }
    }.let { Grid2D(it) }
}