package com.qverkk.aoc.day10

import com.qverkk.aoc.utils.readInput
import java.util.Deque
import java.util.LinkedList

fun main() {
    val validOpenings = listOf('(', '[', '{', '<')

    fun Char.getOpeningCharacter() = when (this) {
        ')' -> '('
        ']' -> '['
        '}' -> '{'
        '>' -> '<'
        else -> throw RuntimeException("Shouldnt get here")
    }

    fun Char.getClosingCharacter() = when (this) {
        '(' -> ')'
        '[' -> ']'
        '{' -> '}'
        '<' -> '>'
        else -> throw RuntimeException("Shouldnt get here")
    }

    fun Char.getPoints() = when (this) {
        ')' -> 3
        ']' -> 57
        '}' -> 1197
        '>' -> 25137
        else -> throw RuntimeException("Shouldnt get here")
    }

    fun part1(input: List<String>): Int {
        return input.map {
            val openings: Deque<Char> = LinkedList()
            it.toCharArray()
                .forEachIndexed { index, c ->
                    if (validOpenings.contains(c)) {
                        openings.addLast(c)
                    } else {
                        val lastOpening = openings.removeLast()
                        if (lastOpening != c.getOpeningCharacter()) {
                            return@map c
                        }
                    }
                }
        }.filterIsInstance<Char>()
            .groupingBy { it }
            .eachCount()
            .map { it.key.getPoints() * it.value }
            .sum()
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("com/qverkk/aoc/day10/Day10_test")
    check(part1(testInput) == 26397)
    // check(part2(testInput) == 1134)

    val input = readInput("com/qverkk/aoc/day10/Day10")
    println(part1(input))
    println(part2(input))
}
