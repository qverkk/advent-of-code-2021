package com.qverkk.aoc.day6

import com.qverkk.aoc.utils.readInput

data class Lanternfish(
    var daysUntillReproduction: Int
) {
    fun reset() {
        daysUntillReproduction = 6
    }

    fun canReproduce(): Boolean {
        return daysUntillReproduction == 0
    }

    fun dayPassed() {
        daysUntillReproduction--
    }

    companion object {
        fun newBorn() = Lanternfish(8)
    }
}


fun breed(fishes: List<Long>): List<Long> {
    return fishes.mapIndexed { index, _ ->
        when(index) {
            6 -> fishes[0] + fishes[7]
            8 -> fishes[0]
            else -> fishes[index + 1]
        }
    }
}

fun main() {
    fun parseInitialLanternFishes(input: String): MutableList<Lanternfish> {
        return input.split(",")
            .map { Lanternfish(it.toInt()) }
            .toMutableList()
    }

    fun part1(input: List<String>, days: Int): Int {
        val fishes = parseInitialLanternFishes(input.first())
        repeat(days) {
            val newFishes = mutableListOf<Lanternfish>()
            fishes.forEach {
                if (it.canReproduce()) {
                    newFishes.add(Lanternfish.newBorn())
                    it.reset()
                } else {
                    it.dayPassed()
                }
            }
            fishes.addAll(newFishes)
        }


        return fishes.size
    }

    fun part2(input: List<String>, days: Int): Long {
        val fishesAndDays = input.first()
            .split(",")
            .map { it.toInt() }
            .groupingBy { it }
            .eachCount()
            .let { fishesWithinADay -> List(9) { fishesWithinADay[it]?.toLong() ?: 0L } }

        return generateSequence(fishesAndDays, ::breed)
            .take(days + 1)
            .last()
            .sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("com/qverkk/aoc/day6/Day06_test")
    check(part1(testInput, 80) == 5934)
    check(part2(testInput, 256) == 26984457539)

    val input = readInput("com/qverkk/aoc/day6/Day06")
    println(part1(input, 80))
    println(part2(input, 256))
}
