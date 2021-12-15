package com.qverkk.aoc.day15

import com.qverkk.aoc.utils.readInput
import java.util.LinkedList
import java.util.Queue

fun main() {
    fun part1(input: List<String>): Int {
        val map = input.mapIndexed { rowIndex, line ->
            line.mapIndexed { columnIndex, number ->
                (rowIndex to columnIndex) to number.digitToInt()
            }
        }.flatten().toMap()

        val firstStep = 0 to 0
        val pointsQueue: Queue<Pair<Int, Int>> = LinkedList(listOf(firstStep))
        val distances = mutableMapOf(firstStep to 0)

        while (pointsQueue.isNotEmpty()) {
            val current = pointsQueue.remove()
            val distanceToCurrentPoint = distances[current]!!

            current.neighbours()
                .filter { map[it] != null }
                .sortedBy { map[it] }
                .forEach {
                    val newDistance = distanceToCurrentPoint + map[it]!!

                    if (distances[it] == null || distances[it]!! > newDistance) {
                        distances[it] = newDistance
                        pointsQueue.add(it)
                    }
                }
        }
        return distances.values.last()
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("com/qverkk/aoc/day15/Day15_test")
    check(part1(testInput) == 40)

    val input = readInput("com/qverkk/aoc/day15/Day15")
    println(part1(input))
    println(part2(input))
}

fun Pair<Int, Int>.neighbours() = listOf(
    first to second + 1,
    first to second - 1,
    first + 1 to second,
    first - 1 to second
)
