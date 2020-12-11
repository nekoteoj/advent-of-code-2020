import java.io.*

data class Machine(
    val acc: Int,
    val pc: Int,
    val codes: List<Pair<String, Int>>
)

fun step(machine: Machine): Machine {
    val (acc, pc, codes) = machine
    val (opcode, operand) = codes[pc]
    return when (opcode) {
        "acc" -> Machine(acc = acc + operand, pc = pc + 1, codes = codes)
        "jmp" -> Machine(acc = acc, pc = pc + operand, codes = codes)
        "nop" -> Machine(acc = acc, pc = pc + 1, codes = codes)
        else -> throw IllegalArgumentException("Invalid opcode is provided.")
    }
}

fun solve(codes: List<Pair<String, Int>>): Int {
    var machine = Machine(acc = 0, pc = 0, codes = codes)
    var executedPc = setOf<Int>()
    while (true) {
        executedPc += machine.pc
        val nextMachine = step(machine)
        if (nextMachine.pc in executedPc) {
            return machine.acc
        }
        machine = nextMachine
    }
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
