package com.nekomatic.types

data class Queue<T : Any>(private val inStack: Stack<T>, private val outStack: Stack<T>) {

    constructor() : this(Stack<T>(), Stack<T>())
    constructor(q: Queue<T>) : this(q.inStack, q.outStack)
    constructor(element: T) : this(Stack<T>(), Stack<T>(element))

    private val reversedInStack by lazy { inStack.reverse() }

    private val peekValue: Option<T>  by lazy {
        when {
            inStack.isEmpty && outStack.isEmpty -> Option.None
            outStack.isEmpty -> { /* outStack is empty so inStack has to have elements*/
                reversedInStack.peek()
            }
            else -> outStack.peek()
        }
    }
    val size by lazy { inStack.size + outStack.size }

    fun enqueue(element: T): Queue<T> = Queue(inStack.push(element), outStack)

    fun dequeue(): Pair<Option<T>, Queue<T>> = when {
        inStack.isEmpty && outStack.isEmpty -> Pair(Option.None, this)
        outStack.isEmpty -> { /* outStack is empty so inStack has to have elements*/
            val (out, s) = reversedInStack.pop()
            Pair(out, Queue(outStack, s))
        }
        else -> {
            val (out, s) = outStack.pop()
            Pair(out, Queue(inStack, s))
        }
    }

    fun enqueueRight(elements: List<T>): Queue<T> =  elements.foldRight<T, Queue<T>>(this) { element: T, q: Queue<T> -> q.enqueue(element) }
    fun enqueueLeft(elements: List<T>): Queue<T> = elements.fold<T, Queue<T>>(this) { q: Queue<T>, element: T -> q.enqueue(element) }
    fun peek(): Option<T> = peekValue
}
