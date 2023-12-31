package day10

fun part1(input: Grid): Int = findFarthestDistanceFromStart(input)

fun findFarthestDistanceFromStart(grid: Grid): Int {
    return getPipePositions(grid).size / 2
}

fun getPipePositions(grid: Grid): Set<GridPosition> {
    val pipe: MutableSet<GridPosition> = mutableSetOf(grid.startingPosition())

    while (true) {
        val previous = pipe.last()
        val next = grid.connectedPositions(previous).firstOrNull() { !pipe.contains(it) }
        if (next != null) {
            pipe.add(next)
            continue
        }
        break
    }

    return pipe
}

sealed interface GridElement {
    data object Ground : GridElement
    data object StartingPosition : GridElement
}

sealed class PipePiece(val first: Direction, val second: Direction) : GridElement {
    data object Vertical : PipePiece(Direction.North, Direction.South)
    data object Horizontal : PipePiece(Direction.East, Direction.West)
    data object NorthEast : PipePiece(Direction.North, Direction.East)
    data object NorthWest : PipePiece(Direction.North, Direction.West)
    data object SouthWest : PipePiece(Direction.South, Direction.West)
    data object SouthEast : PipePiece(Direction.South, Direction.East)

    fun connectsBy(direction: Direction): Boolean = direction == first || direction == second
}

enum class Direction {
    North, South, East, West;

    fun opposite(): Direction {
        return when (this) {
            North -> South
            South -> North
            East -> West
            West -> East
        }
    }
}

class Grid(private val inner: List<List<GridElement>>) : List<List<GridElement>> by inner {
    fun startingPosition(): GridPosition {
        for ((rowIndex, row) in this.withIndex()) {
            for ((columnIndex, element) in row.withIndex()) {
                if (element is GridElement.StartingPosition) {
                    return GridPosition(rowIndex, columnIndex)
                }
            }
        }
        throw Exception("did not find starting position")
    }

    fun connectedPositions(position: GridPosition, exclude: (GridPosition) -> Boolean = { false }): List<GridPosition> =
        when (val element = at(position)) {
            is GridElement.StartingPosition -> adjacentPipePositions(position)
            is GridElement.Ground -> throw Exception("Ground cannot be connected")
            is PipePiece -> listOf(adjacentBy(position, element.first), adjacentBy(position, element.second))
        }.filterNot(exclude)

    private fun adjacentBy(position: GridPosition, direction: Direction): GridPosition = when (direction) {
        Direction.North -> position.copy(row = position.row - 1)
        Direction.South -> position.copy(row = position.row + 1)
        Direction.East -> position.copy(column = position.column + 1)
        Direction.West -> position.copy(column = position.column - 1)
    }

    private fun adjacentPipePositions(position: GridPosition): List<GridPosition> =
        listOf(Direction.North, Direction.East, Direction.South, Direction.West).map {
            Pair(
                it,
                adjacentBy(position, it)
            )
        }.filter { (direction, position) ->
            if (!containsPosition(position)) return@filter false

            val element = at(position)
            if (element !is PipePiece) return@filter false

            element.connectsBy(direction.opposite())
        }.map { (_, position) -> position }


    private fun at(position: GridPosition): GridElement = inner[position.row][position.column]

    private fun containsPosition(position: GridPosition): Boolean {
        val height = inner.size
        val width = if (height > 0) {
            inner.first().size
        } else {
            0
        }
        return position.row in 0..<height && position.column in 0..<width
    }
}

data class GridPosition(val row: Int, val column: Int)
