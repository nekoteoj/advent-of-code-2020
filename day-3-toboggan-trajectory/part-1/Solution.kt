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

    val rightMove = 3
    val downMove = 1

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

    println(encounteredTreeCount)
}
