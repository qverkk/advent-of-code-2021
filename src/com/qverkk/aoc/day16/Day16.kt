package com.qverkk.aoc.day16

import com.qverkk.aoc.utils.readInput
import java.util.Deque
import java.util.LinkedList

sealed class Bits {
    abstract val value: Long

    companion object {
        fun parse(input: Deque<Char>): Bits {
            val packetVersion = input.takeFirst(3).toInt(2)
            val packetType = input.takeFirst(3).toInt(2)
            return when (packetType) {
                4 -> Literal.parse(packetVersion, input)
                else -> Operation.parse(packetVersion, packetType, input)
            }
        }
    }

    abstract fun versions(): List<Int>
}

class Literal(val version: Int, override val value: Long) : Bits() {
    override fun versions(): List<Int> = listOf(version)

    companion object {
        fun parse(version: Int, input: Deque<Char>) =
            Literal(version, getValue(input))

        private fun getValue(input: Deque<Char>): Long {
            var last: String
            val bits = mutableListOf<String>()

            do {
                last = input.takeFirst(5)
                bits.add(last.drop(1))
            } while (last.first() != '0')
            return bits.joinToString("").toLong(2)
        }
    }
}

class Operation(val version: Int, val packetType: Int, val packets: List<Bits>): Bits() {
    override fun versions(): List<Int> = listOf(version) + packets.flatMap { it.versions() }

    override val value: Long = when (packetType) {
        0 -> packets.sumOf { it.value }
        1 -> packets.fold(1) { a, b -> a * b.value }
        2 -> packets.minOf { it.value }
        3 -> packets.maxOf { it.value }
        5 -> if (packets[0].value > packets[1].value) 1 else 0
        6 -> if (packets[0].value < packets[1].value) 1 else 0
        else -> if (packets[0].value == packets[1].value) 1 else 0
    }

    companion object {
        fun parse(packetVersion: Int, packetType: Int, input: Deque<Char>): Operation {
            return when(input.takeFirst(1).toInt(2)) {
                0 -> {
                    val packetLength = input.takeFirst(15).toInt(2)
                    val initialPacketLength = input.size
                    val packets = mutableListOf<Bits>()
                    while (initialPacketLength - packetLength != input.size) {
                        packets.add(parse(input))
                    }
                    Operation(packetVersion, packetType, packets)
                }
                1 -> {
                    val numberOfSubPackets = input.takeFirst(11).toInt(2)
                    val packets = mutableListOf<Bits>()
                    while (packets.size != numberOfSubPackets) {
                        packets.add(parse(input))
                    }
                    Operation(packetVersion, packetType, packets)
                }
                else -> error("Invalid Operator length type")
            }
        }
    }
}

private fun Deque<Char>.takeFirst(size: Int): String {
    return (1..size).map { this.removeFirst() }.joinToString("")
}

fun main() {
    fun part1(input: String): Long {
        val queue: Deque<Char> = LinkedList()
        input.map {
            it.digitToInt(16).toString(2).padStart(4, '0')
        }.forEach { hex ->
            hex.forEach { queue.addLast(it) }
        }
        val parse = Bits.parse(queue)
        val versions = parse.versions()
        return versions.sumOf { it.toLong() }
    }

    fun part2(input: String): Long {
        val queue: Deque<Char> = LinkedList()
        input.map {
            it.digitToInt(16).toString(2).padStart(4, '0')
        }.forEach { hex ->
            hex.forEach { queue.addLast(it) }
        }
        val parse = Bits.parse(queue)
        return parse.value
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("com/qverkk/aoc/day16/Day16_test").first()
    // check(part1(testInput) == 2021L)

    val input = readInput("com/qverkk/aoc/day16/Day16").first()
    println(part1(input))
    println(part2(input))
}
