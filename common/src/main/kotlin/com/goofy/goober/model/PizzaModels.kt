package com.goofy.goober.model

import com.goofy.goober.makeAnswer
import com.goofy.goober.model.Step.*

enum class Step {
    Styles, Sauces, Toppings1
}

sealed class Question {
    abstract val value: String
    abstract val options: Options

    companion object {
        val firstQuestion = Regular(
            step = Styles,
            value = "choose a style:",
            options = Options(styles)
        )
    }

    class Regular(
        override val value: String,
        override val options: Options,
        val step: Step
    ) : Question() {

        fun nextQuestion(): Question {
            return when (step) {
                Styles -> Regular(
                    step = Sauces,
                    value = "choose a sauce:",
                    options = Options(sauces)
                )
                Sauces -> Regular(
                    step = Toppings1,
                    value = "choose a topping:",
                    options = Options(toppings1)
                )
                Toppings1 -> Final(
                    value = "choose another topping:",
                    options = Options(toppings2)
                )
            }
        }
    }

    data class Final(
        override val value: String,
        override val options: Options
    ) : Question() {

        fun computeAnswer(previousChoices: List<String>): String = previousChoices.makeAnswer()
    }
}

data class Options(val values: List<String>)

private val styles = listOf(
    "og neapolitan",
    "new york style",
    "deep dish"
)
private val sauces = listOf(
    "marinara",
    "white",
    "bbq"
)
private val toppings1 = listOf(
    "mushrooms",
    "peppers",
    "prosciutto",
    "garlic",
    "eggplant",
    "olives",
    "nutella"
)
private val toppings2 = listOf(
    "chicken",
    "pepperoni",
    "sausage",
    "broccoli",
    "pineapple?!",
    "more nutella"
)

fun Question.state(newChoices: List<String>): PizzaState {
    return when (this) {
        is Question.Regular -> PizzaState.StillCustomizing(
            newChoices,
            currentQuestion = nextQuestion()
        )
        is Question.Final -> PizzaState.FinishedCustomizing(computeAnswer(newChoices))
    }
}