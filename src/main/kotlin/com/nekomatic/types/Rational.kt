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


@file:Suppress("unused")

package com.nekomatic.types


import java.math.BigInteger


open class Rational(numerator: BigInteger, denominator: PositiveBigInt) : Comparable<Rational> {
    constructor(r: Rational) : this(r.numerator, r.denominatorPbi)
    constructor(r: BigInteger) : this(r, PositiveBigInt.ONE)
    constructor(r: Int) : this(r.toBigInteger())

    companion object {
        /**
         * predefined instance of [Rational] with value of 0
         */
        @JvmField
        val ZERO = Rational(BigInteger.ZERO)
        /**
         * predefined instance of [Rational] with value of 1
         */
        @JvmField
        val ONE = Rational(BigInteger.ONE)

        @JvmStatic
        fun create(numerator: BigInteger, denominator: BigInteger) {
            val denominatorOption = PositiveBigInt.create(denominator)
            when (denominatorOption) {
                is Option.Some -> Option.Some(Rational(numerator, denominatorOption.value))
                is Option.None -> Option.None
            }
        }
    }

    private val gcd: BigInteger = getGcd(numerator, denominator.value)

    private tailrec fun getGcd(a: BigInteger, b: BigInteger): BigInteger {
        return if (b == BigInteger.ZERO)
            a
        else
            getGcd(b, a % b)
    }

    /**
     * @return numerator of this [Rational] as [BigInteger]
     */
    @JvmField
    val numerator = numerator / gcd
    /**
     * @return denominator of this [Rational] as [BigInteger]
     */
    @JvmField
    val denominator = denominator.value / gcd
    /**
     * @return denominator of this [Rational] as [PositiveBigInt]
     */
    @JvmField
    val denominatorPbi = PositiveBigInt(this.denominator)

    /**
     * @return -1, 0 or 1 as the value of this [Rational] is negative, zero or positive
     */
    fun signum() = numerator.signum()

    /**
     * @return negative value of this [Rational]
     */
    fun negate() = Rational(numerator.negate(), denominatorPbi)

    /**
     * @return discrete value of this [Rational] as [BigInteger]
     */
    fun bigIntPart(): BigInteger = if (numerator == BigInteger.ZERO) BigInteger.ZERO else numerator / denominator

    /**
     * @return [Option.Some] of the inversed value of this [Rational] if this is not zero, otherwise returns [Option.None]
     */
    fun inverse(): Option<Rational> {
        return when (numerator.signum()) {
            1 -> Option.Some(Rational(denominator, PositiveBigInt(numerator)))
            -1 -> Option.Some(Rational(denominator.negate(), PositiveBigInt(numerator.negate())))
            else -> Option.None
        }
    }

    /**
     * Operator [plus] sums two instances of [Rational]
     * @param [other] [Rational]
     * @return [Rational]
     */
    operator fun plus(other: Rational): Rational {
        val num = this.numerator * other.denominator + other.numerator * this.denominator
        val dem = this.denominatorPbi * other.denominatorPbi
        return Rational(num, dem)
    }

    /**
     * Operator [minus] substracts an instance of [Rational] from this [Rational]
     * @param [other] [Rational]
     * @return [Rational]
     */
    operator fun minus(other: Rational): Rational = this + other.negate()

    /**
     * Operator [times] multiplies two instances of [Rational]
     * @param [other] [Rational]
     * @return [Rational]
     */
    operator fun times(other: Rational): Rational {
        return Rational(
                this.numerator * other.numerator,
                this.denominatorPbi * other.denominatorPbi
        )
    }

    override fun equals(other: Any?): Boolean =
            when (other) {
                is Rational -> this.numerator == other.numerator && this.denominator == other.denominator
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

    override fun toString(): String {
        return "Rational(numerator=$numerator, denominator=$denominator)"
    }
}

