package com.goofy.goober

import android.app.Application
import com.goofy.goober.ui.PizzaRenderer
import com.goofy.goober.ui.viewmodel.ChildConfigRenderer
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

        factory { ApplicationCoroutineScope(global = get()) }

        factory { PizzaRenderer() }

        factory { ChildConfigRenderer() }

        factory { PizzaFlowEventConsumer(applicationCoroutineScope = get()) }

        factory { PizzaUiInitializer() }

        factory { PizzaUi(externalEventConsumer = get<PizzaFlowEventConsumer>().invoke()) }

        viewModel {
            PizzaViewModel(
                pizzaUi = get(),
                pizzaUiInitializer = get(),
                childRenderer = get()
            )
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
