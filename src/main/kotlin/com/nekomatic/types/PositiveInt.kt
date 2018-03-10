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


import com.nekomatic.types.Option

open class PositiveInt private constructor(val value: Int) {

    companion object {

        fun create(int: Int): Option<PositiveInt> = when {
            int > 0 -> Option.Some(PositiveInt(int))
            else -> Option.None
        }

        fun create(value: Short) = create(value.toInt())
        fun create(value: Byte) = create(value.toInt())
        val ONE: PositiveInt = PositiveInt(1)
        val TWO: PositiveInt = PositiveInt(2)
        val THREE: PositiveInt = PositiveInt(3)
        val FOUR: PositiveInt = PositiveInt(4)
        val FIVE: PositiveInt = PositiveInt(5)
    }
}

