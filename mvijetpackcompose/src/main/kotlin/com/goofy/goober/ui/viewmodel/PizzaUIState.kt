package com.goofy.goober.ui.viewmodel

import androidx.compose.Model
import com.goofy.goober.model.PizzaAction
import com.goofy.goober.model.PizzaState

@Model
class PizzaUIState {

    var state: PizzaState = PizzaState.UnInitialized

    fun reduce(action: PizzaAction) {
        val fromState = state
        fromState.reduce(action).also { toState ->
            if (fromState != toState) state = toState
        }
    }
}
