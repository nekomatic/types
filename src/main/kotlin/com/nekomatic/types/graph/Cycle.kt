package com.nekomatic.types.graph

import com.nekomatic.types.Tuple02
import com.nekomatic.types.Tuple03

fun <V : Any> IGraph<V>.dfsCyclic(): Boolean {

    data class DfsCycleResult<V>(val visited: Set<V>, val isCyclic: Boolean = false)

    fun containsCycleDfs(
            vertex: V,
            graph: IGraph<V>,
            visited: Set<V> = setOf(),
            ancestors: Set<V> = setOf()
    ): DfsCycleResult<V> =
            when {
                ancestors.contains(vertex) -> DfsCycleResult(visited, true)
                visited.contains(vertex) -> DfsCycleResult(visited)
                else -> {
                    val neighbours = graph.neighbours(vertex)
                    neighbours.fold(DfsCycleResult(visited + vertex)) { dcr, ver ->
                        if (dcr.isCyclic) dcr
                        else containsCycleDfs(
                                vertex = ver,
                                graph = graph,
                                visited = dcr.visited,
                                ancestors = ancestors + vertex
                        )
                    }
                }
            }
    val startNodes = this.vertices.filter { v -> this.edges.all { e -> e.second != v } }
    return startNodes.isEmpty() || startNodes.any { n -> containsCycleDfs(n, this).isCyclic }
}