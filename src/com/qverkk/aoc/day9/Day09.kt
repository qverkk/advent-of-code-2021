package com.qverkk.aoc.day9

import com.qverkk.aoc.utils.readInput

fun main() {
    fun part1(input: List<String>): Int {
        val rowSize = input.first().length
        val numbers = MutableList(input.size) { MutableList(rowSize) { 0 } }
        var currentIndex = 0
        input.forEach { row ->
            row.split("")
                .filter { it.isNotEmpty() }
                .map { it.toInt() }
                .forEach { number ->
                    val addAtColumn = currentIndex % rowSize
                    val addAtRow = currentIndex / rowSize
                    numbers[addAtRow][addAtColumn] = number
                    currentIndex++
                }
        }

        val result = mutableListOf<Int>()
        for (rowIndex in numbers.indices) {
            for (columnIndex in numbers[rowIndex].indices) {
                val current = numbers[rowIndex][columnIndex]
                val neighbours = numbers.getNeighboursAtIndex(rowIndex, columnIndex)
                if (neighbours.minOf { it } > current) {
                    result.add(current + 1)
                }
            }
        }
        return result.sum()
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("com/qverkk/aoc/day9/Day09_test")
    check(part1(testInput) == 15)
    // check(part2(testInput) == 168)

    val input = readInput("com/qverkk/aoc/day9/Day09")
    println(part1(input))
    println(part2(input))
}

private fun List<List<Int>>.getNeighboursAtIndex(rowIndex: Int, columnIndex: Int): List<Int> {
    val left = getNumberAt(rowIndex, columnIndex - 1)
    val right = getNumberAt(rowIndex, columnIndex + 1)
    val up = getNumberAt(rowIndex + 1, columnIndex)
    val down = getNumberAt(rowIndex - 1, columnIndex)
    return listOfNotNull(left, right, up, down)
}

private fun List<List<Int>>.getNumberAt(rowIndex: Int, columnIndex: Int): Int? {
    return try {
        this[rowIndex][columnIndex]
    } catch (e: IndexOutOfBoundsException) {
        null
    }
}
