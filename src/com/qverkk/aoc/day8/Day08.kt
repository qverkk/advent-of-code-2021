package com.qverkk.aoc.day8

import com.qverkk.aoc.utils.readInput

private fun String.sort() = this.toSortedSet().joinToString("")

fun main() {

    fun part1(input: List<String>): Int {
        val observedNumbers = input.map { it.split(" | ") }
        return observedNumbers.sumOf { (observedDigits, outputtedDigits) ->
            val availableUniqueNumbers = observedDigits.split(" ")
                .filter {
                    val length = it.length
                    length == 2 || length == 3 || length == 4 || length == 7
                }.map { it.length }
            outputtedDigits.split(" ")
                .count { availableUniqueNumbers.contains(it.length) }
        }
    }

    fun extractNumbers(numbers: List<String>): Map<String, Int> {
        val numbersCopy = mutableListOf<String>().apply { addAll(numbers) }
        val one = numbersCopy.first { it.length == 2 }
        numbersCopy.remove(one)

        val seven = numbers.first { it.length == 3 }
        numbersCopy.remove(seven)

        val four = numbersCopy.first { it.length == 4 }
        numbersCopy.remove(four)

        val eight = numbersCopy.first { it.length == 7 }
        numbersCopy.remove(eight)

        val nine = numbersCopy.first { four.all { c -> it.contains(c) } }
        numbersCopy.remove(nine)

        val zero = numbersCopy.first { it.length == 6 && one.all { c -> it.contains(c) } }
        numbersCopy.remove(zero)

        val three = numbersCopy.first { one.all { c -> it.contains(c) } }
        numbersCopy.remove(three)

        val six = numbersCopy.first { it.length == 6 }
        numbersCopy.remove(six)

        val five = numbersCopy.first { it.all { c -> six.contains(c) } }
        numbersCopy.remove(five)

        val two = numbersCopy.first()

        return mapOf(
            one.sort() to 1,
            two.sort() to 2,
            three.sort() to 3,
            four.sort() to 4,
            five.sort() to 5,
            six.sort() to 6,
            seven.sort() to 7,
            eight.sort() to 8,
            nine.sort() to 9,
            zero.sort() to 0
        )
    }

    fun part2(input: List<String>): Int {
        val numbers = input.map { line -> line.split(" | ").map { it.split(" ") } }
        return numbers.map { list ->
            val extractedNumbers = extractNumbers(list[0])
            list[1].map {
                val i = extractedNumbers[it.sort()]
                i
            }.joinToString("").toInt()
        }.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("com/qverkk/aoc/day8/Day08_test")
    println(part1(testInput))
    check(part1(testInput) == 26)

    val input = readInput("com/qverkk/aoc/day8/Day08")
    println(part1(input))
    println(part2(input))
}
