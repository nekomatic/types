package com.nekomatic.types

//TODO: clean the tests up to cover all and only useful scenarios
class Stack<T : Any> private constructor(
        private val head: Option<Item<T>>,
        private val count: Int) {


    constructor() : this(Option.None, 0)
    constructor(element: T) : this(Item(element, Option.None))
    private constructor(h: Item<T>) : this(Option.Some(h), 1)

    val items: List<T> by lazy {
        when (head) {
            Option.None -> emptySequence<T>()
            is Option.Some -> generateSequence(this.head) { i: Option.Some<Item<T>> ->
                val n = i.value.next
                when (n) {
                    Option.None -> null
                    is Option.Some -> n
                }
            }.map { it.value.data }
        }.toList()
    }

    private val stringValue by lazy {
        when (head) {
            Option.None -> "Stack()";
            is Option.Some -> {
                val s = items.joinToString(", ") { "$it" }
                "Stack($s)"
            }
        }
    }

    private class Item<T>(val data: T, var next: Option<Item<T>>)


    val size = count

    fun push(element: T): Stack<T> {
        val i = Item(element, head)
        return Stack(head = Option.Some(i), count = size + 1)
    }

    fun pushRight(elements: List<T>): Stack<T> = elements.foldRight<T, Stack<T>>(this) { element: T, s: Stack<T> -> s.push(element) }

    fun pushLeft(elements: List<T>): Stack<T> = elements.fold<T, Stack<T>>(this) { s: Stack<T>, element: T -> s.push(element) }

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

    fun peek(): Option<T> = when (head) {
        Option.None -> Option.None
        is Option.Some -> Option.Some(head.value.data)
    }

    fun reverse(): Stack<T> = Stack<T>().pushLeft(items)

    val isEmpty by lazy { head is Option.None }

    override fun equals(other: Any?): Boolean = when (other) {
        is Stack<*> -> this.items.equals(other.items)
        else -> false
    }

    override fun toString(): String = stringValue

    override fun hashCode(): Int {
        return items.hashCode()
    }
}

