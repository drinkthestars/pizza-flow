package com.goofy.goober

import com.goofy.goober.model.PizzaAction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class PizzaUiInitializer {

    operator fun invoke(block: PizzaUiInitializer.() -> Unit) = block()

    suspend fun PizzaUi.initialize() = withContext(Dispatchers.IO) {
        delay(2_000)
        withContext(Dispatchers.Main.immediate) {
            initialize(action = PizzaAction.ShowWelcomeScreen)
        }
    }
}
