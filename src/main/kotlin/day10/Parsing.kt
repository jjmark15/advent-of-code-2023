package day10

import utils.map2D
import utils.to2DCharacterMatrix

fun parse(lines: List<String>): PipeGrid = lines.to2DCharacterMatrix().map2D { character ->
    when (character) {
        "." -> GridElement.Ground
        "S" -> GridElement.StartingPosition
        "|" -> PipePiece.Vertical
        "-" -> PipePiece.Horizontal
        "L" -> PipePiece.NorthEast
        "J" -> PipePiece.NorthWest
        "7" -> PipePiece.SouthWest
        "F" -> PipePiece.SouthEast
        else -> throw Exception("could not parse $character")
    }
}.let { PipeGrid(it) }