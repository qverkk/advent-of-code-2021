package com.qverkk.aoc.day2

import com.qverkk.aoc.utils.readInput

fun main() {
    fun part1(input: List<String>): Int {
        val horizontal = input
            .filter { it.startsWith("forward") }
            .sumOf { it.replace("forward ", "").toInt() }

        val depth = input
            .filter { it.startsWith("up") || it.startsWith("down") }
            .sumOf {
                if (it.startsWith("up ")) {
                    it.replace("up ", "-").toInt()
                } else {
                    it.replace("down ", "").toInt()
                }
            }

        return horizontal * depth
    }

    fun part2(input: List<String>): Int {
        val horizontal = input
            .filter { it.startsWith("forward") }
            .sumOf { it.replace("forward ", "").toInt() }

        var aim = 0
        var depth = 0

        input.forEach {
            val num = it.replace("\\D+ ".toRegex(), "").toInt()
            when {
                it.startsWith("up") -> aim -= num
                it.startsWith("down") -> aim += num
                else -> depth += num * aim
            }
        }

        return horizontal * depth
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("com/qverkk/aoc/day2/Day02_test")
    check(part1(testInput) == 150)

    val input = readInput("com/qverkk/aoc/day2/Day02")
    println(part1(input))
    println(part2(input))
}
