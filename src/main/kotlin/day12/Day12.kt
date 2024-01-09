package day12

import utils.contiguousGroupsMatching
import kotlin.math.min

fun part1(input: List<SpringRow>): Int = input.sumOf { row -> ArrangementCounter().count(row) }

fun part2(input: List<SpringRow>): Int = input.map { it.unfold() }.sumOf { row -> ArrangementCounter().count(row) }

data class SpringRow(val states: List<SpringState>, val damagedSpringContiguousGroupSizes: List<Int>) {
    fun copyWithStateAt(state: SpringState, index: Int): SpringRow =
        this.copy(states = states.toMutableList().also { it[index] = state })

    fun isAllKnown(): Boolean = !states.contains(SpringState.Unknown)

    fun unfold(): SpringRow = SpringRow(mutableListOf<SpringState>().also {
        it.addAll(states)
        it.add(SpringState.Unknown)
        it.addAll(states)
        it.add(SpringState.Unknown)
        it.addAll(states)
        it.add(SpringState.Unknown)
        it.addAll(states)
        it.add(SpringState.Unknown)
        it.addAll(states)
    }, mutableListOf<Int>().also {
        it.addAll(damagedSpringContiguousGroupSizes)
        it.addAll(damagedSpringContiguousGroupSizes)
        it.addAll(damagedSpringContiguousGroupSizes)
        it.addAll(damagedSpringContiguousGroupSizes)
        it.addAll(damagedSpringContiguousGroupSizes)
    })
}

class ArrangementCounter {
    fun count(row: SpringRow): Int {
        var count = 0
        val arrangementQueue: MutableList<SpringRow> = mutableListOf<SpringRow>().also { it.add(row) }

        while (arrangementQueue.size > 0) {
            potentialArrangementsForNextUnknown(arrangementQueue.removeFirst()).filter { this.isValidUpToLastUnknown(it) }
                .forEach { newArrangement ->
                    if (newArrangement.isAllKnown()) {
                        count += 1
                    } else {
                        arrangementQueue.add(newArrangement)
                    }
                }
        }

        return count
    }

    private fun isValidUpToLastUnknown(row: SpringRow): Boolean {
        val allStatesKnown: Boolean = row.isAllKnown()

        val targetIndex = if (allStatesKnown) {
            row.states.size - 1
        } else {
            indexOfLastKnown(row)
        }

        if (targetIndex == -1) return true

        val discoveredDamageGroups: List<Int> =
            row.states.subList(0, targetIndex + 1).contiguousGroupsMatching { state ->
                state == SpringState.Broken
            }.map { it.size }

        if (discoveredDamageGroups.size > row.damagedSpringContiguousGroupSizes.size) return false

        if (allStatesKnown) {
            return row.damagedSpringContiguousGroupSizes == discoveredDamageGroups
        }

        val expectedGroupsUpToIndex = row.damagedSpringContiguousGroupSizes.subList(
            0, min(row.damagedSpringContiguousGroupSizes.size, discoveredDamageGroups.size)
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

    private fun indexOfLastKnown(row: SpringRow): Int {
        return row.states.indexOfFirst { it == SpringState.Unknown } - 1
    }

    private fun potentialArrangementsForNextUnknown(row: SpringRow): List<SpringRow> =
        row.states.withIndex().firstOrNull { (_, state) -> state == SpringState.Unknown }?.index?.let {
            listOf(SpringState.Working, SpringState.Broken).map { state ->
                row.copyWithStateAt(state, it)
            }
        } ?: listOf()
}

sealed interface SpringState {
    data object Working : SpringState
    data object Broken : SpringState
    data object Unknown : SpringState
}