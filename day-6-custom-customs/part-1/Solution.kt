import java.io.*

fun countYesQuestion(answers: List<String>): Int =
    answers
        .flatMap { it.toList() }
        .toSet()
        .count()

fun main(args: Array<String>) {
    val inputStream = File("input.txt").inputStream()

    var groupAnswers = listOf<String>()
    var countSum = 0
    inputStream.bufferedReader().forEachLine { line ->
        if (line.length == 0) {
            countSum += countYesQuestion(groupAnswers)
            groupAnswers = listOf<String>()
        } else {
            groupAnswers += line
        }
    }
    countSum += countYesQuestion(groupAnswers)

    println(countSum)
}
