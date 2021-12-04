package com.qverkk.aoc.day3

import com.qverkk.aoc.utils.readInput

fun main() {
    fun part1(input: List<String>): Int {
        val size = input.first().length
        var gamma = ""
        repeat(size) { index ->
            gamma += input
                .map { it[index] }
                .groupingBy { it }
                .eachCount()
                .maxByOrNull { it.value }?.key
        }

        val epsilon = gamma.replace('0', '2')
            .replace('1', '0')
            .replace('2', '1')

        return gamma.toInt(2) * epsilon.toInt(2)
    }

    fun part2(input: List<String>): Int {
        val size = input.first().length

        var current = input.toMutableList()
        var index = 0
        while (current.size != 1) {
            val currentIndex = index % size
            val onesList = current.filter { it[currentIndex] == '1' }.toMutableList()

            val zeros = current.size - onesList.size
            if (onesList.size > zeros) {
                current = onesList
            } else if (current.size == 2) {
                current = onesList
            } else {
                current.removeAll(onesList)
            }

            index++
        }

        val oxygen = current[0].toInt(2)

        current = input.toMutableList()
        index = 0

        while (current.size != 1) {
            val currentIndex = index % size
            val zerosList = current.filter { it[currentIndex] == '0' }.toMutableList()

            val ones = current.size - zerosList.size
            if (zerosList.size < ones) {
                current = zerosList
            } else if (current.size == 2) {
                current = zerosList
            } else {
                current.removeAll(zerosList)
            }

            index++
        }

        val scrubber = current[0].toInt(2)

        return oxygen * scrubber
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("com/qverkk/aoc/day3/Day03_test")
    check(part1(testInput) == 198)

    val input = readInput("com/qverkk/aoc/day3/Day03")
    println(part1(input))
    println(part2(input))
}
