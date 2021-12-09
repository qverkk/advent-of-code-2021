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
                val neighbours = numbers.getNeighboursAtIndex(rowIndex, columnIndex)
                if (neighbours.minOf { it > columnNumber }) {
                    columnNumber + 1
                } else {
                    null
                }
            }
        }.flatten().filterNotNull().sum()
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

        val res = numbers.mapIndexed { rowIndex, row ->
            row.mapIndexed { columnIndex, columnNumber ->
                val neighbours = numbers.getNeighboursAtIndex(rowIndex, columnIndex)
                if (neighbours.minOf { it > columnNumber }) {
                    mutableListOf(Occurance(rowIndex, columnIndex, columnNumber)).apply {
                        addAll(
                            numbers.getNextIncrementedNumber(
                                columnNumber + 1,
                                rowIndex,
                                columnIndex,
                                mutableListOf()
                            )
                        )
                    }
                } else {
                    null
                }
            }
        }
        return res.flatten().filterNotNull().sortedBy { it.size }.takeLast(3).map { it.size }.reduce { a, b -> a * b }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("com/qverkk/aoc/day9/Day09_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 1134)

    val input = readInput("com/qverkk/aoc/day9/Day09")
    println(part1(input))
    println(part2(input))
}

private fun List<List<Int>>.getNextIncrementedNumber(
    expectedNumber: Int,
    rowIndex: Int,
    columnIndex: Int,
    currentOccurances: MutableList<Occurance>
): List<Occurance> {
    if (expectedNumber == 9) {
        return emptyList()
    }
    val left = getNumberAt(rowIndex, columnIndex - 1)
    val right = getNumberAt(rowIndex, columnIndex + 1)
    val up = getNumberAt(rowIndex + 1, columnIndex)
    val down = getNumberAt(rowIndex - 1, columnIndex)
    val res = listOfNotNull(
        if (left != null) Occurance(rowIndex, columnIndex - 1, left) else null,
        if (right != null) Occurance(rowIndex, columnIndex + 1, right) else null,
        if (up != null) Occurance(rowIndex + 1, columnIndex, up) else null,
        if (down != null) Occurance(rowIndex - 1, columnIndex, down) else null
    ).filter { occurance -> occurance.number == expectedNumber }
        .filter { !currentOccurances.contains(it) }
    currentOccurances.addAll(res)
    return res
        .map { occurance: Occurance ->
            val ttt = getNextIncrementedNumber(
                expectedNumber + 1,
                occurance.row,
                occurance.column,
                currentOccurances
            )
            currentOccurances.addAll(ttt)

            mutableListOf(occurance).apply {
                this.addAll(ttt)
            }
        }.flatten()
}

data class Occurance(
    val row: Int,
    val column: Int,
    val number: Int
)

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
