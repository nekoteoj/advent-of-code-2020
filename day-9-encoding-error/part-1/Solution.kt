import java.io.*

fun main(args: Array<String>) {
    val inputStream = File("input.txt").inputStream()
    val numbers = mutableListOf<Long>()
    inputStream.bufferedReader().forEachLine { numbers.add(it.toLong()) }

    // Map from summation to Pair(lowerIndex, HigherIndex)
    val sumMap = mutableMapOf<Long, Pair<Int, Int>>()
    // Precompute first 25 numbers
    for (i in 0 until 25) {
        for (j in i + 1 until 25) {
            val sum = numbers[i] + numbers[j]
            sumMap[sum] = i to j
        }
    }
    // Compute answers
    for (i in 25 until numbers.size) {
        if (numbers[i] !in sumMap) {
            println(numbers[i])
            break
        }
        val (lowerIndex, higherIndex) = sumMap[numbers[i]]!!
        if (lowerIndex < i - 25) {
            println(numbers[i])
            break
        }
        for (j in i - 24 until i) {
            val sum = numbers[j] + numbers[i]
            if (sum in sumMap && sumMap[sum]!!.first > j) {
                continue
            }
            sumMap[sum] = j to i
        }
    }
}
