package com.goofy.goober.model

sealed class PizzaAction {

    object ShowWelcomeScreen : PizzaAction()

    data class FinishCustomizing(val result: String) : PizzaAction()

    data class ContinueCustomizing(
        val previousChoice: String?,
        val question: Question
    ) : PizzaAction()
}
