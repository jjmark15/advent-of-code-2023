package day12

import utils.contiguousGroupsMatching
import kotlin.math.min

fun part1(input: List<SpringRow>): Int = input.sumOf { row -> row.countValidArrangements() }

fun part2(input: List<SpringRow>): Int = input.map { it.unfold() }.sumOf { row -> row.countValidArrangements() }

data class SpringRow(private val states: List<SpringState>, private val damagedSpringContiguousGroupSizes: List<Int>) {
    fun countValidArrangements(): Int {
        var count = 0

        val unknownStateIndexes = unknownStateIndexes()

        val arrangementQueue: MutableList<Pair<SpringRow, Int>> = mutableListOf()
        arrangementQueue.add(Pair(this, 0))

        while (arrangementQueue.size > 0) {
            val (row, nextUnknownStateIndexIndex) = arrangementQueue.removeFirst()
            val unknownStateIndex = unknownStateIndexes[nextUnknownStateIndexIndex]

            val validStatesAt = row.validStatesAt(unknownStateIndex)
            val newArrangements = validStatesAt.map { state ->
                row.copyWithStateAt(state, unknownStateIndex)
            }.map { newRow -> Pair(newRow, nextUnknownStateIndexIndex + 1) }

            if (nextUnknownStateIndexIndex == unknownStateIndexes.size - 1) {
                count += newArrangements.size
            } else {
                arrangementQueue.addAll(newArrangements)
            }
        }

        return count
    }

    private fun copyWithStateAt(state: SpringState, index: Int): SpringRow =
        this.copy(states = states.toMutableList().also { it[index] = state })

    private fun isValidUpToLastUnknown(): Boolean {
        val allStatesKnown: Boolean = !states.contains(SpringState.Unknown)

        val targetIndex = if (allStatesKnown) {
            states.size - 1
        } else {
            states.indexOfFirst { it == SpringState.Unknown } - 1
        }

        if (targetIndex == -1) return true

        val discoveredDamageGroups: List<Int> = states.subList(0, targetIndex + 1).contiguousGroupsMatching { state ->
            state == SpringState.Broken
        }.map { it.size }

        if (discoveredDamageGroups.size > damagedSpringContiguousGroupSizes.size) return false

        if (allStatesKnown) {
            return damagedSpringContiguousGroupSizes == discoveredDamageGroups
        }

        val expectedGroupsUpToIndex = damagedSpringContiguousGroupSizes.subList(
            0, min(damagedSpringContiguousGroupSizes.size, discoveredDamageGroups.size)
        )

        if (discoveredDamageGroups.isEmpty()) return true

        if (expectedGroupsUpToIndex.take(expectedGroupsUpToIndex.size - 1) != discoveredDamageGroups.take(
                discoveredDamageGroups.size - 1
            )
        ) {
            return false
        }

        if (expectedGroupsUpToIndex.last() < discoveredDamageGroups.last()) return false

        return true
    }

    private fun unknownStateIndexes(): List<Int> = states.mapIndexedNotNull { index, state ->
        when (state) {
            SpringState.Unknown -> index
            else -> null
        }
    }

    private fun validStatesAt(index: Int): List<SpringState> =
        listOf(SpringState.Working, SpringState.Broken).filter { state ->
            this.copyWithStateAt(state, index).isValidUpToLastUnknown()
        }

    fun unfold(): SpringRow {
        val newStates: MutableList<SpringState> = mutableListOf()
        newStates.addAll(states)
        newStates.add(SpringState.Unknown)
        newStates.addAll(states)
        newStates.add(SpringState.Unknown)
        newStates.addAll(states)
        newStates.add(SpringState.Unknown)
        newStates.addAll(states)
        newStates.add(SpringState.Unknown)
        newStates.addAll(states)

        val newGroupSizes: MutableList<Int> = mutableListOf()
        newGroupSizes.addAll(damagedSpringContiguousGroupSizes)
        newGroupSizes.addAll(damagedSpringContiguousGroupSizes)
        newGroupSizes.addAll(damagedSpringContiguousGroupSizes)
        newGroupSizes.addAll(damagedSpringContiguousGroupSizes)
        newGroupSizes.addAll(damagedSpringContiguousGroupSizes)

        return SpringRow(newStates, newGroupSizes)
    }
}

sealed interface SpringState {
    data object Working : SpringState
    data object Broken : SpringState
    data object Unknown : SpringState
}