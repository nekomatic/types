package com.nekomatic.types.graph

open class DirectedGraph<V : Any>(val adjList: Map<V, List<V>> = mapOf()) : IGraph<V> {

    override val vertices: List<V>
            by lazy { adjList.keys.toList() }

    override val edges: List<Pair<V, V>>
            by lazy { adjList.flatMap { (fromV: V, neighbours: List<V>) -> neighbours.map { toV -> Pair(fromV, toV) } } }


    override fun addEdge(fromVertex: V, toVertex: V): DirectedGraph<V> {
        val aNeighbours: List<V> = listOf(toVertex) + neighbours(fromVertex)
        return DirectedGraph(adjList + (fromVertex to aNeighbours))
    }

    override fun neighbours(vertex: V): List<V> = adjList.getOrDefault(vertex, listOf())
}