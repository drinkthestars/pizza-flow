package com.goofy.goober.model

import com.goofy.goober.makeAnswer

private val styles = listOf(
    "deep dish",
    "thin as paper",
    "og neapolitan"
)
private val sauces = listOf(
    "marinara",
    "white",
    "bbq"
)
private val toppings1 = listOf(
    "mushrooms",
    "peppers",
    "garlic",
    "olives",
    "nutella"
)
private val toppings2 = listOf(
    "chicken",
    "pepperoni",
    "sausage",
    "broccoli",
    "pineapple",
    "more nutella"
)

sealed class Question {
    abstract val id: Int
    abstract val value: String
    abstract val options: Options

    companion object {
        val firstQuestion = Regular(
            id = 0,
            value = "choose the style:",
            options = Options(styles)
        )
    }

    class Regular(
        override val value: String,
        override val options: Options,
        override val id: Int
    ) : Question() {

        fun nextQuestion(): Question {
            return when (id) {
                0 -> Regular(
                    id = id + 1,
                    value = "choose the sauce:",
                    options = Options(sauces)
                )
                1 -> Regular(
                    id = id + 1,
                    value = "choose a topping:",
                    options = Options(toppings1)
                )
                else -> Final(
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

        override val id: Int = 2

        fun computeAnswer(previousAnswers: List<String>): String = previousAnswers.makeAnswer()
    }
}

fun Question.state(newAnswers: List<String>): PizzaState {
    return when (this) {
        is Question.Regular -> PizzaState.Ongoing(newAnswers, currentQuestion = nextQuestion())
        is Question.Final -> PizzaState.Ended(computeAnswer(newAnswers))
    }
}

data class Options(
    val values: List<String>
)
