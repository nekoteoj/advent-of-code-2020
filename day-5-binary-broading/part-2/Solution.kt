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

fun calculateSeatId(row: Int, column: Int): Int {
    return 8 * row + column
}

fun getSeatId(seat: String): Int {
    val rowPositionSolver = makePositionSolver('F', 'B')
    val columnPositionSolver = makePositionSolver('L', 'R')

    val row = rowPositionSolver(seat.substring(0, 7))
    val column = columnPositionSolver(seat.substring(7))
    return calculateSeatId(row, column)
}

fun main(args: Array<String>) {
    val inputStream = File("input.txt").inputStream()

    val setId = mutableSetOf<Int>()
    inputStream.bufferedReader().forEachLine { line ->
        setId.add(getSeatId(line))
    }

    for (id in 1..calculateSeatId(127, 6)) {
        if ((id - 1) in setId && id !in setId && (id + 1) in setId) {
            println(id)
            return
        }
    }
}
