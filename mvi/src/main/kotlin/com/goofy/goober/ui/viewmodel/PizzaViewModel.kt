package com.goofy.goober.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goofy.goober.PizzaUiInitializer
import com.goofy.goober.model.PizzaAction
import com.goofy.goober.model.PizzaState
import com.goofy.goober.model.PizzaUi
import com.goofy.goober.ui.state.PizzaScreenStates
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
internal class PizzaViewModel(
    pizzaUiInitializer: PizzaUiInitializer,
    private val pizzaUi: PizzaUi,
    val pizzaScreenStates: PizzaScreenStates
) : ViewModel() {

    val state: StateFlow<PizzaState> get() = pizzaUi.state

    init {
        viewModelScope.launch {
            pizzaUiInitializer { action: PizzaAction ->
                pizzaUi.reduce(action)
            }
        }
    }

    fun consumeAction(action: PizzaAction) = pizzaUi.reduce(action)
}
