package com.goofy.goober.ui

import androidx.compose.Composable
import androidx.compose.emptyContent
import androidx.compose.onCommit
import androidx.ui.animation.animatedFloat
import androidx.ui.core.Alignment
import androidx.ui.core.DrawLayerModifier
import androidx.ui.core.Modifier
import androidx.ui.foundation.Box
import androidx.ui.foundation.ContentGravity
import androidx.ui.foundation.Text
import androidx.ui.layout.Arrangement
import androidx.ui.layout.Column
import androidx.ui.layout.ColumnScope
import androidx.ui.layout.Spacer
import androidx.ui.layout.fillMaxSize
import androidx.ui.layout.preferredHeight
import androidx.ui.layout.wrapContentSize
import androidx.ui.material.Button
import androidx.ui.material.MaterialTheme
import androidx.ui.unit.dp
import com.goofy.goober.model.PizzaAction
import com.goofy.goober.model.Question
import com.goofy.goober.model.relatedAction

@Composable
internal fun OptionButton(
    text: String,
    actionRouter: (PizzaAction) -> Unit,
    action: PizzaAction
) {
    val typography = MaterialTheme.typography
    Button(onClick = { actionRouter(action) }) {
        Text(text = text, style = typography.button)
    }
}

@Composable
internal fun OptionButtonWithMargin(
    text: String,
    actionRouter: (PizzaAction) -> Unit,
    question: Question = Question.firstQuestion
) {
    Column {
        OptionButton(
            text = text,
            actionRouter = actionRouter,
            action = question.relatedAction(choice = text)
        )
        Spacer(Modifier.preferredHeight(10.dp))
    }
}

@Composable
internal fun WrapContentBox(
    modifier: Modifier = Modifier,
    children: @Composable() () -> Unit = emptyContent()
) {
    Box(
        modifier = Modifier.wrapContentSize() + modifier,
        gravity = ContentGravity.Center,
        children = children
    )
}

@Composable
internal fun FadeInCenterContentColumn(
    modifier: Modifier = Modifier.fillMaxSize(),
    children: @Composable() ColumnScope.() -> Unit
) {
    val alpha = animatedFloat(initVal = 0f)
    val layerModifier = object : DrawLayerModifier {
        override val alpha: Float get() = alpha.value
    }
    Column(
        modifier = layerModifier + modifier,
        verticalArrangement = Arrangement.Center,
        horizontalGravity = Alignment.CenterHorizontally,
        children = children
    )
    onCommit {
        alpha.animateTo(1f)
    }
}
