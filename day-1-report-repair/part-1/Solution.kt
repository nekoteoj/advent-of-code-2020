import java.io.*

fun main(args: Array<String>) {
    val inputStream = File("input.txt").inputStream()
    val numberSet = mutableSetOf<Long>()

    inputStream.bufferedReader().forEachLine { line ->
        val number = line.toLong()
        if ((2020 - number) in numberSet) {
            println(number * (2020 - number))
            System.exit(0)
        }
        numberSet.add(number)
    }
}
