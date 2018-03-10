/**
 * MIT License
 *
 * Copyright (c) 2018 nekomatic
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.nekomatic.types


import java.math.BigInteger

open class Rational(num: BigInteger, denom: PositiveBigInt) : Comparable<Rational> {
    constructor(r: Rational) : this(r.numerator, r.denominatorPbi)
    constructor(r: BigInteger) : this(r, PositiveBigInt.ONE)
    constructor(r: Int) : this(r.toBigInteger())

    companion object {
        val ZERO = Rational(BigInteger.ZERO)
        val ONE = Rational(BigInteger.ONE)
        fun fromDecimalMag(toNumberValue: Int, size: Int): Rational {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }

    private val gcd: BigInteger = getgcd(num, denom.value)

    private tailrec fun getgcd(a: BigInteger, b: BigInteger): BigInteger {
        return if (b == BigInteger.ZERO)
            a
        else
            getgcd(b, a % b)
    }

    class PositiveBigInt(private val v: BigInteger) {
        companion object {
            val ONE = PositiveBigInt(BigInteger.ONE)
            val TEN = PositiveBigInt(BigInteger.TEN)
            val P12 = PositiveBigInt(12.toBigInteger())
            val P15 = PositiveBigInt(15.toBigInteger())
            val P24 = PositiveBigInt(24.toBigInteger())
            val P25 = PositiveBigInt(25.toBigInteger())
            val P30 = PositiveBigInt(30.toBigInteger())
            val P100 = PositiveBigInt(100.toBigInteger())
            val P1000 = PositiveBigInt(1000.toBigInteger())
            val P1001 = PositiveBigInt(1001.toBigInteger())
        }

        val value: BigInteger
            get() = v

        fun create(bigint: BigInteger): Option<PositiveBigInt> =
                when (bigint.signum()) {
                    1 -> Option.Some(PositiveBigInt(bigint))
                    else -> Option.None
                }

        operator fun times(second: PositiveBigInt): PositiveBigInt = PositiveBigInt(this.value * second.value)
        operator fun plus(second: PositiveBigInt): PositiveBigInt = PositiveBigInt(this.value + second.value)
    }


    val numerator = num / gcd
    val denominator = denom.value / gcd
    val denominatorPbi = Rational.PositiveBigInt(denominator)

    fun signum() = numerator.signum()
    fun negate() = Rational(numerator.negate(), denominatorPbi)
    fun bigIntPart() = if (numerator == BigInteger.ZERO) BigInteger.ZERO else numerator / denominator

    fun inverse(): Option<Rational> {
        return when (numerator.signum()) {
            1 -> Option.Some(Rational(denominator, Rational.PositiveBigInt(numerator)))
            -1 -> Option.Some(Rational(denominator.negate(), Rational.PositiveBigInt(numerator.negate())))
            else -> Option.None
        }
    }

    override fun equals(other: Any?): Boolean =
            when (other) {
                is Rational -> this.numerator == other.denominator && this.denominator == other.denominator
                else -> false
            }

    override fun compareTo(other: Rational): Int {
        return when {
            this == other -> 0 // these are qual
            this.signum() > other.signum() -> 1
            this.signum() < other.signum() -> -1 //
            this == ZERO -> 0 // signum the same and equal 0
            else -> {
                val signum = this.signum() // both signum is the same but not equal 0
                val n1 = this.numerator.abs() * other.denominator
                val n2 = other.numerator.abs() * this.denominator
                return (if (n1 > n2) 1 else -1) * signum
            }
        }
    }

    override fun hashCode(): Int {
        return 31 * numerator.hashCode() + denominator.hashCode()
    }
}

