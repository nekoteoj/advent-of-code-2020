import java.io.*

fun main(args: Array<String>) {
    val inputStream = File("input.txt").inputStream()
    val adapters = mutableListOf<Long>(0)
    inputStream.bufferedReader().forEachLine { adapters.add(it.toLong()) }

    // Sort list
    adapters.sort()

    // Solve
    var oneJolt = 0L
    var threeJolt = 1L
    for (i in 1 until adapters.size) {
        val diff = adapters[i] - adapters[i - 1]
        if (diff == 1L) {
            oneJolt++
        } else if (diff == 3L) {
            threeJolt++
        }
    }
    val answer = oneJolt * threeJolt

    // Print answer
    println(answer)
}
