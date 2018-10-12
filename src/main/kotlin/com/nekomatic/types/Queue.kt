package com.nekomatic.types

//TODO: clean the tests up to cover all and only useful scenarios
class Queue<T : Any> private constructor(
        private val inStack: Stack<T>,
        private val outStack: Stack<T>,
        private val first: Option<T> = Option.None,
        override val maxWidth: Int = 10
) : IQueue<T> {
    override val degree: Int = 0

    constructor(maxWidth: Int = 10) : this(Stack<T>(), Stack<T>(), Option.None, maxWidth)
    constructor(element: T, maxWidth: Int = 10) : this(Stack<T>(), Stack<T>(element), Option.Some(element), maxWidth)

    private val reversedInStack by lazy { inStack.reverse() }

    private val peekValue: Option<T>  by lazy {
        when {
            inStack.isEmpty && outStack.isEmpty -> Option.None
            outStack.isEmpty -> reversedInStack.peek() // outStack is empty so inStack has to have elements
            else -> outStack.peek()
        }
    }

    val items: List<T> by lazy { inStack.reverse().items + outStack.items }

    override val size by lazy { inStack.size + outStack.size }

    override fun enqueue(element: T): IQueue<T> = Queue(inStack.push(element), outStack, Option.Some(element), maxWidth)

    override fun dequeue(): Pair<Option<T>, IQueue<T>> = when {
        inStack.isEmpty && outStack.isEmpty -> Pair(Option.None, this)
        outStack.isEmpty -> { /* outStack is empty so inStack has to have elements*/
            val (out, s) = reversedInStack.pop()
            val newFirst = if (s.isEmpty) Option.None else this.first
            Pair(out, Queue(outStack, s, newFirst, maxWidth))
        }
        else -> { /* outStack is not empty but inStack may have elements*/
            val (out, s) = outStack.pop()
            val newFirst = if (!inStack.isEmpty || !s.isEmpty) { // still some elements left so first stays the same
                first
            } else { // no elements left
                Option.None
            }
            Pair(out, Queue(inStack, s, newFirst, maxWidth))
        }
    }

    override fun enqueueRight(elements: List<T>): IQueue<T> = elements.foldRight(this) { element: T, q: IQueue<T> -> q.enqueue(element) }
    override fun enqueueLeft(elements: List<T>): IQueue<T> = elements.fold(this) { q: IQueue<T>, element: T -> q.enqueue(element) }
    override fun peekOut(): Option<T> = peekValue
    override fun peekIn(): Option<T> = first

    override fun toString(): String = "Queue(${items.joinToString(", ") { "$it" }})"

    override fun equals(other: Any?): Boolean = when (other) {
        is Queue<*> -> this.items == other.items
        else -> false
    }

    override fun hashCode(): Int {
        var result = inStack.hashCode()
        result = 31 * result + outStack.hashCode()
        return result
    }


}
