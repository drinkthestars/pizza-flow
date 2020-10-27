package com.goofy.goober.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.setContent
import com.goofy.goober.ui.PizzaApp
import com.goofy.goober.ui.theme.PizzaAppTheme
import com.goofy.goober.ui.viewmodel.PizzaViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.viewmodel.ext.android.viewModel

@OptIn(ExperimentalCoroutinesApi::class)
class PizzaActivity : AppCompatActivity() {

    private val viewModel: PizzaViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PizzaAppTheme {
                val state = viewModel.state.collectAsState()

                PizzaApp(
                    state = state.value,
                    onIntent = { viewModel.consumeIntent(it) }
                )
            }
        }
    }
}
