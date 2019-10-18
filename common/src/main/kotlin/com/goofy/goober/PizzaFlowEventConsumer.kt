package com.goofy.goober

import android.util.Log
import com.goofy.goober.model.PizzaAction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch

/**
 * Example of a consumer that wishes to listen for UI events and do some independent
 * tracking/processing that does not affect the UI, such as firing analytics events.
 */
@ExperimentalCoroutinesApi
class PizzaFlowEventConsumer(
    // TODO: Use qualifier
    val applicationCoroutineScope: CoroutineScope
) {

    private val channel = Channel<PizzaAction>()

    init {
        applicationCoroutineScope.launch {
            channel.consumeEach { pizzaAction ->
                Log.d(
                    "PizzaFlowEventConsumer",
                    "PizzaFlowEventConsumer got ${pizzaAction.javaClass.simpleName} action"
                )
            }
        }
    }

    operator fun invoke(): ExternalEventConsumer {
        return ExternalEventConsumer(applicationCoroutineScope, channel)
    }
}
