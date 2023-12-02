package day2

data class Game(val id: Int, val draws: List<CubeDraw>)

sealed class CubeColour {
    data object Red : CubeColour()
    data object Green : CubeColour()
    data object Blue : CubeColour()
}

data class CubeColourCount(val colour: CubeColour, val quantity: Int)

data class CubeDraw(val redCount: Int, val greenCount: Int, val blueCount: Int)

fun part1(games: List<Game>, maxRed: Int, maxGreen: Int, maxBlue: Int): Int =
    games.filter { isPossible(it, maxRed, maxGreen, maxBlue) }.sumOf { it.id }

fun isPossible(game: Game, maxRed: Int, maxGreen: Int, maxBlue: Int): Boolean {
    return game.draws.all { draw ->
        draw.redCount <= maxRed && draw.greenCount <= maxGreen && draw.blueCount <= maxBlue
    }
}