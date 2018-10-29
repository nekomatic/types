package com.nekomatic.types.graph

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertAll

internal class CycleTest {

    val acyclic = DirectedGraph<String>()
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


    val cyclic = DirectedGraph<String>()
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
            .addEdge("Game", "Logging")

    @Test
    fun dfsCyclic() {
        assertAll(
                { assertFalse(acyclic.dfsCyclic()) },
                { assertTrue(cyclic.dfsCyclic()) }
        )
    }
}