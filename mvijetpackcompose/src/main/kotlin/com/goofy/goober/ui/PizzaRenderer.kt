package com.goofy.goober.ui

import androidx.compose.Composable
import androidx.ui.layout.Center
import androidx.ui.tooling.preview.Preview
import com.goofy.goober.model.PizzaAction
import com.goofy.goober.model.PizzaState
import com.goofy.goober.model.Question

class PizzaRenderer {

    @Composable
    fun render(state: PizzaState, actionRouter: (PizzaAction) -> Unit) {
        when (state) {
            PizzaState.UnInitialized -> UnInitialized()
            PizzaState.WelcomeScreen -> WelcomeScreen(actionRouter)
            is PizzaState.StillCustomizing -> StillCustomizing(state.currentQuestion, actionRouter)
            is PizzaState.FinishedCustomizing -> FinishedCustomizing(state.result, actionRouter)
        }.let {}
    }

    @Composable
    private fun FinishedCustomizing(result: String, actionRouter: (PizzaAction) -> Unit) {
        Center {
            CustomizationEndColumn(result, actionRouter)
        }
    }

    @Composable
    private fun StillCustomizing(question: Question, actionRouter: (PizzaAction) -> Unit) {
        Center {
            QuestionColumn(question, actionRouter)
        }
    }

    @Composable
    private fun WelcomeScreen(actionRouter: (PizzaAction) -> Unit) {
        Center {
            WelcomeColumn(actionRouter)
        }
    }

    @Preview
    @Composable
    private fun UnInitialized() {
        Center {
            ProgressBarColumn()
        }
    }
}
