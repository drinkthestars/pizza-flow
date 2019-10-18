package com.goofy.goober

import android.util.Log
import com.goofy.goober.model.PizzaAction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch

class PizzaFlowEventConsumer(
    val applicationCoroutineScope: CoroutineScope
) {

    private val channel = Channel<PizzaAction>()

    init {
        applicationCoroutineScope.launch {
            channel.consumeEach { pizzaAction ->
                Log.d("WARP", "PizzaFlowEventConsumer got $pizzaAction action")
            }
        }
    }

    operator fun invoke(): ExternalEventConsumer {
        return ExternalEventConsumer(applicationCoroutineScope, channel)
    }
}
