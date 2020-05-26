package com.goofy.goober.ui

import androidx.compose.Composable
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.foundation.Text
import androidx.ui.layout.Arrangement
import androidx.ui.layout.Column
import androidx.ui.layout.Spacer
import androidx.ui.layout.padding
import androidx.ui.layout.preferredHeight
import androidx.ui.layout.wrapContentSize
import androidx.ui.material.CircularProgressIndicator
import androidx.ui.material.MaterialTheme
import androidx.ui.material.Surface
import androidx.ui.text.style.TextAlign
import androidx.ui.unit.dp
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
            horizontalGravity = Alignment.CenterHorizontally
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
