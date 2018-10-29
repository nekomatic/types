package com.nekomatic.types.graph

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

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
//            .addEdge("Game", "Logging")

    @Test
    fun khanSort() {
        val sorted = g.khanSort()
        val expected = listOf("Logging", "Commons", "Networking", "Math", "AI Engine", "Graphics", "Physics", "Game")
        assertEquals(expected, sorted)
    }

    @Test
    fun dfsSort() {
        val sorted = g.dfsSort()
        val expected = listOf("Logging", "Networking", "Commons", "Math", "AI Engine", "Graphics", "Physics", "Game")
        assertEquals(expected, sorted)
    }
}