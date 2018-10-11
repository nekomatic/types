@file:kotlin.jvm.JvmName("Traversal")

package com.nekomatic.types.graph

import com.nekomatic.types.Option
import com.nekomatic.types.Queue
import com.nekomatic.types.Stack

fun <V : Any> IGraph<V>.traversalDFSRec(start: V, visited: Set<V> = setOf(), f: (V) -> Unit): Set<V> =
        if (visited.contains(start)) {
            visited
        } else {
            f(start)
            this.neighbours(start).fold(visited + start) { v, n -> this.traversalDFSRec(n, v, f) }
        }

fun <V : Any> IGraph<V>.traversalDFS(start: V, f: (V) -> Unit) {
    generateSequence(Pair(Stack(start), setOf(start)))
    { (stk, visited) ->
        val (vertex, s) = stk.pop()
        when (vertex) {
            is Option.Some -> {
                val newStk = s.pushReversed(this.neighbours(vertex.value).filterNot { v -> visited.contains(v) })
                val newVisited = this.neighbours(vertex.value).toSet() + visited
                Pair(newStk, newVisited)
            }
            Option.None -> null
        }
    }.takeWhile { (s, _) -> s.peek() is Option.Some }
            .forEach { (s, _) -> f((s.peek() as Option.Some).value) }
}


fun <V : Any> IGraph<V>.traversalBFS(start: V, f: (V) -> Unit) {
    generateSequence(Pair(Queue(start), setOf(start)))
    { (queue, visited) ->
        val (vertex, q) = queue.dequeue()
        when (vertex) {
            is Option.Some -> {
                val newStk = q.enqueueLeft(this.neighbours(vertex.value).filterNot { v -> visited.contains(v) })
                val newVisited = this.neighbours(vertex.value).toSet() + visited
                Pair(newStk, newVisited)
            }
            Option.None -> null
        }
    }.takeWhile { (s, _) -> s.peek() is Option.Some }
            .forEach { (s, _) -> f((s.peek() as Option.Some).value) }
}


