package com.goofy.goober.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation.findNavController
import com.goofy.goober.R
import com.goofy.goober.ui.PizzaRenderer
import com.goofy.goober.ui.state.FragmentStateProvider
import com.goofy.goober.ui.state.PizzaChildFragmentStates
import com.goofy.goober.ui.viewmodel.PizzaViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class PizzaActivity : AppCompatActivity(),
    FragmentStateProvider<PizzaChildFragmentStates> {

    private val viewModel: PizzaViewModel by viewModel()
    private val pizzaRenderer: PizzaRenderer by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.main_activity)

        viewModel.state
            .onEach { pizzaState ->
                pizzaRenderer(
                    pizzaState = pizzaState,
                    actionRouter = { action -> viewModel.consumeAction(action) },
                    screenStates = viewModel.pizzaScreenStates,
                    navController = findNavController(this@PizzaActivity, R.id.navHostFragment)
                )
            }.launchIn(lifecycleScope)
    }

    override fun provideFragmentStates(): PizzaChildFragmentStates {
        return viewModel.pizzaScreenStates
    }
}
