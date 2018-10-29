package com.nekomatic.types.graph

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertAll

internal class SortTest {


    val g = DirectedGraph<String>()
            .addEdge("Logging", "Game")
            .addEdge("Logging", "Networking")
            .addEdge("Networking", "Game")
            .addEdge("Commons", "Physics")
            .addEdge("Commons", "Math")
            .addEdge("Math", "Physics")
            .addEdge("Math", "Graphics")
            .addEdge("Math", "AI Engine")
            .addEdge("Physics", "Game")
            .addEdge("Graphics", "Game")
            .addEdge("AI Engine", "Game")

    fun Map<String, Int>.compare(bigger: String, smaller: String): Boolean {
        val s = this[smaller]!!
        val b = this[bigger]!!
        return s < b
    }

    @Test
    fun khanSort() {
        val sorted = g.khanSort()
        val dict = sorted.map { it to sorted.indexOf(it) }.toMap()
        assertAll(
                { assertTrue(dict.compare("Game", "AI Engine")) },
                { assertTrue(dict.compare("Game", "Graphics")) },
                { assertTrue(dict.compare("Game", "Physics")) },
                { assertTrue(dict.compare("Game", "Logging")) },
                { assertTrue(dict.compare("Game", "Networking")) },
                { assertTrue(dict.compare("AI Engine", "Math")) },
                { assertTrue(dict.compare("Graphics", "Math")) },
                { assertTrue(dict.compare("Physics", "Math")) },
                { assertTrue(dict.compare("Networking", "Logging")) },
                { assertTrue(dict.compare("Math", "Commons")) },
                { assertTrue(dict.compare("Physics", "Commons")) }
        )
    }

    @Test
    fun dfsSort() {
        val sorted = g.dfsSort()
        val dict = sorted.map { it to sorted.indexOf(it) }.toMap()
        assertAll(
                { assertTrue(dict.compare("Game", "AI Engine")) },
                { assertTrue(dict.compare("Game", "Graphics")) },
                { assertTrue(dict.compare("Game", "Physics")) },
                { assertTrue(dict.compare("Game", "Logging")) },
                { assertTrue(dict.compare("Game", "Networking")) },
                { assertTrue(dict.compare("AI Engine", "Math")) },
                { assertTrue(dict.compare("Graphics", "Math")) },
                { assertTrue(dict.compare("Physics", "Math")) },
                { assertTrue(dict.compare("Networking", "Logging")) },
                { assertTrue(dict.compare("Math", "Commons")) },
                { assertTrue(dict.compare("Physics", "Commons")) }
        )
    }
}