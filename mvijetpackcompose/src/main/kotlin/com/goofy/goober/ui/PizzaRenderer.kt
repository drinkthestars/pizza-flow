package com.goofy.goober.ui

import androidx.compose.Composable
import androidx.compose.unaryPlus
import androidx.ui.core.Text
import androidx.ui.layout.Center
import androidx.ui.layout.Column
import androidx.ui.material.themeTextStyle
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
            is PizzaState.StillCustomizing -> ongoing(state.currentQuestion, actionRouter)
            is PizzaState.FinishedCustomizing -> ended(state.result)
        }.let {}
    }

    @Composable
    private fun ended(result: String) {
        Center {
            Column {
                Text(text = result, style = +themeTextStyle { subtitle1 })
            }
        }
    }

    @Composable
    private fun ongoing(question: Question, actionRouter: (PizzaAction) -> Unit) {
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
