package com.nekomatic.types

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertAll
import java.math.BigInteger

internal class RationalExtensionsTest {
//TODO:complete Rational Tests
    private val first = Rational(BigInteger.TEN, PositiveBigInt.P100)
    private val second = Rational(2.toBigInteger(), PositiveBigInt.P1001)
    private val firstBySecond: Rational = ((first / second) as Option.Some).value
    private val zero = Rational.ZERO

    @Test
    fun rationalPlusROption() {
        assertAll(
                { assertTrue(first + Option.None == Option.None) },
                { assertEquals(Option.Some(first + second), first + Option.Some(second)) }
        )
    }

    @Test
    fun rOptionPlusRational() {
        assertAll(
                { assertTrue(Option.None + first == Option.None) },
                { assertEquals(Option.Some(first + second), Option.Some(first) + second) }
        )
    }

    @Test
    fun rOptionPlusROption() {
        assertAll(
                { assertTrue(Option.None + Option.None == Option.None) },
                { assertEquals(Option.Some(first + second), Option.Some(first) + Option.Some(second)) },
                { assertEquals(Option.None, Option.Some(first) + Option.None) },
                { assertEquals(Option.None, Option.None + Option.Some(second)) }
        )
    }

    @Test
    fun rationalMinusROption() {
        assertAll(
                { assertTrue(first - Option.None == Option.None) },
                { assertEquals(Option.Some(first - second), first - Option.Some(second)) }
        )
    }

    @Test
    fun rOptionMinusRational() {
        assertAll(
                { assertTrue(Option.None - first == Option.None) },
                { assertEquals(Option.Some(first - second), Option.Some(first) - second) }
        )
    }

    @Test
    fun rOptionMinusROption() {
        assertAll(
                { assertTrue(Option.None - Option.None == Option.None) },
                { assertEquals(Option.Some(first - second), Option.Some(first) - Option.Some(second)) },
                { assertEquals(Option.None, Option.Some(first) - Option.None) },
                { assertEquals(Option.None, Option.None - Option.Some(second)) }
        )
    }


    @Test
    fun rationalTimesROption() {
        assertAll(
                { assertTrue(first * Option.None == Option.None) },
                { assertEquals(Option.Some(first * second), first * Option.Some(second)) }
        )
    }

    @Test
    fun rOptionTimeasRational() {
        assertAll(
                { assertTrue(Option.None * first == Option.None) },
                { assertEquals(Option.Some(first * second), Option.Some(first) * second) }
        )
    }

    @Test
    fun rOptionTimesROption() {
        assertAll(
                { assertTrue(Option.None * Option.None == Option.None) },
                { assertEquals(Option.Some(first * second), Option.Some(first) * Option.Some(second)) },
                { assertEquals(Option.None, Option.Some(first) * Option.None) },
                { assertEquals(Option.None, Option.None * Option.Some(second)) }
        )
    }


    @Test
    fun rationalDivZero() {
        assertAll(
                { assertEquals(Option.None, first / zero) }
        )
    }

    @Test
    fun zeroDivRational() {
        assertAll(
                { assertEquals(Option.Some(zero), zero / first) }
        )
    }

    @Test
    fun zeroDivZero() {
        assertAll(
                { assertEquals(Option.None, zero / zero) }
        )
    }

    @Test
    fun rationalDivNonZeroRational() {
        assertAll(
                { assertEquals(Option.Some(firstBySecond), first / second) }
        )
    }

    @Test
    fun rOptionDivRational() {
        assertAll(
                { assertEquals(Option.Some(firstBySecond), Option.Some(first) / second) },
                { assertEquals(Option.None, Option.Some(first) / zero) },
                { assertEquals(Option.Some(zero), Option.Some(zero) / first) },
                { assertEquals(Option.None, Option.None / first) },
                { assertEquals(Option.None, Option.None / zero) }
        )
    }

    @Test
    fun rationalDivROption() {
        assertAll(
                { assertEquals(Option.Some(firstBySecond), first / Option.Some(second)) },
                { assertEquals(Option.None, first / Option.Some(zero)) },
                { assertEquals(Option.Some(zero), zero / Option.Some(first)) },
                { assertEquals(Option.None, first / Option.None) },
                { assertEquals(Option.None, zero / Option.None) }
        )
    }

    @Test
    fun rOptionDivRoption() {
        assertAll(
                { assertEquals(Option.Some(firstBySecond), Option.Some(first) / Option.Some(second)) },
                { assertEquals(Option.None, Option.Some(first) / Option.Some(zero)) },
                { assertEquals(Option.Some(zero), Option.Some(zero) / Option.Some(first)) },
                { assertEquals(Option.None, Option.None / Option.Some(first)) },
                { assertEquals(Option.None, Option.None / Option.Some(zero)) },
                { assertEquals(Option.None, Option.Some(first) / Option.None) },
                { assertEquals(Option.None, Option.Some(zero) / Option.None) },
                { assertEquals(Option.None, Option.None / Option.None) }
        )
    }

    @Test
    fun plus() {
    }

    @Test
    fun plus1() {
    }

    @Test
    fun plus2() {
    }

    @Test
    fun minus() {
    }

    @Test
    fun minus1() {
    }

    @Test
    fun minus2() {
    }

    @Test
    fun times() {
    }

    @Test
    fun times1() {
    }

    @Test
    fun times2() {
    }

    @Test
    fun times3() {
    }

    @Test
    fun times4() {
    }

    @Test
    fun times5() {
    }

    @Test
    fun times6() {
    }

    @Test
    fun div() {
    }

    @Test
    fun div1() {
    }

    @Test
    fun div2() {
    }

    @Test
    fun div3() {
    }

    @Test
    fun div4() {
    }

    @Test
    fun div5() {
    }

    @Test
    fun div6() {
    }

    @Test
    fun div7() {
    }

}