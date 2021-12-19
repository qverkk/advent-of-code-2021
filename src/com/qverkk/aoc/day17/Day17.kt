package com.qverkk.aoc.day17

import com.qverkk.aoc.utils.readInput
import java.awt.Point
import kotlin.math.absoluteValue

fun main() {
    fun parseInput(input: String): MutableList<Point> {
        val split = input.replace("target area: x=", "")
            .replace(", y=", " ")
            .split(" ")
        val x = split[0]
            .split("..")
            .map { it.toInt() }
        val y = split[1]
            .split("..")
            .map { it.toInt() }

        val pointsToHit = mutableListOf<Point>()
        for (i in x[0]..x[1]) {
            for (j in y[0]..y[1]) {
                pointsToHit.add(Point(i, j))
            }
        }
        return pointsToHit
    }

    fun part1(input: String): Int {
        val pointsToHit = parseInput(input)

        val minX = pointsToHit.minOf { it.x }
        val minY = pointsToHit.minOf { it.y }
        val startingPoints = mutableListOf<Point>()
        for (i in 1..minX) {
            for (j in 1..minY.absoluteValue) {
                startingPoints.add(Point(i, j))
            }
        }

        val possibleHits = startingPoints.map { p ->
            var moveBy = Point(p.x, p.y)
            var current = Point(p.x, p.y)
            val result = mutableListOf(current)
            while (current.y > minY) {
                val newX = when {
                    moveBy.x == 0 -> 0
                    moveBy.x > 0 -> moveBy.x - 1
                    else -> moveBy.x + 1
                }
                val newY = moveBy.y - 1
                moveBy = Point(newX, newY)
                current = Point(current.x + moveBy.x, current.y + moveBy.y)
                result.add(current)
                if (pointsToHit.contains(current)) {
                    return@map result
                }
            }
            emptyList()
        }

        return possibleHits.filter { it.isNotEmpty() }
            .map { p -> p.maxOf { it.y } }
            .maxOf { it }
    }

    fun part2(input: String): Int {
        val pointsToHit = parseInput(input)

        val minX = pointsToHit.maxOf { it.x }
        val minY = pointsToHit.minOf { it.y }
        val startingPoints = mutableListOf<Point>()
        for (i in 1..minX) {
            for (j in minY..minY.absoluteValue) {
                startingPoints.add(Point(i, j))
            }
        }

        val possibleHits = startingPoints.map { p ->
            var moveBy = Point(p.x, p.y)
            var current = Point(p.x, p.y)
            val result = mutableListOf(current)
            while (current.y >= minY) {
                if (pointsToHit.contains(current)) {
                    return@map result
                }
                val newX = when {
                    moveBy.x == 0 -> 0
                    moveBy.x > 0 -> moveBy.x - 1
                    else -> moveBy.x + 1
                }
                val newY = moveBy.y - 1
                moveBy = Point(newX, newY)
                current = Point(current.x + moveBy.x, current.y + moveBy.y)
                result.add(current)
            }
            emptyList()
        }

        return possibleHits.count { it.isNotEmpty() }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("com/qverkk/aoc/day17/Day17_test").first()
    println(part1(testInput))
    check(part1(testInput) == 45)
    println(part2(testInput))
    check(part2(testInput) == 112)

    val input = readInput("com/qverkk/aoc/day17/Day17").first()
    println(part1(input))
    println(part2(input))
}
