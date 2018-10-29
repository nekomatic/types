package com.nekomatic.types

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.assertAll

@Suppress("RemoveExplicitTypeArguments")
internal class StackTest {

    private val stackEmpty = Stack<String>()
    private val stackOneElConst = Stack("A")
    private val stackOneElGen = stackEmpty.push("A")
    private val stackTwoEl = stackOneElGen.push("B")
    private val stackOneElReduPair = stackTwoEl.pop()
    private val stackNonElReduPair = stackOneElReduPair.second.pop()
    private val stackTwoRev = stackTwoEl.reverse()
    private val stackTwoPushL = stackEmpty.pushLeft(listOf("A", "B"))
    private val stackTwoPushR = stackEmpty.pushRight(listOf("A", "B"))
    private val list = listOf("X", "Y", "Z")


    @DisplayName("Stack.isEmpty")
    @Test
    fun isEmpty() {
        assertAll(
                { assertTrue(stackEmpty.isEmpty) },
                { assertFalse(stackOneElGen.isEmpty) },
                { assertFalse(stackTwoEl.isEmpty) },
                { assertFalse(stackOneElReduPair.second.isEmpty) },
                { assertTrue(stackNonElReduPair.second.isEmpty) },
                { assertFalse(stackTwoRev.isEmpty) },
                { assertFalse(stackTwoPushL.isEmpty) },
                { assertFalse(stackTwoPushR.isEmpty) }
        )
    }

    @DisplayName("Stack.items")
    @Test
    fun items() {
        assertAll(
                { assertEquals(listOf<String>(), stackEmpty.items) },
                { assertEquals(listOf<String>("A"), stackOneElGen.items) },
                { assertEquals(listOf<String>("B", "A"), stackTwoEl.items) },
                { assertEquals(listOf<String>("A"), stackOneElReduPair.second.items) },
                { assertEquals(listOf<String>(), stackNonElReduPair.second.items) },
                { assertEquals(listOf<String>("A", "B"), stackTwoRev.items) },
                { assertEquals(listOf<String>("B", "A"), stackTwoPushL.items) },
                { assertEquals(listOf<String>("A", "B"), stackTwoPushR.items) }
        )
    }

    @DisplayName("Stack.push( element : T )")
    @Test
    fun push() {
        assertAll(
                { assertEquals(listOf("X"), stackEmpty.push("X").items) },
                { assertEquals(listOf("X", "A"), stackOneElConst.push("X").items) },
                { assertEquals(listOf("X", "A"), stackOneElGen.push("X").items) },
                { assertEquals(listOf("X", "B", "A"), stackTwoEl.push("X").items) },
                { assertEquals(listOf("X", "A"), stackOneElReduPair.second.push("X").items) },
                { assertEquals(listOf("X"), stackNonElReduPair.second.push("X").items) },
                { assertEquals(listOf("X", "A", "B"), stackTwoRev.push("X").items) },
                { assertEquals(listOf("X", "B", "A"), stackTwoPushL.push("X").items) }
        )
    }

    @DisplayName("Stack.pop()")
    @Test
    fun pop() {
        fun assertPop(popVal: Option<String>, popSize: Int, stack: Stack<String>) {
            val pop = stack.pop()
            assertAll(
                    { assertEquals(popVal, pop.first) },
                    { assertEquals(popSize, pop.second.size) })
        }
        assertAll(
                { assertPop(Option.None, 0, stackEmpty) },
                { assertPop(Option.Some("A"), 0, stackOneElGen) },
                { assertPop(Option.Some("B"), 1, stackTwoEl) },
                { assertPop(Option.Some("A"), 0, stackOneElReduPair.second) },
                { assertPop(Option.None, 0, stackNonElReduPair.second) },
                { assertPop(Option.Some("A"), 1, stackTwoRev) },
                { assertPop(Option.Some("B"), 1, stackTwoPushL) },
                { assertPop(Option.Some("A"), 1, stackTwoPushR) }
        )
    }

    @DisplayName("Stack.reverse()")
    @Test
    fun reverse() {
        assertAll(
                { assertEquals(listOf<String>(), stackEmpty.reverse().items) },
                { assertEquals(listOf<String>("A"), stackOneElGen.reverse().items) },
                { assertEquals(listOf<String>("A", "B"), stackTwoEl.reverse().items) },
                { assertEquals(listOf<String>("A"), stackOneElReduPair.second.reverse().items) },
                { assertEquals(listOf<String>(), stackNonElReduPair.second.reverse().items) },
                { assertEquals(listOf<String>("B", "A"), stackTwoRev.reverse().items) },
                { assertEquals(listOf<String>("A", "B"), stackTwoPushL.reverse().items) },
                { assertEquals(listOf<String>("B", "A"), stackTwoPushR.reverse().items) }
        )
    }

    @DisplayName("Stack.pushRight( list : List<T> )")
    @Test
    fun pushRight() {
        assertAll(
                { assertEquals(listOf("X", "Y", "Z"), stackEmpty.pushRight(list).items) },
                { assertEquals(listOf("X", "Y", "Z", "A"), stackOneElGen.pushRight(list).items) },
                { assertEquals(listOf("X", "Y", "Z", "B", "A"), stackTwoEl.pushRight(list).items) },
                { assertEquals(listOf("X", "Y", "Z", "A"), stackOneElReduPair.second.pushRight(list).items) },
                { assertEquals(listOf("X", "Y", "Z"), stackNonElReduPair.second.pushRight(list).items) },
                { assertEquals(listOf("X", "Y", "Z", "A", "B"), stackTwoRev.pushRight(list).items) },
                { assertEquals(listOf("X", "Y", "Z", "B", "A"), stackTwoPushL.pushRight(list).items) },
                { assertEquals(listOf("X", "Y", "Z", "A", "B"), stackTwoPushR.pushRight(list).items) }
        )
    }

    @DisplayName("Stack.pushLeft( list : List<T> )")
    @Test
    fun pushLeft() {
        assertAll(
                { assertEquals(listOf("Z", "Y", "X"), stackEmpty.pushLeft(list).items) },
                { assertEquals(listOf("Z", "Y", "X", "A"), stackOneElGen.pushLeft(list).items) },
                { assertEquals(listOf("Z", "Y", "X", "B", "A"), stackTwoEl.pushLeft(list).items) },
                { assertEquals(listOf("Z", "Y", "X", "A"), stackOneElReduPair.second.pushLeft(list).items) },
                { assertEquals(listOf("Z", "Y", "X"), stackNonElReduPair.second.pushLeft(list).items) },
                { assertEquals(listOf("Z", "Y", "X", "A", "B"), stackTwoRev.pushLeft(list).items) },
                { assertEquals(listOf("Z", "Y", "X", "B", "A"), stackTwoPushL.pushLeft(list).items) },
                { assertEquals(listOf("Z", "Y", "X", "A", "B"), stackTwoPushR.pushLeft(list).items) }
        )
    }

    @DisplayName("Stack.peek()")
    @Test
    fun peek() {

        fun assertPeek(peekVal: Option<String>, peekSize: Int, stack: Stack<String>) {
            val peek = stack.peek()
            assertAll(
                    { assertEquals(peekVal, peek) },
                    { assertEquals(peekSize, stack.size) })
        }
        assertAll(
                { assertPeek(Option.None, 0, stackEmpty) },
                { assertPeek(Option.Some("A"), 1, stackOneElGen) },
                { assertPeek(Option.Some("B"), 2, stackTwoEl) },
                { assertPeek(Option.Some("A"), 1, stackOneElReduPair.second) },
                { assertPeek(Option.None, 0, stackNonElReduPair.second) },
                { assertPeek(Option.Some("A"), 2, stackTwoRev) },
                { assertPeek(Option.Some("B"), 2, stackTwoPushL) },
                { assertPeek(Option.Some("A"), 2, stackTwoPushR) }
        )
    }

    @DisplayName("Stack.toString()")
    @Test
    fun stackToString() {
        assertAll(
                { assertEquals("Stack()", stackEmpty.toString()) },
                { assertEquals("Stack( A )", stackOneElGen.toString()) },
                { assertEquals("Stack( B, A )", stackTwoEl.toString()) },
                { assertEquals("Stack( A )", stackOneElReduPair.second.toString()) },
                { assertEquals("Stack()", stackNonElReduPair.second.toString()) },
                { assertEquals("Stack( A, B )", stackTwoRev.toString()) },
                { assertEquals("Stack( B, A )", stackTwoPushL.toString()) },
                { assertEquals("Stack( A, B )", stackTwoPushR.toString()) }
        )
    }

    @DisplayName("Stack.equals( other : Any? )")
    @Test
    fun equals() {
        assertAll(
                { assertTrue(stackEmpty == stackNonElReduPair.second) },
                { assertTrue(stackOneElReduPair.second == stackOneElGen) },
                { assertTrue(stackTwoPushL == stackTwoEl) },
                { assertTrue(stackTwoPushR == stackTwoRev) },
                { assertFalse(stackEmpty == stackOneElGen) },
                { assertFalse(stackTwoPushL == stackOneElGen) },
                { assertFalse(stackTwoPushL == stackTwoPushR) }
        )
    }
}