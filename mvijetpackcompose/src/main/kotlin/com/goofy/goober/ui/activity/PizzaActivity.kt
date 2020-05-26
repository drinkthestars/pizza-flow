package com.goofy.goober.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.ui.core.setContent
import com.goofy.goober.ui.PizzaApp
import com.goofy.goober.ui.theme.PizzaAppTheme
import com.goofy.goober.ui.viewmodel.PizzaViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class PizzaActivity : AppCompatActivity() {

    private val viewModel: PizzaViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PizzaAppTheme {
                PizzaApp(
                    state = viewModel.currentState,
                    actionRouter = { viewModel.reduce(action = it) }
                )
            }
        }
    }
}
