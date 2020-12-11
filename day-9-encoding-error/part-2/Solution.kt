import java.io.*

fun findInvalidNumber(numbers: List<Long>): Long {
    // Map from summation to Pair(lowerIndex, HigherIndex)
    val sumMap = mutableMapOf<Long, Pair<Int, Int>>()
    // Precompute first 25 numbers
    for (i in 0 until 25) {
        for (j in i + 1 until 25) {
            val sum = numbers[i] + numbers[j]
            sumMap[sum] = i to j
        }
    }
    // Compute invalid number
    for (i in 25 until numbers.size) {
        if (numbers[i] !in sumMap) {
            return numbers[i]
        }
        val (lowerIndex, higherIndex) = sumMap[numbers[i]]!!
        if (lowerIndex < i - 25) {
            return numbers[i]
        }
        for (j in i - 24 until i) {
            val sum = numbers[j] + numbers[i]
            if (sum in sumMap && sumMap[sum]!!.first > j) {
                continue
            }
            sumMap[sum] = j to i
        }
    }
    throw IllegalStateException("No invalid number in the list.")
}

fun findContiguousSum(targetSum: Long, numbers: List<Long>): List<Long> {
    val deque = ArrayDeque<Long>()
    var sum = 0L
    for (number in numbers) {
        deque.addLast(number)
        sum += number
        while (sum > targetSum) {
            sum -= deque.removeFirst()
        }
        if (sum == targetSum) {
            return deque
        }
    }
    throw IllegalStateException("No valid contiguous sum sublist in the list.")
}

fun main(args: Array<String>) {
    val inputStream = File("input.txt").inputStream()
    val numbers = mutableListOf<Long>()
    inputStream.bufferedReader().forEachLine { numbers.add(it.toLong()) }

    val invalidNumber = findInvalidNumber(numbers)
    val contiguousList = findContiguousSum(invalidNumber, numbers)

    println(contiguousList.minOrNull()!! + contiguousList.maxOrNull()!!)
}
