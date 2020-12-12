import java.io.*

class SeatingSystem(
    val grid: List<List<Char>>
) {
    val adjacents = listOf(
        -1 to -1,
        -1 to 0,
        -1 to 1,
        0 to -1,
        0 to 1,
        1 to -1,
        1 to 0,
        1 to 1
    )

    val acquiredSeat: Int
        get() = grid
            .map { it.count { seat -> seat == '#' } }
            .sum()

    /*
     * Check if direction have seat available (row, column, position)
     * Position index
     * 012
     * 3#4
     * 567
     */
    fun getLookUpSeat(): List<List<List<Boolean>>> {
        var lookUpSeat = mutableListOf<MutableList<MutableList<Boolean>>>()
        for (i in 0 until grid.size) {
            var row = mutableListOf<MutableList<Boolean>>()
            for (j in 0 until grid[0].size) {
                row.add(mutableListOf(true, true ,true, true, true, true, true ,true))
            }
            lookUpSeat.add(row)
        }
        var downDirections = listOf(
            -1 to -1, -1 to 0, -1 to 1, 0 to -1
        )
        var upDirections = downDirections.reversed().map { -it.first to -it.second }
        // Down Direction
        for (i in 1 until grid.size - 1) {
            for (j in 1 until grid[0].size - 1) {
                for ((k, diff) in downDirections.withIndex()) {
                    val (diffRow, diffCol) = diff
                    if (grid[i + diffRow][j + diffCol] == '.') {
                        lookUpSeat[i][j][k] = lookUpSeat[i + diffRow][j + diffCol][k]
                    } else if (grid[i + diffRow][j + diffCol] == '#') {
                        lookUpSeat[i][j][k] = false
                    } else if (grid[i + diffRow][j + diffCol] == 'L') {
                        lookUpSeat[i][j][k] = true
                    }
                }
            }
        }
        // Up Direction
        for (i in grid.size - 2 downTo 1) {
            for (j in grid[0].size - 2 downTo 1) {
                for ((k, diff) in upDirections.withIndex()) {
                    val (diffRow, diffCol) = diff
                    if (grid[i + diffRow][j + diffCol] == '.') {
                        lookUpSeat[i][j][k + 4] = lookUpSeat[i + diffRow][j + diffCol][k + 4]
                    } else if (grid[i + diffRow][j + diffCol] == '#') {
                        lookUpSeat[i][j][k + 4] = false
                    } else if (grid[i + diffRow][j + diffCol] == 'L') {
                        lookUpSeat[i][j][k + 4] = true
                    }
                }
            }
        }
        return lookUpSeat
    }

    // Return new seating system and changed
    fun step(): Pair<SeatingSystem, Boolean> {
        var changed = false
        var newGrid = listOf<List<Char>>()
        val lookUpSeat = getLookUpSeat()
        for (i in 1 until grid.size - 1) {
            var newRow = listOf<Char>()
            for (j in 1 until grid[0].size - 1) {
                // println(lookUpSeat[i][j])
                if (grid[i][j] == 'L') {
                    val noAdjacent = lookUpSeat[i][j].all { it == true }
                    if (noAdjacent) {
                        newRow += '#'
                        changed = true
                    } else {
                        newRow += 'L'
                    }
                } else if (grid[i][j] == '#') {
                    val adjacentCount = lookUpSeat[i][j].count { it == false }
                    if (adjacentCount >= 5) {
                        newRow += 'L'
                        changed = true
                    } else {
                        newRow += '#'
                    }
                } else if (grid[i][j] == '.') {
                    newRow += '.'
                }
            }
            newRow = listOf('.') + newRow + listOf('.')
            newGrid += listOf(newRow)
        }
        newGrid = listOf((1..newGrid[0].size).map { '.' }) + newGrid + listOf((1..newGrid[0].size).map { '.' })
        return SeatingSystem(newGrid) to changed
    }

    override fun toString(): String = grid.map { it.joinToString("") }.joinToString("\n")
}

fun main(args: Array<String>) {
    val inputStream = File("input.txt").inputStream()
    var initialGrid = listOf<List<Char>>()
    inputStream.bufferedReader().forEachLine { initialGrid += listOf(".$it.".toList()) }
    initialGrid = listOf((1..initialGrid[0].size).map { '.' }) + initialGrid + listOf((1..initialGrid[0].size).map { '.' })

    var seatingSystem = SeatingSystem(initialGrid)
    while (true) {
        // println(seatingSystem.toString() + "\n")
        val (newSeatingSystem, changed) = seatingSystem.step()
        if (!changed) {
            break
        }
        seatingSystem = newSeatingSystem
    }

    println(seatingSystem.acquiredSeat)
}
