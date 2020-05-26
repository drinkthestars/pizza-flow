package com.goofy.goober.model

sealed class PizzaAction {

    object ShowWelcome : PizzaAction()

    data class FinishCustomizing(val lastChoice: String) : PizzaAction()

    data class ContinueCustomizing(
        val previousChoice: String?,
        val question: Question.Regular
    ) : PizzaAction()

    object StartOver : PizzaAction()
}
