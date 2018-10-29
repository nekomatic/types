package com.nekomatic.types.graph

import com.nekomatic.types.Monoid

data class WeightedEdge<V : Any, W : Any>(val destination: V, val weight: W)