import java.io.*
import kotlin.math.*

val String.tail: String
    get() = substring(1)

val String.head: Char
    get() = this[0]

fun pow(n: Int, k: Int): Int {
    if (k == 0) {
        return 1
    }
    if (k % 2 == 1) {
        return n * pow(n, k - 1)
    }
    val sqrtVal = pow(n, k / 2)
    return sqrtVal * sqrtVal
}

fun makePositionSolver(lowerSymbol: Char, higherSymbol: Char): (String) -> Int {
    fun positionSolver(seq: String): Int {
        if (seq.length == 0) {
            return 0
        }
        return positionSolver(seq.tail) + when (seq.head) {
            lowerSymbol -> 0
            higherSymbol -> pow(2, seq.length - 1)
            else -> throw IllegalArgumentException("Invalid direction symbol")
        }
    }
    return ::positionSolver
}

fun main(args: Array<String>) {
    val rowPositionSolver = makePositionSolver('F', 'B')
    val columnPositionSolver = makePositionSolver('L', 'R')

    val inputStream = File("input.txt").inputStream()

    var maxId = -1

    inputStream.bufferedReader().forEachLine { line ->
        val row = rowPositionSolver(line.substring(0, 7))
        val column = columnPositionSolver(line.substring(7))
        val id = 8 * row + column
        maxId = max(maxId, id)
    }

    println(maxId)
}
