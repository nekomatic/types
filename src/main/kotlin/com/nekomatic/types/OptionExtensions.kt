@file:kotlin.jvm.JvmName("OptionExtensions")

package com.nekomatic.types

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