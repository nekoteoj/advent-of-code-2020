import java.io.*
import java.util.Queue
import java.util.LinkedList

// DFS
val mem = mutableMapOf<String, Int>()
fun dfs(node: String, graph: Map<String, List<Pair<String, Int>>>): Int {
    if (node !in graph) {
        return 1
    }
    if (node in mem) {
        return mem[node]!!
    }
    val totalAmount = 1 + graph[node]!!.map {
        val (childNode, amount) = it
        amount * dfs(childNode, graph)
    }.sum()
    mem[node] = totalAmount
    return totalAmount
}

fun main(args: Array<String>) {
    val inputStream = File("input.txt").inputStream()

    // Create graph
    val graph = mutableMapOf<String, MutableList<Pair<String, Int>>>()
    inputStream.bufferedReader().forEachLine { line ->
        if ("no other bags" in line) {
            return@forEachLine
        }
        val (outerBagString, innerBagStrings) = line.trim { it == '.' }.split(" contain ")
        val outerBag = outerBagString.substring(0, outerBagString.indexOf(" bags"))
        val innerBags = innerBagStrings.split(", ").map {
            val splitPosition = it.indexOf(" ")
            val amount = it.substring(0, splitPosition).toInt()
            val innerBag = it.substring(splitPosition + 1, it.indexOf(" bag"))
            innerBag to amount
        }
        if (outerBag !in graph) {
            graph[outerBag] = mutableListOf()
        }
        for (innerBag in innerBags) {
            graph[outerBag]!!.add(innerBag)
        }
    }

    // Answer
    val bagCount = dfs("shiny gold", graph) - 1
    println(bagCount)
}
