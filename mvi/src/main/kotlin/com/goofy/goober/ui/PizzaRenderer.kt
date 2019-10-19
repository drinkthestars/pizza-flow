package com.goofy.goober.ui

import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import com.goofy.goober.R
import com.goofy.goober.model.PizzaAction
import com.goofy.goober.model.PizzaState
import com.goofy.goober.model.Question
import com.goofy.goober.model.relatedAction
import com.goofy.goober.ui.fragment.EndFragment
import com.goofy.goober.ui.fragment.QuestionFragment
import com.goofy.goober.ui.fragment.WelcomeFragment
import com.goofy.goober.ui.viewmodel.ChildConfigRenderer

class PizzaRenderer {

    operator fun invoke(block: PizzaRenderer.() -> Unit) = block()

    fun createObserver(
        actionRouter: (PizzaAction) -> Unit,
        childConfigRenderer: ChildConfigRenderer,
        navController: NavController
    ): Observer<PizzaState> {
        return Observer { pizzaState ->
            when (pizzaState) {
                PizzaState.UnInitialized -> {
                    unInitialized(childConfigRenderer)
                }
                PizzaState.WelcomeScreen -> {
                    welcomeScreen(actionRouter, childConfigRenderer)
                }
                is PizzaState.StillCustomizing -> {
                    ongoing(
                        actionRouter,
                        pizzaState.currentQuestion,
                        childConfigRenderer,
                        navController
                    )
                }
                is PizzaState.FinishedCustomizing -> {
                    ended(
                        answer = pizzaState.result,
                        childConfigRenderer = childConfigRenderer,
                        navController = navController
                    )
                }
            }.let {}
        }
    }

    private fun unInitialized(childConfigRenderer: ChildConfigRenderer) {
        childConfigRenderer.welcomeConfig.value = WelcomeFragment.Config(
            welcomeVisibility = View.GONE,
            progressVisibility = View.VISIBLE,
            onStartClick = View.OnClickListener { }
        )
    }

    private fun welcomeScreen(
        actionRouter: (PizzaAction) -> Unit,
        childConfigRenderer: ChildConfigRenderer
    ) {
        childConfigRenderer.welcomeConfig.value = WelcomeFragment.Config(
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
        )
    }

    private fun ongoing(
        actionRouter: (PizzaAction) -> Unit,
        question: Question,
        childConfigRenderer: ChildConfigRenderer,
        navController: NavController
    ) {
        with(navController) {
            if (currentDestination?.id == R.id.welcomeFragment) {
                navigate(R.id.action_welcomeFragment_to_questionFragment)
            }
        }
        childConfigRenderer.questionConfig.value = QuestionFragment.Config(
            question = question,
            clickListener = { text -> actionRouter(question.relatedAction(choice = text)) }
        )
    }

    private fun ended(
        answer: String,
        childConfigRenderer: ChildConfigRenderer,
        navController: NavController
    ) {
        navController.navigate(R.id.action_questionFragment_to_endFragment)
        childConfigRenderer.endConfig.value = EndFragment.Config(answer)
    }
}
