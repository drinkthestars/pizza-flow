package com.goofy.goober.model

import com.goofy.goober.ext.makeAnswer
import com.goofy.goober.model.PizzaIntent.*

sealed class PizzaState {

    abstract fun reduce(intent: PizzaIntent): PizzaState

    object Loading: PizzaState() {
        override fun reduce(intent: PizzaIntent): PizzaState {
            return when(intent) {
                ShowWelcome -> Welcome

                StartOver,
                is ContinueCustomizing,
                is FinishCustomizing -> this
            }
        }
    }

    object Welcome: PizzaState() {
        override  fun reduce(intent: PizzaIntent): PizzaState {
            return when(intent) {
                is ContinueCustomizing -> StillCustomizing(
                    choicesMadeSoFar = emptyList(),
                    currentQuestion = intent.question
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
        override  fun reduce(intent: PizzaIntent): PizzaState {
            return when(intent) {
                is ContinueCustomizing -> {
                    val newChoices = intent.previousChoice
                        ?.let { choicesMadeSoFar + it }
                        ?: choicesMadeSoFar
                    StillCustomizing(
                        newChoices,
                        currentQuestion = intent.question.nextQuestion()
                    )
                }
                is FinishCustomizing -> {
                    val allChoices = choicesMadeSoFar + intent.lastChoice
                    FinishedCustomizing(allChoices.makeAnswer())
                }

                StartOver,
                ShowWelcome -> this
            }
        }
    }

    data class FinishedCustomizing(val result: String) : PizzaState() {
        override  fun reduce(intent: PizzaIntent): PizzaState {
            return when(intent) {
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
    val intent: PizzaIntent
)
