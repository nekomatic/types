package com.nekomatic.types

/**
 * immutable Stack, also known as persistent or functional Stack
 */
@Suppress("RemoveExplicitTypeArguments")
//TODO: clean the tests up to cover all and only useful scenarios
class Stack<T : Any> private constructor(
        private val head: Option<ListItem<T>>,
        private val count: Int) {


    constructor() : this(Option.None, 0)
    constructor(element: T) : this(ListItem(element, Option.None))
    private constructor(h: ListItem<T>) : this(Option.Some(h), 1)

    /**
     * returns stacked items as a list in order of their retrieval
     */
    val items: List<T> by lazy {
        when (head) {
            Option.None -> emptySequence<T>()
            is Option.Some -> generateSequence(this.head) { i: Option.Some<ListItem<T>> ->
                val n = i.value.next
                when (n) {
                    Option.None -> null
                    is Option.Some -> n
                }
            }.map { it.value.data }
        }.toList()
    }

    /**
     * returns number of stacked items
     */
    val size = count

    /**
     * returns new instance of Stack with the item as the top element
     */
    fun push(item: T): Stack<T> {
        val i = ListItem(item, head)
        return Stack(head = Option.Some(i), count = size + 1)
    }

    /**
     * returns new stack with all elements of th elist added on top of the current stack
     * the elements of the list are added in order from the last to the first
     */
    fun pushRight(items: List<T>): Stack<T> = items.foldRight<T, Stack<T>>(this) { element: T, s: Stack<T> -> s.push(element) }

    /**
     * returns new stack with all elements of th elist added on top of the current stack
     * the elements of the list are added in order from the fist to the last
     */
    fun pushLeft(items: List<T>): Stack<T> = items.fold<T, Stack<T>>(this) { s: Stack<T>, element: T -> s.push(element) }

    /**
     * returns option of the top element from the stack and new stack with this element removed as a Pair
     * if the stack has no elements the element returned is Option.None and the stack returned is the current instance
     */
    fun pop(): Pair<Option<T>, Stack<T>> =
            when (head) {
                Option.None -> Option.None to this
                is Option.Some -> {
                    val element = Option.Some(head.value.data)
                    val next = head.value.next
                    val newElementsCount = size - 1
                    val stack = Stack(next, newElementsCount)
                    Pair(element, stack)
                }
            }

    /**
     * returns the Option of the top element of the stack without changing the stack
     */
    fun peek(): Option<T> = when (head) {
        Option.None -> Option.None
        is Option.Some -> Option.Some(head.value.data)
    }

    /**
     * returns new stack with elements stacked in reverse order
     */
    fun reverse(): Stack<T> = Stack<T>().pushLeft(items)

    /**
     * returns true if there is no elements on the stack, otherwise returns true
     */
    val isEmpty by lazy { head is Option.None }

    @Suppress("ReplaceCallWithBinaryOperator")
    override fun equals(other: Any?): Boolean = when (other) {
        is Stack<*> -> this.items.equals(other.items)
        else -> false
    }

    override fun toString(): String = stringValue

    override fun hashCode(): Int {
        return items.hashCode()
    }

    private class ListItem<T>(val data: T, var next: Option<ListItem<T>>)

    private val stringValue by lazy {
        when (head) {
            Option.None -> "Stack()"
            is Option.Some -> {
                "Stack( ${items.joinToString(", ") { "$it" }} )"
            }
        }
    }
}

