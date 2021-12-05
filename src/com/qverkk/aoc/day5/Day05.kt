package com.qverkk.aoc.day5

import com.qverkk.aoc.utils.readInput

data class Coordinate(
    val x: Int,
    val y: Int
)

data class CoordinateRange(
    val begin: Coordinate,
    val end: Coordinate
) {
    fun positionsCovered(): List<Coordinate> {
        val result = mutableListOf<Coordinate>()
        if (begin.x == end.x) {
            result.addAll(getCoordinatesInRange(begin.y, end.y, true))
        }
        if (begin.y == end.y) {
            result.addAll(getCoordinatesInRange(begin.x, end.x, false))
        }
        return result
    }

    private fun getCoordinatesInRange(beginning: Int, ending: Int, x: Boolean): List<Coordinate> {
        var startY = beginning
        var endY = ending
        if (startY > endY) {
            startY = ending
            endY = beginning
        }

        return (startY..endY).map {
            if (x) {
                Coordinate(begin.x, it)
            } else {
                Coordinate(it, begin.y)
            }
        }
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        val coordinateRanges = input.map {
            val split = it
                .replace("->", ",")
                .split(",")
                .map { number -> number.trim().toInt() }
            CoordinateRange(
                Coordinate(split[0], split[1]),
                Coordinate(split[2], split[3]),
            )
        }

        return coordinateRanges
            .flatMap { it.positionsCovered() }
            .groupingBy { it }
            .eachCount()
            .count { it.value > 1 }
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("com/qverkk/aoc/day5/Day05_test")
    check(part1(testInput) == 5)

    val input = readInput("com/qverkk/aoc/day5/Day05")
    println(part1(input))
    println(part2(input))
}
