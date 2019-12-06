package com.goofy.goober.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.ui.core.setContent
import androidx.ui.layout.Container
import androidx.ui.layout.FlexColumn
import androidx.ui.material.MaterialTheme
import com.goofy.goober.ui.PizzaRenderer
import com.goofy.goober.ui.viewmodel.PizzaViewModel
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class PizzaActivity : AppCompatActivity() {

    private val viewModel: PizzaViewModel by viewModel()
    private val currentState get() = viewModel.currentCurrentPizzaState
    private val pizzaRenderer: PizzaRenderer by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                App()
            }
        }
    }

    @Composable
    fun App() {
        FlexColumn {
            expanded(flex = 1F) {
                Container(expanded = true) {
                    pizzaRenderer.render(
                        state = currentState.currentState,
                        routeAction = { currentState.reduce(action = it) }
                    )
                }
            }
        }
    }
}
