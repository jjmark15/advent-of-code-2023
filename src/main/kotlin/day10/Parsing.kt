package day10

fun parse(lines: List<String>): Grid {
    return Grid(lines.map { line ->
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