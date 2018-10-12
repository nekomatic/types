package com.nekomatic.types

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.assertAll


@Suppress("PrivatePropertyName")
internal class QueueTest {
    private fun <A : Any, B : Any, C : Any> Pair<A, B>.map(f1: (A, B) -> C): C =
            f1(this.first, this.second)

    private data class DequeueAssertData<A : Any>(val value: Option<A>, val size: Int, val items: List<A>)

    private fun <A : Option<C>, B : IQueue<C>, C : Any> Pair<A, B>.toDequeueAssertData() =
            this.map { value: A, q: B ->
                DequeueAssertData(value, q.size, q.items)
            }


    private val q00_s0: Queue<String> = Queue<String>()
    private val q10_s1: Queue<String> = q00_s0.enqueue("1") as Queue<String>
    private val q01_s1: Queue<String> = q10_s1.enqueue("2").dequeue().second as Queue<String>
    private val q11_s2 = q01_s1.enqueue("3") as Queue<String>
    private val q21_s3 = q11_s2.enqueue("4") as Queue<String>
    private val q12_s3 = q10_s1.enqueue("5").enqueue("6").dequeue().second.enqueue("7") as Queue<String>
    private val q22_s4 = q12_s3.enqueue("8") as Queue<String>
    private val exhausted = q01_s1.dequeue().second as Queue<String>
    private val equalTo_q10_s1 = q10_s1.enqueue("1").dequeue().second as Queue<String>
    private val equalTo_q22_s4 = q00_s0.enqueueLeft(listOf("5", "6", "7", "8")) as Queue<String>
    private val list = listOf("X", "Y", "Z")

    @DisplayName("Assert internal stack's sizes")
    @Test
    fun stackSizes() {
        assertAll(
                { assertEquals(0, q00_s0.inStack.size) },
                { assertEquals(0, q00_s0.outStack.size) },
                { assertEquals(1, q10_s1.inStack.size) },
                { assertEquals(0, q10_s1.outStack.size) },
                { assertEquals(0, q01_s1.inStack.size) },
                { assertEquals(1, q01_s1.outStack.size) },
                { assertEquals(1, q11_s2.inStack.size) },
                { assertEquals(1, q11_s2.outStack.size) },
                { assertEquals(2, q21_s3.inStack.size) },
                { assertEquals(1, q21_s3.outStack.size) },
                { assertEquals(1, q12_s3.inStack.size) },
                { assertEquals(2, q12_s3.outStack.size) },
                { assertEquals(2, q22_s4.inStack.size) },
                { assertEquals(2, q22_s4.outStack.size) },
                { assertEquals(0, exhausted.inStack.size) },
                { assertEquals(0, exhausted.outStack.size) },
                { assertEquals(0, equalTo_q10_s1.inStack.size) },
                { assertEquals(1, equalTo_q10_s1.outStack.size) },
                { assertEquals(4, equalTo_q22_s4.inStack.size) },
                { assertEquals(0, equalTo_q22_s4.outStack.size) }

        )
    }

    @DisplayName("Queue.items")
    @Test
    fun items() {
        assertAll(
                { assertEquals(listOf<String>(), q00_s0.items) },
                { assertEquals(listOf("1"), q10_s1.items) },
                { assertEquals(listOf("2"), q01_s1.items) },
                { assertEquals(listOf("2", "3"), q11_s2.items) },
                { assertEquals(listOf("2", "3", "4"), q21_s3.items) },
                { assertEquals(listOf("5", "6", "7"), q12_s3.items) },
                { assertEquals(listOf("5", "6", "7", "8"), q22_s4.items) },
                { assertEquals(listOf<String>(), exhausted.items) }
        )
    }

    @DisplayName("Queue.size")
    @Test
    fun size() {
        assertAll(
                { assertEquals(0, q00_s0.size) },
                { assertEquals(1, q10_s1.size) },
                { assertEquals(1, q01_s1.size) },
                { assertEquals(2, q11_s2.size) },
                { assertEquals(3, q21_s3.size) },
                { assertEquals(3, q12_s3.size) },
                { assertEquals(4, q22_s4.size) },
                { assertEquals(0, exhausted.size) }
        )
    }

    @DisplayName("Queue.enqueue( item :T )")
    @Test
    fun enqueue() {
        assertAll(
                { assertEquals(listOf("A"), q00_s0.enqueue("A").items) },
                { assertEquals(listOf("1", "A"), q10_s1.enqueue("A").items) },
                { assertEquals(listOf("2", "A"), q01_s1.enqueue("A").items) },
                { assertEquals(listOf("2", "3", "A"), q11_s2.enqueue("A").items) },
                { assertEquals(listOf("2", "3", "4", "A"), q21_s3.enqueue("A").items) },
                { assertEquals(listOf("5", "6", "7", "A"), q12_s3.enqueue("A").items) },
                { assertEquals(listOf("5", "6", "7", "8", "A"), q22_s4.enqueue("A").items) },
                { assertEquals(listOf("A"), exhausted.enqueue("A").items) }
        )
    }


    @DisplayName("Queue.dequeue()")
    @Test
    fun dequeue() {
        assertAll(
                { assertEquals(DequeueAssertData(Option.None, 0, listOf<String>()), q00_s0.dequeue().toDequeueAssertData()) },
                { assertEquals(DequeueAssertData(Option.Some("1"), 0, listOf()), q10_s1.dequeue().toDequeueAssertData()) },
                { assertEquals(DequeueAssertData(Option.Some("2"), 0, listOf()), q01_s1.dequeue().toDequeueAssertData()) },
                { assertEquals(DequeueAssertData(Option.Some("2"), 1, listOf("3")), q11_s2.dequeue().toDequeueAssertData()) },
                { assertEquals(DequeueAssertData(Option.Some("2"), 2, listOf("3", "4")), q21_s3.dequeue().toDequeueAssertData()) },
                { assertEquals(DequeueAssertData(Option.Some("5"), 2, listOf("6", "7")), q12_s3.dequeue().toDequeueAssertData()) },
                { assertEquals(DequeueAssertData(Option.Some("5"), 3, listOf("6", "7", "8")), q22_s4.dequeue().toDequeueAssertData()) },
                { assertEquals(DequeueAssertData(Option.None, 0, listOf<String>()), exhausted.dequeue().toDequeueAssertData()) }
        )
    }

    @DisplayName("Queue.peekIn()")
    @Test
    fun peekIn() {

        assertAll(
                { assertEquals(Option.None, q00_s0.peekIn()) },
                { assertEquals(Option.Some("1"), q10_s1.peekIn()) },
                { assertEquals(Option.Some("2"), q01_s1.peekIn()) },
                { assertEquals(Option.Some("3"), q11_s2.peekIn()) },
                { assertEquals(Option.Some("4"), q21_s3.peekIn()) },
                { assertEquals(Option.Some("7"), q12_s3.peekIn()) },
                { assertEquals(Option.Some("8"), q22_s4.peekIn()) },
                { assertEquals(Option.None, exhausted.peekIn()) }
        )
    }

    @DisplayName("Queue.peekOut()")
    @Test
    fun peekOut() {

        assertAll(
                { assertEquals(Option.None, q00_s0.peekOut()) },
                { assertEquals(Option.Some("1"), q10_s1.peekOut()) },
                { assertEquals(Option.Some("2"), q01_s1.peekOut()) },
                { assertEquals(Option.Some("2"), q11_s2.peekOut()) },
                { assertEquals(Option.Some("2"), q21_s3.peekOut()) },
                { assertEquals(Option.Some("5"), q12_s3.peekOut()) },
                { assertEquals(Option.Some("5"), q22_s4.peekOut()) },
                { assertEquals(Option.None, exhausted.peekOut()) }
        )
    }


    @DisplayName("Queue.enqueueLeft( list : List<T> )")
    @Test
    fun enqueueLeft() {
        assertAll(
                { assertEquals(listOf("X", "Y", "Z"), q00_s0.enqueueLeft(list).items) },
                { assertEquals(listOf("1", "X", "Y", "Z"), q10_s1.enqueueLeft(list).items) },
                { assertEquals(listOf("2", "X", "Y", "Z"), q01_s1.enqueueLeft(list).items) },
                { assertEquals(listOf("2", "3", "X", "Y", "Z"), q11_s2.enqueueLeft(list).items) },
                { assertEquals(listOf("2", "3", "4", "X", "Y", "Z"), q21_s3.enqueueLeft(list).items) },
                { assertEquals(listOf("5", "6", "7", "X", "Y", "Z"), q12_s3.enqueueLeft(list).items) },
                { assertEquals(listOf("5", "6", "7", "8", "X", "Y", "Z"), q22_s4.enqueueLeft(list).items) },
                { assertEquals(listOf("X", "Y", "Z"), exhausted.enqueueLeft(list).items) }
        )
    }

    //private val list = listOf("X", "Y", "Z")
    @DisplayName("Queue.enqueueRight( list : List<T> )")
    @Test
    fun enqueueRight() {
        assertAll(
                { assertEquals(listOf("Z", "Y", "X"), q00_s0.enqueueRight(list).items) },
                { assertEquals(listOf("1", "Z", "Y", "X"), q10_s1.enqueueRight(list).items) },
                { assertEquals(listOf("2", "Z", "Y", "X"), q01_s1.enqueueRight(list).items) },
                { assertEquals(listOf("2", "3", "Z", "Y", "X"), q11_s2.enqueueRight(list).items) },
                { assertEquals(listOf("2", "3", "4", "Z", "Y", "X"), q21_s3.enqueueRight(list).items) },
                { assertEquals(listOf("5", "6", "7", "Z", "Y", "X"), q12_s3.enqueueRight(list).items) },
                { assertEquals(listOf("5", "6", "7", "8", "Z", "Y", "X"), q22_s4.enqueueRight(list).items) },
                { assertEquals(listOf("Z", "Y", "X"), exhausted.enqueueRight(list).items) }
        )
    }

    @DisplayName("Queue.toString()")
    @Test
    fun queueToString() {
        assertAll(
                { assertEquals("Queue()", q00_s0.toString()) },
                { assertEquals("Queue( 1 )", q10_s1.toString()) },
                { assertEquals("Queue( 2 )", q01_s1.toString()) },
                { assertEquals("Queue( 2, 3 )", q11_s2.toString()) },
                { assertEquals("Queue( 2, 3, 4 )", q21_s3.toString()) },
                { assertEquals("Queue( 5, 6, 7 )", q12_s3.toString()) },
                { assertEquals("Queue( 5, 6, 7, 8 )", q22_s4.toString()) },
                { assertEquals("Queue()", exhausted.toString()) }
        )
    }

    @DisplayName("Queue.equals( other : Any?)")
    @Test
    fun equals() {
        assertAll(
                { assertTrue(q00_s0 == exhausted) },
                { assertTrue(q10_s1 == equalTo_q10_s1) },
                { assertTrue(q22_s4 == equalTo_q22_s4) },
                { assertFalse(q00_s0 == q10_s1) },
                { assertFalse(q10_s1 == equalTo_q22_s4) }
        )
    }

}