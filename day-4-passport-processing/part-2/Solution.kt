import java.io.*

fun createIntValidator(minValue: Int, maxValue: Int): (Int) -> Boolean {
    return { value: Int -> value >= minValue && value <= maxValue }
}

fun createStringValidator(pattern: String): (String) -> Boolean {
    val regex = pattern.toRegex()
    return { value: String -> regex.matches(input = value) }
}

fun createHeightValidator(prefix: String, minValue: Int, maxValue: Int): (String) -> Boolean {
    val intValidator = createIntValidator(minValue, maxValue)
    return fun (height: String): Boolean {
        if (height.substring(height.length - 2) != prefix) {
            return false
        }
        val value = height.substring(0, height.length - 2).toInt()
        return intValidator(value)
    }
}

fun validate(key: String, value: String): Boolean = when (key) {
    "byr" -> createIntValidator(1920, 2002)(value.toInt())
    "iyr" -> createIntValidator(2010, 2020)(value.toInt())
    "eyr" -> createIntValidator(2020, 2030)(value.toInt())
    "hgt" -> createHeightValidator("cm", 150, 193)(value) || createHeightValidator("in", 59, 76)(value)
    "hcl" -> createStringValidator("""#[0-9a-f]{6}""")(value)
    "ecl" -> createStringValidator("""amb|blu|brn|gry|grn|hzl|oth""")(value)
    "pid" -> createStringValidator("""[0-9]{9}""")(value)
    else -> true
}

fun main(args: Array<String>) {
    val inputStream = File("input.txt").inputStream()
    val requiredFields = listOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")

    var currentPassportFields = mutableMapOf<String, String>()
    var validPassportCount = 0

    fun validateAndCount() {
        for (field in requiredFields) {
            if (field !in currentPassportFields || !validate(field, currentPassportFields[field]!!)) {
                return
            }
        }
        validPassportCount++
    }

    inputStream.bufferedReader().forEachLine { line ->
        if (line.length == 0) {
            validateAndCount()
            currentPassportFields.clear()
        } else {
            line.split(" ").forEach {
                val (field, value) = it.split(":")
                currentPassportFields[field] = value
            }
        }
    }
    validateAndCount()

    println(validPassportCount) 
}
