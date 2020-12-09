import java.io.*

fun main(args: Array<String>) {
    val inputStream = File("input.txt").inputStream()
    var validCount = 0

    inputStream.bufferedReader().forEachLine { line ->
        val (policy, password) = line.split(": ")
        val (countRange, letter) = policy.split(" ")
        val (firstPosition ,secondPosition) = countRange.split("-").map { it.toInt() }
        if ((password[firstPosition - 1] == letter[0]) xor (password[secondPosition - 1] == letter[0])) {
            validCount++
        }
    }

    println(validCount)
}
