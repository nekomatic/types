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

/**
 * [PositiveInt] wraps an immutable [Int] value which is guaranteed to be positive
 */
open class PositiveInt private constructor(val value: Int) {

    companion object {
        /**
         * Creates instance of PositiveInt option from given [Int]
         * @param [value] input value of type [Int]
         * @return [Option.Some] Option.Some<PositiveInt> if input is positive, otherwise [Option.None]
         */
        @JvmStatic
        fun create(value: Int): Option<PositiveInt> = when {
            value > 0 -> Option.Some(PositiveInt(value))
            else -> Option.None
        }

        /**
         * Creates instance of PositiveInt option from given [Short]
         * @param [value] input value of type [Short]
         * @return [Option.Some] Option.Some<PositiveInt> if input is positive, otherwise [Option.None]
         */
        @JvmStatic
        fun create(value: Short) = create(value.toInt())

        /**
         * Creates instance of PositiveInt option from given [Byte]
         * @param [value] input value of type [Byte]
         * @return [Option.Some] Option.Some<PositiveInt> if input is positive, otherwise [Option.None]
         */
        @JvmStatic
        fun create(value: Byte) = create(value.toInt())

        /**
         * predefined instance of [PositiveInt] with value of 1
         */
        @JvmField
        val ONE: PositiveInt = PositiveInt(1)
        /**
         * predefined instance of [PositiveInt] with value of 2
         */
        @JvmField
        val TWO: PositiveInt = PositiveInt(2)
        /**
         * predefined instance of [PositiveInt] with value of 3
         */
        @JvmField
        val THREE: PositiveInt = PositiveInt(3)
        /**
         * predefined instance of [PositiveInt] with value of 4
         */
        @JvmField
        val FOUR: PositiveInt = PositiveInt(4)
        /**
         * predefined instance of [PositiveInt] with value of 5
         */
        @JvmField
        val FIVE: PositiveInt = PositiveInt(5)
    }
}

