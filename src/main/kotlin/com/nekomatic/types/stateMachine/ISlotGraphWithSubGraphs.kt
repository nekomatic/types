package com.nekomatic.types.stateMachine

interface ISlotGraphWithSubGraphs<VERTEX : Any, SLOT : Any> :
    ISlotGraph<VERTEX, SLOT> {
    fun subGraph(roots: List<VERTEX>): ISlotGraphWithSubGraphs<VERTEX, SLOT>
}