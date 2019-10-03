package com.nekomatic.types.stateMachine

import com.nekomatic.types.Option
import com.nekomatic.types.toOption

data class Target<VERTEX : Any, SLOT : Any>(val toVertex: VERTEX, val throughSocket: SLOT)

//TODO: try to make it independent form the underlying graph
//TODO: Simplify
@Suppress("unused")
data class StateMachine<STATE, COMMAND> internal constructor(
    val slotDiGraph: SlotDiGraph<STATE, COMMAND>
) : IStateMachine<StateMachine<STATE, COMMAND>, STATE, COMMAND>
        where STATE : IState,
              COMMAND : ICommand {

    constructor() : this(slotDiGraph = SlotDiGraph())


    override fun addTransition(transition: Transition<STATE, COMMAND>): StateMachine<STATE, COMMAND> {
        val newDiGraph = slotDiGraph.addConnection(transition.toConnection())
        return StateMachine(newDiGraph)
    }

    fun addTransition(fromState: STATE, toState: STATE, onCommand: COMMAND): StateMachine<STATE, COMMAND> {
        val transition = Transition(fromState, toState, onCommand)
        return addTransition(transition)
    }

    override fun transitionsOf(state: STATE): Map<COMMAND, STATE> = slotDiGraph.adjMap.getOrDefault(state, mapOf())

    override val states: Set<STATE> by lazy { slotDiGraph.adjMap.keys }

    override val transitions: Set<Transition<STATE, COMMAND>> by lazy {
        slotDiGraph.connections.map { it.toTransition() }.toSet()
    }

    override fun execute(command: COMMAND, onState: STATE): Option<STATE> {
        val currentTargets = slotDiGraph.adjMap.getOrDefault(onState, mapOf())
        return currentTargets[command].toOption()
    }
}

fun <STATE : IState, COMMAND : ICommand> Connection<STATE, COMMAND>.toTransition(): Transition<STATE, COMMAND> =
    Transition(this.fromVertex, this.toVertex, this.throughSocket)

fun <STATE : IState, COMMAND : ICommand> Transition<STATE, COMMAND>.toConnection(): Connection<STATE, COMMAND> =
    Connection(this.fromState, this.toState, this.onCommand)


