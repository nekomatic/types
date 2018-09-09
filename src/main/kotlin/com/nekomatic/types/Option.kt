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
 *
 * @author nekomatic
 */

package com.nekomatic.types


/**
 * Functional [Option] of any non-nullable type [T]
 */
sealed class Option<out T : Any> {

    /**
     * @constructor Returns a singleton of untyped [None] option
     */
    object None : Option<Nothing>()

    /**
     * @constructor Creates an instance of [Some] option of type [T]
     */
    data class Some<out T : Any>(val value: T) : Option<T>()
}


/**
 * Converts option of type [A] into an option of type [B] using functor map(T->B)
 * @receiver [Option] Option<A>
 * @param [f]  function A -> B
 * @return [Option] Option<B>
 */
inline infix fun <reified A : Any, reified B : Any> Option<A>.map(f: (A) -> B): Option<B> =
        when (this) {
            is Option.None -> Option.None
            is Option.Some -> Option.Some(f(this.value))
        }

/**
 * Combines two options of types [A] and [B] into an option of type [C]
 * @receiver [Option] Option<A>
 * @param [that] [Option] Option<B>
 * @param [f]  function A,B -> C
 * @return [Option] Option<C>
 */
inline fun <reified A : Any, reified B : Any, reified C : Any> Option<A>.map(that: Option<B>, f: (A, B) -> C): Option<C> =
        when (that) {
            is Option.None -> Option.None
            is Option.Some -> this map { f(it, that.value) }
        }

/**
 * Applies function to the value of type [A] of the receiver option which returns an option of type [B]
 * @receiver [Option] Option<A>
 * @param [f] function A -> Option<B>
 * @return [Option] Option<C>
 */
inline infix fun <reified A : Any, reified B : Any> Option<A>.mapToOption(f: (A) -> Option<B>): Option<B> =
        when (this) {
            is Option.None -> Option.None
            is Option.Some -> f(this.value)
        }

/**
 * Applies function to the values of option [this] and [that] which returns an option
 * @receiver [Option] Option<A>
 * @param [that] Option<B>
 * @param [f] function A,B -< Option<C>
 * @return [Option] Option<C>
 */
inline fun <reified A : Any, reified B : Any, reified C : Any> Option<A>.mapToOption(that: Option<B>, f: (A, B) -> Option<C>): Option<C> =
        when (this) {
            is Option.None -> Option.None
            is Option.Some -> when (that) {
                is Option.None -> Option.None
                is Option.Some -> f(this.value, that.value)
            }
        }