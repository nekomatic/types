package com.nekomatic.types.graph

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertAll

internal class DirectedGraphTest {

    val g = DirectedGraph<String>()
            .addEdge("London", "Lisbon")
            .addEdge("Lisbon", "Mardit")
            .addEdge("Madrit", "London")
            .addEdge("Madrit", "Rome")
            .addEdge("Rome", "London")
            .addEdge("Paris", "Rome")

    @Test
    fun getVertices() {
        val v = g.vertices
        assertAll(
                { assertEquals(5, v.size) },
                { assertEquals(1, v.filter { it == "Rome" }.size) },
                { assertEquals(5, v.distinct().size) }
        )
    }

    @Test
    fun getEdges() {
        val e = g.edges
        assertAll(
                { assertTrue(e.contains("Madrit" to "Rome")) },
                { assertTrue(e.contains("London" to "Lisbon")) },
                { assertTrue(e.contains("Paris" to "Rome")) },
                { assertFalse(e.contains("Rome" to "Paris")) },
                { assertFalse(e.contains("Paris" to "London")) },
                { assertEquals(1, e.filter { it == "Paris" to "Rome" }.size) }
        )
    }

    @Test
    fun addEdge() {
        val testGraph = g.addEdge("London", "Paris")
        assertAll(
                { assertEquals(7, testGraph.edges.size) },
                { assertEquals(5, testGraph.vertices.size) },
                { assertEquals(1, testGraph.neighbours("Lisbon").size) },
                { assertEquals(2, testGraph.neighbours("London").size) }
        )
    }

    @Test
    fun neighbours() {
        assertAll(
                { assertEquals(listOf("Rome", "London"), g.neighbours("Madrit")) },
                { assertEquals(listOf("Rome"), g.neighbours("Paris")) },
                { assertEquals(listOf("Lisbon"), g.neighbours("London")) }
        )
    }
}