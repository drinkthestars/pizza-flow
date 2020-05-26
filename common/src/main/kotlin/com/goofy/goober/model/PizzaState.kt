package com.goofy.goober.model

import com.goofy.goober.makeAnswer
import com.goofy.goober.model.PizzaAction.*

sealed class PizzaState {

    abstract fun reduce(action: PizzaAction): PizzaState

    object UnInitialized: PizzaState() {
        override fun reduce(action: PizzaAction): PizzaState {
            return when(action) {
                ShowWelcome -> Welcome

                StartOver,
                is ContinueCustomizing,
                is FinishCustomizing -> this
            }
        }
    }

    object Welcome: PizzaState() {
        override  fun reduce(action: PizzaAction): PizzaState {
            return when(action) {
                is ContinueCustomizing -> StillCustomizing(
                    choicesMadeSoFar = emptyList(),
                    currentQuestion = action.question
                )

                StartOver,
                ShowWelcome,
                is FinishCustomizing -> this
            }
        }
    }

    data class StillCustomizing(
        val choicesMadeSoFar: List<String>,
        val currentQuestion: Question
    ) : PizzaState() {
        override  fun reduce(action: PizzaAction): PizzaState {
            return when(action) {
                is ContinueCustomizing -> {
                    val newChoices = action.previousChoice
                        ?.let { choicesMadeSoFar + it }
                        ?: choicesMadeSoFar
                    StillCustomizing(
                        newChoices,
                        currentQuestion = action.question.nextQuestion()
                    )
                }
                is FinishCustomizing -> {
                    val allChoices = choicesMadeSoFar + action.lastChoice
                    FinishedCustomizing(allChoices.makeAnswer())
                }

                StartOver,
                ShowWelcome -> this
            }
        }
    }

    data class FinishedCustomizing(val result: String) : PizzaState() {
        override  fun reduce(action: PizzaAction): PizzaState {
            return when(action) {
                StartOver -> Welcome

                ShowWelcome,
                is ContinueCustomizing,
                is FinishCustomizing -> this
            }
        }
    }
}

data class Transition(
    val fromState: PizzaState,
    val toState: PizzaState,
    val action: PizzaAction
)
