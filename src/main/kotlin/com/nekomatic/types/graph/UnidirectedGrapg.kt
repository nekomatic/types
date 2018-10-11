package com.nekomatic.types.graph

open class UnidirectedGrapg<V : Any>(adjList: Map<V, List<V>> = mapOf()) : DirectedGraph<V>(adjList) {
    override fun addEdge(fromVertex: V, toVertex: V): UnidirectedGrapg<V> {
        val aNeighbours: List<V> = listOf(toVertex) + neighbours(fromVertex)
        val bNeighbours: List<V> = listOf(fromVertex) + neighbours(toVertex)
        return UnidirectedGrapg(adjList + (fromVertex to aNeighbours) + (toVertex to bNeighbours))
    }
}