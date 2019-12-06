package com.goofy.goober

import com.goofy.goober.model.PizzaAction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class PizzaUiInitializer {

    suspend operator fun invoke(block: PizzaUiInitializer.(PizzaAction) -> Unit) = withContext(
        Dispatchers.IO) {
        // Do some work
        delay(2_000)

        withContext(Dispatchers.Main.immediate) {
            block(PizzaAction.ShowWelcomeScreen)
        }
    }
}
