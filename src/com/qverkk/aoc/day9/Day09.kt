package com.qverkk.aoc.day9

import com.qverkk.aoc.utils.readInput

fun main() {
    fun part1(input: List<String>): Int {
        val numbers = input.map { row ->
            row.split("")
                .filter { it.isNotEmpty() }
                .map { it.toInt() }
                .map { number ->
                    number
                }
        }

        return numbers.mapIndexed { rowIndex, row ->
            row.mapIndexed { columnIndex, columnNumber ->
                val neighbours = numbers.getNeighboursAtIndex(rowIndex, columnIndex).filter { it != -1 }
                if (neighbours.minOf { it > columnNumber }) {
                    columnNumber + 1
                } else {
                    null
                }
            }
        }.flatten().filterNotNull().sum()
    }

    fun searchBasins(
        pair: Pair<Int, Pair<Int, Int>>,
        res: MutableMap<Pair<Int, Int>, Int>,
        numbers: List<List<Int>>
    )  {
        val neighbors = pair.second.neighbors()
        neighbors
            .filter { !res.contains(it) }
            .map { numbers.getNumberAt(it.first, it.second) to it }
            .filter { it.first > -1 && it.first < 9 }
            .onEach { res[it.second] = it.first }
            .forEach { searchBasins(it, res, numbers) }
    }

    fun part2(input: List<String>): Int {
        val numbers = input.map { row ->
            row.split("")
                .filter { it.isNotEmpty() }
                .map { it.toInt() }
                .map { number ->
                    number
                }
        }

        val lowPoints = numbers.mapIndexed { rowIndex, row ->
            row.mapIndexed { columnIndex, columnNumber ->
                val neighbours = numbers.getNeighboursAtIndex(rowIndex, columnIndex).filter { it != -1 }
                if (neighbours.minOf { it > columnNumber }) {
                    columnNumber to (rowIndex to columnIndex)
                } else {
                    null
                }
            }
        }.flatten().filterNotNull()

        val map = lowPoints.map {
            val res = mutableMapOf<Pair<Int, Int>, Int>()
            searchBasins(it, res, numbers)
            res
        }

        return map
            .map { it.size }
            .sorted()
            .takeLast(3)
            .reduce { a, b -> a * b }
    }

    val testInput = readInput("com/qverkk/aoc/day9/Day09_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 1134)

    val input = readInput("com/qverkk/aoc/day9/Day09")
    println(part1(input))
    println(part2(input))
}

fun Pair<Int, Int>.neighbors() = listOf(
    this.first to this.second + 1,
    this.first to this.second - 1,
    this.first + 1 to this.second,
    this.first - 1 to this.second
)

private fun List<List<Int>>.getNeighboursAtIndex(rowIndex: Int, columnIndex: Int): List<Int> {
    val left = getNumberAt(rowIndex, columnIndex - 1)
    val right = getNumberAt(rowIndex, columnIndex + 1)
    val up = getNumberAt(rowIndex + 1, columnIndex)
    val down = getNumberAt(rowIndex - 1, columnIndex)
    return listOfNotNull(left, right, up, down)
}

private fun List<List<Int>>.getNumberAt(rowIndex: Int, columnIndex: Int): Int {
    return try {
        this[rowIndex][columnIndex]
    } catch (e: IndexOutOfBoundsException) {
        -1
    }
}
