package com.nekomatic.types

import com.nekomatic.types.PositiveBigInt.Companion.P100
import com.nekomatic.types.PositiveBigInt.Companion.P1001
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertAll
import java.math.BigInteger

internal class RationalTest {

    private val first = Rational(BigInteger.TEN, P100)
    private val second = Rational(2.toBigInteger(), P1001)

    @Test
    fun plus() {

        val expectedNumerator = first.numerator * second.denominator + second.numerator * first.denominator
        val expectedDenominator = first.denominatorPbi * second.denominatorPbi

        val actual = first + second
        val expected = Rational(expectedNumerator, expectedDenominator)
        assertAll(
                { assertEquals(expected, actual) },
                { assertEquals(expected.negate(), actual.negate()) },
                { assertEquals(expected.signum(), actual.signum()) },
                { assertEquals(expected.denominatorPbi, actual.denominatorPbi) }
        )
    }

    @Test
    fun minus() {
        val expectedNumerator = first.numerator * second.denominator - second.numerator * first.denominator
        val expectedDenominator = first.denominatorPbi * second.denominatorPbi

        val actual = first - second
        val expected = Rational(expectedNumerator, expectedDenominator)
        assertAll(
                { assertEquals(expected, actual) },
                { assertEquals(expected.negate(), actual.negate()) },
                { assertEquals(expected.signum(), actual.signum()) },
                { assertEquals(expected.denominatorPbi, actual.denominatorPbi) }
        )
    }

    @Test
    fun times() {
        val expectedNumerator = first.numerator * second.numerator
        val expectedDenominator = first.denominatorPbi * second.denominatorPbi

        val actual = first * second
        val expected = Rational(expectedNumerator, expectedDenominator)
        assertAll(
                { assertEquals(expected, actual) },
                { assertEquals(expected.negate(), actual.negate()) },
                { assertEquals(expected.signum(), actual.signum()) },
                { assertEquals(expected.denominatorPbi, actual.denominatorPbi) }
        )
    }
}