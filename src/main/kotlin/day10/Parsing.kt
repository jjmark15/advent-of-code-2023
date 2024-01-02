package day10

import utils.Grid2D

fun parse(lines: List<String>): Grid2D<GridElement> {
    return Grid2D(lines.map { line ->
        line.split("").filter { it.isNotEmpty() }.map { character ->
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
        }
    })
}