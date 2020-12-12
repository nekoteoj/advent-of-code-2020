import java.io.*

class SeatingSystem(
    val grid: List<List<Char>>
) {
    val adjacents = listOf(
        -1 to -1,
        0 to -1,
        1 to -1,
        -1 to 0,
        1 to 0,
        -1 to 1,
        0 to 1,
        1 to 1
    )

    val acquiredSeat: Int
        get() = grid
            .map { it.count { seat -> seat == '#' } }
            .sum()

    // Return new seating system and changed
    fun step(): Pair<SeatingSystem, Boolean> {
        var changed = false
        var newGrid = listOf<List<Char>>()
        for (i in 1 until grid.size - 1) {
            var newRow = listOf<Char>()
            for (j in 1 until grid[0].size - 1) {
                if (grid[i][j] == 'L') {
                    var noAdjacent = true
                    for ((diffRow, diffCol) in adjacents) {
                        val status = grid[i + diffRow][j + diffCol]
                        if (status == '#') {
                            noAdjacent = false
                            break
                        }
                    }
                    if (noAdjacent) {
                        newRow += '#'
                        changed = true
                    } else {
                        newRow += 'L'
                    }
                } else if (grid[i][j] == '#') {
                    var adjacentCount = 0
                    for ((diffRow, diffCol) in adjacents) {
                        val status = grid[i + diffRow][j + diffCol]
                        if (status == '#') {
                            adjacentCount++
                        }
                    }
                    if (adjacentCount >= 4) {
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
