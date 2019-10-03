package com.nekomatic.types.stateMachine

interface ISlotGraph<VERTEX : Any, SLOT : Any> {
    val vertices: List<VERTEX>
    val connections: List<Connection<VERTEX, SLOT>>
    fun targetsOf(vertex: VERTEX): List<Target<VERTEX, SLOT>>
    fun neighbours(vertex: VERTEX): Map<SLOT, VERTEX>

}