package com.goofy.goober

import android.app.Application
import com.goofy.goober.ui.PizzaRenderer
import com.goofy.goober.ui.viewmodel.CurrentPizzaUIState
import com.goofy.goober.ui.viewmodel.PizzaViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class PizzaApplication : Application() {

    private val appModule = module {

        // TODO: Use qualifier
        factory<CoroutineScope> { GlobalScope }

        factory { ApplicationCoroutineScope(globalScope = get()) }

        factory { PizzaRenderer() }

        factory { PizzaUiInitializer() }

        factory { CurrentPizzaUIState() }

        viewModel {
            PizzaViewModel(currentCurrentPizzaState = get())
        }
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@PizzaApplication)
            modules(appModule)
        }
    }
}
