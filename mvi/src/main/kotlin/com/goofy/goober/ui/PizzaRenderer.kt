package com.goofy.goober.ui

import android.view.View
import com.goofy.goober.model.PizzaIntent
import com.goofy.goober.model.PizzaState
import com.goofy.goober.model.Question
import com.goofy.goober.model.intent
import com.goofy.goober.ui.fragment.EndFragment
import com.goofy.goober.ui.fragment.WelcomeFragment
import com.goofy.goober.ui.navigation.NavRouter
import com.goofy.goober.ui.navigation.Screen
import com.goofy.goober.ui.view.QuestionView

internal class PizzaRenderer(
    private val navRouter: NavRouter
) {

    fun render(
        pizzaState: PizzaState,
        onIntent: (PizzaIntent) -> Unit
    ) {
        pizzaState.renderInternal(onIntent)
    }

    private fun PizzaState.renderInternal(onIntent: (PizzaIntent) -> Unit) {
        when (this) {
            PizzaState.Loading -> {
                loading()
            }
            PizzaState.Welcome -> {
                welcomeScreen(onIntent)
            }
            is PizzaState.StillCustomizing -> {
                ongoing(onIntent, this.currentQuestion)
            }
            is PizzaState.FinishedCustomizing -> {
                ended(answer = this.result)
            }
        }.let {}
    }

    private fun loading() {
        val screenState = WelcomeFragment.State(
            welcomeVisibility = View.GONE,
            progressVisibility = View.VISIBLE,
            onStartClick = { }
        )
        navRouter.navigateTo(Screen.Welcome(screenState))
    }

    private fun welcomeScreen(onIntent: (PizzaIntent) -> Unit) {
        val screenState = WelcomeFragment.State(
            welcomeVisibility = View.VISIBLE,
            progressVisibility = View.GONE,
            onStartClick = {
                onIntent(
                    PizzaIntent.ContinueCustomizing(
                        previousChoice = null,
                        question = Question.firstQuestion
                    )
                )
            }
        )
        navRouter.navigateTo(Screen.Welcome(screenState))
    }

    private fun ongoing(
        onIntent: (PizzaIntent) -> Unit,
        question: Question
    ) {
        val screenState = QuestionView.State(
            question = question,
            clickListener = { text -> onIntent(question.intent(choice = text)) }
        )
        navRouter.navigateTo(Screen.Question(screenState))
    }

    private fun ended(answer: String) {
        val screenState = EndFragment.State(answer)
        navRouter.navigateTo(Screen.End(screenState))
    }
}
