package com.nekomatic.types.stateMachine

data class Connection<VERTEX : Any, SLOT : Any>(val fromVertex: VERTEX, val toVertex: VERTEX, val throughSocket: SLOT)