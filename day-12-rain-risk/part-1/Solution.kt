import java.io.*
import kotlin.math.abs

class Ship(
    var position: Pair<Int, Int> = 0 to 0, // Position (x, y)
) {
    val directions = listOf(1 to 0, 0 to -1, -1 to 0, 0 to 1)
    val rotateOperations = setOf('L', 'R')
    var currentDirectionIndex = 0

    val manhattanDistance: Int
        get() = abs(position.first) + abs(position.second)

    fun move(instruction: String) {
        val operation = instruction[0]
        val amount = instruction.substring(1).toInt()

        val moveDirectionIndex = when (operation) {
            'N' -> 3
            'S' -> 1
            'E' -> 0
            'W' -> 2
            'L' -> (((currentDirectionIndex - amount / 90) % 4) + 4) % 4
            'R' -> (currentDirectionIndex + amount / 90) % 4
            'F' -> currentDirectionIndex
            else -> throw IllegalArgumentException("Instruction direction is not valid.")
        }

        if (operation in rotateOperations) {
            currentDirectionIndex = moveDirectionIndex
        } else {
            val distance = amount
            val (xDirection, yDirection) = directions[moveDirectionIndex]
            position = ((xDirection * distance) + position.first) to ((yDirection * distance) + position.second)
        }

    }
}

fun main(args: Array<String>) {
    val inputStream = File("input.txt").inputStream()
    val ship = Ship()

    inputStream.bufferedReader().forEachLine { ship.move(it) }

    println(ship.manhattanDistance)
}
