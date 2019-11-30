package com.goofy.goober.ui

import android.view.View
import androidx.navigation.NavController
import com.goofy.goober.R
import com.goofy.goober.model.PizzaAction
import com.goofy.goober.model.PizzaState
import com.goofy.goober.model.Question
import com.goofy.goober.model.relatedAction
import com.goofy.goober.ui.fragment.EndFragment
import com.goofy.goober.ui.fragment.QuestionFragment
import com.goofy.goober.ui.fragment.WelcomeFragment
import com.goofy.goober.ui.viewmodel.PizzaScreenConfigs

class PizzaRenderer {

    operator fun invoke(block: PizzaRenderer.() -> Unit) = block()

    fun PizzaState.render(
        actionRouter: (PizzaAction) -> Unit,
        screenConfigs: PizzaScreenConfigs,
        navController: NavController
    ) {
        when (this) {
            PizzaState.UnInitialized -> {
                unInitialized(screenConfigs)
            }
            PizzaState.WelcomeScreen -> {
                welcomeScreen(actionRouter, screenConfigs)
            }
            is PizzaState.StillCustomizing -> {
                ongoing(
                    actionRouter,
                    this.currentQuestion,
                    screenConfigs,
                    navController
                )
            }
            is PizzaState.FinishedCustomizing -> {
                ended(
                    answer = this.result,
                    screenConfigs = screenConfigs,
                    navController = navController
                )
            }
        }.let {}
    }

    private fun unInitialized(screenConfigs: PizzaScreenConfigs) {
        WelcomeFragment.ViewConfig(
            welcomeVisibility = View.GONE,
            progressVisibility = View.VISIBLE,
            onStartClick = View.OnClickListener { }
        ).also { screenConfigs.updateWelcomeConfig(it) }
    }

    private fun welcomeScreen(
        actionRouter: (PizzaAction) -> Unit,
        screenConfigs: PizzaScreenConfigs
    ) {
        WelcomeFragment.ViewConfig(
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
        ).also { screenConfigs.updateWelcomeConfig(it) }
    }

    private fun ongoing(
        actionRouter: (PizzaAction) -> Unit,
        question: Question,
        screenConfigs: PizzaScreenConfigs,
        navController: NavController
    ) {
        with(navController) {
            if (currentDestination?.id == R.id.welcomeFragment) {
                navigate(R.id.action_welcomeFragment_to_questionFragment)
            }
        }
        QuestionFragment.ViewConfig(
            question = question,
            clickListener = { text -> actionRouter(question.relatedAction(choice = text)) }
        ).also { screenConfigs.updateQuestionConfig(it) }
    }

    private fun ended(
        answer: String,
        screenConfigs: PizzaScreenConfigs,
        navController: NavController
    ) {
        navController.navigate(R.id.action_questionFragment_to_endFragment)
        screenConfigs.updateEndConfig(EndFragment.ViewConfig(answer))
    }
}
