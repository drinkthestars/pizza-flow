package com.goofy.goober.ui

import androidx.compose.Composable
import androidx.compose.unaryPlus
import androidx.ui.core.Text
import androidx.ui.core.dp
import androidx.ui.layout.Center
import androidx.ui.layout.Column
import androidx.ui.layout.HeightSpacer
import androidx.ui.material.Button
import androidx.ui.material.themeTextStyle
import com.goofy.goober.model.PizzaAction
import com.goofy.goober.model.PizzaAction.Ongoing.ProceedNextQuestion
import com.goofy.goober.model.Question

@Composable
private fun OptionButton(
    text: String,
    actionRouter: (PizzaAction) -> Unit,
    question: Question = Question.firstQuestion
) {
    Center {
        Button(
            text = text,
            onClick = { actionRouter(ProceedNextQuestion(previousAnswer = text, question = question)) }
        )
    }
}

@Composable
private fun OptionButtonWithSpace(
    text: String,
    actionRouter: (PizzaAction) -> Unit,
    question: Question = Question.firstQuestion
) {
    Column {
        OptionButton(
            text = text,
            actionRouter = actionRouter,
            question = question
        )
        HeightSpacer(height = 10.dp)
    }
}

@Composable
fun WelcomeColumn(actionRouter: (PizzaAction) -> Unit) {
    Column {
        Column {
            OptionButton(
                text = "Start",
                actionRouter = actionRouter
            )
        }
    }
}

@Composable
fun QuestionColumn(
    question: Question,
    actionRouter: (PizzaAction) -> Unit
) {
    Column {
        Center { Text(text = question.value, style = +themeTextStyle { h5 }) }
        HeightSpacer(height = 20.dp)
        Column {
            question.options.values.forEach {
                OptionButtonWithSpace(it, actionRouter, question)
            }
        }
    }
}
