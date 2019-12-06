package com.goofy.goober.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.goofy.goober.model.PizzaAction

class PizzaViewModel(
    val currentCurrentPizzaState: CurrentPizzaUIState
) : ViewModel() {

    init {
        currentCurrentPizzaState.reduce(PizzaAction.ShowWelcomeScreen)
    }
}
