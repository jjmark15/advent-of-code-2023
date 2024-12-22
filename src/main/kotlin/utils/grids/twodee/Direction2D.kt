package utils.grids.twodee

sealed interface Direction2D {
    data object NorthEast : Direction2D
    data object SouthEast : Direction2D
    data object SouthWest : Direction2D
    data object NorthWest : Direction2D
    data object North : CardinalDirection2D
    data object East : CardinalDirection2D
    data object South : CardinalDirection2D
    data object West : CardinalDirection2D

    fun opposite(): Direction2D = when (this) {
        North -> South
        NorthEast -> SouthWest
        East -> West
        SouthEast -> NorthWest
        South -> North
        SouthWest -> NorthEast
        West -> East
        NorthWest -> SouthEast
    }

    fun right90(): Direction2D = when (this) {
        North -> East
        East -> South
        South -> West
        West -> North
        else -> TODO()
    }

    sealed interface CardinalDirection2D : Direction2D
}