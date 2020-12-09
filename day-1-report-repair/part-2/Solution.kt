import java.io.*

fun main(args: Array<String>) {
    val targetSum = 2020L
    val inputStream = File("input.txt").inputStream()
    val numberMap = mutableMapOf<Long, Pair<Long, Long>>()
    val numberList = mutableListOf<Long>()

    inputStream.bufferedReader().forEachLine { line ->
        val number = line.toLong()
        if ((targetSum - number) in numberMap) {
            val (number2, number3) = numberMap[targetSum - number]!!
            println(number * number2 * number3)
            System.exit(0)
        }
        for (numberInList in numberList) {
            numberMap[number + numberInList] = number to numberInList
        }
        numberList.add(number)
    }
}
