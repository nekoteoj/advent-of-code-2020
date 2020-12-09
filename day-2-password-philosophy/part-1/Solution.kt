import java.io.*

fun main(args: Array<String>) {
    val inputStream = File("input.txt").inputStream()
    var validCount = 0

    inputStream.bufferedReader().forEachLine { line ->
        val (policy, password) = line.split(": ")
        val (countRange, letter) = policy.split(" ")
        val (lowestCount ,highestCount) = countRange.split("-").map { it.toInt() }
        val count = password.count { it == letter[0] }
        if (count >= lowestCount && count <= highestCount) {
            validCount++
        }
    }

    println(validCount)
}