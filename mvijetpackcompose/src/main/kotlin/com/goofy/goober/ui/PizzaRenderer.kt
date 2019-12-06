package com.goofy.goober.ui

import androidx.compose.Composable
import androidx.ui.layout.Center
import androidx.ui.tooling.preview.Preview
import com.goofy.goober.model.PizzaAction
import com.goofy.goober.model.PizzaState
import com.goofy.goober.model.Question

class PizzaRenderer {

    @Composable
    fun render(state: PizzaState, routeAction: (PizzaAction) -> Unit) {
        when (state) {
            PizzaState.UnInitialized -> UnInitialized()
            PizzaState.WelcomeScreen -> WelcomeScreen(routeAction)
            is PizzaState.StillCustomizing -> StillCustomizing(state.currentQuestion, routeAction)
            is PizzaState.FinishedCustomizing -> FinishedCustomizing(state.result, routeAction)
        }.let {}
    }

    @Composable
    private fun FinishedCustomizing(result: String, routeAction: (PizzaAction) -> Unit) {
        Center {
            CustomizationEndColumn(result, routeAction)
        }
    }

    @Composable
    private fun StillCustomizing(question: Question, routeAction: (PizzaAction) -> Unit) {
        Center {
            QuestionColumn(question, routeAction)
        }
    }

    @Composable
    private fun WelcomeScreen(routeAction: (PizzaAction) -> Unit) {
        Center {
            WelcomeColumn(routeAction)
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
