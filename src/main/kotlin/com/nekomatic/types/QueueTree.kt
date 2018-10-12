//package com.nekomatic.types
//
////TODO: replace 'data' with basic equality
//data class QueueTree<T : Any> private constructor(
//
//        val innerQueue: IQueue<IQueue<T>> = Queue(),
//        override val maxWidth: Int,
//        override val degree: Int,
//        override val size: Int
//) : IQueue<T> {
//    private constructor(q: IQueue<T>) : this(
//            inQueue = q,
//            innerQueue = Queue<IQueue<T>>(),
//            outQueue = Queue(),
//            maxWidth = q.maxWidth,
//            degree = q.degree + 1,
//            size = q.size
//    )
//
//
//
//
//}
//
