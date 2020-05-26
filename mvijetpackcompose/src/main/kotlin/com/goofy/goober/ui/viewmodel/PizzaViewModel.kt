package com.goofy.goober.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.goofy.goober.model.PizzaAction

internal class PizzaViewModel(
    private val uiState: PizzaUIState
) : ViewModel() {

    val currentState get() = uiState.state

    init {
        reduce(action = PizzaAction.ShowWelcome)
    }

    fun reduce(action: PizzaAction) = uiState.reduce(action)
}
