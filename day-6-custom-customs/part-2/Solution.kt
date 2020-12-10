import java.io.*

fun countYesQuestion(answers: List<String>): Int {
    val answerSets = answers.map { it.toSet() }
    var allYesSet = answerSets[0]
    for (set in answerSets) {
        allYesSet = allYesSet.intersect(set)
    }
    return allYesSet.count()
}

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
