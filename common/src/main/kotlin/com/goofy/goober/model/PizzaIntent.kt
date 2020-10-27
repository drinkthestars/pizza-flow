package com.goofy.goober.model

sealed class PizzaIntent {

    object ShowWelcome : PizzaIntent()

    data class FinishCustomizing(val lastChoice: String) : PizzaIntent()

    data class ContinueCustomizing(
        val previousChoice: String?,
        val question: Question.Regular
    ) : PizzaIntent()

    object StartOver : PizzaIntent()
}
