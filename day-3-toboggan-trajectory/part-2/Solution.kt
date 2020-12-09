import java.io.*

fun main(args: Array<String>) {
    val inputStream = File("input.txt").inputStream()
    val pattern = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine { line ->
        if (line.length != 0) {
            pattern.add(line)
        }
    }

    val patternRowCount = pattern.count()
    val patternColumnCount = pattern[0].length

    var moves = listOf(
        1 to 1,
        3 to 1,
        5 to 1,
        7 to 1,
        1 to 2
    )

    var productEncounteredTree = 1

    for ((rightMove, downMove) in moves) {
        var rowPosition = 0
        var columnPosition = 0

        var encounteredTreeCount = 0

        while (rowPosition < patternRowCount) {
            if (pattern[rowPosition][columnPosition] == '#') {
                encounteredTreeCount++
            }
            columnPosition = (columnPosition + rightMove) % patternColumnCount
            rowPosition += downMove
        }

        productEncounteredTree *= encounteredTreeCount
    }

    println(productEncounteredTree)
}
