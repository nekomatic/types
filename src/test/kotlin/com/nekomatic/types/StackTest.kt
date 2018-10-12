package com.nekomatic.types

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertAll

internal class StackTest {
//TODO: recreate all tests to use pregenerated stacks
    val stackEmpty = Stack<String>()
    val stackOneElConst = Stack("A")
    val stackOneElGen = stackEmpty.push("A")
    val stackTwoEl = stackOneElGen.push("B")
    val stackOneElReduPair = stackTwoEl.pop()
    val stackNonElReduPair = stackOneElReduPair.second.pop()
    val stackTwoRev = stackTwoEl.reverse()
    val stackTwoItems = stackTwoEl.items
    val peekValue = stackTwoEl.peek()
    val stackTwoPushL = stackEmpty.pushLeft(listOf("A", "B"))
    val stackTwoPushR = stackEmpty.pushRight(listOf("A", "B"))

    @Test
    fun pushReversedList() {
        val stack = Stack<String>()
                .pushRight(listOf("C", "B", "A"))

        val elements = generateSequence(stack.pop()) { (_, s) -> s.pop() }
                .takeWhile { (e, _) -> e is Option.Some }
                .map { (e, _) -> (e as Option.Some).value }
                .toList()
        assertAll(
                { assertEquals(3, stack.size) },
                { assertEquals(listOf("C", "B", "A"), elements) }
        )
    }

    @Test
    fun push() {
        val stack = Stack<String>()
                .push("A")
                .push("B")
                .push("C")
        val elements = generateSequence(stack.pop()) { (_, s) -> s.pop() }
                .takeWhile { (e, _) -> e is Option.Some }
                .map { (e, _) -> (e as Option.Some).value }
                .toList()
        assertAll(
                { assertEquals(3, stack.size) },
                { assertEquals(listOf("C", "B", "A"), elements) }
        )
    }


    @Test
    fun reverse() {
        val stack = Stack<String>()
                .push("A")
                .push("B")
                .push("C").reverse()
        val elements = generateSequence(stack.pop()) { (_, s) -> s.pop() }
                .takeWhile { (e, _) -> e is Option.Some }
                .map { (e, _) -> (e as Option.Some).value }
                .toList()
        assertAll(
                { assertEquals(3, stack.size) },
                { assertEquals(listOf("A", "B", "C"), elements) }
        )
    }

    @Test
    fun pushList() {
        val stack = Stack<String>()
                .pushLeft(listOf("A", "B", "C"))

        val elements = generateSequence(stack.pop()) { (_, s) -> s.pop() }
                .takeWhile { (e, _) -> e is Option.Some }
                .map { (e, _) -> (e as Option.Some).value }
                .toList()
        assertAll(
                { assertEquals(3, stack.size) },
                { assertEquals(listOf("C", "B", "A"), elements) }
        )
    }

    @Test
    fun peek() {
        val stack = Stack<String>()
                .push("A")
                .push("B")
                .push("C")
        val (_, s1) = stack.pop()
        val (_, s2) = s1.pop()
        val (_, s3) = s2.pop()
        val peek1 = stack.peek()
        val peek2 = stack.peek()
        val peek3 = s3.peek()
        assertAll(
                { assertEquals(Option.Some("C"), peek1) },
                { assertEquals(Option.Some("C"), peek2) },
                { assertEquals(3, stack.size) },
                { assertEquals(Option.None, peek3) },
                { assertTrue(s3.isEmpty) }
        )
    }

    @Test
    fun pop() {
        val stack = Stack<String>()
                .push("A")
                .push("B")
                .push("C")

        val (pop1, s1) = stack.pop()
        val (pop2, s2) = s1.pop()
        val (pop3, s3) = s2.pop()
        val (pop4, s4) = s3.pop()
        assertAll(
                { assertEquals(3, stack.size) },
                { assertFalse(stack.isEmpty) },
                { assertEquals(2, s1.size) },
                { assertEquals(Option.Some("C"), pop1) },
                { assertEquals(1, s2.size) },
                { assertEquals(Option.Some("B"), pop2) },
                { assertEquals(0, s3.size) },
                { assertEquals(Option.Some("A"), pop3) },
                { assertEquals(0, s4.size) },
                { assertEquals(Option.None, pop4) },
                { assertTrue(s3.isEmpty) },
                { assertEquals(s3, s4) }
        )
    }

    @Test
    fun isEmpty() {
        val stack1 = Stack<String>()
                .push("A")
                .push("B")
                .push("C")
        val stack2 = Stack<String>()
        assertAll(
                { assertFalse(stack1.isEmpty) },
                { assertTrue(stack2.isEmpty) }
        )
    }

    @Test
    fun stackToString() {
        val stack1 = Stack<String>()
                .push("A")
                .push("B")
                .push("C")
        val stack2 = Stack<String>()
        assertAll(
                { assertEquals("Stack(C, B, A)", stack1.toString()) },
                { assertEquals("Stack()", stack2.toString()) }
        )
    }
}