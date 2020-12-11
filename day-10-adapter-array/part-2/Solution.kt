import java.io.*

fun main(args: Array<String>) {
    val inputStream = File("input.txt").inputStream()
    val adapters = mutableListOf<Long>(0)
    inputStream.bufferedReader().forEachLine { adapters.add(it.toLong()) }

    // Sort list
    adapters.sort()

    // Dynamic Programming
    val dp = LongArray(adapters.size)
    // Base case
    dp[0] = 1
    // Bottom-up approach
    for (i in 1 until adapters.size) {
        for (j in i - 1 downTo 0) {
            if (adapters[i] - adapters[j] > 3) {
                break
            }
            dp[i] += dp[j]
        }
    }

    // Print Answer
    println(dp.last())
}
