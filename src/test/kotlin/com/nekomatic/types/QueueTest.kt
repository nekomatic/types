package com.nekomatic.types

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertAll



internal class QueueTest {
//TODO: prepare Queues in various internal stack configurations and recreate tests based on these

    /*
    * in, out
    * 0 , 0
    * 1 , 0
    * 0 , 1
    * 1 , 1
    * 1 , 2
    * 2 , 1
    * 2 , 2
    * */

    val queue = Queue<String>("0")
            .enqueue("A")
            .enqueue("B")
            .enqueue("C")
            .enqueue("D")
            .enqueue("E")
    val emptyQueue = Queue<String>()


    @Test
    fun getSize() {
        val (_, q1) = queue.dequeue()
        val q2 = queue.enqueue("F")
        assertAll(
                { assertEquals(6, queue.size) },
                { assertEquals(5, q1.size) },
                { assertEquals(7, q2.size) },
                { assertEquals(0, emptyQueue.size) }
        )
    }

    @Test
    fun enqueue() {
        val newQueue = queue.enqueue("F")

        val elements =
                generateSequence(newQueue.dequeue()) { (_, q) -> q.dequeue() }
                        .takeWhile { (e, _) -> e is Option.Some }
                        .map { (e: Option<String>, _) -> (e as Option.Some).value }.toList()
        assertAll(
                { assertEquals(7, newQueue.size) },
                { assertEquals(listOf("0", "A", "B", "C", "D", "E", "F"), elements) }
        )
    }

    @Test
    fun enqueueLeft() {
        val newQueue = queue.enqueueLeft(listOf("F", "G", "H"))

        val elements =
                generateSequence(newQueue.dequeue()) { (_, q) -> q.dequeue() }
                        .takeWhile { (e, _) -> e is Option.Some }
                        .map { (e: Option<String>, _) -> (e as Option.Some).value }.toList()
        assertAll(
                { assertEquals(9, newQueue.size) },
                { assertEquals(listOf("0", "A", "B", "C", "D", "E", "F", "G", "H"), elements) }
        )
    }

    @Test
    fun enqueueRight() {
        val newQueue = queue.enqueueRight(listOf("F", "G", "H"))

        val elements =
                generateSequence(newQueue.dequeue()) { (_, q) -> q.dequeue() }
                        .takeWhile { (e, _) -> e is Option.Some }
                        .map { (e: Option<String>, _) -> (e as Option.Some).value }.toList()
        assertAll(
                { assertEquals(9, newQueue.size) },
                { assertEquals(listOf("0", "A", "B", "C", "D", "E", "H", "G", "F"), elements) }
        )
    }


    @Test
    fun dequeue() {
        val (dequeuedValue, newQueue) = queue.dequeue()

        val elements =
                generateSequence(newQueue.dequeue()) { (_, q) -> q.dequeue() }
                        .takeWhile { (e, _) -> e is Option.Some }
                        .map { (e: Option<String>, _) -> (e as Option.Some).value }.toList()
        assertAll(
                { assertEquals(5, newQueue.size) },
                { assertEquals(listOf("A", "B", "C", "D", "E"), elements) },
                { assertEquals(Option.Some("0"), dequeuedValue) }
        )
    }

    @Test
    fun peekFirst() {

        val oneElQ = emptyQueue.enqueue("X")
        val twoElQ = Queue("X").enqueue("Y")
        val (_, reducedOneElQ) = oneElQ.dequeue()
        val (_, reducedTwoElQ) = twoElQ.dequeue()
        val (_, reducedMoreTwoElQ) = reducedTwoElQ.dequeue()
        val restoredTwoElQ = reducedTwoElQ.enqueue("Z")

        assertAll(
                { assertEquals(Option.Some("E"), queue.peekIn()) },
                { assertEquals(Option.None, emptyQueue.peekIn()) },
                { assertEquals(Option.Some("X"), oneElQ.peekIn()) },
                { assertEquals(Option.Some("Y"), twoElQ.peekIn()) },
                { assertEquals(Option.None, reducedOneElQ.peekIn()) },
                { assertEquals(Option.Some("Y"), reducedTwoElQ.peekIn()) },
                { assertEquals(Option.None, reducedMoreTwoElQ.peekIn()) },
                { assertEquals(Option.Some("Z"), restoredTwoElQ.peekIn()) }
        )
    }
}