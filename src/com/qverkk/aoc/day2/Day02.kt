package com.qverkk.aoc.day2

import com.qverkk.aoc.utils.readInput

fun main() {
    fun part1and2(input: List<String>, part2: Boolean = false): Int {
        var depth = 0
        var horizontal = 0
        var aim = 0

        input.forEach {
            val num = it.replace("\\D+ ".toRegex(), "").toInt()
            when {
                it.startsWith("up") -> aim -= num
                it.startsWith("down") -> aim += num
                else -> {
                    horizontal += num
                    if (part2) {
                        depth += num * aim
                    }
                }
            }
        }

        return horizontal * if (part2) depth else aim
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("com/qverkk/aoc/day2/Day02_test")
    check(part1and2(testInput) == 150)
    check(part1and2(testInput, true) == 900)

    val input = readInput("com/qverkk/aoc/day2/Day02")
    println(part1and2(input))
    println(part1and2(input, true))
}
