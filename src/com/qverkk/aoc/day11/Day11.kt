package com.qverkk.aoc.day11

import com.qverkk.aoc.utils.readInput

data class Octopus(
    val x: Int,
    val y: Int,
    var value: Int,
    var flashed: Boolean = false,
    var flashedTimes: Long = 0,
    var reset: Boolean = false
) {

    fun findAdajecent() = listOf(
        x + 1 to y,
        x - 1 to y,
        x to y + 1,
        x to y - 1,
        x - 1 to y - 1,
        x + 1 to y - 1,
        x - 1 to y + 1,
        x + 1 to y + 1
    )

    private fun flash() {
        if (value > 9 && !flashed) {
            flashed = true
            flashedTimes += 1
            value = 0
        }
    }

    fun increment() {
        if (!flashed) {
            value += 1
        }
    }

    fun reset() {
        flashed = false
        reset = true
    }

    fun tick(): Boolean {
        flash()
        if (reset && flashed) {
            reset = false
            return true
        }
        return false
    }

    fun inOneOfThePositions(adajecentPositions: List<Pair<Int, Int>>): Boolean {
        return adajecentPositions.any { it.first == x && it.second == y }
    }
}

fun flashAdajecent(octopuses: List<Octopus>, currentOctopus: Octopus): List<Octopus> {
    val adajecentPositions = currentOctopus.findAdajecent()
    val filter = octopuses
        .filter { it.inOneOfThePositions(adajecentPositions) }
    return filter
        .map {
            it.increment()
            it
        }
        .map {
            if (it.tick()) {
                flashAdajecent(octopuses, it)
            }
            it
        }
}

fun main() {

    fun octopusSequence(octopuses: List<Octopus>): Sequence<List<Octopus>> {
        val seq = generateSequence(octopuses) {
            it.forEach { octopus ->
                octopus.reset()
                octopus.increment()
            }
            it.forEach { octopus ->
                if (octopus.tick()) {
                    flashAdajecent(octopuses, octopus)
                }
            }
            octopuses
        }
        return seq
    }

    fun parseOctopuses(input: List<String>) = input.mapIndexed { rowIndex, row ->
        row.toCharArray()
            .mapIndexed { colIndex, char ->
                Octopus(rowIndex, colIndex, char.digitToInt())
            }
    }.flatten()

    fun part1(input: List<String>): Long {
        val octopuses = parseOctopuses(input)

        val seq = octopusSequence(octopuses).take(101)
        return seq
            .last()
            .sumOf { it.flashedTimes }
    }

    fun part2(input: List<String>): Int {
        val octopuses = parseOctopuses(input)

        val seq = octopusSequence(octopuses)
        return seq.take(1000)
            .indexOfFirst { it.all { o -> o.flashed } }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("com/qverkk/aoc/day11/Day11_test")
    check(part1(testInput) == 1656L)
    check(part2(testInput) == 195)

    val input = readInput("com/qverkk/aoc/day11/Day11")
    println(part1(input))
    println(part2(input))
}
