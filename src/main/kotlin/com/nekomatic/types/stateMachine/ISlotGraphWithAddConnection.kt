package com.nekomatic.types.stateMachine

interface ISlotGraphWithAddConnection<VERTEX : Any, SLOT : Any, GRAPH : ISlotGraph<VERTEX, SLOT>> :
    ISlotGraph<VERTEX, SLOT> {
    fun addConnection(e: Connection<VERTEX, SLOT>): GRAPH
}