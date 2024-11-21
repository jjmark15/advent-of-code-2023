package twenty_three.day8

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

fun part2(instructions: Instructions, mappings: NetworkMap): Long {
    var steps = 0L
    val startingChar = 'A'
    val targetChar = 'Z'
    var currentNodes = mappings.keys.filter { it.endsWith(startingChar) }
    var allEndInTarget = false

    while (!allEndInTarget) {
        val direction = instructions.getNext()
        var endInTarget = true
        currentNodes = currentNodes.map { node ->
            val newNode = mappings.get(node, direction)
            if (!newNode.endsWith(targetChar)) endInTarget = false
            newNode
        }
        allEndInTarget = endInTarget

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

data class Node(val name: String) {
    fun endsWith(c: Char): Boolean = name.endsWith(c)
}

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