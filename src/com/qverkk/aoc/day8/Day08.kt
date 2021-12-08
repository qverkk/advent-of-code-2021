package com.qverkk.aoc.day8

import com.qverkk.aoc.utils.readInput

fun main() {

    fun part1(input: List<String>): Int {
        val observedNumbers = input.map { it.split(" | ") }
        return observedNumbers.sumOf { (observedDigits, outputtedDigits) ->
            val availableUniqueNumbers = observedDigits.split(" ")
                .filter {
                    val length = it.length
                    length == 2 || length == 3 || length == 4 || length == 7
                }.map { it.length }
            outputtedDigits.split(" ")
                .count { availableUniqueNumbers.contains(it.length) }
        }
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("com/qverkk/aoc/day8/Day08_test")
    println(part1(testInput))
    check(part1(testInput) == 26)

    val input = readInput("com/qverkk/aoc/day8/Day08")
    println(part1(input))
    println(part2(input))
}
