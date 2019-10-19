package com.goofy.goober.ui

import androidx.animation.AnimationEndReason
import androidx.animation.FastOutLinearInEasing
import androidx.animation.TweenBuilder
import androidx.compose.Composable
import androidx.compose.effectOf
import androidx.compose.onCommit
import androidx.compose.unaryPlus
import androidx.ui.animation.animatedFloat
import androidx.ui.core.Opacity
import androidx.ui.core.Text
import androidx.ui.core.dp
import androidx.ui.layout.Center
import androidx.ui.layout.Column
import androidx.ui.layout.HeightSpacer
import androidx.ui.material.Button
import androidx.ui.material.CircularProgressIndicator
import androidx.ui.material.themeTextStyle
import com.goofy.goober.model.PizzaAction
import com.goofy.goober.model.Question
import com.goofy.goober.model.relatedAction

@Composable
private fun OptionButton(
    text: String,
    actionRouter: (PizzaAction) -> Unit,
    question: Question = Question.firstQuestion
) {
    Center {
        Button(
            text = text,
            onClick = { actionRouter(question.relatedAction(choice = text)) }
        )
    }
}

@Composable
private fun OptionButtonWithMargin(
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
        FadeIn {
            OptionButton(text = "Start", actionRouter = actionRouter)
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
                OptionButtonWithMargin(it, actionRouter, question)
            }
        }
    }
}

@Composable
fun ProgressBarColumn() {
    FadeIn {
        Column {
            Center { Text(text = "Loading...", style = +themeTextStyle { h5 }) }
            HeightSpacer(height = 20.dp)
            Column {
                Center { CircularProgressIndicator() }
            }
        }
    }
}

@Composable
fun CustomizationEndColumn(result: String) {
    Column {
        FadeIn {
            Text(text = result, style = +themeTextStyle { subtitle1 })
        }
    }
}

@Composable
private inline fun FadeIn(crossinline children: @Composable() () -> Unit) {
    Opacity(
        opacity = +animatedOpacity(visible = true),
        children = children
    )
}

private fun animatedOpacity(
    visible: Boolean,
    onAnimationFinish: () -> Unit = {}
) = effectOf<Float> {

    val animatedFloat = +animatedFloat(if (!visible) 1f else 0f)

    +onCommit(visible) {
        animatedFloat.animateTo(
            targetValue = if (visible) 1f else 0f,
            anim = TweenBuilder<Float>().apply {
                duration = 400
                easing = FastOutLinearInEasing
            },
            onEnd = { reason, _ ->
                if (reason == AnimationEndReason.TargetReached) onAnimationFinish()
            }
        )
    }

    animatedFloat.value
}
