package com.goofy.goober.interactor

import com.goofy.goober.model.PizzaAction
import com.goofy.goober.model.PizzaState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class PizzaInteractor {

    suspend fun initAction(): PizzaAction = withContext(Dispatchers.IO) {
        // Do some work
        delay(2_000)

        PizzaAction.ShowWelcome
    }
}
