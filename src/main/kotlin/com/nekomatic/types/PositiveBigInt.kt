@file:Suppress("unused")

package com.nekomatic.types

import java.math.BigInteger

/**
 * [PositiveBigInt] wraps an immutable [BigInteger] value which is guaranteed to be positive
 */
data class PositiveBigInt(private val v: BigInteger) {
    companion object {
        /**
         * Creates instance of PositiveInt option from given [BigInteger]
         * @param [value] input value of type [BigInteger]
         * @return [Option.Some] Option.Some<PositiveBigInt> if input is positive, otherwise [Option.None]
         */
        @JvmStatic
        fun create(value: BigInteger): Option<PositiveBigInt> =
                when (value.signum()) {
                    1 -> Option.Some(PositiveBigInt(value))
                    else -> Option.None
                }

        /**
         * Creates instance of PositiveInt option from given [Int]
         * @param [value] input value of type [Int]
         * @return [Option.Some] Option.Some<PositiveBigInt> if input is positive, otherwise [Option.None]
         */
        @JvmStatic
        fun create(value: Int) = create(value.toBigInteger())

        /**
         * Creates instance of PositiveInt option from given [Short]
         * @param [value] input value of type [Short]
         * @return [Option.Some] Option.Some<PositiveBigInt> if input is positive, otherwise [Option.None]
         */
        @JvmStatic
        fun create(value: Short) = create(value.toInt())

        /**
         * Creates instance of PositiveInt option from given [Byte]
         * @param [value] input value of type [Byte]
         * @return [Option.Some] Option.Some<PositiveBigInt> if input is positive, otherwise [Option.None]
         */
        @JvmStatic
        fun create(value: Byte) = create(value.toInt())

        /**
         * predefined instance of [PositiveBigInt] with value of 1
         */
        @JvmField
        val ONE = PositiveBigInt(BigInteger.ONE)

        /**
         * predefined instance of [PositiveBigInt] with value of 10
         */
        @JvmField
        val P10 = PositiveBigInt(BigInteger.TEN)

        /**
         * predefined instance of [PositiveBigInt] with value of 12
         */
        @JvmField
        val P12 = PositiveBigInt(12.toBigInteger())

        /**
         * predefined instance of [PositiveBigInt] with value of 15
         */
        @JvmField
        val P15 = PositiveBigInt(15.toBigInteger())

        /**
         * predefined instance of [PositiveBigInt] with value of 24
         */
        @JvmField
        val P24 = PositiveBigInt(24.toBigInteger())

        /**
         * predefined instance of [PositiveBigInt] with value of 25
         */
        @JvmField
        val P25 = PositiveBigInt(25.toBigInteger())

        /**
         * predefined instance of [PositiveBigInt] with value of 30
         */
        @JvmField
        val P30 = PositiveBigInt(30.toBigInteger())

        /**
         * predefined instance of [PositiveBigInt] with value of 100
         */
        @JvmField
        val P100 = PositiveBigInt(100.toBigInteger())

        /**
         * predefined instance of [PositiveBigInt] with value of 1000
         */
        @JvmField
        val P1000 = PositiveBigInt(1000.toBigInteger())

        /**
         * predefined instance of [PositiveBigInt] with value of 1001
         */
        @JvmField
        val P1001 = PositiveBigInt(1001.toBigInteger())
    }

    /**
     *
     */
    @JvmField
    val value: BigInteger = v

    /**
     * Operator [times] multiples two instances of [PositiveBigInt]
     * @param [that] [PositiveBigInt]
     * @return [PositiveBigInt]
     */
    operator fun times(that: PositiveBigInt): PositiveBigInt = PositiveBigInt(this.value * that.value)

    /**
     * Operator [plus] sums two instances of [PositiveBigInt]
     * @param [that] [PositiveBigInt]
     * @return [PositiveBigInt]
     */
    operator fun plus(that: PositiveBigInt): PositiveBigInt = PositiveBigInt(this.value + that.value)
}