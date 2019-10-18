package com.goofy.goober.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.goofy.goober.PizzaUi
import com.goofy.goober.PizzaUiInitializer
import com.goofy.goober.model.PizzaAction
import com.goofy.goober.model.PizzaState
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class PizzaViewModel(
    pizzaUiInitializer: PizzaUiInitializer,
    private val pizzaUi: PizzaUi
) : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext = Job() + Dispatchers.Main

    val state: LiveData<PizzaState> = pizzaUi.state

    init {
        pizzaUiInitializer {
            launch { pizzaUi.initialize() }
        }
    }

    fun consumeAction(action: PizzaAction) {
        pizzaUi.reduce(action)
    }

    override fun onCleared() = cancel()
}
