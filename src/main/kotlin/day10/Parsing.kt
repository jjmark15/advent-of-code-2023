package day10

import utils.map2D
import utils.toCharList

fun parse(lines: List<String>): PipeGrid = lines.map { it.toCharList() }.map2D { character ->
    when (character) {
        '.' -> GridElement.Ground
        'S' -> GridElement.StartingPosition
        '|' -> PipePiece.Vertical
        '-' -> PipePiece.Horizontal
        'L' -> PipePiece.NorthEast
        'J' -> PipePiece.NorthWest
        '7' -> PipePiece.SouthWest
        'F' -> PipePiece.SouthEast
        else -> throw Exception("could not parse $character")
    }
}.let { PipeGrid(it) }