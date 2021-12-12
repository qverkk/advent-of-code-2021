package com.qverkk.aoc.day12

import com.qverkk.aoc.utils.readInput

data class TunnelConnection(
    private val entry: String,
    private val ending: String
) {

    companion object {
        fun parse(line: String): TunnelConnection {
            val split = line.split("-")
            return TunnelConnection(split[0], split[1])
        }
    }

    fun connectedWith(cave: String) = when (cave) {
        entry -> ending
        ending -> entry
        else -> ""
    }
}

class TunnelPath private constructor(val path: List<String>, val alreadyVisitedSmallCave: Boolean) {
    constructor() : this(listOf("start"), false)

    val currentTunnel: String = path.last()

    val traversingFinished = currentTunnel == "end"

    fun alreadyVisited(cave: String) = path.contains(cave)

    fun addCave(cave: String) = TunnelPath(path + cave, cave.isSmall() && alreadyVisited(cave))

    private fun String.isSmall() = this[0].isLowerCase() && this != "start"
}

private fun List<TunnelConnection>.cavesConnectedTo(cave: String) =
    this.map { it.connectedWith(cave) }.filter { it.isNotBlank() }

private fun String.isLarge() = this[0].isUpperCase()

fun main() {
    fun part1(input: List<String>): Int {
        val connections = input.map {
            TunnelConnection.parse(it)
        }

        var count = 0
        var paths = listOf(TunnelPath())
        while (paths.isNotEmpty()) {
            count += paths.count { it.traversingFinished }
            paths = paths
                .filter { !it.traversingFinished }
                .flatMap { path ->
                    connections.cavesConnectedTo(path.currentTunnel)
                        .filter { it != "start" }
                        .filter { it.isLarge() || !path.alreadyVisited(it) }
                        .map { path.addCave(it) }
                }
        }

        return count
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("com/qverkk/aoc/day12/Day12_test")
    check(part1(testInput) == 19)
    // check(part2(testInput) == 195)

    val input = readInput("com/qverkk/aoc/day12/Day12")
    println(part1(input))
    println(part2(input))
}
