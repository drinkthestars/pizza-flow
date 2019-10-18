package com.goofy.goober.model

import com.goofy.goober.model.PizzaAction.*

sealed class PizzaState {

    abstract fun reduce(action: PizzaAction): PizzaState

    object UnInitialized: PizzaState() {
        override fun reduce(action: PizzaAction): PizzaState {
            return when(action) {
                ShowWelcomeScreen -> WelcomeScreen

                is ContinueCustomizing,
                is FinishCustomizing -> this
            }
        }
    }

    object WelcomeScreen: PizzaState() {
        override  fun reduce(action: PizzaAction): PizzaState {
            return when(action) {
                is ContinueCustomizing -> StillCustomizing(
                    choicesMadeSoFar = emptyList(),
                    currentQuestion = action.question
                )

                ShowWelcomeScreen,
                is FinishCustomizing -> this
            }
        }
    }

    data class StillCustomizing(val choicesMadeSoFar: List<String>, val currentQuestion: Question) :
        PizzaState() {
        override  fun reduce(action: PizzaAction): PizzaState {
            return when(action) {
                is ContinueCustomizing -> {
                    val newChoices = action.previousChoice
                        ?.let { choicesMadeSoFar + it }
                        ?: choicesMadeSoFar
                    action.question.state(newChoices = newChoices)
                }
                is FinishCustomizing -> FinishedCustomizing(result = action.result)

                ShowWelcomeScreen -> this
            }
        }
    }

    data class FinishedCustomizing(val result: String) : PizzaState() {
        override  fun reduce(action: PizzaAction): PizzaState {
            return when(action) {
                // TODO: Add functionality to start over
                ShowWelcomeScreen,
                is ContinueCustomizing,
                is FinishCustomizing -> this
            }
        }
    }
}
