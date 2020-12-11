import java.io.*
import java.util.Queue
import java.util.LinkedList

data class Machine(
    val acc: Int,
    val pc: Int,
    val codes: List<Pair<String, Int>>
) {
    val halted: Boolean
        get() = codes.size == pc
}

fun step(machine: Machine): Machine {
    if (machine.halted) {
        return machine
    }
    val (acc, pc, codes) = machine
    val (opcode, operand) = codes[pc]
    return when (opcode) {
        "acc" -> Machine(acc = acc + operand, pc = pc + 1, codes = codes)
        "jmp" -> Machine(acc = acc, pc = pc + operand, codes = codes)
        "nop" -> Machine(acc = acc, pc = pc + 1, codes = codes)
        else -> throw IllegalArgumentException("Invalid opcode is provided.")
    }
}

fun solve(initialCodes: List<Pair<String, Int>>, ): Int {
    // BFS
    // queue (machine, edited, visited pc)
    val queue: Queue<Triple<Machine, Boolean, Set<Int>>> = LinkedList<Triple<Machine, Boolean, Set<Int>>>()
    // visited (acc, pc, edited)
    var visited = setOf<Triple<Int, Int, Boolean>>()
    val initialMachine = Machine(acc = 0, pc = 0, codes = initialCodes)
    queue.add(Triple(initialMachine, false, setOf()))
    while (!queue.isEmpty()) {
        val (machine, edited, visitedPc) = queue.remove()
        if (machine.halted) {
            return machine.acc
        }
        if (Triple(machine.acc, machine.pc, edited) in visited) {
            continue
        }
        val (acc, pc, codes) = machine
        val (opcode, operand) = codes[pc]
        if (!edited && (opcode == "jmp" || opcode == "nop")) {
            val editedCode = codes.subList(0, pc) + when (opcode) {
                "jmp" -> "nop" to operand
                "nop" -> "jmp" to operand
                else -> throw IllegalStateException("Impossible state occured.")
            } + codes.subList(pc + 1, codes.size)
            val editedMachine = Machine(acc, pc, editedCode)
            val nextEditedMachine = step(editedMachine)
            if (nextEditedMachine.pc !in visitedPc) {
                queue.add(Triple(step(editedMachine), true, visitedPc + nextEditedMachine.pc))
            }
        }
        val nextMachine = step(machine)
        if (nextMachine.pc !in visitedPc) {
            queue.add(Triple(step(machine), edited, visitedPc + nextMachine.pc))
        }
        visited += Triple(acc, pc, edited)
    }
    throw IllegalStateException("Solution doesn't exists.")
}

fun main(args: Array<String>) {
    val inputStream = File("input.txt").inputStream()

    var codes = listOf<Pair<String, Int>>()
    inputStream.bufferedReader().forEachLine { line ->
        val (opcode, operand) = line.split(" ")
        codes += opcode to operand.toInt()
    }

    println(solve(codes))
}
