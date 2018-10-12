package com.nekomatic.types

interface IQueue<T : Any> {
    val size: Int
    val degree: Int
    val maxWidth: Int
    val items: List<T>
    fun enqueue(item: T): IQueue<T>
    fun dequeue(): Pair<Option<T>, IQueue<T>>
    fun enqueueRight(list: List<T>): IQueue<T>
    fun enqueueLeft(list: List<T>): IQueue<T>
    fun peekOut(): Option<T>
    fun peekIn(): Option<T>
}