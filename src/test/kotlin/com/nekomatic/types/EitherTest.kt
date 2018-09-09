package com.nekomatic.types

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

internal class EitherTest {
    @Test
    @DisplayName("Cases of same type and values should equal")
    fun TestSameTypesAndValues() {
        val a: Either<Int, String> = Either.Left<Int, String>(1)
        val b: Either<Int, Int> = Either.Left<Int, Int>(1)
        assert(a.equals(b))
    }

    @Test
    @DisplayName("Cases of same type and different values should not equal")
    fun TestSameTypesAndDifferentValues() {
        val a: Either<Int, String> = Either.Left(1)
        val b: Either<Int, Int> = Either.Left(2)
        assert(!a.equals(b))
    }

    @Test
    @DisplayName("Cases of different types should not equal")
    fun ValueAndObjectCompareWhenTypesDontMatch() {
        val a: Either<Int, String> = Either.Right("1")
        val b: Either<Int, Int> = Either.Right(1)
        assert(!a.equals(b))
    }

    @Test
    @DisplayName("Left case of any type is Either.Left")
    fun TestLeftSmartcast() {
        val a: Either<Int, String> = Either.Left(1)
        when (a) {
            is Either.Right -> assert(false)
            is Either.Left -> assert(a.value == 1)
        }
    }

    @Test
    @DisplayName("Right case of any type is Either.Right")
    fun TestRightSmartcast() {
        val a: Either<Int, String> = Either.Right("1")
        when (a) {
            is Either.Right -> assert(a.value == "1")
            is Either.Left -> assert(false)
        }
    }
}