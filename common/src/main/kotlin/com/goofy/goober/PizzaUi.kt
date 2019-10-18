package com.goofy.goober

import androidx.lifecycle.MutableLiveData
import com.goofy.goober.model.PizzaAction
import com.goofy.goober.model.PizzaState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.launch

class PizzaUi(private val externalEventConsumer: ExternalEventConsumer) {

    val state: MutableLiveData<PizzaState> = MutableLiveData<PizzaState>().apply { value =
        PizzaState.UnInitialized
    }

    operator fun invoke(block: PizzaUi.() -> Unit) = block()

    fun initialize(action: PizzaAction.ShowWelcomeScreen) {
        reduce(action)
    }

    fun reduce(action: PizzaAction) {
        val currentState = state.value
        currentState?.reduce(action)?.also {
            if (currentState != it) state.value = it
        }

        with(externalEventConsumer) {
            coroutineScope.launch(Dispatchers.Default) {
                sendChannel.send(action)
            }
        }
    }
}

data class ExternalEventConsumer(
    val coroutineScope: CoroutineScope,
    val sendChannel: SendChannel<PizzaAction>
)
