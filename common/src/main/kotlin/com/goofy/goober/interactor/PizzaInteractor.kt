package com.goofy.goober.interactor

import com.goofy.goober.model.PizzaIntent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class PizzaInteractor {

    suspend fun produceInitIntent(): PizzaIntent = withContext(Dispatchers.IO) {
        // Do some work
        delay(2_000)

        PizzaIntent.ShowWelcome
    }
}
