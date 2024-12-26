package utils.grids.twodee

import java.util.function.Function

data class PositionAndCost(val position: Position2D, val cost: Long) {
    fun moved(move: Function<Position2D, Position2D>, additionalCost: Long): PositionAndCost =
        PositionAndCost(move.apply(position), cost + additionalCost)
}