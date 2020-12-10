import java.io.*
import java.util.Queue
import java.util.LinkedList

fun main(args: Array<String>) {
    val inputStream = File("input.txt").inputStream()

    // Create graph
    val graph = mutableMapOf<String, MutableList<String>>()
    inputStream.bufferedReader().forEachLine { line ->
        val (outerBagString, innerBagStrings) = line.trim { it == '.' }.split(" contain ")
        val outerBag = outerBagString.substring(0, outerBagString.indexOf(" bags"))
        val innerBags = innerBagStrings.split(", ").map { it.substring(it.indexOf(" ") + 1, it.indexOf(" bag")) }
        if (innerBags.size == 1 && innerBags[0] == "no other bags") {
            return@forEachLine
        }
        for (innerBag in innerBags) {
            if (innerBag !in graph) {
                graph[innerBag] = mutableListOf()
            }
            graph[innerBag]!!.add(outerBag)
        }
    }

    // BFS
    val queue: Queue<String> = LinkedList<String>()
    val visited = mutableSetOf<String>()
    queue.add("shiny gold")
    visited.add("shiny gold")
    while (!queue.isEmpty()) {
        val bag = queue.remove()
        if (bag in graph) {
            for (outerBag in graph[bag]!!) {
                if (outerBag !in visited) {
                    visited.add(outerBag)
                    queue.add(outerBag)
                }
            }
        }
    }

    // Answer
    val bagCount = visited.size - 1
    println(bagCount)
}
