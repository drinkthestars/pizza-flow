package com.goofy.goober.ui

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.goofy.goober.model.PizzaAction
import com.goofy.goober.model.PizzaState
import com.goofy.goober.model.Question

@Composable
internal fun PizzaApp(state: PizzaState, actionRouter: (PizzaAction) -> Unit) {
    Surface(color = MaterialTheme.colors.background) {
        when (state) {
            is PizzaState.UnInitialized -> UnInitializedScreen()
            is PizzaState.Welcome -> WelcomeScreen(actionRouter)
            is PizzaState.StillCustomizing -> StillCustomizingScreen(
                state.currentQuestion,
                actionRouter
            )
            is PizzaState.FinishedCustomizing -> FinishedCustomizingScreen(
                state.result,
                actionRouter
            )
        }
    }
}

@Composable
internal fun WelcomeScreen(actionRouter: (PizzaAction) -> Unit) {
    FadeInCenterContentColumn {
        OptionButton(
            text = "Start",
            actionRouter = actionRouter,
            action = PizzaAction.ContinueCustomizing(
                question = Question.firstQuestion,
                previousChoice = null
            )
        )
    }
}

@Composable
internal fun StillCustomizingScreen(
    question: Question,
    actionRouter: (PizzaAction) -> Unit
) {
    val typography = MaterialTheme.typography
    FadeInCenterContentColumn {
        WrapContentBox { Text(text = question.value, style = typography.h5) }
        Spacer(Modifier.preferredHeight(20.dp))
        Column(
            modifier = Modifier.wrapContentSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            question.options.values.forEach {
                OptionButtonWithMargin(it, actionRouter, question)
            }
        }
    }
}

@Composable
internal fun UnInitializedScreen() {
    val typography = MaterialTheme.typography
    FadeInCenterContentColumn {
        WrapContentBox { Text(text = "Loading...", style = typography.h5) }
        Spacer(Modifier.preferredHeight(20.dp))
        WrapContentBox { CircularProgressIndicator() }
    }
}

@Composable
internal fun FinishedCustomizingScreen(
    result: String,
    actionRouter: (PizzaAction) -> Unit
) {
    FadeInCenterContentColumn {
        Text(
            text = result,
            modifier = Modifier.padding(horizontal = 30.dp),
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.preferredHeight(16.dp))
        OptionButton(
            text = "Start Over",
            action = PizzaAction.StartOver,
            actionRouter = actionRouter
        )
    }
}
