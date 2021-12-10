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

    fun Char.getCorruptionPoints() = when (this) {
        ')' -> 3
        ']' -> 57
        '}' -> 1197
        '>' -> 25137
        else -> throw RuntimeException("Shouldnt get here")
    }

    fun Char.getAutocompletePoints() = when (this) {
        ')' -> 1
        ']' -> 2
        '}' -> 3
        '>' -> 4
        else -> throw RuntimeException("Shouldnt get here")
    }

    fun part1(input: List<String>): Int {
        return input.map {
            val openings: Deque<Char> = LinkedList()
            it.toCharArray()
                .forEach { c ->
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
            .map { it.key.getCorruptionPoints() * it.value }
            .sum()
    }

    fun List<String>.getCorruptedLines() = this.map {
        val openings: Deque<Char> = LinkedList()
        it.toCharArray()
            .forEach { c ->
                if (validOpenings.contains(c)) {
                    openings.addLast(c)
                } else {
                    val lastOpening = openings.removeLast()
                    if (lastOpening != c.getOpeningCharacter()) {
                        return@map it to c
                    }
                }
            }
    }.filterIsInstance<Pair<String, Char>>()

    fun part2(input: List<String>): Long {
        val corrupted = input.getCorruptedLines()
        val res = input.asSequence().filter { !corrupted.any { corr -> corr.first == it } }.map {
            val openings: Deque<Char> = LinkedList()
            it.toCharArray()
                .forEach { c ->
                    if (validOpenings.contains(c)) {
                        openings.addLast(c)
                    } else {
                        val lastOpening = openings.lastOrNull()
                        if (lastOpening != null) {
                            openings.removeLast()
                        }

                        if (lastOpening != c.getOpeningCharacter()) {
                            openings.clear()
                            return@forEach
                        }
                    }
                }
            openings.map { char -> char.getClosingCharacter() }.reversed()
        }
            .filter { it.isNotEmpty() }
            .map { list ->
                var res = 0L
                list.forEach { char ->
                    res = res * 5 + char.getAutocompletePoints()
                }
                res
            }.sorted().toList()

        return res[(res.size / 2)]
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("com/qverkk/aoc/day10/Day10_test")
    check(part1(testInput) == 26397)
    check(part2(testInput) == 288957L)

    val input = readInput("com/qverkk/aoc/day10/Day10")
    println(part1(input))
    println(part2(input))
}
