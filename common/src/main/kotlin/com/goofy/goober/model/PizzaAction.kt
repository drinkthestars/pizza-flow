package com.goofy.goober.model

sealed class PizzaAction {

    object ShowWelcomeScreen: PizzaAction()

    sealed class Terminal {
        data class End(val result: String) : PizzaAction()
    }

    sealed class Ongoing {
        data class ProceedNextQuestion(
            val previousAnswer: String?,
            val question: Question
        ): PizzaAction()
    }
}
