package com.nekomatic.types.graph

interface IGraph<V : Any> {
    val vertices: List<V>
    val edges: List<Pair<V, V>>
    fun addEdge(fromVertex: V, toVertex: V): IGraph<V>
    fun neighbours(vertex: V): List<V>
}


