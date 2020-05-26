package com.goofy.goober.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.Navigation.findNavController
import com.goofy.goober.R
import com.goofy.goober.ui.PizzaRenderer
import com.goofy.goober.ui.state.FragmentStateProvider
import com.goofy.goober.ui.state.PizzaChildFragmentStates
import com.goofy.goober.ui.viewmodel.PizzaViewModel
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class PizzaActivity : AppCompatActivity(),
    FragmentStateProvider<PizzaChildFragmentStates> {

    private val viewModel: PizzaViewModel by viewModel()
    private val pizzaRenderer: PizzaRenderer by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        viewModel.state.observe(this@PizzaActivity, Observer { pizzaState ->
            pizzaRenderer {
                pizzaState?.render(
                    actionRouter = { action -> viewModel.consumeAction(action) },
                    screenStates = viewModel.pizzaScreenStates,
                    navController = findNavController(this@PizzaActivity, R.id.navHostFragment)
                )
            }
        })
    }

    override fun provideFragmentStates(): PizzaChildFragmentStates {
        return viewModel.pizzaScreenStates
    }
}
