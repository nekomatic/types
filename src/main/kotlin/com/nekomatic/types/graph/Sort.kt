package com.nekomatic.types.graph

import com.nekomatic.types.Option
import com.nekomatic.types.map

fun <V : Any> IGraph<V>.khanSort(): List<V> {
    val zeroMap =
            this.vertices.map { it to 0 }.toMap()

    val inDegreeMap: Map<V, Int> = zeroMap + this.edges.asSequence().groupBy { it.second }.map { (n, l) -> n to l.size }.toList()
    val startNodes = inDegreeMap.filter { it.value == 0 }.keys.toList()

    fun topologicalSort(sNodes: List<V>, inDegMap: Map<V, Int>): List<V> =
            if (sNodes.isEmpty()) listOf<V>()
            else {
                val nodeMs = this.neighbours(sNodes.first())
                val newMCounts = nodeMs.map { n -> n to (inDegMap[n]!! - 1) }.toMap()
                val newSNodes = sNodes.drop(1) + nodeMs.filter { newMCounts[it] == 0 }
                listOf(sNodes.first()) + topologicalSort(newSNodes, inDegMap + newMCounts)
            }
    return topologicalSort(startNodes, inDegreeMap)
}


fun <V : Any> IGraph<V>.dfsSort(): List<V> {
    data class DfsStep<V : Any>(val visited: Set<V> = setOf<V>(), val sort: List<V> = listOf<V>())

    fun sort(node: V, dfsStep: DfsStep<V>): DfsStep<V> =
            if (dfsStep.visited.contains(node)) dfsStep
            else {
                val preDfsStep = dfsStep.copy(visited = dfsStep.visited + node)
                val postDfsStep = this.neighbours(node).fold(preDfsStep) { step, n -> sort(n, step) }
                postDfsStep.copy(sort = listOf(node) + postDfsStep.sort)
            }
    return this.vertices.fold(DfsStep<V>()) { step, n -> sort(n, step) }.sort
}

