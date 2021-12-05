package com.qverkk.aoc.day5

import com.qverkk.aoc.utils.readInput

data class Coordinate(
    val x: Int,
    val y: Int
)

enum class Direction(
    val xAddition: Int,
    val yAddition: Int
) {
    NE(-1, 1),
    NW(-1, -1),
    SE(1, 1),
    SW(1, -1);

    companion object {
        fun getDirection(begin: Coordinate, end: Coordinate): Direction {
            return if (begin.x > end.x) {
                if (begin.y > end.y) {
                    NW
                } else {
                    NE
                }
            } else {
                if (begin.y > end.y) {
                    SW
                } else {
                    SE
                }
            }
        }
    }
}

data class CoordinateRange(
    val begin: Coordinate,
    val end: Coordinate
) {
    fun horizontalPositionsCovered(): List<Coordinate> {
        val result = mutableListOf<Coordinate>()
        if (begin.x == end.x) {
            result.addAll(getHorizontalAndVerticalCoordinatesInRange(begin.y, end.y, true))
        }
        if (begin.y == end.y) {
            result.addAll(getHorizontalAndVerticalCoordinatesInRange(begin.x, end.x, false))
        }
        return result
    }

    fun diagonalPositionsCovered(): List<Coordinate> {
        if (begin.x == end.x || begin.y == end.y) {
            return emptyList()
        }
        val direction = Direction.getDirection(begin, end)
        val result = mutableListOf<Coordinate>()
        var currentX = begin.x
        var currentY = begin.y
        result.add(Coordinate(currentX, currentY))
        while (currentX != end.x && currentY != end.y) {
            currentX += direction.xAddition
            currentY += direction.yAddition
            result.add(Coordinate(currentX, currentY))
        }
        return result
    }

    private fun getHorizontalAndVerticalCoordinatesInRange(beginning: Int, ending: Int, x: Boolean): List<Coordinate> {
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
            .flatMap { it.horizontalPositionsCovered() }
            .groupingBy { it }
            .eachCount()
            .count { it.value > 1 }
    }

    fun part2(input: List<String>): Int {
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
            .flatMap { it.horizontalPositionsCovered() + it.diagonalPositionsCovered() }
            .groupingBy { it }
            .eachCount()
            .count { it.value > 1 }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("com/qverkk/aoc/day5/Day05_test")
    check(part1(testInput) == 5)
    check(part2(testInput) == 12)

    val input = readInput("com/qverkk/aoc/day5/Day05")
    println(part1(input))
    println(part2(input))
}
