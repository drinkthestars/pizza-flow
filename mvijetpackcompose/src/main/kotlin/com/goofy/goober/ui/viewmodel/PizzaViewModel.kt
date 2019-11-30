package com.goofy.goober.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goofy.goober.PizzaUiInitializer
import kotlinx.coroutines.launch

class PizzaViewModel(
    pizzaUiInitializer: PizzaUiInitializer,
    val currentCurrentPizzaState: CurrentPizzaUIState
) : ViewModel() {

    init {
        pizzaUiInitializer { action ->
            viewModelScope.launch { currentCurrentPizzaState.reduce(action) }
        }
    }
}
