package com.goofy.goober.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.compose.unaryPlus
import androidx.ui.core.setContent
import androidx.ui.layout.FlexColumn
import androidx.ui.material.MaterialTheme
import com.goofy.goober.observe
import com.goofy.goober.ui.PizzaRenderer
import com.goofy.goober.ui.viewmodel.PizzaViewModel
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class PizzaActivity : AppCompatActivity() {

    private val viewModel: PizzaViewModel by viewModel()
    private val pizzaRenderer: PizzaRenderer by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                App(viewModel)
            }
        }
    }

    @Composable
    fun App(model: PizzaViewModel) {
        FlexColumn {
            expanded(1F) {
                val state = +model.state.observe()
                pizzaRenderer.render(
                    state = state,
                    actionRouter = { viewModel.consumeAction(it) }
                )
            }
        }
    }
}
