package com.nekomatic.types.stateMachine

import com.nekomatic.types.Option

interface IState

data class Transition<STATE : IState, COMMAND : ICommand>(val fromState: STATE, val toState: STATE, val onCommand: COMMAND)
interface ICommand

interface IStateMachine<MACHINE, STATE, COMMAND>
        where MACHINE : IStateMachine<MACHINE, STATE, COMMAND>,
              STATE : IState,
              COMMAND : ICommand {
    val states: Set<STATE>
    val transitions: Set<Transition<STATE, COMMAND>>
    fun addTransition(transition: Transition<STATE, COMMAND>): MACHINE
    fun transitionsOf(state: STATE): Map<COMMAND, STATE>
    fun execute(command: COMMAND, onState: STATE): Option<STATE>
}