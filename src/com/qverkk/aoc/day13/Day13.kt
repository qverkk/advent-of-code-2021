package com.qverkk.aoc.day13

import com.qverkk.aoc.utils.readInput

enum class FoldDirection {
    UP,
    LEFT;

    companion object {
        fun parse(str: String): FoldDirection {
            return when (str) {
                "y" -> UP
                "x" -> LEFT
                else -> throw RuntimeException()
            }
        }
    }
}

data class Fold(
    val value: Int,
    val foldDirection: FoldDirection
)

fun calculateNewHeight(value: Int, foldValue: Int): Int {
    return foldValue - (value - foldValue)
}

fun fold(fold: Fold, points: List<Pair<Int, Int>>): List<Pair<Int, Int>> {
    return points.map {
        when (fold.foldDirection) {
            FoldDirection.UP -> {
                if (it.second > fold.value) {
                    it.first to calculateNewHeight(it.second, fold.value)
                } else {
                    it
                }
            }
            else -> {
                if (it.first > fold.value) {
                    calculateNewHeight(it.first, fold.value) to it.second
                } else {
                    it
                }
            }
        }
    }.distinct()
}

fun countDots(foldedPoints: List<Pair<Int, Int>>): Int {
    val maxWidth = foldedPoints.maxOf { it.first }
    val maxHeight = foldedPoints.maxOf { it.second }
    var result = 0
    for (i in 0..maxWidth) {
        for (j in 0..maxHeight) {
            if (foldedPoints.contains(i to j)) {
                result++
            }
        }
    }
    return result
}

fun main() {

    fun part1(input: List<String>): Int {
        val folds = input
            .filter { it.startsWith("fold along") }
            .map {
                it.replace("fold along ", "")
                    .replace("=", " ").split(" ")
            }.map {
                Fold(it[1].toInt(), FoldDirection.parse(it[0]))
            }

        val points = input
            .filter { !it.startsWith("fold along") && it.isNotBlank() }
            .map { line ->
                val split = line.split(",").map { it.toInt() }
                split[0] to split[1]
            }
        val foldedPoints = fold(folds.first(), points)

        return countDots(foldedPoints)
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("com/qverkk/aoc/day13/Day13_test")
    check(part1(testInput) == 17)
    // check(part2(testInput) == 103)

    val input = readInput("com/qverkk/aoc/day13/Day13")
    println(part1(input))
    // println(part2(input))
}
