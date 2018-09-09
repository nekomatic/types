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


@file:kotlin.jvm.JvmName("RationalExtensions")

package com.nekomatic.types


import java.math.BigInteger

typealias ROption = Option<Rational>

// r + option -> option
operator fun Rational.plus(other: ROption): ROption = other map { this + it }


// option + r -> option
operator fun ROption.plus(other: Rational): ROption = this map { it + other }

// option + option -> option
operator fun ROption.plus(other: ROption): ROption = this.map(other) { a, b -> a + b }


// r - option -> option
operator fun Rational.minus(other: ROption): ROption = other map { this - it }

// option - r -> option
operator fun ROption.minus(other: Rational): ROption = this map { it - other }

// option - option -> option
operator fun ROption.minus(other: ROption): ROption = this.map(other) { a, b -> a - b }


// r * option -> option
operator fun Rational.times(other: ROption): ROption = other map { this * it }

// option * r -> option
operator fun ROption.times(other: Rational): ROption = this map { it * other }

// option * option -> option
operator fun ROption.times(other: ROption): ROption = this.map(other) { a, b -> a * b }

// r * bigint -> r
operator fun Rational.times(factor: BigInteger): Rational = Rational(this.numerator * factor)

// r * int -> r
operator fun Rational.times(factor: Int): Rational = Rational(this.numerator * factor.toBigInteger())

// r / r -> option
operator fun Rational.div(other: Rational): ROption = this * other.inverse()

// option / r -> option
operator fun ROption.div(other: Rational): ROption = this mapToOption { it / other }

// r / option -> option
operator fun Rational.div(other: ROption): ROption = other mapToOption { this / it }

// option / option -> option
operator fun ROption.div(other: ROption): ROption = this.mapToOption(other) { a, b -> a / b }

// r / bigint -> option
operator fun Rational.div(other: BigInteger): ROption = this * Rational(other).inverse()

// r / int -> option
operator fun Rational.div(other: Int): ROption = this * Rational(other).inverse()


