package com.goofy.goober.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goofy.goober.PizzaUiInitializer
import com.goofy.goober.model.PizzaAction
import com.goofy.goober.model.PizzaState
import kotlinx.coroutines.launch

class PizzaViewModel(
    pizzaUiInitializer: PizzaUiInitializer,
    private val pizzaUi: PizzaUi,
    val childFragmentConfigs: PizzaScreenConfigs
) : ViewModel() {

    val state: LiveData<PizzaState> = pizzaUi.state

    init {
        pizzaUiInitializer { action: PizzaAction ->
            viewModelScope.launch { pizzaUi.reduce(action) }
        }
    }

    fun consumeAction(action: PizzaAction) = pizzaUi.reduce(action)
}
