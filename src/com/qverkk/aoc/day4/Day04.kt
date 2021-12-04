package com.qverkk.aoc.day4

import com.qverkk.aoc.utils.readInput

data class BingoBoardNumber(
    val number: Int,
    var selected: Boolean = false
) {
    override fun toString(): String {
        return number.toString()
    }
}

data class BingoBoard(
    val numbers: List<List<BingoBoardNumber>>
) {
    fun notSelectedNumbersSum(): Int = numbers
        .sumOf { row ->
            row
                .filter { !it.selected }
                .sumOf { it.number }
        }

    fun check(number: Int): Boolean {
        numbers.forEach { row ->
            row.find { it.number == number }?.selected = true
        }

        return didWin()
    }

    private fun didWin(): Boolean {
        return winningRow() || winningColumn()
    }

    private fun winningColumn(): Boolean {
        repeat(5) { index ->
            val winningRow = numbers.map { it[index] }
                .all { it.selected }
            if (winningRow) {
                return true
            }
        }
        return false
    }

    private fun winningRow(): Boolean {
        return numbers.firstOrNull { row ->
            row.all { it.selected }
        } != null
    }

    override fun toString(): String {
        return numbers.joinToString(
            separator = " ",
            postfix = "\n"
        ) { it.joinToString(postfix = "\n") { number -> number.toString() } }
    }
}

fun main() {
    val NUMBER_REGEX = "\\d+".toPattern()

    fun part1(input: List<String>): Int {
        val bingoSequentialNumbers = input.first().split(",").map { it.toInt() }

        val boards = mutableListOf<BingoBoard>()
        var currentNumbers = mutableListOf<List<BingoBoardNumber>>()
        input.drop(2).forEach {
            when (it.isEmpty()) {
                true -> {
                    boards.add(BingoBoard(currentNumbers))
                    currentNumbers = mutableListOf()
                }
                else -> {
                    val matcher = NUMBER_REGEX.matcher(it)
                    val rowResult = mutableListOf<BingoBoardNumber>()
                    while (matcher.find()) {
                        val currentNumber = matcher.group().toInt()
                        rowResult.add(BingoBoardNumber(currentNumber))
                    }
                    currentNumbers.add(rowResult)
                }
            }
        }
        boards.add(BingoBoard(currentNumbers))

        var currentNumber: Int = 0
        repeat(bingoSequentialNumbers.size) { index ->
            val currentBoard = boards.firstOrNull { board ->
                currentNumber = bingoSequentialNumbers[index]
                board.check(currentNumber)
            }
            if (currentBoard != null) {
                return currentBoard.notSelectedNumbersSum() * currentNumber
            }
        }

        return 0
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("com/qverkk/aoc/day4/Day04_test")
    check(part1(testInput) == 4512)

    val input = readInput("com/qverkk/aoc/day4/Day04")
    println(part1(input))
    println(part2(input))
}
