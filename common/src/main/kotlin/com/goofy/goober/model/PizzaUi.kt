package com.goofy.goober.model

import com.goofy.goober.model.PizzaState.Loading
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalCoroutinesApi::class)
class PizzaUi(private val externalEventConsumer: (Transition) -> Unit) {
    val state: StateFlow<PizzaState> get() = _state

    private val _state: MutableStateFlow<PizzaState> = MutableStateFlow(Loading)

    operator fun invoke(block: PizzaUi.() -> Unit) = block()

    fun reduce(intent: PizzaIntent) {
        val fromState = _state.value
        val toState = fromState.reduce(intent)
        if (fromState != toState) _state.value = toState

        Transition(
            fromState = fromState,
            toState = toState,
            intent = intent
        ).also { externalEventConsumer(it) }
    }
}
