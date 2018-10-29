package com.nekomatic.types.graph

import com.nekomatic.types.Monoid

class WeightedGraph<V : Any,W:Any>(val adjList: Map<V, List<WeightedEdge<V, W>>>, val monoid:Monoid<W>) : IGraph<V> {
    override val vertices: List<V>
            by lazy { adjList.keys.toList() }

    override val edges: List<Pair<V, V>>
            by lazy { adjList.flatMap { (vertex, edges) -> edges.map { edge -> (vertex to edge.destination) } }.toList() }


    fun addEdge(vertex: V, weightedEdge: WeightedEdge<V,W>): WeightedGraph<V,W> {
        val aNeighbours = listOf(weightedEdge) + adjList.getOrDefault(vertex, listOf())
        return WeightedGraph(adjList + (vertex to aNeighbours), monoid)
    }

    override fun addEdge(fromVertex: V, toVertex: V): WeightedGraph<V,W> = addEdge(fromVertex, WeightedEdge(toVertex, monoid.sumIdentity))

    override fun neighbours(vertex: V): List<V> = adjList.getOrDefault(vertex, listOf()).map { it.destination }

    fun neighboursWeights(vertex: V): List<WeightedEdge<V,W>> = adjList.getOrDefault(vertex, listOf())
}