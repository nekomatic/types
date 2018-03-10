package com.nekomatic.types


import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.math.BigInteger

internal class BigIntegerExtensionsKtTest {

    @Test
    @DisplayName("Decimal decimalMagnitude of positive BigInteger. ")
    fun magnitudeOfPositive() {
        for (i in 0..24) {
            val bigint = BigInteger.TEN.pow(i) + (i % 10).toBigInteger()
            val mag = bigint.decimalMagnitude()
            assert(i == bigint.toString().length - 1) // remove most significant digit
            assert(mag is Option.Some && i == mag.value)

        }
    }

    @Test
    @DisplayName("Decimal decimalMagnitude of negative BigInteger. ")
    fun magnitudeOfNegative() {
        for (i in 0..24) {
            val bigint = (BigInteger.TEN.pow(i) + (i % 10).toBigInteger()).negate()
            val mag = bigint.decimalMagnitude()
            assert(i == bigint.toString().length - 2) // remove most significant digit and the 'minus' sign
            assert(mag is Option.Some && i == mag.value)
        }

    }

    @Test
    @DisplayName("Decimal decimalMagnitude of zero BigInteger should be None. ")
    fun magnitudeOfZero() {
        assert(BigInteger.ZERO.decimalMagnitude() is Option.None)
    }
}