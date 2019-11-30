package com.goofy.goober.ui.viewmodel

import androidx.compose.Model
import com.goofy.goober.model.PizzaAction
import com.goofy.goober.model.PizzaState

@Model
class CurrentPizzaUIState() {

    var currentState: PizzaState = PizzaState.UnInitialized

    operator fun invoke(block: CurrentPizzaUIState.() -> Unit) = block()

    fun reduce(action: PizzaAction) {
        val fromState = currentState
        fromState.reduce(action).also { toState ->
            if (fromState != toState) currentState = toState
        }
    }
}
