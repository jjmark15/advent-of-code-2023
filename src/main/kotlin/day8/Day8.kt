package day8

fun part1(instructions: Instructions, mappings: NetworkMap): Long {
    var steps = 0L
    val targetNode = Node("ZZZ")
    var currentNode = Node("AAA")

    while (currentNode != targetNode) {
        currentNode = mappings.get(currentNode, instructions.getNext())
        steps++
    }

    return steps
}

enum class Direction {
    Left, Right
}

class Instructions(private val directions: List<Direction>) {
    private var currentIndex = 0

    fun getNext(): Direction {
        val direction = directions[currentIndex]
        if (currentIndex == directions.size - 1) {
            currentIndex = 0
        } else {
            currentIndex++
        }

        return direction
    }
}

data class DirectionTakenAtNode(val direction: Direction, val node: Node)

data class Node(val name: String)

data class DirectionAlternatives(val left: Node, val right: Node)

class NetworkMap(private val inner: Map<Node, DirectionAlternatives>) : Map<Node, DirectionAlternatives> by inner {
    fun get(node: Node, direction: Direction): Node {
        val (left, right) = this.inner[node]!!
        if (direction == Direction.Left) {
            return left
        }
        return right
    }
}