import java.io.*

fun main(args: Array<String>) {
    val inputStream = File("input.txt").inputStream()
    val requiredFields = listOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")

    var currentPassportFields = mutableSetOf<String>()
    var validPassportCount = 0

    fun validateAndCount() {
        if (requiredFields.all { it in currentPassportFields }) {
            validPassportCount++
        }
    }

    inputStream.bufferedReader().forEachLine { line ->
        if (line.length == 0) {
            validateAndCount()
            currentPassportFields.clear()
        } else {
            val fields = line.split(" ").map { it.split(":")[0] }
            currentPassportFields.addAll(fields)
        }
    }
    validateAndCount()

    println(validPassportCount)
}
