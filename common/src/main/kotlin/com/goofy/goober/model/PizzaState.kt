package com.goofy.goober.model

sealed class PizzaState {

    abstract fun reduce(action: PizzaAction): PizzaState

    object UnInitialized: PizzaState() {
        override fun reduce(action: PizzaAction): PizzaState {
            return when(action) {
                PizzaAction.ShowWelcomeScreen -> WelcomeScreen

                is PizzaAction.Ongoing.ProceedNextQuestion,
                is PizzaAction.Terminal.End -> this
            }
        }
    }

    object WelcomeScreen: PizzaState() {
        override  fun reduce(action: PizzaAction): PizzaState {
            return when(action) {
                is PizzaAction.Ongoing.ProceedNextQuestion -> Ongoing(
                    answers = emptyList(),
                    currentQuestion = action.question
                )

                PizzaAction.ShowWelcomeScreen,
                is PizzaAction.Terminal.End -> this
            }
        }
    }

    data class Ongoing(val answers: List<String>, val currentQuestion: Question): PizzaState() {
        override  fun reduce(action: PizzaAction): PizzaState {
            return when(action) {
                is PizzaAction.Ongoing.ProceedNextQuestion -> {
                    val newAnswers = action.previousAnswer?.let { answers + it } ?: answers
                    action.question.state(newAnswers = newAnswers)
                }
                is PizzaAction.Terminal.End -> Ended(
                    result = action.result
                )

                PizzaAction.ShowWelcomeScreen -> this
            }
        }
    }

    data class Ended(val result: String): PizzaState() {
        override  fun reduce(action: PizzaAction): PizzaState {
            return when(action) {
                is PizzaAction.Ongoing.ProceedNextQuestion -> TODO()

                PizzaAction.ShowWelcomeScreen,
                is PizzaAction.Terminal.End -> this
            }
        }
    }
}