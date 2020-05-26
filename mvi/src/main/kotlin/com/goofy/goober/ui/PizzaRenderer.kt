package com.goofy.goober.ui

import android.view.View
import androidx.navigation.NavController
import com.goofy.goober.R
import com.goofy.goober.model.PizzaAction
import com.goofy.goober.model.PizzaState
import com.goofy.goober.model.Question
import com.goofy.goober.model.relatedAction
import com.goofy.goober.ui.fragment.EndFragment
import com.goofy.goober.ui.fragment.WelcomeFragment
import com.goofy.goober.ui.state.PizzaScreenStates
import com.goofy.goober.ui.view.QuestionView

class PizzaRenderer {

    operator fun invoke(block: PizzaRenderer.() -> Unit) = block()

    fun PizzaState.render(
        actionRouter: (PizzaAction) -> Unit,
        screenStates: PizzaScreenStates,
        navController: NavController
    ) {
        when (this) {
            PizzaState.UnInitialized -> {
                unInitialized(screenStates)
            }
            PizzaState.Welcome -> {
                welcomeScreen(actionRouter, screenStates)
            }
            is PizzaState.StillCustomizing -> {
                ongoing(
                    actionRouter,
                    this.currentQuestion,
                    screenStates,
                    navController
                )
            }
            is PizzaState.FinishedCustomizing -> {
                ended(
                    answer = this.result,
                    screenStates = screenStates,
                    navController = navController
                )
            }
        }.let {}
    }

    private fun unInitialized(screenStates: PizzaScreenStates) {
        WelcomeFragment.State(
            welcomeVisibility = View.GONE,
            progressVisibility = View.VISIBLE,
            onStartClick = View.OnClickListener { }
        ).also { screenStates.updateWelcomeState(it) }
    }

    private fun welcomeScreen(
        actionRouter: (PizzaAction) -> Unit,
        screenStates: PizzaScreenStates
    ) {
        WelcomeFragment.State(
            welcomeVisibility = View.VISIBLE,
            progressVisibility = View.GONE,
            onStartClick = View.OnClickListener {
                actionRouter(
                    PizzaAction.ContinueCustomizing(
                        previousChoice = null,
                        question = Question.firstQuestion
                    )
                )
            }
        ).also { screenStates.updateWelcomeState(it) }
    }

    private fun ongoing(
        actionRouter: (PizzaAction) -> Unit,
        question: Question,
        screenStates: PizzaScreenStates,
        navController: NavController
    ) {
        with(navController) {
            if (currentDestination?.id == R.id.welcomeFragment) {
                navigate(R.id.action_welcomeFragment_to_questionFragment)
            }
        }
        QuestionView.State(
            question = question,
            clickListener = { text -> actionRouter(question.relatedAction(choice = text)) }
        ).also { screenStates.updateQuestionState(it) }
    }

    private fun ended(
        answer: String,
        screenStates: PizzaScreenStates,
        navController: NavController
    ) {
        navController.navigate(R.id.action_questionFragment_to_endFragment)
        screenStates.updateEndState(EndFragment.State(answer))
    }
}
