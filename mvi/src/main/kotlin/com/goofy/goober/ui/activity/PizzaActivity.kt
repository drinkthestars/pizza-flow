package com.goofy.goober.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation.findNavController
import com.goofy.goober.R
import com.goofy.goober.ui.PizzaRenderer
import com.goofy.goober.ui.viewmodel.FragmentConfigProvider
import com.goofy.goober.ui.viewmodel.PizzaChildFragmentConfigs
import com.goofy.goober.ui.viewmodel.PizzaViewModel
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class PizzaActivity : AppCompatActivity(), FragmentConfigProvider<PizzaChildFragmentConfigs> {

    private val viewModel: PizzaViewModel by viewModel()
    private val pizzaRenderer: PizzaRenderer by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        pizzaRenderer {
            val observer = createObserver(
                actionRouter = { action -> viewModel.consumeAction(action) },
                screenConfigs = viewModel.screenConfigs,
                navController = findNavController(this@PizzaActivity, R.id.navHostFragment)
            )
            viewModel.state.observe(this@PizzaActivity, observer)
        }
    }

    override fun provideFragmentConfigs(): PizzaChildFragmentConfigs = viewModel.screenConfigs
}
