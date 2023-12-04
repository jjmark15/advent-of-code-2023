package day2

import kotlin.math.max

data class Game(val id: Int, val draws: List<CubeDraw>)

enum class CubeColour {
    Red, Green, Blue
}

data class CubeColourCount(val colour: CubeColour, val quantity: Int)

data class CubeDraw(val redCount: Int, val greenCount: Int, val blueCount: Int)

fun part1(games: List<Game>, maxRed: Int, maxGreen: Int, maxBlue: Int): Int =
    games.filter { isPossible(it, maxRed, maxGreen, maxBlue) }.sumOf { it.id }

fun isPossible(game: Game, maxRed: Int, maxGreen: Int, maxBlue: Int): Boolean = game.draws.all { draw ->
    draw.redCount <= maxRed && draw.greenCount <= maxGreen && draw.blueCount <= maxBlue
}


fun part2(games: List<Game>): Int = games.map { game ->
    game.draws.reduce { minimums, current ->
        CubeDraw(
            max(minimums.redCount, current.redCount),
            max(minimums.greenCount, current.greenCount),
            max(minimums.blueCount, current.blueCount)
        )
    }
}.sumOf { it.blueCount * it.redCount * it.greenCount }
