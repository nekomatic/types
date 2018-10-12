package com.nekomatic.types

interface IQueue<T : Any> {
    val size: Int
    val degree: Int
    val maxWidth: Int
    fun enqueue(element: T): IQueue<T>
    fun dequeue(): Pair<Option<T>, IQueue<T>>
    fun enqueueRight(elements: List<T>): IQueue<T>
    fun enqueueLeft(elements: List<T>): IQueue<T>
    fun peekOut(): Option<T>
    fun peekIn(): Option<T>
}