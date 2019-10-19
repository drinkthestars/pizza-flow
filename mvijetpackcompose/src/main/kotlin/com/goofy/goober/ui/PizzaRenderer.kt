package com.goofy.goober.ui

import androidx.compose.Composable
import androidx.ui.layout.Center
import com.goofy.goober.model.PizzaAction
import com.goofy.goober.model.PizzaState
import com.goofy.goober.model.Question

class PizzaRenderer {

    @Composable
    fun render(state: PizzaState?, actionRouter: (PizzaAction) -> Unit) {
        if (state == null) return

        when (state) {
            PizzaState.UnInitialized -> unInitialized()
            PizzaState.WelcomeScreen -> welcomeScreen(actionRouter)
            is PizzaState.StillCustomizing -> stillCustomizing(state.currentQuestion, actionRouter)
            is PizzaState.FinishedCustomizing -> finishedCustomizing(state.result)
        }.let {}
    }

    @Composable
    private fun finishedCustomizing(result: String) {
        Center {
            CustomizationEndColumn(result)
        }
    }

    @Composable
    private fun stillCustomizing(question: Question, actionRouter: (PizzaAction) -> Unit) {
        Center {
            QuestionColumn(question, actionRouter)
        }
    }

    @Composable
    private fun welcomeScreen(actionRouter: (PizzaAction) -> Unit) {
        Center {
            WelcomeColumn(actionRouter)
        }
    }

    @Composable
    private fun unInitialized() {
        Center {
            ProgressBarColumn()
        }
    }
}
