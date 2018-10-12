package com.nekomatic.types

class Queue<T : Any> private constructor(
        internal val inStack: Stack<T>,
        internal val outStack: Stack<T>,
        private val peekInValue: Option<T> = Option.None,
        override val maxWidth: Int = 10
) : IQueue<T> {
    override val degree: Int = 0

    constructor(maxWidth: Int = 10) : this(Stack<T>(), Stack<T>(), Option.None, maxWidth)
    constructor(item: T, maxWidth: Int = 10) : this(Stack<T>(), Stack<T>(item), Option.Some(item), maxWidth)

    private val reversedInStack by lazy { inStack.reverse() }
    private val peekOutValue: Option<T>  by lazy {
        when {
            inStack.isEmpty && outStack.isEmpty -> Option.None
            outStack.isEmpty -> reversedInStack.peek() // outStack is empty so inStack has to have elements
            else -> outStack.peek()
        }
    }

    /**
     * returns queued items as a list in order of their retrieval
     */
    override val items: List<T> by lazy { outStack.items + reversedInStack.items }

    /**
     * returns number of queued items
     */
    override val size by lazy { inStack.size + outStack.size }

    /**
     * returns new queue with the item added to irs end
     */
    override fun enqueue(item: T): IQueue<T> = Queue(inStack.push(item), outStack, Option.Some(item), maxWidth)

    /**
     * returns option of the first item from the queue and new queue with this item removed as a Pair
     * if the queue has no elements the  item returned is Option.None and the queue returned is the current instance
     */
    override fun dequeue(): Pair<Option<T>, IQueue<T>> = when {
        inStack.isEmpty && outStack.isEmpty -> Pair(Option.None, this)
        outStack.isEmpty -> { /* outStack is empty so inStack has to have elements*/
            val (out, s) = reversedInStack.pop()
            val newPeekInValue = if (s.isEmpty) Option.None else this.peekInValue
            Pair(out, Queue(outStack, s, newPeekInValue, maxWidth))
        }
        else -> { /* outStack is not empty but inStack may have elements*/
            val (out, s) = outStack.pop()
            val newFirst = if (!inStack.isEmpty || !s.isEmpty) { // still some elements left so first stays the same
                peekInValue
            } else { // no elements left
                Option.None
            }
            Pair(out, Queue(inStack, s, newFirst, maxWidth))
        }
    }
    /**
     * returns new queue with all elements of the list added on end of the current queue
     * the items of the list are added in order from the last to the first
     */
    override fun enqueueRight(list: List<T>): IQueue<T> = list.foldRight(this) { item: T, q: IQueue<T> -> q.enqueue(item) }

    /**
     * returns new queue with all elements of the list added on end of the current queue
     * the items of the list are added in order from the fist to the last
     */
    override fun enqueueLeft(list: List<T>): IQueue<T> = list.fold(this) { q: IQueue<T>, item: T -> q.enqueue(item) }

    /**
     * returns an Option of the first (out) item of the queue without changing the queue
     */
    override fun peekOut(): Option<T> = peekOutValue

    /**
     * returns an Option of the last (in) item of the queue without changing the queue
     */
    override fun peekIn(): Option<T> = peekInValue

    override fun toString(): String =
            if (items.isEmpty()) "Queue()" else "Queue( ${items.joinToString(", ") { "$it" }} )"

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
