package com.nekomatic.types.graph

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class TraversalTest {

    val g = DirectedGraph<String>()
            .addEdge("A", "B")
            .addEdge("B", "C")
            .addEdge("C", "E")
            .addEdge("C", "D")
            .addEdge("A", "G")
            .addEdge("G", "H")
            .addEdge("H", "F")
            .addEdge("F", "A")
            .addEdge("D", "E")

    @Test
    fun traversalDFSRec() {
        val list = mutableListOf<String>()
        g.traversalDFSRec("A") { v -> list.add(v) }
        assertEquals(listOf("A", "G", "H", "F", "B", "C", "D", "E"), list)

    }

    @Test
    fun traversalDFS() {
        val list = mutableListOf<String>()
        g.traversalDFS("A") { v -> list.add(v) }
        assertEquals(listOf("A", "G", "H", "F", "B", "C", "D", "E"), list)
    }

    @Test
    fun traversalBFS() {
        val list = mutableListOf<String>()
        g.traversalBFS("A") { v -> list.add(v) }
        assertEquals(listOf("A", "G", "B", "H", "C", "F", "D", "E"), list)
    }
}