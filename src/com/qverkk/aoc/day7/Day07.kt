package com.qverkk.aoc.day7

import com.qverkk.aoc.utils.readInput
import kotlin.math.abs

fun main() {

    fun med(list: List<Int>) = list.sorted().let {
        if (it.size % 2 == 0)
            (it[it.size / 2] + it[(it.size - 1) / 2]) / 2
        else
            it[it.size / 2]
    }

    fun part1(input: List<Int>): Int {
        val median = med(input)
        val possibilities = mutableListOf<Int>()
        for (i in 0..median) {
            possibilities.add(
                input.sumOf { abs(i - it) }
            )
        }
        return possibilities.minOf { it }
    }

    fun part2(input: List<Int>): Int {
        val lastNumber = input.maxOf { it }
        val possibilities = mutableListOf<Int>()
        for (i in 0..lastNumber) {
            possibilities.add(
                input.sumOf { number ->
                    val requiredSteps = abs(i - number)
                    val fuelUsed = generateSequence(1) { it + 1 }.take(requiredSteps).sum()
                    fuelUsed
                }
            )
        }
        return possibilities.minOf { it }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("com/qverkk/aoc/day7/Day07_test").first().split(",").map { it.toInt() }
    check(part1(testInput) == 37)
    check(part2(testInput) == 168)

    val input = readInput("com/qverkk/aoc/day7/Day07").first().split(",").map { it.toInt() }
    println(part1(input))
    println(part2(input))
}
