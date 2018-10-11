package com.nekomatic.types


data class Stack<T : Any>(
        private val head: Option<Item<T>>,
        private val items: Int) {


    constructor() : this(Option.None, 0)
    constructor(element: T) : this(Item(element, Option.None))
    constructor(q: Stack<T>) : this(q.head, q.size)
    private constructor(h: Item<T>) : this(Option.Some(h), 1)

    data class Item<T>(val data: T, var next: Option<Item<T>>)

    val size = items


    fun push(element: T): Stack<T> {

        val i = Item(element, head)
        return Stack(head = Option.Some(i), items = size + 1)
    }

    fun pushReversed(elements: List<T>): Stack<T> = elements.foldRight<T, Stack<T>>(this) { element: T, s: Stack<T> -> s.push(element) }
    fun push(elements: List<T>): Stack<T> = elements.fold<T, Stack<T>>(this) { s: Stack<T>, element: T -> s.push(element) }

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

    fun reverse(): Stack<T> = generateSequence(this.pop()) { (_, s) -> s.pop() }
            .takeWhile { (r, _) -> r is Option.Some }.fold(Stack<T>()) { acc: Stack<T>, b: Pair<Option<T>, Stack<T>> -> acc.push((b.first as Option.Some).value) }

    val isEmpty by lazy { head is Option.None }
}

