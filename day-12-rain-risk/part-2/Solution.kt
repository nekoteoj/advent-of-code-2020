import java.io.*
import kotlin.math.abs

class Ship(
    var position: Pair<Int, Int> = 0 to 0, // Position (x, y)
    var wayPoint: Pair<Int, Int> = 10 to 1, // east to north
) {
    val rotateOperations = setOf('L', 'R')
    val wayPointOperations = setOf('N', 'E', 'W', 'S')

    val manhattanDistance: Int
        get() = abs(position.first) + abs(position.second)

    fun turnLeft(degree: Int) {
        val amount = (degree / 90) % 4
        wayPoint = when (amount) {
            0 -> wayPoint
            1 -> -wayPoint.second to wayPoint.first
            2 -> -wayPoint.first to -wayPoint.second
            3 -> wayPoint.second to -wayPoint.first
            else -> throw IllegalStateException("Degree must not be negative.")
        }
    }

    fun turnRight(degree: Int) {
        val amount = (degree / 90) % 4
        turnLeft((4 - amount) * 90)
    }

    fun rotateWayPoint(rotate: Char, amount: Int) {
        when (rotate) {
            'L' -> turnLeft(amount)
            'R' -> turnRight(amount)
            else -> throw IllegalArgumentException("Rotate argument is wrong.")
        }
    }

    fun moveWayPoint(direction: Char, amount: Int) {
        wayPoint = when (direction) {
            'N' -> wayPoint.first to (wayPoint.second + amount)
            'S' -> wayPoint.first to (wayPoint.second - amount)
            'E' -> (wayPoint.first + amount) to wayPoint.second
            'W' -> (wayPoint.first - amount) to wayPoint.second
            else -> throw IllegalArgumentException("Direction argument is wrong.")
        }
    }

    fun move(instruction: String) {
        val operation = instruction[0]
        val amount = instruction.substring(1).toInt()

        if (operation in rotateOperations) {
            rotateWayPoint(operation, amount)
        } else if (operation in wayPointOperations) {
            moveWayPoint(operation, amount)
        } else {
            val distance = amount
            val (xDirection, yDirection) = wayPoint
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
