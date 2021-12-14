package com.qverkk.aoc.day14

import com.qverkk.aoc.utils.readInput

fun main() {
    fun part1(input: List<String>): Int {
        val initialSequence = input.first()
        val pairs = input
            .drop(2)
            .associate {
                val split = it.split(" -> ")
                split[0] to split[1]
            }

        val charCounts = generateSequence(initialSequence) { s ->
            val newChars = s.windowed(2).joinToString("") { pair -> pairs[pair]!! }
            s.zip(newChars).joinToString("") { "${it.first}${it.second}" } + s.last()
        }
            .take(11)
            .last()
            .groupingBy { it }
            .eachCount()

        return charCounts.maxOf { it.value } - charCounts.minOf { it.value }
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("com/qverkk/aoc/day14/Day14_test")
    check(part1(testInput) == 1588)

    val input = readInput("com/qverkk/aoc/day14/Day14")
    println(part1(input))
    println(part2(input))
}
