package com.nekomatic.types

abstract class Monoid<T:Any>(val value: T) {
    abstract infix fun T.sum(another: T) :T
    abstract val sumIdentity:T
}


