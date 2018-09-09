/**
 * @author nekomatic
 */


@file:Suppress("unused")

package com.nekomatic.types

sealed class Either<out T1 : Any, out T2 : Any> {
    data class Left<out T1 : Any, out T2 : Any>(val value: T1) : Either<T1, T2>()
    data class Right<out T1 : Any, out T2 : Any>(val value: T2) : Either<T1, T2>()
}

