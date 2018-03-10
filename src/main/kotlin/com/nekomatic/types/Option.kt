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

//TODO: Create tests
sealed class Option<out T : Any> {
    object None : Option<Nothing>()
    data class Some<out T : Any>(val value: T) : Option<T>()
}

// f: (T) -> B
inline infix fun <T : Any, B : Any> Option<T>.map(f: (T) -> B): Option<B> =
        when (this) {
            is Option.None -> Option.None
            is Option.Some -> Option.Some(f(this.value))
        }

// f: (T,B) -> C
inline fun <T : Any, B : Any, C : Any> Option<T>.map(second: Option<B>, f: (T, B) -> C): Option<C> =
        when (second) {
            is Option.None -> Option.None
            is Option.Some -> this map { f(it, second.value) }
        }

// f: (T) -> Option<B>
inline infix fun <T : Any, B : Any> Option<T>.mapToOption(f: (T) -> Option<B>): Option<B> =
        when (this) {
            is Option.None -> Option.None
            is Option.Some -> f(this.value)
        }

// f: (T,B) -> Option<C>
inline fun <T : Any, B : Any, C : Any> Option<T>.mapToOption(second: Option<B>, f: (T, B) -> Option<C>): Option<C> =
        when (this) {
            is Option.None -> Option.None
            is Option.Some -> when (second) {
                is Option.None -> Option.None
                is Option.Some -> f(this.value, second.value)
            }
        }