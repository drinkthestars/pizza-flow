package com.goofy.goober

import com.goofy.goober.model.PizzaAction

class PizzaUiInitializer {

    operator fun invoke(block: PizzaUiInitializer.() -> Unit) = block()

    // Commenting out since this isn't working with the stdlib 1.3.60-eap-25
//    suspend fun PizzaUi.initialize() = withContext(Dispatchers.IO) {
//        delay(2_000)
//        withContext(Dispatchers.Main.immediate) {
//            initialize(action = PizzaAction.ShowWelcomeScreen)
//        }
//    }

    fun PizzaUi.initialize() {
        initialize(action = PizzaAction.ShowWelcomeScreen)
    }
}
